package info.firozansari.data.entity.mapper

import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * Class used to transform from Strings representing json to valid objects.
 */
class UserEntityJsonMapper @Inject constructor() {
    private val gson: Gson

    /**
     * Transform from valid json string to [UserEntity].
     *
     * @param userJsonResponse A json representing a user profile.
     * @return [UserEntity].
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    @Throws(JsonSyntaxException::class)
    fun transformUserEntity(userJsonResponse: String?): UserEntity {
        val userEntityType: Type = object : TypeToken<UserEntity?>() {}.getType()
        return gson.fromJson(userJsonResponse, userEntityType)
    }

    /**
     * Transform from valid json string to List of [UserEntity].
     *
     * @param userListJsonResponse A json representing a collection of users.
     * @return List of [UserEntity].
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    @Throws(JsonSyntaxException::class)
    fun transformUserEntityCollection(userListJsonResponse: String?): List<UserEntity> {
        val listOfUserEntityType: Type = object : TypeToken<List<UserEntity?>?>() {}.getType()
        return gson.fromJson(userListJsonResponse, listOfUserEntityType)
    }

    init {
        gson = Gson()
    }
}