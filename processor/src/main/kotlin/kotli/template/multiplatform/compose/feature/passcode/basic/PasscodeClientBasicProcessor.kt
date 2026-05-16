package kotli.template.multiplatform.compose.feature.passcode.basic

import kotli.engine.FeatureProcessor
import kotli.template.multiplatform.compose.data.encryption.korlibs.KorlibsEncryptionProcessor
import kotli.template.multiplatform.compose.data.settings.datastore.DataStoreProcessor
import kotli.template.multiplatform.compose.feature.UserFeatureProcessor
import kotli.template.multiplatform.compose.feature.passcode.PasscodeClientApiProcessor
import kotlin.reflect.KClass

object PasscodeClientBasicProcessor : UserFeatureProcessor() {

    override val moduleName: String = "feature:passcode:client:basic"

    override val featureName: String = "BasicPasscodeProvider"

    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        PasscodeClientApiProcessor::class,
        KorlibsEncryptionProcessor::class,
        DataStoreProcessor::class,
    )
}