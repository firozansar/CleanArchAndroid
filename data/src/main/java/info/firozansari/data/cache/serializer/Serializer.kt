package info.firozansari.data.cache.serializer

import com.google.gson.Gson

/**
 * Json Serializer/Deserializer.
 */
@Singleton
class Serializer @Inject internal constructor() {
    private val gson: Gson = Gson()

    /**
     * Serialize an object to Json.
     *
     * @param object to serialize.
     */
    fun serialize(`object`: Any?, clazz: Class<*>?): String {
        return gson.toJson(`object`, clazz)
    }

    /**
     * Deserialize a json representation of an object.
     *
     * @param string A json string to deserialize.
     */
    fun <T> deserialize(string: String?, clazz: Class<T>?): T {
        return gson.fromJson(string, clazz)
    }
}