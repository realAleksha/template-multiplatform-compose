package kotli.template.multiplatform.compose.data.encoding

import kotli.engine.FeatureProcessor
import kotli.template.multiplatform.compose.data.BaseDataProvider
import kotli.template.multiplatform.compose.data.encoding.common.CommonEncodingProcessor

object EncodingProvider : BaseDataProvider() {

    override fun getId(): String = "data.encoding"

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        CommonEncodingProcessor
    )
}
