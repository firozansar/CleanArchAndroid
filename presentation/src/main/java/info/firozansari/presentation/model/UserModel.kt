package info.firozansari.presentation.model

/**
 * Class that represents a user in the presentation layer.
 */
class UserModel(private val userId: Int) {
    private var coverUrl: String? = null
    private var fullName: String? = null
    private var email: String? = null
    private var description: String? = null
    private var followers = 0
    fun getUserId(): Int {
        return userId
    }

    fun getCoverUrl(): String? {
        return coverUrl
    }

    fun setCoverUrl(coverUrl: String?) {
        this.coverUrl = coverUrl
    }

    fun getFullName(): String? {
        return fullName
    }

    fun setFullName(fullName: String?) {
        this.fullName = fullName
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getFollowers(): Int {
        return followers
    }

    fun setFollowers(followers: Int) {
        this.followers = followers
    }
}