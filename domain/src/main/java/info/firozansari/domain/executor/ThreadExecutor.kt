package info.firozansari.domain.executor

import java.util.concurrent.Executor

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the
 * [info.firozansari.domain.interactor.UseCase] out of the UI thread.
 */
interface ThreadExecutor : Executor