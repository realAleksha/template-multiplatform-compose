package feature.update.sideload

import feature.update.sideload.domain.usecase.GetUpdatePathUseCase

internal class PersistentSideloadUpdateStateResolver(
    private val getUpdatePathUseCase: GetUpdatePathUseCase,
    private val resolver: SideloadUpdateStateResolver
) : SideloadUpdateStateResolver {

    override suspend fun resolve(): SideloadUpdateState {
        val path = getUpdatePathUseCase()
        if (path != null) {
            return SideloadUpdateState.ReadyToInstall(path)
        }
        return resolver.resolve()
    }
}
