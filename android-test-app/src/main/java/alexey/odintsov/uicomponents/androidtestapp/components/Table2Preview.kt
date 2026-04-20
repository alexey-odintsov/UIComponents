package alexey.odintsov.uicomponents.androidtestapp.components

import alexey.odintsov.uicomponents.table.ColumnInfo
import alexey.odintsov.uicomponents.table.Table2
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Table2Preview() {
    val columns = remember {
        listOf(
            ColumnInfo(key = "id", title = "ID", visible = true, order = 0, weight = 1f),
            ColumnInfo(key = "name", title = "Name", visible = true, order = 1, weight = 2f)
        )
    }
    val items = listOf("Element A", "Element B", "Element C")

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            Text("Table2 Preview", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))
            Table2(
                modifier = Modifier.fillMaxWidth().weight(1f),
                items = items,
                columns = columns
            )
        }
    }
}