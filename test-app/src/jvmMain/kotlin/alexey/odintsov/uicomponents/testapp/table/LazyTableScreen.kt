package alexey.odintsov.uicomponents.testapp.table

import alexey.odintsov.uicomponents.buttons.CustomButton
import alexey.odintsov.uicomponents.preview.PreviewDarkAndLightTheme
import alexey.odintsov.uicomponents.table.DesktopLazyTable
import alexey.odintsov.uicomponents.table.mapAlign
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


data class LogItem(
    val key: Int,
    val data: Map<String, String>,
)

@Composable
fun LazyTableScreen() {
    val viewModel: LazyTableScreenViewModel = viewModel {
        LazyTableScreenViewModel()
    }
    val onColumnResized = viewModel::onColumnResized
    val listState = remember { LazyListState() }
    val horizontalScrollState = rememberScrollState(1)
    var wrapContent by remember { mutableStateOf(false) }

    Column(Modifier.padding(32.dp)) {
        CustomButton(onClick = { wrapContent = !wrapContent }) {
            Text("Wrap content: $wrapContent")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(Modifier.weight(0.5f)) {
                Column {
                    Text("DesktopTable (default)")
                    DesktopLazyTable(
                        items = viewModel.indices,
                        columns = columns,
                        listState = listState,
                        wrapContent = wrapContent,
                        horizontalScrollState = horizontalScrollState,
                        onColumnResized = onColumnResized,
                        cellContent = { index, column, offset ->
                            val message = viewModel.getMessageByIndex(index)
                            if (message != null) {
                                Row {
                                    Text(
                                        text = message.data[column.key] ?: "",
                                        modifier = Modifier.weight(1f).padding(2.dp),
                                        textAlign = mapAlign(column)
                                    )
                                    if (column.key == "message") {
                                        CustomButton(onClick = {}) {
                                            Text("Edit")
                                        }
                                    }
                                }
                            } else {
                                Text("..")
                            }
                        },
                        requestLoadMessagesInRange = { first, last ->
                            viewModel.loadMessagesInRange(first, last)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTableScreen() {
    PreviewDarkAndLightTheme(true) {
        LazyTableScreen()
    }
}