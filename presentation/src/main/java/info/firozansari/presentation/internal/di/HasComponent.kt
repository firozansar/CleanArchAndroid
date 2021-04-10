package info.firozansari.presentation.internal.di

/**
 * Interface representing a contract for clients that contains a component for dependency injection.
 */
interface HasComponent<C> {
    open fun getComponent(): C?
}