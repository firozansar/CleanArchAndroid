
package info.firozansari.presentation.internal.di.modules;

import android.content.Context;
import info.firozansari.data.cache.UserCache;
import info.firozansari.data.cache.UserCacheImpl;
import info.firozansari.data.executor.JobExecutor;
import info.firozansari.data.repository.UserDataRepository;
import info.firozansari.domain.executor.PostExecutionThread;
import info.firozansari.domain.executor.ThreadExecutor;
import info.firozansari.domain.repository.UserRepository;
import info.firozansari.presentation.AndroidApplication;
import info.firozansari.presentation.UIThread;
import info.firozansari.presentation.navigation.Navigator;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton UserCache provideUserCache(UserCacheImpl userCache) {
    return userCache;
  }

  @Provides @Singleton UserRepository provideUserRepository(UserDataRepository userDataRepository) {
    return userDataRepository;
  }
}
