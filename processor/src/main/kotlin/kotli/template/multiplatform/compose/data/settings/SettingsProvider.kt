package kotli.template.multiplatform.compose.data.settings

import kotli.engine.FeatureProcessor
import kotli.template.multiplatform.compose.data.BaseDataProvider
import kotli.template.multiplatform.compose.data.settings.common.CommonSettingsProcessor
import kotli.template.multiplatform.compose.data.settings.datastore.DataStoreProcessor

object SettingsProvider : BaseDataProvider() {

    override fun getId(): String = "data.settings"
    override fun isMultiple(): Boolean = false
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        CommonSettingsProcessor,
        DataStoreProcessor
    )
}