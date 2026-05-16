package kotli.template.multiplatform.compose.data.paging

import kotli.engine.FeatureProcessor
import kotli.template.multiplatform.compose.data.BaseDataProvider
import kotli.template.multiplatform.compose.data.common.CommonDataProcessor
import kotli.template.multiplatform.compose.data.paging.jetpack.JetpackPagingProcessor
import kotlin.reflect.KClass

object PagingProvider : BaseDataProvider() {

    override fun getId(): String = "data.paging"

    override fun isMultiple(): Boolean = false

    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        CommonDataProcessor::class,
        CommonPagingProcessor::class
    )

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        CommonPagingProcessor,
        JetpackPagingProcessor
    )

}