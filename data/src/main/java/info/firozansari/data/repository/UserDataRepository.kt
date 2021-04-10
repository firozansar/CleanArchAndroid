package info.firozansari.data.repository

import info.firozansari.domain.User

/**
 * [UserRepository] for retrieving user data.
 */
@Singleton
class UserDataRepository @Inject internal constructor(
    dataStoreFactory: UserDataStoreFactory,
    userEntityDataMapper: UserEntityDataMapper
) : UserRepository {
    private val userDataStoreFactory: UserDataStoreFactory
    private val userEntityDataMapper: UserEntityDataMapper
    fun users(): Observable<List<User>> {
        //we always get all users from the cloud
        val userDataStore: UserDataStore = userDataStoreFactory.createCloudDataStore()
        return userDataStore.userEntityList().map(userEntityDataMapper::transform)
    }

    fun user(userId: Int): Observable<User> {
        val userDataStore: UserDataStore = userDataStoreFactory.create(userId)
        return userDataStore.userEntityDetails(userId).map(userEntityDataMapper::transform)
    }

    /**
     * Constructs a [UserRepository].
     *
     * @param dataStoreFactory A factory to construct different data source implementations.
     * @param userEntityDataMapper [UserEntityDataMapper].
     */
    init {
        userDataStoreFactory = dataStoreFactory
        this.userEntityDataMapper = userEntityDataMapper
    }
}