package info.firozansari.data.executor

import info.firozansari.domain.executor.ThreadExecutor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Decorated [java.util.concurrent.ThreadPoolExecutor]
 */
@Singleton
class JobExecutor @Inject internal constructor() : ThreadExecutor {
    private val threadPoolExecutor: ThreadPoolExecutor
    fun execute(runnable: Runnable) {
        threadPoolExecutor.execute(runnable)
    }

    private class JobThreadFactory : ThreadFactory {
        private var counter = 0
        override fun newThread(runnable: Runnable): Thread {
            return Thread(runnable, "android_" + counter++)
        }
    }

    init {
        threadPoolExecutor = ThreadPoolExecutor(
            3, 5, 10, TimeUnit.SECONDS,
            LinkedBlockingQueue(), JobThreadFactory()
        )
    }
}