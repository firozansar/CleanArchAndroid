package info.firozansari.presentation

import android.app.Application
import info.firozansari.presentation.internal.di.components.DaggerApplicationComponent

/**
 * Android Main Application
 */
class AndroidApplication : Application() {
    private var applicationComponent: ApplicationComponent? = null
    override fun onCreate() {
        super.onCreate()
        initializeInjector()
    }

    private fun initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    fun getApplicationComponent(): ApplicationComponent? {
        return applicationComponent
    }
}