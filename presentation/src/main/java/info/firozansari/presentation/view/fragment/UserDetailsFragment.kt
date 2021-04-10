package info.firozansari.presentation.view.fragment

import android.content.Context
import android.view.View
import android.widget.Button
import androidx.core.util.Preconditions
import butterknife.BindView

/**
 * Fragment that shows details of a certain user.
 */
class UserDetailsFragment : BaseFragment(), UserDetailsView {
    @kotlin.jvm.JvmField
    @Inject
    var userDetailsPresenter: UserDetailsPresenter? = null

    @BindView(R.id.iv_cover)
    var iv_cover: AutoLoadImageView? = null

    @BindView(R.id.tv_fullname)
    var tv_fullname: TextView? = null

    @BindView(R.id.tv_email)
    var tv_email: TextView? = null

    @BindView(R.id.tv_followers)
    var tv_followers: TextView? = null

    @BindView(R.id.tv_description)
    var tv_description: TextView? = null

    @BindView(R.id.rl_progress)
    var rl_progress: RelativeLayout? = null

    @BindView(R.id.rl_retry)
    var rl_retry: RelativeLayout? = null

    @BindView(R.id.bt_retry)
    var bt_retry: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent<UserComponent?>(UserComponent::class.java).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater?, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView: View = inflater.inflate(R.layout.fragment_user_details, container, false)
        ButterKnife.bind(this, fragmentView)
        return fragmentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDetailsPresenter.setView(this)
        if (savedInstanceState == null) {
            loadUserDetails()
        }
    }

    override fun onResume() {
        super.onResume()
        userDetailsPresenter.resume()
    }

    override fun onPause() {
        super.onPause()
        userDetailsPresenter.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        userDetailsPresenter.destroy()
    }

    override fun renderUser(user: UserModel?) {
        if (user != null) {
            iv_cover.setImageUrl(user.getCoverUrl())
            tv_fullname.setText(user.getFullName())
            tv_email.setText(user.getEmail())
            tv_followers.setText(user.getFollowers().toString())
            tv_description.setText(user.getDescription())
        }
    }

    override fun showLoading() {
        rl_progress.setVisibility(View.VISIBLE)
        this.activity.setProgressBarIndeterminateVisibility(true)
    }

    override fun hideLoading() {
        rl_progress.setVisibility(View.GONE)
        this.activity.setProgressBarIndeterminateVisibility(false)
    }

    override fun showRetry() {
        rl_retry.setVisibility(View.VISIBLE)
    }

    override fun hideRetry() {
        rl_retry.setVisibility(View.GONE)
    }

    override fun showError(message: String?) {
        showToastMessage(message)
    }

    override fun context(): Context? {
        return activity.applicationContext
    }

    /**
     * Load user details.
     */
    private fun loadUserDetails() {
        if (userDetailsPresenter != null) {
            userDetailsPresenter.initialize(currentUserId())
        }
    }

    /**
     * Get current user id from fragments arguments.
     */
    @SuppressLint("RestrictedApi")
    private fun currentUserId(): Int {
        val arguments: Bundle? = arguments
        Preconditions.checkNotNull<Bundle?>(arguments, "Fragment arguments cannot be null")
        return arguments.getInt(PARAM_USER_ID)
    }

    @OnClick(R.id.bt_retry)
    fun onButtonRetryClick() {
        loadUserDetails()
    }

    companion object {
        private val PARAM_USER_ID: String? = "param_user_id"
        fun forUser(userId: Int): UserDetailsFragment? {
            val userDetailsFragment = UserDetailsFragment()
            val arguments = Bundle()
            arguments.putInt(PARAM_USER_ID, userId)
            userDetailsFragment.arguments = arguments
            return userDetailsFragment
        }
    }

    init {
        retainInstance = true
    }
}