
package info.firozansari.presentation.presenter;

import android.support.annotation.NonNull;
import info.firozansari.domain.User;
import info.firozansari.domain.exception.DefaultErrorBundle;
import info.firozansari.domain.exception.ErrorBundle;
import info.firozansari.domain.interactor.DefaultObserver;
import info.firozansari.domain.interactor.GetUserDetails;
import info.firozansari.domain.interactor.GetUserDetails.Params;
import info.firozansari.presentation.exception.ErrorMessageFactory;
import info.firozansari.presentation.internal.di.PerActivity;
import info.firozansari.presentation.mapper.UserModelDataMapper;
import info.firozansari.presentation.model.UserModel;
import info.firozansari.presentation.view.UserDetailsView;
import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class UserDetailsPresenter implements Presenter {

  private UserDetailsView viewDetailsView;

  private final GetUserDetails getUserDetailsUseCase;
  private final UserModelDataMapper userModelDataMapper;

  @Inject
  public UserDetailsPresenter(GetUserDetails getUserDetailsUseCase,
      UserModelDataMapper userModelDataMapper) {
    this.getUserDetailsUseCase = getUserDetailsUseCase;
    this.userModelDataMapper = userModelDataMapper;
  }

  public void setView(@NonNull UserDetailsView view) {
    this.viewDetailsView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
    this.getUserDetailsUseCase.dispose();
    this.viewDetailsView = null;
  }

  /**
   * Initializes the presenter by showing/hiding proper views
   * and retrieving user details.
   */
  public void initialize(int userId) {
    this.hideViewRetry();
    this.showViewLoading();
    this.getUserDetails(userId);
  }

  private void getUserDetails(int userId) {
    this.getUserDetailsUseCase.execute(new UserDetailsObserver(), Params.forUser(userId));
  }

  private void showViewLoading() {
    this.viewDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.viewDetailsView.hideLoading();
  }

  private void showViewRetry() {
    this.viewDetailsView.showRetry();
  }

  private void hideViewRetry() {
    this.viewDetailsView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.context(),
        errorBundle.getException());
    this.viewDetailsView.showError(errorMessage);
  }

  private void showUserDetailsInView(User user) {
    final UserModel userModel = this.userModelDataMapper.transform(user);
    this.viewDetailsView.renderUser(userModel);
  }

  private final class UserDetailsObserver extends DefaultObserver<User> {

    @Override public void onComplete() {
      UserDetailsPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      UserDetailsPresenter.this.hideViewLoading();
      UserDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      UserDetailsPresenter.this.showViewRetry();
    }

    @Override public void onNext(User user) {
      UserDetailsPresenter.this.showUserDetailsInView(user);
    }
  }
}
