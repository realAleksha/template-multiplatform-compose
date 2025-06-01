package feature.passcode.basic.presentation.set

import org.jetbrains.compose.resources.StringResource
import template.feature.passcode.client.basic.generated.resources.Res
import template.feature.passcode.client.basic.generated.resources.passcode_set_confirm_new_title
import template.feature.passcode.client.basic.generated.resources.passcode_set_enter_new_title
import template.feature.passcode.client.basic.generated.resources.passcode_set_unlock_title

internal sealed class SetPasscodeStep(open val titleRes: StringResource) {

    data class UnlockExisting(
        override val titleRes: StringResource = Res.string.passcode_set_unlock_title
    ) : SetPasscodeStep(titleRes)

    data class EnterNew(
        override val titleRes: StringResource = Res.string.passcode_set_enter_new_title
    ) : SetPasscodeStep(titleRes)

    data class ConfirmNew(
        override val titleRes: StringResource = Res.string.passcode_set_confirm_new_title,
        val code: String
    ) : SetPasscodeStep(titleRes)

}
