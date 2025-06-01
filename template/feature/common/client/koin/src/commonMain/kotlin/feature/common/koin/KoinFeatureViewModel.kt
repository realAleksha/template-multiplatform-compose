package feature.common.koin

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import shared.presentation.viewmodel.BaseViewModel

@Composable
inline fun <reified T : BaseViewModel> koinFeatureViewModel(key: String? = null): T {
    val viewModel = koinViewModel<T>(key = key)
    viewModel.bind()
    return viewModel
}