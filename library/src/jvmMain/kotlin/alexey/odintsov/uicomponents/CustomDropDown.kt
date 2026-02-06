package alexey.odintsov.uicomponents

import alexey.odintsov.uicomponents.preview.PreviewDarkAndLightTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CustomDropDown(
    modifier: Modifier,
    items: List<String>,
    initialSelectedIndex: Int,
    onItemsSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(initialSelectedIndex) }

    Box(
        modifier = modifier
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier.clickable(onClick = { expanded = true }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                items[selectedIndex],
                modifier = Modifier.padding(horizontal = 4.dp).weight(1f),
                maxLines = 1
            )
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentSize().background(MaterialTheme.colorScheme.background)
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    modifier = Modifier.height(20.dp),
                    onClick = {
                        selectedIndex = index
                        expanded = false
                        onItemsSelected(selectedIndex)
                    }, text = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = s,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    })
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCustomDropDown() {
    PreviewDarkAndLightTheme(true) {
        CustomDropDown(
            modifier = Modifier.width(200.dp),
            items = mutableStateListOf("a", "b", "c"),
            initialSelectedIndex = 1,
            onItemsSelected = { _ -> })
    }
}
