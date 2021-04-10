package info.firozansari.core.util

//import io.reactivex.rxjava3.core.Scheduler

data class SchedulerProvider(
    val subscribe: Scheduler,
    val observe: Scheduler
)
