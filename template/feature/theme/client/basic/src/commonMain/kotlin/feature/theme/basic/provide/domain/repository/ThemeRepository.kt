package feature.theme.basic.provide.domain.repository

import feature.theme.basic.provide.domain.model.ThemeConfigModel

internal interface ThemeRepository {

    suspend fun restore(): ThemeConfigModel?

    suspend fun store(model: ThemeConfigModel)

}