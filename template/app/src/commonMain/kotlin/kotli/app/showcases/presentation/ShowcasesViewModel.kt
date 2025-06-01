package kotli.app.showcases.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotli.app.showcases.domain.Showcase
import kotli.app.showcases.presentation.dataflow.ai.gemini.GeminiRoute
import kotli.app.showcases.presentation.dataflow.cache.basic.BasicCacheRoute
import kotli.app.showcases.presentation.dataflow.encryption.BasicEncryptionRoute
import kotli.app.showcases.presentation.dataflow.http.basic.BasicHttpRoute
import kotli.app.showcases.presentation.dataflow.paging.basic.BasicPagingRoute
import kotli.app.showcases.presentation.dataflow.room.crud.RoomCrudRoute
import kotli.app.showcases.presentation.dataflow.room.paging.RoomPagingRoute
import kotli.app.showcases.presentation.dataflow.settings.`object`.ObjectSettingsRoute
import kotli.app.showcases.presentation.dataflow.settings.primitive.PrimitiveSettingsRoute
import kotli.app.showcases.presentation.dataflow.sqldelight.crud.SqlDelightCrudRoute
import kotli.app.showcases.presentation.dataflow.sqldelight.paging.SqlDelightPagingRoute
import kotli.app.showcases.presentation.userflow.common.component.filepicker.FilePickerRoute
import kotli.app.showcases.presentation.userflow.common.component.image.coil.CoilRoute
import kotli.app.showcases.presentation.userflow.common.component.markdown.MarkdownRoute
import shared.presentation.viewmodel.BaseViewModel

class ShowcasesViewModel : BaseViewModel() {

    private val showcases = listOf(
        Showcase.Header("Data Flow :: Cache"),
        BasicCacheRoute.screen,
        Showcase.Header("Data Flow :: Encryption"),
        BasicEncryptionRoute.screen,
        Showcase.Header("Data Flow :: Http"),
        BasicHttpRoute.screen,
        Showcase.Header("Data Flow :: Settings"),
        PrimitiveSettingsRoute.screen,
        ObjectSettingsRoute.screen,
        Showcase.Header("Data Flow :: Paging"),
        BasicPagingRoute.screen,
        Showcase.Header("Data Flow :: Room"),
        RoomCrudRoute.screen,
        RoomPagingRoute.screen,
        Showcase.Header("Data Flow :: SqlDelight"),
        SqlDelightCrudRoute.screen,
        SqlDelightPagingRoute.screen,
        Showcase.Header("Data Flow :: AI"),
        GeminiRoute.screen,
        Showcase.Header("User Flow :: Components"),
        FilePickerRoute.screen,
        MarkdownRoute.screen,
        CoilRoute.screen,
    )

    private val _state = ShowcasesMutableState(showcases)
    val state: ShowcasesState = _state

    fun onShowHint() {
        _state.showHint = true
    }

    fun onHideHint() {
        _state.showHint = false
    }

    private class ShowcasesMutableState(
        override val showcases: List<Showcase>
    ) : ShowcasesState {
        override var showHint: Boolean by mutableStateOf(false)
    }

}
