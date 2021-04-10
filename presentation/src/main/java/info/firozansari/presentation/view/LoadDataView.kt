package info.firozansari.presentation.view

import android.content.Context

/**
 * Interface representing a View that will use to load data.
 */
interface LoadDataView {
    /**
     * Show a view with a progress bar indicating a loading process.
     */
    open fun showLoading()

    /**
     * Hide a loading view.
     */
    open fun hideLoading()

    /**
     * Show a retry view in case of an error when retrieving data.
     */
    open fun showRetry()

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    open fun hideRetry()

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    open fun showError(message: String?)

    /**
     * Get a [android.content.Context].
     */
    open fun context(): Context?
}