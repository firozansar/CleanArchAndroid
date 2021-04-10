package info.firozansari.data.net

import io.reactivex.Observable

/**
 * RestApi for retrieving data from the network.
 */
interface RestApi {
    /**
     * Retrieves an [Observable] which will emit a List of [UserEntity].
     */
    fun userEntityList(): Observable<List<UserEntity?>?>

    /**
     * Retrieves an [Observable] which will emit a [UserEntity].
     *
     * @param userId The user id used to get user data.
     */
    fun userEntityById(userId: Int): Observable<UserEntity?>

    companion object {
        const val API_BASE_URL =
            "https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/"

        /** Api url for getting all users  */
        const val API_URL_GET_USER_LIST = API_BASE_URL + "users.json"

        /** Api url for getting a user profile: Remember to concatenate id + 'json'  */
        const val API_URL_GET_USER_DETAILS = API_BASE_URL + "user_"
    }
}