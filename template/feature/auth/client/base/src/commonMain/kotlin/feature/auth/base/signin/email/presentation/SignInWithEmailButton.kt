package feature.auth.base.signin.email.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import shared.presentation.ui.component.DsOutlinedButton
import shared.presentation.ui.icon.DsIconModel
import template.feature.auth.client.base.generated.resources.Res
import template.feature.auth.client.base.generated.resources.auth_email
import template.feature.auth.client.base.generated.resources.auth_sign_in_email

@Composable
internal fun SignInWithEmailButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    DsOutlinedButton(
        modifier = modifier,
        icon = DsIconModel.DrawableResource(Res.drawable.auth_email),
        text = stringResource(Res.string.auth_sign_in_email),
        onClick = onClick,
    )
}