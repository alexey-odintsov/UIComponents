package alexey.odintsov.uicomponents.androidtestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import alexey.odintsov.uicomponents.theme.ThemeManager
import alexey.odintsov.uicomponents.StatusBar
import alexey.odintsov.uicomponents.table.Table
import alexey.odintsov.uicomponents.table.Table2
import alexey.odintsov.uicomponents.table.ColumnInfo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThemeManager.AppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
            MenuScreen(onNavigate = { route -> navController.navigate(route) })
        }
        composable("statusBar") {
            StatusBarPreview()
        }
        composable("table") {
            TablePreview()
        }
        composable("table2") {
            Table2Preview()
        }
    }
}

@Composable
fun MenuScreen(onNavigate: (String) -> Unit) {
    val items = listOf(
        "StatusBar" to "statusBar",
        "Table (Standard)" to "table",
        "Table2 (Resizable)" to "table2"
    )
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            items(items) { item ->
                Text(
                    text = item.first,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(item.second) }
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun StatusBarPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            Text("StatusBar Preview", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))
            StatusBar(progress = 0.5f, statusText = "Loading components...")
            StatusBar(progress = 0.8f, statusText = "Almost finished")
            StatusBar(progress = 0f, statusText = "Ready")
        }
    }
}

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

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            Text("Table Preview", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))
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
