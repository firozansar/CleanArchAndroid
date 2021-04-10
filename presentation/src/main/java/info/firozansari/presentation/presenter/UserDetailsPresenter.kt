package info.firozansari.presentation.presenter

import info.firozansari.domain.User
import info.firozansari.domain.exception.DefaultErrorBundle
import info.firozansari.domain.exception.ErrorBundle
import info.firozansari.domain.interactor.DefaultObserver
import info.firozansari.domain.interactor.GetUserDetails
import info.firozansari.domain.interactor.GetUserDetails.Params.Companion.forUser
import info.firozansari.presentation.exception.ErrorMessageFactory
import info.firozansari.presentation.internal.di.PerActivity
import info.firozansari.presentation.mapper.UserModelDataMapper
import info.firozansari.presentation.view.UserDetailsView
import javax.inject.Inject

/**
 * [Presenter] that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
class UserDetailsPresenter @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetails?,
    private val userModelDataMapper: UserModelDataMapper?
) : Presenter {
    private var viewDetailsView: UserDetailsView? = null
    fun setView(view: UserDetailsView) {
        viewDetailsView = view
    }

    override fun resume() {}
    override fun pause() {}
    override fun destroy() {
        getUserDetailsUseCase.dispose()
        viewDetailsView = null
    }

    /**
     * Initializes the presenter by showing/hiding proper views
     * and retrieving user details.
     */
    fun initialize(userId: Int) {
        hideViewRetry()
        showViewLoading()
        getUserDetails(userId)
    }

    private fun getUserDetails(userId: Int) {
        getUserDetailsUseCase.execute(UserDetailsObserver(), forUser(userId))
    }

    private fun showViewLoading() {
        viewDetailsView.showLoading()
    }

    private fun hideViewLoading() {
        viewDetailsView.hideLoading()
    }

    private fun showViewRetry() {
        viewDetailsView.showRetry()
    }

    private fun hideViewRetry() {
        viewDetailsView.hideRetry()
    }

    private fun showErrorMessage(errorBundle: ErrorBundle?) {
        val errorMessage = ErrorMessageFactory.create(
            viewDetailsView.context(),
            errorBundle.getException()
        )
        viewDetailsView.showError(errorMessage)
    }

    private fun showUserDetailsInView(user: User?) {
        val userModel = userModelDataMapper.transform(user)
        viewDetailsView.renderUser(userModel)
    }

    private inner class UserDetailsObserver : DefaultObserver<User?>() {
        override fun onComplete() {
            hideViewLoading()
        }

        override fun onError(e: Throwable?) {
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(e as Exception?))
            showViewRetry()
        }

        override fun onNext(user: User?) {
            showUserDetailsInView(user)
        }
    }
}