package info.firozansari.data.cache

import io.reactivex.Observable

/**
 * An interface representing a user Cache.
 */
interface UserCache {
    /**
     * Gets an [Observable] which will emit a [UserEntity].
     *
     * @param userId The user id to retrieve data.
     */
    operator fun get(userId: Int): Observable<UserEntity?>

    /**
     * Puts and element into the cache.
     *
     * @param userEntity Element to insert in the cache.
     */
    fun put(userEntity: UserEntity?)

    /**
     * Checks if an element (User) exists in the cache.
     *
     * @param userId The id used to look for inside the cache.
     * @return true if the element is cached, otherwise false.
     */
    fun isCached(userId: Int): Boolean

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    val isExpired: Boolean

    /**
     * Evict all elements of the cache.
     */
    fun evictAll()
}