package alexey.odintsov.uicomponents.androidtestapp.components

import alexey.odintsov.uicomponents.table.ColumnInfo
import alexey.odintsov.uicomponents.table.Table
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TablePreview() {
    val columns = remember {
        listOf(
            ColumnInfo(key = "id", title = "ID", visible = true, order = 0, weight = 1f),
            ColumnInfo(key = "value", title = "Value", visible = true, order = 1, weight = 2f)
        )
    }
    val items = listOf("Data 1", "Data 2", "Data 3", "Data 4", "Data 5")
    val scrollState = rememberLazyListState()
    var selectedRow by remember { mutableStateOf<Int?>(null) }

    ComponentPreviewWrapper(title = "Table Preview") {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                "Standard Table",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Table(
                items = items,
                modifier = Modifier.fillMaxWidth().weight(1f),
                scrollState = scrollState,
                selectedRow = selectedRow,
                onRowSelected = { selectedRow = it },
                columns = columns,
                onColumnResized = { _, _ -> },
                header = {
                    columns.forEach { col ->
                        cell(col) { Text(col.title, style = MaterialTheme.typography.titleSmall) }
                    }
                }
            ) { index, item ->
                cell(columns[0]) { Text(index.toString()) }
                cell(columns[1]) { Text(item) }
            }
        }
    }
}
