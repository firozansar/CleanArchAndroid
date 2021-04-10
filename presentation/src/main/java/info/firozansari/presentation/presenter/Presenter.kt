package info.firozansari.presentation.presenter

/**
 * Interface representing a Presenter in a model view presenter (MVP) pattern.
 */
interface Presenter {
    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    open fun resume()

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    open fun pause()

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    open fun destroy()
}