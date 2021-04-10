package info.firozansari.domain.interactor

import io.reactivex.observers.DisposableObserver

/**
 * Default [DisposableObserver] base class to be used whenever you want default error handling.
 */
open class DefaultObserver<T> : DisposableObserver<T?>() {
    open fun onNext(t: T?) {
        // no-op by default.
    }

    open fun onComplete() {
        // no-op by default.
    }

    open fun onError(exception: Throwable?) {
        // no-op by default.
    }
}