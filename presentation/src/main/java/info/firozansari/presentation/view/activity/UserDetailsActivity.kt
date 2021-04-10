package info.firozansari.presentation.view.activity

import android.content.Context
import android.view.Window
import info.firozansari.presentation.internal.di.components.DaggerUserComponent

/**
 * Activity that shows details of a certain user.
 */
class UserDetailsActivity : BaseActivity(), HasComponent<UserComponent?> {
    private var userId = 0
    private var userComponent: UserComponent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)
        setContentView(R.layout.activity_layout)
        initializeActivity(savedInstanceState)
        initializeInjector()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_USER_ID, userId)
        }
        super.onSaveInstanceState(outState)
    }

    /**
     * Initializes this activity.
     */
    private fun initializeActivity(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            userId = intent.getIntExtra(INTENT_EXTRA_PARAM_USER_ID, -1)
            addFragment(R.id.fragmentContainer, UserDetailsFragment.Companion.forUser(userId))
        } else {
            userId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_USER_ID)
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

    companion object {
        private val INTENT_EXTRA_PARAM_USER_ID: String? = "org.android10.INTENT_PARAM_USER_ID"
        private val INSTANCE_STATE_PARAM_USER_ID: String? = "org.android10.STATE_PARAM_USER_ID"
        fun getCallingIntent(context: Context?, userId: Int): Intent? {
            val callingIntent = Intent(context, UserDetailsActivity::class.java)
            callingIntent.putExtra(INTENT_EXTRA_PARAM_USER_ID, userId)
            return callingIntent
        }
    }
}