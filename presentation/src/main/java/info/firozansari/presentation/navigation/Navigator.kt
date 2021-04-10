package info.firozansari.presentation.navigation

import android.content.Context
import android.content.Intent
import info.firozansari.presentation.view.activity.UserDetailsActivity
import info.firozansari.presentation.view.activity.UserListActivity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class used to navigate through the application.
 */
@Singleton
class Navigator @Inject constructor() {
    /**
     * Goes to the user list screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    fun navigateToUserList(context: Context?) {
        if (context != null) {
            val intentToLaunch: Intent = UserListActivity.Companion.getCallingIntent(context)
            context.startActivity(intentToLaunch)
        }
    }

    /**
     * Goes to the user details screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    fun navigateToUserDetails(context: Context?, userId: Int) {
        if (context != null) {
            val intentToLaunch: Intent =
                UserDetailsActivity.Companion.getCallingIntent(context, userId)
            context.startActivity(intentToLaunch)
        }
    }
}