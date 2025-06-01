package kotli.app.feature.presentation

import androidx.compose.runtime.Composable
import shared.presentation.ui.container.DsFixedTopBarLayout

@Composable
fun FeatureScreen(
    title: String,
    onBack: () -> Unit
) {
    DsFixedTopBarLayout(
        title = title,
        onBack = onBack,
    ) {
        //
    }
}