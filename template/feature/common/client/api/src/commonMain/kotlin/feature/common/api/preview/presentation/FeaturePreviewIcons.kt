package feature.common.api.preview.presentation

import shared.presentation.ui.icon.DsIconModel
import template.feature.common.client.api.generated.resources.Res
import template.feature.common.client.api.generated.resources.ic_chevron_right
import template.feature.common.client.api.generated.resources.ic_debug_panel
import template.feature.common.client.api.generated.resources.ic_debug_panel_off

internal object FeaturePreviewIcons {
    val debugPanelOn: DsIconModel
        get() = DsIconModel.DrawableResource(Res.drawable.ic_debug_panel)
    val debugPanelOff: DsIconModel
        get() = DsIconModel.DrawableResource(Res.drawable.ic_debug_panel_off)
    val chevronRight: DsIconModel
        get() = DsIconModel.DrawableResource(Res.drawable.ic_chevron_right)

    fun debugPanel(visible: Boolean): DsIconModel = if (visible) debugPanelOff else debugPanelOn
}