package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.preview.PreviewDarkAndLightTheme
import alexey.odintsov.kmp.uicomponents.table.ColumnAlign
import alexey.odintsov.kmp.uicomponents.table.ColumnInfo
import alexey.odintsov.kmp.uicomponents.table.Table
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Column(Modifier.padding(32.dp)) {
        Box {
            Table<LogItem>(
                items = items,
                modifier = Modifier.fillMaxSize(),
                columns = columns,
                scrollState = scrollState,
                onColumnResized = onColumnResized,
                selectedRow = viewModel.selectedRowIndex.value,
                onRowSelected = viewModel::onRowSelected,
                header = {
                    columns.forEach { c ->
                        cell(columnInfo = c) {
                            Text(
                                modifier = Modifier.fillMaxSize(),
                                text = c.title,
                                textAlign = mapAlign(c),
                            )
                        }
                    }
                },
                headerWrapper = { content ->
                    val menuItems = mutableListOf(
                        ContextMenuItem("Header menu", {}),
                    )
                    ContextMenuArea(items = { menuItems }) {
                        content()
                    }
                },
                rowWrapper = { i, item, content ->
                    val menuItems = mutableListOf(
                        ContextMenuItem("Menu for #$i ${item.key}", {}),
                        ContextMenuItem("Menu 2 for #$i ${item.key}", {}),
                    )
                    ContextMenuArea(items = { menuItems }) {
                        content()
                    }
                },
            ) { i, item ->
                val color = when (i) {
                    2 -> {
                        Color.Yellow.copy(0.5f)
                    }

                    else -> {
                        MaterialTheme.colorScheme.surface
                    }
                }
                columns.forEach { c ->
                    cell(
                        background = color,
                        columnInfo = c,
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),//.border(1.dp, Color.Gray),
                            text = item.data[c.title].toString(),
                            textAlign = mapAlign(c)
                        )
                    }

                }
            }
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(
                    scrollState = scrollState
                )
            )
        }
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