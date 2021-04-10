package info.firozansari.domain.repository

import info.firozansari.domain.User
import io.reactivex.Observable

/**
 * Interface that represents a Repository for getting [User] related data.
 */
interface UserRepository {
    /**
     * Get an [Observable] which will emit a List of [User].
     */
    open fun users(): Observable<MutableList<User?>?>?

    /**
     * Get an [Observable] which will emit a [User].
     *
     * @param userId The user id used to retrieve user data.
     */
    open fun user(userId: Int): Observable<User?>?
}