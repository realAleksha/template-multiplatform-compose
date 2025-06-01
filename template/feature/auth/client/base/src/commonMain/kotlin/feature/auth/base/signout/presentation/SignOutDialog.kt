package feature.auth.base.signout.presentation

import androidx.compose.runtime.Composable
import feature.common.koin.koinFeatureViewModel
import org.jetbrains.compose.resources.stringResource
import shared.presentation.state.ViewStateHandler
import shared.presentation.ui.component.DsAlertDialog
import template.feature.auth.client.base.generated.resources.Res
import template.feature.auth.client.base.generated.resources.auth_sign_out_cancel
import template.feature.auth.client.base.generated.resources.auth_sign_out_confirm
import template.feature.auth.client.base.generated.resources.auth_sign_out_message
import template.feature.auth.client.base.generated.resources.auth_sign_out_title

@Composable
internal fun SignOutDialog(
    onCancel: () -> Unit,
    onSuccess: () -> Unit,
) {
    val viewModel: SignOutViewModel = koinFeatureViewModel()
    val state = viewModel.state

    ViewStateHandler(
        state = state,
        onEvent = { event ->
            when (event) {
                is SignOutState.OnSuccess -> onSuccess()
                else -> Unit
            }
        },
        content = {
            DsAlertDialog(
                dismissAction = onCancel,
                onDismissRequest = onCancel,
                confirmAction = viewModel::onConfirm,
                title = stringResource(Res.string.auth_sign_out_title),
                text = stringResource(Res.string.auth_sign_out_message),
                dismissLabel = stringResource(Res.string.auth_sign_out_cancel),
                confirmLabel = stringResource(Res.string.auth_sign_out_confirm),
            )
        }
    )
}