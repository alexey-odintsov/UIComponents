package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.table.Table
import alexey.odintsov.kmp.uicomponents.theme.SystemTheme
import alexey.odintsov.kmp.uicomponents.theme.ThemeManager
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
    val timestamp: Long,
    val tag: String,
    val message: String,
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
            Table(
                items = items,
                modifier = Modifier.fillMaxSize(),
                columns = columns,
                scrollState = scrollState,
                onColumnResized = onColumnResized,
                selectedRow = viewModel.selectedRow.value,
                onRowSelected = viewModel::onRowSelected,
                header = {
                    columns.forEach { c ->
                        cell(columnKey = c.title, rowKey = -999) {
                            Text(
                                modifier = Modifier.fillMaxSize(),
                                text = c.title
                            )
                        }
                    }
                }
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
                    when (c.title) {
                        "Timestamp" -> cell(
                            background = color,
                            columnKey = c.title,
                            rowKey = i,
                        ) {
                            Text(modifier = Modifier.border(1.dp, Color.Gray), text = item.timestamp.toString())
                        }

                        "Tag" -> cell(
                            background = color,
                            columnKey = c.title,
                            rowKey = i,
                        ) {
                            Text(
                                text = item.tag,
                                textAlign = TextAlign.Center
                            )
                        }

                        "Message" -> cell(
                            background = color,
                            columnKey = c.title,
                            rowKey = i,
                        ) {
                            Text(item.message)
                        }
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

@Preview
@Composable
fun PreviewTableScreen() {
    ThemeManager.CustomTheme(SystemTheme(true)) {
        TableScreen()
    }
}