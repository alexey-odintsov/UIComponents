package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.buttons.CustomButton
import alexey.odintsov.kmp.uicomponents.buttons.CustomDropDownButton
import alexey.odintsov.kmp.uicomponents.buttons.DropDownItem
import alexey.odintsov.kmp.uicomponents.buttons.ToggleImageButton
import alexey.odintsov.kmp.uicomponents.checkbox.CustomCheckbox
import alexey.odintsov.kmp.uicomponents.resources.Res
import alexey.odintsov.kmp.uicomponents.resources.icon_copy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonsScreen() {
    var checked by remember { mutableStateOf(true) }

    Column(Modifier.padding(6.dp)) {
        Text("CustomButtons: ")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            CustomButton(onClick = {}) {
                Text("Button")
            }
            CustomButton(enabled = false, onClick = {}) {
                Text("Disabled Button")
            }
        }

        Text("CustomDropDownButtons: ")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            CustomDropDownButton(
                items = listOf(
                    DropDownItem(title = "A", clickHandler = {}),
                    DropDownItem(title = "B", clickHandler = {}),
                    DropDownItem(title = "C", clickHandler = {}),
                )
            )
        }

        ToggleImageButton(
            icon = Res.drawable.icon_copy,
            checkedState = checked,
            updateCheckedState = { c -> checked = c })

        CustomCheckbox(checked = checked, onCheckedChange = { c -> checked = c })
    }
}