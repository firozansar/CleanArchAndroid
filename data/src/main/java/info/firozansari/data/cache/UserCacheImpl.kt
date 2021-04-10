package info.firozansari.data.cache

import android.content.Context
import info.firozansari.data.cache.serializer.Serializer
import info.firozansari.domain.executor.ThreadExecutor
import java.io.File

/**
 * [UserCache] implementation.
 */
@Singleton
class UserCacheImpl @Inject internal constructor(
    context: Context?, serializer: Serializer?,
    fileManager: FileManager?, executor: ThreadExecutor?
) : UserCache {
    private val context: Context
    private val cacheDir: File
    private val serializer: Serializer
    private val fileManager: FileManager
    private val threadExecutor: ThreadExecutor?
    override fun get(userId: Int): Observable<UserEntity?> {
        return Observable.create { emitter ->
            val userEntityFile = buildFile(userId)
            val fileContent = fileManager.readFileContent(userEntityFile)
            val userEntity: UserEntity =
                serializer.deserialize<UserEntity>(fileContent, UserEntity::class.java)
            if (userEntity != null) {
                emitter.onNext(userEntity)
                emitter.onComplete()
            } else {
                emitter.onError(UserNotFoundException())
            }
        }
    }

    override fun put(userEntity: UserEntity?) {
        if (userEntity != null) {
            val userEntityFile = buildFile(userEntity.getUserId())
            if (!isCached(userEntity.getUserId())) {
                val jsonString = serializer.serialize(userEntity, UserEntity::class.java)
                executeAsynchronously(CacheWriter(fileManager, userEntityFile, jsonString))
                setLastCacheUpdateTimeMillis()
            }
        }
    }

    override fun isCached(userId: Int): Boolean {
        val userEntityFile = buildFile(userId)
        return fileManager.exists(userEntityFile)
    }

    override val isExpired: Boolean
        get() {
            val currentTime = System.currentTimeMillis()
            val lastUpdateTime = lastCacheUpdateTimeMillis
            val expired = currentTime - lastUpdateTime > EXPIRATION_TIME
            if (expired) {
                evictAll()
            }
            return expired
        }

    override fun evictAll() {
        executeAsynchronously(CacheEvictor(fileManager, cacheDir))
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param userId The id user to build the file.
     * @return A valid file.
     */
    private fun buildFile(userId: Int): File {
        val fileNameBuilder = StringBuilder()
        fileNameBuilder.append(cacheDir.path)
        fileNameBuilder.append(File.separator)
        fileNameBuilder.append(DEFAULT_FILE_NAME)
        fileNameBuilder.append(userId)
        return File(fileNameBuilder.toString())
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private fun setLastCacheUpdateTimeMillis() {
        val currentMillis = System.currentTimeMillis()
        fileManager.writeToPreferences(
            context, SETTINGS_FILE_NAME,
            SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis
        )
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private val lastCacheUpdateTimeMillis: Long
        private get() = fileManager.getFromPreferences(
            context, SETTINGS_FILE_NAME,
            SETTINGS_KEY_LAST_CACHE_UPDATE
        )

    /**
     * Executes a [Runnable] in another Thread.
     *
     * @param runnable [Runnable] to execute
     */
    private fun executeAsynchronously(runnable: Runnable) {
        threadExecutor.execute(runnable)
    }

    /**
     * [Runnable] class for writing to disk.
     */
    private class CacheWriter internal constructor(
        private val fileManager: FileManager,
        private val fileToWrite: File,
        private val fileContent: String?
    ) : Runnable {
        override fun run() {
            fileManager.writeToFile(fileToWrite, fileContent)
        }
    }

    /**
     * [Runnable] class for evicting all the cached files
     */
    private class CacheEvictor internal constructor(
        private val fileManager: FileManager,
        private val cacheDir: File
    ) : Runnable {
        override fun run() {
            fileManager.clearDirectory(cacheDir)
        }
    }

    companion object {
        private const val SETTINGS_FILE_NAME = "com.fernandocejas.android10.SETTINGS"
        private const val SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update"
        private const val DEFAULT_FILE_NAME = "user_"
        private const val EXPIRATION_TIME = (60 * 10 * 1000).toLong()
    }

    /**
     * Constructor of the class [UserCacheImpl].
     *
     * @param context A
     * @param serializer [Serializer] for object serialization.
     * @param fileManager [FileManager] for saving serialized objects to the file system.
     */
    init {
        require(!(context == null || serializer == null || fileManager == null || executor == null)) { "Invalid null parameter" }
        this.context = context.applicationContext
        cacheDir = this.context.cacheDir
        this.serializer = serializer
        this.fileManager = fileManager
        threadExecutor = executor
    }
}