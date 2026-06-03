package kotli.template.multiplatform.compose.feature.common

import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.RenamePackage
import kotli.template.multiplatform.compose.Rules
import kotli.template.multiplatform.compose.feature.UserFeatureProcessor

object CommonClientApiProcessor : UserFeatureProcessor() {

    override val moduleName: String = "feature:common:client:api"

    override val featureName: String = "feature.common.api.Feature"

    override fun isInternal(): Boolean = true

    override fun doRemove(state: TemplateState) {
        super.doRemove(state)

        state.onApplyRules(
            "${Rules.ClientAppRoot}/presentation",
            RemoveFile()
        )

        state.onApplyRules(
            "${Rules.ClientCommonMain}/kotlin",
            RenamePackage(
                "kotli.app.presentation_no_features",
                "kotli.app.presentation"
            )
        )
        state.onApplyRules(
            Rules.ClientAppConfigKt,
            RemoveMarkedLine("FeatureContext")
        )
    }
}