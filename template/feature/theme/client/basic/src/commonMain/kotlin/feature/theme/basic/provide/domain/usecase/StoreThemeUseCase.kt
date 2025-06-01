package feature.theme.basic.provide.domain.usecase

import feature.theme.basic.provide.domain.model.ThemeConfigModel
import feature.theme.basic.provide.domain.repository.ThemeRepository

internal class StoreThemeUseCase(
    private val repository: ThemeRepository
) {

    suspend fun invoke(model: ThemeConfigModel) {
        runCatching { repository.store(model) }
    }
}