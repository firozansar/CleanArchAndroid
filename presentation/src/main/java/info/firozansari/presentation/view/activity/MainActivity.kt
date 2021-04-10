package info.firozansari.presentation.view.activity

import android.widget.Button
import butterknife.BindView

/**
 * Main application screen. This is the app entry point.
 */
class MainActivity : BaseActivity() {
    @BindView(R.id.btn_LoadData)
    var btn_LoadData: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
    }

    /**
     * Goes to the user list screen.
     */
    @OnClick(R.id.btn_LoadData)
    fun navigateToUserList() {
        navigator.navigateToUserList(this)
    }
}