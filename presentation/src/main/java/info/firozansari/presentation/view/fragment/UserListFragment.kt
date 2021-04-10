package info.firozansari.presentation.view.fragment

import android.content.Context
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import info.firozansari.presentation.view.adapter.UsersAdapter

/**
 * Fragment that shows a list of Users.
 */
class UserListFragment : BaseFragment(), UserListView {
    /**
     * Interface for listening user list events.
     */
    interface UserListListener {
        open fun onUserClicked(userModel: UserModel?)
    }

    @kotlin.jvm.JvmField
    @Inject
    var userListPresenter: UserListPresenter? = null

    @kotlin.jvm.JvmField
    @Inject
    var usersAdapter: UsersAdapter? = null

    @BindView(R.id.rv_users)
    var rv_users: RecyclerView? = null

    @BindView(R.id.rl_progress)
    var rl_progress: RelativeLayout? = null

    @BindView(R.id.rl_retry)
    var rl_retry: RelativeLayout? = null

    @BindView(R.id.bt_retry)
    var bt_retry: Button? = null
    private var userListListener: UserListListener? = null
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is UserListListener) {
            userListListener = activity as UserListListener?
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent<UserComponent?>(UserComponent::class.java).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater?, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView: View = inflater.inflate(R.layout.fragment_user_list, container, false)
        ButterKnife.bind(this, fragmentView)
        setupRecyclerView()
        return fragmentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userListPresenter.setView(this)
        if (savedInstanceState == null) {
            loadUserList()
        }
    }

    override fun onResume() {
        super.onResume()
        userListPresenter.resume()
    }

    override fun onPause() {
        super.onPause()
        userListPresenter.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rv_users.setAdapter(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        userListPresenter.destroy()
    }

    override fun onDetach() {
        super.onDetach()
        userListListener = null
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

    override fun renderUserList(userModelCollection: MutableCollection<UserModel?>?) {
        if (userModelCollection != null) {
            usersAdapter.setUsersCollection(userModelCollection)
        }
    }

    override fun viewUser(userModel: UserModel?) {
        if (userListListener != null) {
            userListListener.onUserClicked(userModel)
        }
    }

    override fun showError(message: String?) {
        showToastMessage(message)
    }

    override fun context(): Context? {
        return this.activity.applicationContext
    }

    private fun setupRecyclerView() {
        usersAdapter.setOnItemClickListener(onItemClickListener)
        rv_users.setLayoutManager(UsersLayoutManager(context()))
        rv_users.setAdapter(usersAdapter)
    }

    /**
     * Loads all users.
     */
    private fun loadUserList() {
        userListPresenter.initialize()
    }

    @OnClick(R.id.bt_retry)
    fun onButtonRetryClick() {
        loadUserList()
    }

    private val onItemClickListener: UsersAdapter.OnItemClickListener? =
        UsersAdapter.OnItemClickListener { userModel ->
            if (userListPresenter != null && userModel != null) {
                userListPresenter.onUserClicked(userModel)
            }
        }

    init {
        retainInstance = true
    }
}