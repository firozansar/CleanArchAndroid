package info.firozansari.presentation.internal.di.components

import android.app.Activity
import dagger.Component
import info.firozansari.presentation.internal.di.PerActivity
import info.firozansari.presentation.internal.di.modules.ActivityModule

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 * [info.firozansari.presentation.internal.di.PerActivity]
 */
@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
internal interface ActivityComponent {
    //Exposed to sub-graphs.
    open fun activity(): Activity?
}