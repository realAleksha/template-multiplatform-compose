package feature.auth.base.signin.email.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import shared.presentation.ui.container.DsFixedTopBarLayout
import template.feature.auth.client.base.generated.resources.Res
import template.feature.auth.client.base.generated.resources.auth_sign_in_email

@Composable
internal fun SignInWithEmailScreen(
    modifier: Modifier = Modifier,
    onVerify: (email: String) -> Unit,
    onBack: () -> Unit,
) {
    DsFixedTopBarLayout(
        modifier = modifier,
        title = stringResource(Res.string.auth_sign_in_email),
        onBack = onBack,
        content = {
            SignInWithEmailForm(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onVerify = onVerify,
            )
        }
    )
}