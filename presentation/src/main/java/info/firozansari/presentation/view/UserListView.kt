package info.firozansari.presentation.view

import info.firozansari.presentation.model.UserModel

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of [UserModel].
 */
interface UserListView : LoadDataView {
    /**
     * Render a user list in the UI.
     *
     * @param userModelCollection The collection of [UserModel] that will be shown.
     */
    open fun renderUserList(userModelCollection: MutableCollection<UserModel?>?)

    /**
     * View a [UserModel] profile/details.
     *
     * @param userModel The user that will be shown.
     */
    open fun viewUser(userModel: UserModel?)
}