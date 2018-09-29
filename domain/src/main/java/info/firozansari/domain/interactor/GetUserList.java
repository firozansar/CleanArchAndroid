
package info.firozansari.domain.interactor;

import info.firozansari.domain.User;
import info.firozansari.domain.executor.PostExecutionThread;
import info.firozansari.domain.executor.ThreadExecutor;
import info.firozansari.domain.repository.UserRepository;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link User}.
 */
public class GetUserList extends UseCase<List<User>, Void> {

  private final UserRepository userRepository;

  @Inject
  GetUserList(UserRepository userRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.userRepository = userRepository;
  }

  @Override Observable<List<User>> buildUseCaseObservable(Void unused) {
    return this.userRepository.users();
  }
}
