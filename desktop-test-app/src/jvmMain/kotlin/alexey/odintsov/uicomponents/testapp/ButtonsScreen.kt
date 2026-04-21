package alexey.odintsov.uicomponents.testapp

import alexey.odintsov.uicomponents.buttons.CustomButton
import alexey.odintsov.uicomponents.buttons.CustomDropDownButton
import alexey.odintsov.uicomponents.buttons.DropDownItem
import alexey.odintsov.uicomponents.buttons.ImageButton
import alexey.odintsov.uicomponents.buttons.ToggleImageButton
import alexey.odintsov.uicomponents.checkbox.CustomCheckbox
import alexey.odintsov.uicomponents.preview.PreviewDarkAndLightTheme
import alexey.odintsov.uicomponents.resources.Res
import alexey.odintsov.uicomponents.resources.icon_copy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                    DropDownItem(title = "A", clickHandler = { println("A clicked") }),
                    DropDownItem(title = "B", clickHandler = { println("B clicked") }),
                    DropDownItem(title = "C", clickHandler = { println("C clicked") }),
                )
            )
        }

        ToggleImageButton(
            icon = Res.drawable.icon_copy,
            checkedState = checked,
            updateCheckedState = { c -> checked = c })

        ImageButton(
            modifier = Modifier.size(32.dp),
            icon = Res.drawable.icon_copy,
            title = "",
            onClick = {})

        CustomCheckbox(checked = checked, onCheckedChange = { c -> checked = c })
    }
}

@Preview
@Composable
private fun PreviewButtonsScreen() {
    PreviewDarkAndLightTheme(true) {
        ButtonsScreen()
    }
}