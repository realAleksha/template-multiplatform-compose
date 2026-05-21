package feature.update.sideload

interface SideloadUpdateStateResolver {
    suspend fun resolve(): SideloadUpdateState
}
