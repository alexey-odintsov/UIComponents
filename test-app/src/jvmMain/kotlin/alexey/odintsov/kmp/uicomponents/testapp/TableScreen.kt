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


data class LogItem(
    val timestamp: Long,
    val tag: String,
    val message: String,
)

@Composable
fun TableScreen() {
    val tags = listOf("System", "Monitoring", "App")
    val items = remember {
        (1..100).map {
            val tag = tags.random()
            val message = ('A'..'z').map { it }.shuffled().subList(0, 40).joinToString("")
            LogItem(System.currentTimeMillis(), tag, message)
        }
    }

    val scrollState = remember { LazyListState() }

    Column(Modifier.padding(32.dp)) {
        Box {
            Table(
                items = items,
                columns = 3,
                modifier = Modifier.fillMaxSize(),
                scrollState = scrollState,
                header = {
                    cell(size = 100.dp) { Text("Timestamp") }
                    cell(size = 80.dp) { Text("Tag") }
                    cell(weight = 1f) { Text("Message") }
                }
            ) { i, item ->
                val color = if (i == 2) {
                    Color.Yellow.copy(0.5f)
                } else {
                    MaterialTheme.colorScheme.surface
                }

                cell(size = 100.dp, background = color) { Text(item.timestamp.toString()) }
                cell(size = 80.dp, background = color) { Text(item.tag) }
                cell(weight = 1f, background = color) {
                    Text(item.message)
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