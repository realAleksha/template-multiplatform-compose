package kotli.template.multiplatform.compose.userflow.passcode

import kotli.engine.FeatureProcessor
import kotli.template.multiplatform.compose.userflow.BaseUserFlowProvider
import kotli.template.multiplatform.compose.userflow.passcode.basic.BasicPasscodeProcessor

object PasscodeProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.passcode"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BasicPasscodeProcessor
    )

}