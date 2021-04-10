package info.firozansari.data.entity

import com.google.gson.annotations.SerializedName

/**
 * User Entity used in the data layer.
 */
class UserEntity {
    @SerializedName("id")
    var userId = 0

    @SerializedName("cover_url")
    val coverUrl: String? = null

    @SerializedName("full_name")
    var fullname: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("followers")
    val followers = 0

    @SerializedName("email")
    val email: String? = null
}