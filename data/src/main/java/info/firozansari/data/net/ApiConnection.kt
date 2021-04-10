package info.firozansari.data.net

import okhttp3.OkHttpClient
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Callable

/**
 * Api Connection class used to retrieve data from the cloud.
 * Implements [java.util.concurrent.Callable] so when executed asynchronously can
 * return a value.
 */
internal class ApiConnection private constructor(url: String) : Callable<String?> {
    private val url: URL
    private var response: String? = null

    /**
     * Do a request to an api synchronously.
     * It should not be executed in the main thread of the application.
     *
     * @return A string response
     */
    fun requestSyncCall(): String? {
        connectToApi()
        return response
    }

    private fun connectToApi() {
        val okHttpClient: OkHttpClient = createClient()
        val request: Request = Builder()
            .url(url)
            .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
            .get()
            .build()
        try {
            response = okHttpClient.newCall(request).execute().body().string()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun createClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Throws(Exception::class)
    override fun call(): String? {
        return requestSyncCall()
    }

    companion object {
        private const val CONTENT_TYPE_LABEL = "Content-Type"
        private const val CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8"
        @Throws(MalformedURLException::class)
        fun createGET(url: String): ApiConnection {
            return ApiConnection(url)
        }
    }

    init {
        this.url = URL(url)
    }
}