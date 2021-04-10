package info.firozansari.presentation.internal.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import info.firozansari.data.cache.UserCache
import info.firozansari.data.cache.UserCacheImpl
import info.firozansari.data.executor.JobExecutor
import info.firozansari.data.repository.UserDataRepository
import info.firozansari.domain.executor.PostExecutionThread
import info.firozansari.domain.executor.ThreadExecutor
import info.firozansari.domain.repository.UserRepository
import info.firozansari.presentation.AndroidApplication
import info.firozansari.presentation.UIThread
import javax.inject.Singleton

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
class ApplicationModule(private val application: AndroidApplication?) {
    @Provides
    @Singleton
    fun provideApplicationContext(): Context? {
        return application
    }

    @Provides
    @Singleton
    fun provideThreadExecutor(jobExecutor: JobExecutor?): ThreadExecutor? {
        return jobExecutor
    }

    @Provides
    @Singleton
    fun providePostExecutionThread(uiThread: UIThread?): PostExecutionThread? {
        return uiThread
    }

    @Provides
    @Singleton
    fun provideUserCache(userCache: UserCacheImpl?): UserCache? {
        return userCache
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDataRepository: UserDataRepository?): UserRepository? {
        return userDataRepository
    }
}