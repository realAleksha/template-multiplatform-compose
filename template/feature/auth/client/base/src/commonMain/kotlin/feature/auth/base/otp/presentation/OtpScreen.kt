package feature.auth.base.otp.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import shared.presentation.ui.container.DsFixedTopBarLayout

@Composable
internal fun OtpScreen(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    onBack: () -> Unit,
    onSuccess: () -> Unit,
    onResend: suspend () -> Unit,
    onVerify: suspend (otp: String) -> Unit,
) {
    DsFixedTopBarLayout(
        modifier = modifier,
        onBack = onBack,
        content = {
            OtpForm(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                title = title,
                subTitle = subTitle,
                onVerify = onVerify,
                onResend = onResend,
                onSuccess = onSuccess,
            )
        }
    )
}
