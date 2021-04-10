package info.firozansari.data.repository.datasource

import io.reactivex.Observable

/**
 * Interface that represents a data store from where data is retrieved.
 */
interface UserDataStore {
    /**
     * Get an [Observable] which will emit a List of [UserEntity].
     */
    fun userEntityList(): Observable<List<UserEntity?>?>?

    /**
     * Get an [Observable] which will emit a [UserEntity] by its id.
     *
     * @param userId The id to retrieve user data.
     */
    fun userEntityDetails(userId: Int): Observable<UserEntity?>
}