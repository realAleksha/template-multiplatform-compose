package feature.update.sideload

sealed interface SideloadUpdateState {
    data object None : SideloadUpdateState
    data class Optional(val url: String) : SideloadUpdateState
    data class Force(val url: String) : SideloadUpdateState
    data class ReadyToInstall(val filePath: String) : SideloadUpdateState
    data class Error(val message: String) : SideloadUpdateState
}
