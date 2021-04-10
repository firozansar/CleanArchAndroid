package info.firozansari.domain.interactor

import info.firozansari.domain.User
import io.reactivex.Observable

/**
 * This class is an implementation of [UseCase] that represents a use case for
 * retrieving a collection of all [User].
 */
class GetUserList @Inject internal constructor(
    userRepository: UserRepository?, threadExecutor: ThreadExecutor?,
    postExecutionThread: PostExecutionThread?
) : UseCase<MutableList<User?>?, Void?>(threadExecutor, postExecutionThread) {
    private val userRepository: UserRepository?
    public override fun buildUseCaseObservable(unused: Void?): Observable<MutableList<User?>?>? {
        return userRepository.users()
    }

    init {
        this.userRepository = userRepository
    }
}