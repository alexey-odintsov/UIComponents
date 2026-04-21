package alexey.odintsov.uicomponents.testapp

import alexey.odintsov.uicomponents.buttons.CustomButton
import alexey.odintsov.uicomponents.dialogs.ColorPickerDialog
import alexey.odintsov.uicomponents.dialogs.DialogOperation
import alexey.odintsov.uicomponents.dialogs.FileDialog
import alexey.odintsov.uicomponents.dialogs.FileDialogState
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

    var showFileDialog by remember { mutableStateOf(false) }
    var selectedFile by remember { mutableStateOf<String?>(null) }

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
    if (showFileDialog) {
        FileDialog(
            FileDialogState(
                visible = true,
                operation = DialogOperation.OPEN,
                title = "Open file",
                fileCallback = {
                    selectedFile = it.toString()
                    showFileDialog = false
                },
                cancelCallback = {
                    showFileDialog = false
                }
            )
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomButton(onClick = { showFileDialog = true }) {
                Text("Open file dialog")
            }
            Text("Selected file: $selectedFile")
        }
    }
}