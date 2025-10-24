package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.table.Table
import alexey.odintsov.kmp.uicomponents.theme.SystemTheme
import alexey.odintsov.kmp.uicomponents.theme.ThemeManager
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
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
                header = {
                    columns.forEach { key, c ->
                        cell(columnKey = c.title) { Text(c.title) }
                    }
                }
            ) { i, item ->
                val color = if (i == 2) {
                    Color.Yellow.copy(0.5f)
                } else {
                    MaterialTheme.colorScheme.surface
                }
                columns.forEach { key, c ->
                    when (c.title) {
                        "Timestamp" -> cell(
                            background = color,
                            columnKey = c.title
                        ) { Text(item.timestamp.toString()) }

                        "Tag" -> cell(background = color, columnKey = c.title) { Text(item.tag) }
                        "Message" -> cell(weight = 1f, background = color, columnKey = c.title) {
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