package feature.theme.basic.provide.domain.model

import kotlinx.serialization.Serializable

@Serializable
internal data class ThemeConfigModel(
    val defaultThemeId: String? = null,
    val lightThemeId: String? = null,
    val darkThemeId: String? = null,
    val autoDark: Boolean? = null,
)