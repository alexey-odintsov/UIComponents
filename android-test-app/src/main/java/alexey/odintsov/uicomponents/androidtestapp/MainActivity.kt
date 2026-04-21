package alexey.odintsov.uicomponents.androidtestapp

import alexey.odintsov.uicomponents.androidtestapp.components.StatusBarPreview
import alexey.odintsov.uicomponents.androidtestapp.components.Table2Preview
import alexey.odintsov.uicomponents.androidtestapp.components.TablePreview
import alexey.odintsov.uicomponents.theme.ThemeManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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

enum class Screen(val title: String, val route: String) {
    StatusBar("StatusBar", "statusBar"),
    Table("Table (Standard)", "table"),
    Table2("Table2 (Resizable)", "table2")
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
            MenuScreen(onNavigate = { route -> navController.navigate(route) })
        }
        Screen.entries.forEach { screen ->
            composable(screen.route) {
                when (screen) {
                    Screen.StatusBar -> StatusBarPreview()
                    Screen.Table -> TablePreview()
                    Screen.Table2 -> Table2Preview()
                }
            }
        }
    }
}

@Composable
fun MenuScreen(onNavigate: (String) -> Unit) {
    val items = Screen.entries
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            items(items) { item ->
                Text(
                    text = item.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(item.route) }
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMenuScreen() {
    ThemeManager.AppTheme {
        MenuScreen(onNavigate = {})
    }
}
