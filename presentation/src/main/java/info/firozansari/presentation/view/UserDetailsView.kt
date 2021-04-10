package info.firozansari.presentation.view

import info.firozansari.presentation.model.UserModel

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a user profile.
 */
interface UserDetailsView : LoadDataView {
    /**
     * Render a user in the UI.
     *
     * @param user The [UserModel] that will be shown.
     */
    open fun renderUser(user: UserModel?)
}