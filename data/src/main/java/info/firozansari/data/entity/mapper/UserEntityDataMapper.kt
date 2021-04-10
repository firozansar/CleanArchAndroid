package info.firozansari.data.entity.mapper

import java.util.ArrayList
import javax.inject.Inject

/**
 * Mapper class used to transform [UserEntity] (in the data layer) to [User] in the
 * domain layer.
 */
@Singleton
class UserEntityDataMapper @Inject internal constructor() {
    /**
     * Transform a [UserEntity] into an [User].
     *
     * @param userEntity Object to be transformed.
     * @return [User] if valid [UserEntity] otherwise null.
     */
    fun transform(userEntity: UserEntity?): User? {
        var user: User? = null
        if (userEntity != null) {
            user = User(userEntity.getUserId())
            user.setCoverUrl(userEntity.getCoverUrl())
            user.setFullName(userEntity.getFullname())
            user.setDescription(userEntity.getDescription())
            user.setFollowers(userEntity.getFollowers())
            user.setEmail(userEntity.getEmail())
        }
        return user
    }

    /**
     * Transform a List of [UserEntity] into a Collection of [User].
     *
     * @param userEntityCollection Object Collection to be transformed.
     * @return [User] if valid [UserEntity] otherwise null.
     */
    fun transform(userEntityCollection: Collection<UserEntity?>): List<User> {
        val userList: MutableList<User> = ArrayList<User>(20)
        for (userEntity in userEntityCollection) {
            val user: User = transform(userEntity)
            if (user != null) {
                userList.add(user)
            }
        }
        return userList
    }
}