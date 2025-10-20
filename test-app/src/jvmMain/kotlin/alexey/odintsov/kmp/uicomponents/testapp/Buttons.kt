package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.buttons.ToggleImageButton
import alexey.odintsov.kmp.uicomponents.resources.Res
import alexey.odintsov.kmp.uicomponents.resources.icon_copy
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun ButtonsScreen() {
    var checked by remember { mutableStateOf(true) }
    Column {
        ToggleImageButton(
            icon = Res.drawable.icon_copy,
            checkedState = checked,
            updateCheckedState = { c -> checked = c })
    }
}