package info.firozansari.domain.exception

/**
 * Interface to represent a wrapper around an [java.lang.Exception] to manage errors.
 */
interface ErrorBundle {
    open fun getException(): Exception?
    open fun getErrorMessage(): String?
}