package info.firozansari.data.repository.datasource

import io.reactivex.Observable

/**
 * [UserDataStore] implementation based on connections to the api (Cloud).
 */
internal class CloudUserDataStore(restApi: RestApi, userCache: UserCache) : UserDataStore {
    private val restApi: RestApi
    private val userCache: UserCache
    override fun userEntityList(): Observable<List<UserEntity?>?>? {
        return restApi.userEntityList()
    }

    override fun userEntityDetails(userId: Int): Observable<UserEntity?> {
        return restApi.userEntityById(userId)
            .doOnNext { userEntity: UserEntity? -> userCache.put(userEntity) }
    }

    /**
     * Construct a [UserDataStore] based on connections to the api (Cloud).
     *
     * @param restApi The [RestApi] implementation to use.
     * @param userCache A [UserCache] to cache data retrieved from the api.
     */
    init {
        this.restApi = restApi
        this.userCache = userCache
    }
}