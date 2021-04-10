package info.firozansari.domain.interactor

import info.firozansari.domain.User
import io.reactivex.Observable

/**
 * This class is an implementation of [UseCase] that represents a use case for
 * retrieving data related to an specific [User].
 */
class GetUserDetails internal constructor(
    userRepository: UserRepository?, threadExecutor: ThreadExecutor?,
    postExecutionThread: PostExecutionThread?
) : UseCase<User?, GetUserDetails.Params?>(threadExecutor, postExecutionThread) {
    private val userRepository: UserRepository?
    public override fun buildUseCaseObservable(params: Params?): Observable<User?>? {
        return userRepository.user(params.userId)
    }

    class Params private constructor(private val userId: Int) {
        companion object {
            @JvmStatic
            fun forUser(userId: Int): Params? {
                return Params(userId)
            }
        }
    }

    init {
        this.userRepository = userRepository
    }
}