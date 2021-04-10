package info.firozansari.presentation.internal.di.components

import dagger.Component
import info.firozansari.presentation.internal.di.PerActivity
import info.firozansari.presentation.internal.di.modules.ActivityModule
import info.firozansari.presentation.internal.di.modules.UserModule
import info.firozansari.presentation.view.fragment.UserDetailsFragment
import info.firozansari.presentation.view.fragment.UserListFragment

/**
 * A scope [info.firozansari.presentation.internal.di.PerActivity] component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class, UserModule::class]
)
interface UserComponent : ActivityComponent {
    open fun inject(userListFragment: UserListFragment?)
    open fun inject(userDetailsFragment: UserDetailsFragment?)
}