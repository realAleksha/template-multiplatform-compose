package feature.auth.base.userflow.presentation.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.auth.api.AuthUser
import feature.auth.base.common.presentation.AuthUserAvatar
import feature.auth.base.signin.email.presentation.SignInWithEmailButton
import feature.auth.base.signin.google.presentation.SignInWithGoogleButton
import feature.common.koin.koinFeatureViewModel
import org.jetbrains.compose.resources.stringResource
import shared.presentation.state.UiState
import shared.presentation.ui.component.DsCircularProgressIndicator
import shared.presentation.ui.component.DsTextButton
import shared.presentation.ui.container.DsFixedTopBarColumn
import template.feature.auth.client.base.generated.resources.Res
import template.feature.auth.client.base.generated.resources.auth_sign_out

@Composable
internal fun BasicAuthScreen(
    title: String?,
    onBack: () -> Unit,
    onSignInWithEmail: () -> Unit,
    onSignOut: () -> Unit,
) {
    DsFixedTopBarColumn(
        title = title,
        onBack = onBack,
        content = {
            BasicAuthContent(
                onSignInWithEmail = onSignInWithEmail,
                onSignOut = onSignOut,
            )
        }
    )
}

@Composable
internal fun BasicAuthContent(
    onSignInWithEmail: () -> Unit,
    onSignOut: () -> Unit,
) {
    val viewModel: BasicAuthViewModel = koinFeatureViewModel()
    val state = viewModel.state

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (state.uiState) {
            is UiState.Loading -> LoadingContent()
            else -> {
                val user = state.authUser
                if (user != null) {
                    AuthorizedContent(user, onSignOut)
                } else {
                    NotAuthorizedContent(onSignInWithEmail)
                }
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    DsCircularProgressIndicator()
}

@Composable
private fun AuthorizedContent(
    user: AuthUser,
    onSignOut: () -> Unit,
) {
    AuthUserAvatar(
        model = user
    )
    DsTextButton(
        text = stringResource(Res.string.auth_sign_out),
        onClick = onSignOut,
    )
}

@Composable
private fun NotAuthorizedContent(
    onSignInWithEmail: () -> Unit
) {
    SignInWithEmailButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onSignInWithEmail,
    )

    SignInWithGoogleButton(
        modifier = Modifier.fillMaxWidth()
    )
}