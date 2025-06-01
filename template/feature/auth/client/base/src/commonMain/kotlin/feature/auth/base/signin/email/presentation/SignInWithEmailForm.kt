package feature.auth.base.signin.email.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.common.koin.koinFeatureViewModel
import org.jetbrains.compose.resources.stringResource
import shared.presentation.state.ViewStateHandler
import shared.presentation.ui.component.DsElevatedButton
import shared.presentation.ui.component.DsTextField
import template.feature.auth.client.base.generated.resources.Res
import template.feature.auth.client.base.generated.resources.auth_sign_in_email_action
import template.feature.auth.client.base.generated.resources.auth_sign_in_email_placeholder

@Composable
internal fun SignInWithEmailForm(
    modifier: Modifier = Modifier,
    onVerify: (email: String) -> Unit
) {
    val viewModel: SignInWithEmailViewModel = koinFeatureViewModel()
    val state = viewModel.state

    ViewStateHandler(
        state = state,
        onEvent = { event ->
            when (event) {
                is SignInWithEmailState.OnVerify -> onVerify(state.email)
            }
        },
        content = {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DsTextField(
                    modifier = Modifier.fillMaxWidth(),
                    getValue = state::email::get,
                    onValueChange = viewModel::onChangeEmail,
                    placeholder = stringResource(Res.string.auth_sign_in_email_placeholder),
                    autoFocus = true
                )

                DsElevatedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.auth_sign_in_email_action),
                    onClick = viewModel::onVerify,
                    enabled = state.canVerify,
                )
            }
        }
    )
}
