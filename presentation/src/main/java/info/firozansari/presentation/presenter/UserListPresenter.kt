package info.firozansari.presentation.presenter

import info.firozansari.domain.User
import info.firozansari.domain.exception.DefaultErrorBundle
import info.firozansari.domain.exception.ErrorBundle
import info.firozansari.domain.interactor.DefaultObserver
import info.firozansari.domain.interactor.GetUserList
import info.firozansari.presentation.exception.ErrorMessageFactory
import info.firozansari.presentation.internal.di.PerActivity
import info.firozansari.presentation.mapper.UserModelDataMapper
import info.firozansari.presentation.model.UserModel
import info.firozansari.presentation.view.UserListView
import javax.inject.Inject

/**
 * [Presenter] that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
class UserListPresenter @Inject constructor(
    private val getUserListUseCase: GetUserList?,
    private val userModelDataMapper: UserModelDataMapper?
) : Presenter {
    private var viewListView: UserListView? = null
    fun setView(view: UserListView) {
        viewListView = view
    }

    override fun resume() {}
    override fun pause() {}
    override fun destroy() {
        getUserListUseCase.dispose()
        viewListView = null
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    fun initialize() {
        loadUserList()
    }

    /**
     * Loads all users.
     */
    private fun loadUserList() {
        hideViewRetry()
        showViewLoading()
        getUserList()
    }

    fun onUserClicked(userModel: UserModel?) {
        viewListView.viewUser(userModel)
    }

    private fun showViewLoading() {
        viewListView.showLoading()
    }

    private fun hideViewLoading() {
        viewListView.hideLoading()
    }

    private fun showViewRetry() {
        viewListView.showRetry()
    }

    private fun hideViewRetry() {
        viewListView.hideRetry()
    }

    private fun showErrorMessage(errorBundle: ErrorBundle?) {
        val errorMessage = ErrorMessageFactory.create(
            viewListView.context(),
            errorBundle.getException()
        )
        viewListView.showError(errorMessage)
    }

    private fun showUsersCollectionInView(usersCollection: MutableCollection<User?>?) {
        val userModelsCollection = userModelDataMapper.transform(usersCollection)
        viewListView.renderUserList(userModelsCollection)
    }

    private fun getUserList() {
        getUserListUseCase.execute(UserListObserver(), null)
    }

    private inner class UserListObserver : DefaultObserver<MutableList<User?>?>() {
        override fun onComplete() {
            hideViewLoading()
        }

        override fun onError(e: Throwable?) {
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(e as Exception?))
            showViewRetry()
        }

        override fun onNext(users: MutableList<User?>?) {
            showUsersCollectionInView(users)
        }
    }
}