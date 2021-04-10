package info.firozansari.presentation.exception

import android.content.Context
import info.firozansari.data.exception.NetworkConnectionException
import info.firozansari.data.exception.UserNotFoundException
import info.firozansari.presentation.R

/**
 * Factory used to create error messages from an Exception as a condition.
 */
object ErrorMessageFactory {
    /**
     * Creates a String representing an error message.
     *
     * @param context Context needed to retrieve string resources.
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return [String] an error message.
     */
    fun create(context: Context?, exception: Exception?): String? {
        var message = context.getString(R.string.exception_message_generic)
        if (exception is NetworkConnectionException) {
            message = context.getString(R.string.exception_message_no_connection)
        } else if (exception is UserNotFoundException) {
            message = context.getString(R.string.exception_message_user_not_found)
        }
        return message
    }
}