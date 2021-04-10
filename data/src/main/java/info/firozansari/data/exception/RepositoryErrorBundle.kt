package info.firozansari.data.exception

import info.firozansari.domain.exception.ErrorBundle

/**
 * Wrapper around Exceptions used to manage errors in the repository.
 */
internal class RepositoryErrorBundle(val exception: Exception?) : ErrorBundle {
    val errorMessage: String?
        get() {
            var message: String? = ""
            if (exception != null) {
                message = exception.message
            }
            return message
        }
}