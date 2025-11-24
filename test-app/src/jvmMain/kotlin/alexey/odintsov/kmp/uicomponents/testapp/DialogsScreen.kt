package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.buttons.CustomButton
import alexey.odintsov.kmp.uicomponents.dialogs.ColorPickerDialog
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DialogsScreen() {
    var showDialog by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(Color.Cyan) }

    if (showDialog) {
        ColorPickerDialog(
            visible = showDialog,
            onDialogClosed = { showDialog = false },
            initialColor = selectedColor,
            onColorUpdate = {
                selectedColor = it
                showDialog = false
            }
        )
    }

    Column(Modifier.padding(6.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomButton(onClick = { showDialog = true }) {
                Text("ColorPicker")
            }
            Text("Selected color: $selectedColor")
        }
    }
}