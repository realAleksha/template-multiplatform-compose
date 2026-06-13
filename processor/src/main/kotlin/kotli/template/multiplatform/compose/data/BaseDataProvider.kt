package kotli.template.multiplatform.compose.data

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.multiplatform.compose.data.common.CommonDataProcessor
import kotli.template.multiplatform.compose.data.encoding.common.CommonEncodingProcessor
import kotlin.reflect.KClass

abstract class BaseDataProvider : BaseFeatureProvider() {

    final override fun getType(): FeatureType = FeatureTypes.DataLayer

    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        CommonDataProcessor::class,
        CommonEncodingProcessor::class
    )
}