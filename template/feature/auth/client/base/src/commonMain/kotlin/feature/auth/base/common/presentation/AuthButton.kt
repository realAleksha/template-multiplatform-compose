package feature.auth.base.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import shared.presentation.ui.component.DsOutlinedButton
import shared.presentation.ui.icon.DsIconModel

@Composable
internal fun AuthButton(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    text: String,
    onClick: () -> Unit,
) {
    DsOutlinedButton(
        modifier = modifier,
        icon = DsIconModel.DrawableResource(icon),
        iconTint = Color.Unspecified,
        onClick = onClick,
        text = text
    )
}