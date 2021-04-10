package info.firozansari.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import info.firozansari.presentation.R
import info.firozansari.presentation.internal.di.HasComponent
import info.firozansari.presentation.internal.di.components.UserComponent
import info.firozansari.presentation.model.UserModel
import info.firozansari.presentation.view.fragment.UserListFragment
import info.firozansari.presentation.view.fragment.UserListFragment.UserListListener

/**
 * Activity that shows a list of Users.
 */
class UserListActivity : BaseActivity(), HasComponent<UserComponent?>, UserListListener {
    private var userComponent: UserComponent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)
        setContentView(R.layout.activity_layout)
        initializeInjector()
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, UserListFragment())
        }
    }

    private fun initializeInjector() {
        userComponent = DaggerUserComponent.builder()
            .applicationComponent(applicationComponent)
            .activityModule(activityModule)
            .build()
    }

    override fun getComponent(): UserComponent? {
        return userComponent
    }

    override fun onUserClicked(userModel: UserModel?) {
        navigator.navigateToUserDetails(this, userModel.getUserId())
    }

    companion object {
        fun getCallingIntent(context: Context?): Intent? {
            return Intent(context, UserListActivity::class.java)
        }
    }
}