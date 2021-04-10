package info.firozansari.presentation.mapper

import info.firozansari.domain.User
import info.firozansari.presentation.internal.di.PerActivity
import info.firozansari.presentation.model.UserModel
import java.util.ArrayList
import javax.inject.Inject

/**
 * Mapper class used to transform [User] (in the domain layer) to [UserModel] in the
 * presentation layer.
 */
@PerActivity
class UserModelDataMapper @Inject constructor() {
    /**
     * Transform a [User] into an [UserModel].
     *
     * @param user Object to be transformed.
     * @return [UserModel].
     */
    fun transform(user: User?): UserModel? {
        requireNotNull(user) { "Cannot transform a null value" }
        val userModel = UserModel(user.getUserId())
        userModel.coverUrl = user.getCoverUrl()
        userModel.fullName = user.getFullName()
        userModel.email = user.getEmail()
        userModel.description = user.getDescription()
        userModel.followers = user.getFollowers()
        return userModel
    }

    /**
     * Transform a Collection of [User] into a Collection of [UserModel].
     *
     * @param usersCollection Objects to be transformed.
     * @return List of [UserModel].
     */
    fun transform(usersCollection: MutableCollection<User?>?): MutableCollection<UserModel?>? {
        val userModelsCollection: MutableCollection<UserModel?>?
        if (usersCollection != null && !usersCollection.isEmpty()) {
            userModelsCollection = ArrayList()
            for (user in usersCollection) {
                userModelsCollection.add(transform(user))
            }
        } else {
            userModelsCollection = emptyList()
        }
        return userModelsCollection
    }
}