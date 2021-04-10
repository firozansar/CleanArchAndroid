package info.firozansari.data.net

import android.content.Context
import io.reactivex.Observable
import java.net.MalformedURLException

/**
 * [RestApi] implementation for retrieving data from the network.
 */
class RestApiImpl(context: Context?, userEntityJsonMapper: UserEntityJsonMapper?) : RestApi {
    private val context: Context
    private val userEntityJsonMapper: UserEntityJsonMapper?
    override fun userEntityList(): Observable<List<UserEntity?>?> {
        return Observable.create { emitter ->
            if (isThereInternetConnection) {
                try {
                    val responseUserEntities = userEntitiesFromApi
                    if (responseUserEntities != null) {
                        emitter.onNext(
                            userEntityJsonMapper.transformUserEntityCollection(
                                responseUserEntities
                            )
                        )
                        emitter.onComplete()
                    } else {
                        emitter.onError(NetworkConnectionException())
                    }
                } catch (e: Exception) {
                    emitter.onError(NetworkConnectionException(e.cause))
                }
            } else {
                emitter.onError(NetworkConnectionException())
            }
        }
    }

    override fun userEntityById(userId: Int): Observable<UserEntity?> {
        return Observable.create { emitter ->
            if (isThereInternetConnection) {
                try {
                    val responseUserDetails = getUserDetailsFromApi(userId)
                    if (responseUserDetails != null) {
                        emitter.onNext(userEntityJsonMapper.transformUserEntity(responseUserDetails))
                        emitter.onComplete()
                    } else {
                        emitter.onError(NetworkConnectionException())
                    }
                } catch (e: Exception) {
                    emitter.onError(NetworkConnectionException(e.cause))
                }
            } else {
                emitter.onError(NetworkConnectionException())
            }
        }
    }

    @get:Throws(MalformedURLException::class)
    private val userEntitiesFromApi: String?
        private get() = ApiConnection.Companion.createGET(RestApi.Companion.API_URL_GET_USER_LIST)
            .requestSyncCall()

    @Throws(MalformedURLException::class)
    private fun getUserDetailsFromApi(userId: Int): String? {
        val apiUrl: String = RestApi.Companion.API_URL_GET_USER_DETAILS + userId + ".json"
        return ApiConnection.Companion.createGET(apiUrl).requestSyncCall()
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private val isThereInternetConnection: Boolean
        private get() {
            val isConnected: Boolean
            val connectivityManager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo: NetworkInfo = connectivityManager.getActiveNetworkInfo()
            isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting()
            return isConnected
        }

    /**
     * Constructor of the class
     *
     * @param context [android.content.Context].
     * @param userEntityJsonMapper [UserEntityJsonMapper].
     */
    init {
        require(!(context == null || userEntityJsonMapper == null)) { "The constructor parameters cannot be null!!!" }
        this.context = context.applicationContext
        this.userEntityJsonMapper = userEntityJsonMapper
    }
}