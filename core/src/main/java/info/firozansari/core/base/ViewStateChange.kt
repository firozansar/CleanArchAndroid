package info.firozansari.core.base

interface ViewStateChange<VS : ViewState> {
    fun apply(currentViewState: VS): VS
}
