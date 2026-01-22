package alexey.odintsov.uicomponents.testapp

import alexey.odintsov.uicomponents.CustomDropDown
import alexey.odintsov.uicomponents.edit.AutoCompleteEditText
import alexey.odintsov.uicomponents.edit.CustomEditText
import alexey.odintsov.uicomponents.edit.HighlightTransformation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditScreen() {
    var value by remember { mutableStateOf<String>("") }

    Column(Modifier.padding(6.dp)) {
        Text("CustomEditText: ")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            CustomEditText(
                value = value,
                onValueChange = { v ->
                    value = v
                }
            )
        }

        Text("AutoCompleteEditText: ")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            AutoCompleteEditText(
                value = value,
                onValueChange = { v ->
                    value = v
                },
                onEnterClicked = {},
                items = mutableStateListOf("test", "abc", "def"),
                visualTransformation = HighlightTransformation(3, MaterialTheme.colorScheme.error)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Font:", Modifier.width(70.dp))
            CustomDropDown(
                modifier = Modifier.width(150.dp).padding(horizontal = 4.dp),
                items = remember { mutableStateListOf("abc", "cba", "bde") },
                initialSelectedIndex = 0,
                onItemsSelected = { index ->
                }
            )
        }

    }
}