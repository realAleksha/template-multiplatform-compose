package kotli.app.showcases

import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
import androidx.lifecycle.viewmodel.initializer
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotli.app.get
import kotli.app.showcases.presentation.ShowcasesRoute
import kotli.app.showcases.presentation.ShowcasesScreen
import kotli.app.showcases.presentation.ShowcasesViewModel
import kotli.app.showcases.presentation.dataflow.ai.gemini.GeminiRoute
import kotli.app.showcases.presentation.dataflow.ai.gemini.GeminiScreen
import kotli.app.showcases.presentation.dataflow.ai.gemini.GeminiViewModel
import kotli.app.showcases.presentation.dataflow.cache.basic.BasicCacheRoute
import kotli.app.showcases.presentation.dataflow.cache.basic.BasicCacheScreen
import kotli.app.showcases.presentation.dataflow.cache.basic.BasicCacheViewModel
import kotli.app.showcases.presentation.dataflow.encryption.BasicEncryptionRoute
import kotli.app.showcases.presentation.dataflow.encryption.BasicEncryptionScreen
import kotli.app.showcases.presentation.dataflow.encryption.BasicEncryptionViewModel
import kotli.app.showcases.presentation.dataflow.http.basic.BasicHttpRoute
import kotli.app.showcases.presentation.dataflow.http.basic.BasicHttpScreen
import kotli.app.showcases.presentation.dataflow.http.basic.BasicHttpViewModel
import kotli.app.showcases.presentation.dataflow.paging.basic.BasicPagingRoute
import kotli.app.showcases.presentation.dataflow.paging.basic.BasicPagingScreen
import kotli.app.showcases.presentation.dataflow.paging.basic.BasicPagingViewModel
import kotli.app.showcases.presentation.dataflow.room.crud.RoomCrudRoute
import kotli.app.showcases.presentation.dataflow.room.crud.RoomCrudScreen
import kotli.app.showcases.presentation.dataflow.room.crud.RoomCrudViewModel
import kotli.app.showcases.presentation.dataflow.room.paging.RoomPagingRoute
import kotli.app.showcases.presentation.dataflow.room.paging.RoomPagingScreen
import kotli.app.showcases.presentation.dataflow.room.paging.RoomPagingViewModel
import kotli.app.showcases.presentation.dataflow.settings.`object`.ObjectSettingsRoute
import kotli.app.showcases.presentation.dataflow.settings.`object`.ObjectSettingsScreen
import kotli.app.showcases.presentation.dataflow.settings.`object`.ObjectSettingsViewModel
import kotli.app.showcases.presentation.dataflow.settings.primitive.PrimitiveSettingsRoute
import kotli.app.showcases.presentation.dataflow.settings.primitive.PrimitiveSettingsScreen
import kotli.app.showcases.presentation.dataflow.settings.primitive.PrimitiveSettingsViewModel
import kotli.app.showcases.presentation.dataflow.sqldelight.crud.SqlDelightCrudRoute
import kotli.app.showcases.presentation.dataflow.sqldelight.crud.SqlDelightCrudScreen
import kotli.app.showcases.presentation.dataflow.sqldelight.crud.SqlDelightCrudViewModel
import kotli.app.showcases.presentation.dataflow.sqldelight.paging.SqlDelightPagingRoute
import kotli.app.showcases.presentation.dataflow.sqldelight.paging.SqlDelightPagingScreen
import kotli.app.showcases.presentation.dataflow.sqldelight.paging.SqlDelightPagingViewModel
import kotli.app.showcases.presentation.userflow.common.component.filepicker.FilePickerRoute
import kotli.app.showcases.presentation.userflow.common.component.filepicker.FilePickerScreen
import kotli.app.showcases.presentation.userflow.common.component.filepicker.FilePickerViewModel
import kotli.app.showcases.presentation.userflow.common.component.image.coil.CoilRoute
import kotli.app.showcases.presentation.userflow.common.component.image.coil.CoilScreen
import kotli.app.showcases.presentation.userflow.common.component.markdown.MarkdownRoute
import kotli.app.showcases.presentation.userflow.common.component.markdown.MarkdownScreen
import org.koin.dsl.module
import shared.presentation.navigation.back
import shared.presentation.navigation.newInstance

fun NavGraphBuilder.showcases(navController: NavHostController) {
    composable<ShowcasesRoute> { ShowcasesScreen(navController::newInstance) }
    composable<FilePickerRoute> { FilePickerScreen(navController::back) }
    composable<CoilRoute> { CoilScreen(navController::back) }
    composable<MarkdownRoute> { MarkdownScreen(navController::back) }
    composable<GeminiRoute> { GeminiScreen(navController::back) }
    composable<BasicCacheRoute> { BasicCacheScreen(navController::back) }
    composable<BasicEncryptionRoute> { BasicEncryptionScreen(navController::back) }
    composable<BasicHttpRoute> { BasicHttpScreen(navController::back) }
    composable<ObjectSettingsRoute> { ObjectSettingsScreen(navController::back) }
    composable<PrimitiveSettingsRoute> { PrimitiveSettingsScreen(navController::back) }
    composable<BasicPagingRoute> { BasicPagingScreen(navController::back) }
    composable<RoomCrudRoute> { RoomCrudScreen(navController::back) }
    composable<RoomPagingRoute> { RoomPagingScreen(navController::back) }
    composable<SqlDelightCrudRoute> { SqlDelightCrudScreen(navController::back) }
    composable<SqlDelightPagingRoute> { SqlDelightPagingScreen(navController::back) }
}

fun InitializerViewModelFactoryBuilder.showcases() {
    initializer { ShowcasesViewModel() }
    initializer { BasicPagingViewModel(get()) }
    initializer { BasicHttpViewModel(get()) }
    initializer { PrimitiveSettingsViewModel(get()) }
    initializer { ObjectSettingsViewModel(get()) }
    initializer { SqlDelightCrudViewModel(get()) }
    initializer { SqlDelightPagingViewModel(get(), get()) }
    initializer { BasicCacheViewModel(get()) }
    initializer { BasicEncryptionViewModel(get()) }
    initializer { FilePickerViewModel() }
    initializer { GeminiViewModel(get()) }
    initializer { RoomCrudViewModel(get()) }
    initializer { RoomPagingViewModel(get(), get()) }
}

val showcases = module {}