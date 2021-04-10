package info.firozansari.presentation.internal.di.modules

import android.app.Activity
import dagger.Module
import dagger.Provides
import info.firozansari.presentation.internal.di.PerActivity

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module
class ActivityModule(private val activity: Activity?) {
    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    fun activity(): Activity? {
        return activity
    }
}