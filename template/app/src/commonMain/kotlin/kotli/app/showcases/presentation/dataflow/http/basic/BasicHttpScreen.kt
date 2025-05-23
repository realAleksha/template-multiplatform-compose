package kotli.app.showcases.presentation.dataflow.http.basic

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotli.app.showcases.presentation.ShowcaseHintBlock
import shared.presentation.ui.component.DsElevatedButton
import shared.presentation.ui.component.DsSpacer16
import shared.presentation.ui.component.DsText
import shared.presentation.ui.container.DsFixedTopBarColumn
import shared.presentation.viewmodel.provideViewModel

@Composable
fun BasicHttpScreen(onBack: () -> Unit) {
    val viewModel: BasicHttpViewModel = provideViewModel()
    val state = viewModel.state

    DsFixedTopBarColumn(
        title = BasicHttpRoute.screen.label,
        onBack = onBack,
        content = {
            ShowcaseHintBlock(
                text = """
                    By clicking on the button, the app will attempt to retrieve the device's IP address using a public HTTP API, following this flow:
                    
                    1. Delay the request for a brief period and display a loading state.
                    
                    2. Send the request to obtain the device's IP address.
                    
                    3. If the request succeeds, display the IP address.
                    
                    4. If an error occurs, display the error's stack trace.
                    
                    In case of any issue, also check the system console for additional information.
                """.trimIndent()
            )
            DsElevatedButton(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = viewModel::onFetchIp,
                text = "Fetch my ip"
            )
            IpBlock(state)
            DsSpacer16()
        }
    )
}

@Composable
private fun IpBlock(state: BasicHttpState) {
    DsText(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = state.ip
    )
}