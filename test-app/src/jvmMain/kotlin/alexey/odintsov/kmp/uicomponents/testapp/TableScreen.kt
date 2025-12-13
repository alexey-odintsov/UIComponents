package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.buttons.CustomButton
import alexey.odintsov.kmp.uicomponents.edit.CustomEditText
import alexey.odintsov.kmp.uicomponents.preview.PreviewDarkAndLightTheme
import alexey.odintsov.kmp.uicomponents.table.ColumnAlign
import alexey.odintsov.kmp.uicomponents.table.ColumnInfo
import alexey.odintsov.kmp.uicomponents.table.DesktopTable
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.text.style.TextAlign
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
                        onColumnResized = onColumnResized,
                        headerCellContent = { column ->
                            val menuItems = mutableListOf(
                                ContextMenuItem("Hide ${column.title} ", {}),
                            )
                            ContextMenuArea(items = { menuItems }) {
                                Text(column.title, Modifier.padding(2.dp))
                            }
                        },
                        cellContent = { index, column, item ->
                            Text(item.data[column.key] ?: "", Modifier.padding(2.dp))
                        }
                    )
                }
            }

            Box(Modifier.weight(0.5f)) {
                Column {
                    Text("DesktopTable (Custom)")
                    DesktopTable(
                        items = viewModel.items,
                        columns = columns,
                        scrollState = scrollState,
                        onColumnResized = onColumnResized,
                        headerCellContent = { column ->
                            val menuItems = mutableListOf(
                                ContextMenuItem("Hide ${column.title} ", {}),
                            )
                            ContextMenuArea(items = { menuItems }) {
                                Text(column.title, Modifier.padding(2.dp))
                            }
                        },
                        rowWrapContent = { i, item, content ->
                            val menuItems = mutableListOf(
                                ContextMenuItem("Menu for #$i ${item.key}", {}),
                                ContextMenuItem("Menu 2 for #$i ${item.key}", {}),
                            )
                            ContextMenuArea(items = { menuItems }) {
                                val color =
                                    if (viewModel.selectedRowIndex.value == i) Color.Gray else (colors[i]
                                        ?: Color.White)
                                Row(
                                    Modifier
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
                            Text(item.data[column.key] ?: "", Modifier.padding(2.dp))
                        }
                    )
                }
            }
        }


//        Box {
//            Table<LogItem>(
//                items = items,
//                modifier = Modifier.fillMaxSize(),
//                columns = columns,
//                scrollState = scrollState,
//                onColumnResized = onColumnResized,
//                selectedRow = viewModel.selectedRowIndex.value,
//                onRowSelected = viewModel::onRowSelected,
//                header = {
//                    columns.forEach { c ->
//                        cell(columnInfo = c) {
//                            Text(
//                                modifier = Modifier.fillMaxSize(),
//                                text = c.title,
//                                textAlign = mapAlign(c),
//                            )
//                        }
//                    }
//                },
//                headerWrapper = { content ->
//                    val menuItems = mutableListOf(
//                        ContextMenuItem("Header menu", {}),
//                    )
//                    ContextMenuArea(items = { menuItems }) {
//                        content()
//                    }
//                },
//                rowWrapper = { i, item, content ->
//                    val menuItems = mutableListOf(
//                        ContextMenuItem("Menu for #$i ${item.key}", {}),
//                        ContextMenuItem("Menu 2 for #$i ${item.key}", {}),
//                    )
//                    ContextMenuArea(items = { menuItems }) {
//                        content()
//                    }
//                },
//            ) { i, item ->
//                val color = colors[i] ?: MaterialTheme.colorScheme.surface
//
//                columns.forEach { c ->
//                    cell(
//                        background = color,
//                        columnInfo = c,
//                    ) {
//                        Text(
//                            modifier = Modifier.fillMaxWidth(),//.border(1.dp, Color.Gray),
//                            text = item.data[c.key].toString(),
//                            textAlign = mapAlign(c)
//                        )
//                    }
//
//                }
//            }
//            VerticalScrollbar(
//                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
//                adapter = rememberScrollbarAdapter(
//                    scrollState = scrollState
//                )
//            )
//        }
    }
}

private fun mapAlign(c: ColumnInfo): TextAlign = when (c.align) {
    ColumnAlign.Left -> TextAlign.Left
    ColumnAlign.Center -> TextAlign.Center
    ColumnAlign.Right -> TextAlign.Right
}

@Preview
@Composable
private fun PreviewTableScreen() {
    PreviewDarkAndLightTheme(true) {
        TableScreen()
    }
}