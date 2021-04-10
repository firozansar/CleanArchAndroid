package info.firozansari.data.repository.datasource

import android.content.Context
import javax.inject.Inject

/**
 * Factory that creates different implementations of [UserDataStore].
 */
@Singleton
class UserDataStoreFactory @Inject internal constructor(context: Context, userCache: UserCache) {
    private val context: Context
    private val userCache: UserCache

    /**
     * Create [UserDataStore] from a user id.
     */
    fun create(userId: Int): UserDataStore {
        val userDataStore: UserDataStore
        userDataStore = if (!userCache.isExpired() && userCache.isCached(userId)) {
            DiskUserDataStore(userCache)
        } else {
            createCloudDataStore()
        }
        return userDataStore
    }

    /**
     * Create [UserDataStore] to retrieve data from the Cloud.
     */
    fun createCloudDataStore(): UserDataStore {
        val userEntityJsonMapper = UserEntityJsonMapper()
        val restApi: RestApi = RestApiImpl(context, userEntityJsonMapper)
        return CloudUserDataStore(restApi, userCache)
    }

    init {
        this.context = context.applicationContext
        this.userCache = userCache
    }
}