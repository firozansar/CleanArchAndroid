package info.firozansari.domain.exception

/**
 * Wrapper around Exceptions used to manage default errors.
 */
class DefaultErrorBundle(private val exception: Exception?) : ErrorBundle {
    override fun getException(): Exception? {
        return exception
    }

    override fun getErrorMessage(): String? {
        return if (exception != null) exception.message else DEFAULT_ERROR_MSG
    }

    companion object {
        private val DEFAULT_ERROR_MSG: String? = "Unknown error"
    }
}