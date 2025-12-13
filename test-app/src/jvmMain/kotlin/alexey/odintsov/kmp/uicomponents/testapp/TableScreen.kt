package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.buttons.CustomButton
import alexey.odintsov.kmp.uicomponents.edit.CustomEditText
import alexey.odintsov.kmp.uicomponents.preview.PreviewDarkAndLightTheme
import alexey.odintsov.kmp.uicomponents.table.DesktopTable
import alexey.odintsov.kmp.uicomponents.table.mapAlign
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


data class LogItem(
    val key: String,
    val data: Map<String, String>,
)

@Composable
fun TableScreen() {
    val viewModel: TableScreenViewModel = viewModel {
        TableScreenViewModel()
    }
    val columns = viewModel.columns
    val items = viewModel.items
    val onColumnResized = viewModel::onColumnResized
    val scrollState = remember { LazyListState() }
    val scrollState2 = remember { LazyListState() }
    val horizontalScrollState = rememberScrollState(1)
    var colorValue by remember { mutableStateOf(0) }
    val colors = viewModel.colors
    val focusManager = LocalFocusManager.current

    Column(Modifier.padding(32.dp)) {
        Row {
            CustomEditText(colorValue.toString(), onValueChange = {
                colorValue = it.toIntOrNull() ?: 0
            })
            CustomButton(onClick = {
                viewModel.changeColor(colorValue, Color.Red)
            }) {
                Text("Change color")
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(Modifier.weight(0.5f)) {
                Column {
                    Text("DesktopTable (default)")
                    DesktopTable(
                        items = viewModel.items,
                        columns = columns,
                        scrollState = scrollState2,
                        maxWidth = 3000.dp,
                        horizontalScrollState = horizontalScrollState,
                        onColumnResized = onColumnResized,
                        cellContent = { index, column, item ->
                            Row {
                                Text(
                                    item.data[column.key] ?: "",
                                    Modifier.weight(1f).padding(2.dp),
                                    textAlign = mapAlign(column)
                                )
                                if (column.key == "message") {
                                    CustomButton(onClick = {}) {
                                        Text("Edit")
                                    }
                                }
                            }
                        }
                    )
                }
            }

            Box(Modifier.weight(0.5f)) {
                Column {
                    Text("DesktopTable (Custom)")
                    DesktopTable(
                        modifier = Modifier.fillMaxSize(),
                        items = viewModel.items,
                        columns = columns,
                        maxWidth = 3000.dp,
                        scrollState = scrollState,
                        horizontalScrollState = horizontalScrollState,
                        onColumnResized = onColumnResized,
                        headerCellContent = { column ->
                            val menuItems = mutableListOf(
                                ContextMenuItem("Hide ${column.title} ", {}),
                            )
                            ContextMenuArea(items = { menuItems }) {
                                Text(column.title, Modifier.padding(2.dp))
                            }
                        },
                        headerRowWrapContent = { modifier, content ->
                            Row(modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceBright)) {
                                content()
                            }
                        },
                        rowWrapContent = { modifier, i, item, content ->
                            val menuItems = mutableListOf(
                                ContextMenuItem("Menu for #$i ${item.key}", {}),
                                ContextMenuItem("Menu 2 for #$i ${item.key}", {}),
                            )
                            ContextMenuArea(items = { menuItems }) {
                                val color =
                                    if (viewModel.selectedRowIndex.value == i) Color.Gray else (colors[i]
                                        ?: MaterialTheme.colorScheme.surfaceBright)
                                Row(
                                    modifier
                                        .background(color)
                                        .onFocusChanged { state ->
                                            if (state.isFocused) {
                                                viewModel.onRowSelected(i)
                                            }
                                        }
                                        .selectable(
                                            selected = false,
                                            onClick = {
                                                viewModel.onRowSelected(i)
                                            }
                                        )
                                        .onKeyEvent(onKeyEvent = { e ->
                                            if (e.type == KeyEventType.KeyDown) {
                                                return@onKeyEvent when (e.key) {
                                                    Key.S, Key.DirectionDown -> {
                                                        if (i < items.lastIndex) {
                                                            focusManager.moveFocus(FocusDirection.Down)
                                                        }
                                                        true
                                                    }

                                                    Key.W, Key.DirectionUp -> {
                                                        if (i > 0) {
                                                            focusManager.moveFocus(FocusDirection.Up)
                                                        }
                                                        true
                                                    }

                                                    else -> false
                                                }
                                            }
                                            false
                                        })
                                ) {
                                    content()
                                }
                            }
                        },
                        cellContent = { index, column, item ->
                            Text(item.data[column.key] ?: "", Modifier.fillMaxWidth().padding(2.dp))
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
        TableScreen()
    }
}