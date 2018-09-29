
package info.firozansari.presentation.internal.di.components;

import info.firozansari.presentation.internal.di.PerActivity;
import info.firozansari.presentation.internal.di.modules.ActivityModule;
import info.firozansari.presentation.internal.di.modules.UserModule;
import info.firozansari.presentation.view.fragment.UserDetailsFragment;
import info.firozansari.presentation.view.fragment.UserListFragment;
import dagger.Component;

/**
 * A scope {@link info.firozansari.presentation.internal.di.PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {
  void inject(UserListFragment userListFragment);
  void inject(UserDetailsFragment userDetailsFragment);
}
