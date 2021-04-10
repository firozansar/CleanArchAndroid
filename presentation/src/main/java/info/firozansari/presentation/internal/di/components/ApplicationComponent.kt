package info.firozansari.presentation.internal.di.components

import android.content.Context
import dagger.Component
import info.firozansari.domain.executor.PostExecutionThread
import info.firozansari.domain.executor.ThreadExecutor
import info.firozansari.domain.repository.UserRepository
import info.firozansari.presentation.internal.di.modules.ApplicationModule
import info.firozansari.presentation.view.activity.BaseActivity

/**
 * A component whose lifetime is the life of the application.
 */
// Constraints this component to one-per-application or unscoped bindings.
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    open fun inject(baseActivity: BaseActivity?)

    //Exposed to sub-graphs.
    open fun context(): Context?
    open fun threadExecutor(): ThreadExecutor?
    open fun postExecutionThread(): PostExecutionThread?
    open fun userRepository(): UserRepository?
}