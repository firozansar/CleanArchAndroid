package info.firozansari.data.repository.datasource

import io.reactivex.Observable

/**
 * [UserDataStore] implementation based on file system data store.
 */
internal class DiskUserDataStore(userCache: UserCache) : UserDataStore {
    private val userCache: UserCache
    override fun userEntityList(): Observable<List<UserEntity?>?>? {
        //TODO: implement simple cache for storing/retrieving collections of users.
        throw UnsupportedOperationException("Operation is not available!!!")
    }

    override fun userEntityDetails(userId: Int): Observable<UserEntity?> {
        return userCache.get(userId)
    }

    /**
     * Construct a [UserDataStore] based file system data store.
     *
     * @param userCache A [UserCache] to cache data retrieved from the api.
     */
    init {
        this.userCache = userCache
    }
}