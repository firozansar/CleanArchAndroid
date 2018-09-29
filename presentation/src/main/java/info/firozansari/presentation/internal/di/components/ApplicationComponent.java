
package info.firozansari.presentation.internal.di.components;

import android.content.Context;
import info.firozansari.domain.executor.PostExecutionThread;
import info.firozansari.domain.executor.ThreadExecutor;
import info.firozansari.domain.repository.UserRepository;
import info.firozansari.presentation.internal.di.modules.ApplicationModule;
import info.firozansari.presentation.view.activity.BaseActivity;
import dagger.Component;
import javax.inject.Singleton;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);

  //Exposed to sub-graphs.
  Context context();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();
  UserRepository userRepository();
}
