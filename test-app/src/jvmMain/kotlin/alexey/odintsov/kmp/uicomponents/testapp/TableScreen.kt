package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.table.Table
import alexey.odintsov.kmp.uicomponents.theme.SystemTheme
import alexey.odintsov.kmp.uicomponents.theme.ThemeManager
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    val items = remember {
        listOf(
            LogItem(System.currentTimeMillis(), "System", "Start process 3566"),
            LogItem(System.currentTimeMillis(), "Monitoring", "CPU0: 14%, CPU1: 23%"),
            LogItem(System.currentTimeMillis(), "System", "Kill process 245"),
            LogItem(System.currentTimeMillis(), "App", "Starting"),
            LogItem(System.currentTimeMillis(), "App", "Calculating"),
            LogItem(System.currentTimeMillis(), "System", "Kill process 234324"),
            LogItem(System.currentTimeMillis(), "App", "Closing"),
        )
    }

    Column(Modifier.padding(32.dp)) {
        Table(
            items = items,
            columns = 3,
            modifier = Modifier.fillMaxSize(),
            header = {
                cell(size = 100.dp) { Text("Timestamp") }
                cell(size = 80.dp) { Text("Tag") }
                cell(weight = 1f) { Text("Message") }
            }
        ) { i, item ->
            val color = if (i == 2) {
                Color.Yellow
            } else {
                MaterialTheme.colorScheme.surface
            }

            cell(size = 100.dp, background = color) { Text(item.timestamp.toString()) }
            cell(size = 80.dp, background = color) { Text(item.tag) }
            cell(weight = 1f, background = color) {
                Text(item.message)
            }
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