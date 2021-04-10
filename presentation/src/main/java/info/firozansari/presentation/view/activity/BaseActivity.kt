package info.firozansari.presentation.view.activity

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import info.firozansari.presentation.AndroidApplication
import info.firozansari.presentation.internal.di.components.ApplicationComponent
import info.firozansari.presentation.internal.di.modules.ActivityModule
import info.firozansari.presentation.navigation.Navigator
import javax.inject.Inject

/**
 * Base [android.app.Activity] class for every Activity in this application.
 */
abstract class BaseActivity : Activity() {
    @kotlin.jvm.JvmField
    @Inject
    var navigator: Navigator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApplicationComponent().inject(this)
    }

    /**
     * Adds a [Fragment] to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected fun addFragment(containerViewId: Int, fragment: Fragment?) {
        val fragmentTransaction = this.fragmentManager.beginTransaction()
        fragmentTransaction.add(containerViewId, fragment)
        fragmentTransaction.commit()
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return [com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent]
     */
    protected fun getApplicationComponent(): ApplicationComponent? {
        return (application as AndroidApplication).applicationComponent
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return [com.fernandocejas.android10.sample.presentation.internal.di.modules.ActivityModule]
     */
    protected fun getActivityModule(): ActivityModule? {
        return ActivityModule(this)
    }
}