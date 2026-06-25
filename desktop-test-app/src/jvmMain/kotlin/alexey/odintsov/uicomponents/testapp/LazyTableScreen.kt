package alexey.odintsov.uicomponents.testapp

import alexey.odintsov.uicomponents.buttons.CustomButton
import alexey.odintsov.uicomponents.preview.PreviewDarkAndLightTheme
import alexey.odintsov.uicomponents.table.DesktopLazyTable
import alexey.odintsov.uicomponents.table.mapAlign
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun LazyTableScreen() {
    val viewModel: LazyTableScreenViewModel = viewModel {
        LazyTableScreenViewModel()
    }
    val columns = viewModel.columns
    val items = viewModel.indices
    val onColumnResized = viewModel::onColumnResized
    val scrollState = remember { LazyListState() }
    val scrollState2 = remember { LazyListState() }
    val horizontalScrollState = rememberScrollState(1)
    var wrapContent by remember { mutableStateOf(false) }
    val colors = viewModel.colors
    val focusManager = LocalFocusManager.current

    Column(Modifier.padding(32.dp)) {
        CustomButton(onClick = { wrapContent = !wrapContent }) {
            Text("Wrap content: $wrapContent")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(Modifier.weight(0.5f)) {
                Column {
                    Text("DesktopTable (default)")
                    DesktopLazyTable(
                        items = items,
                        columns = columns,
                        scrollState = scrollState2,
                        wrapContent = wrapContent,
                        horizontalScrollState = horizontalScrollState,
                        onColumnResized = onColumnResized,
                        cellContent = { index, column, item ->
                            val message = viewModel.getMessageByIndex(index)
                            Row {
                                Text(
                                    message?.data[column.key] ?: "",
                                    Modifier.weight(1f).padding(2.dp),
                                    textAlign = mapAlign(column)
                                )
                                if (column.key == "message") {
                                    CustomButton(onClick = {}) {
                                        Text("Edit")
                                    }
                                }
                            }
                        },
                        requestLoadMessagesInRange = { first, last ->
                            viewModel.loadMessagesInRange(first, last)
                        }
                    )
                }
            }

            Box(Modifier.weight(0.5f)) {
                Column {
                    Text("DesktopTable (Custom)")
                    DesktopLazyTable(
                        modifier = Modifier.fillMaxSize(),
                        items = items,
                        columns = columns,
                        wrapContent = wrapContent,
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
                            val message = viewModel.getMessageByIndex(i)
                            val menuItems = mutableListOf(
                                ContextMenuItem("Menu for #$i ${message?.key}", {}),
                                ContextMenuItem("Menu 2 for #$i ${message?.key}", {}),
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
                            val message = viewModel.getMessageByIndex(index)
                            Text(message?.data[column.key] ?: "", Modifier.fillMaxWidth().padding(2.dp))
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
        TableScreen()
    }
}