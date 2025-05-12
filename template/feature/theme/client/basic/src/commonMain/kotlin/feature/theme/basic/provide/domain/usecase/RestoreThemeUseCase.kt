package feature.theme.basic.provide.domain.usecase

import feature.theme.basic.provide.domain.model.ThemeConfigModel
import feature.theme.basic.provide.domain.repository.ThemeRepository

internal class RestoreThemeUseCase(
    private val repository: ThemeRepository
) {

    suspend fun invoke(): ThemeConfigModel? {
        return runCatching { repository.restore() }.getOrNull()
    }
}