package alexey.odintsov.uicomponents.testapp

import alexey.odintsov.uicomponents.StatusBar
import alexey.odintsov.uicomponents.tabs.TabsPanel
import alexey.odintsov.uicomponents.theme.ThemeManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

data class TabInfo(val index: Int, val title: String, val key: String)

@Composable
@Preview
fun App() {
    ThemeManager.AppTheme {
        val tabs = remember {
            linkedMapOf(
                "buttons" to TabInfo(0, "Buttons", "buttons"),
                "table" to TabInfo(1, "Table", "table"),
                "lazy_table" to TabInfo(2, "Lazy table", "lazy_table"),
                "edit" to TabInfo(3, "Edit", "edit"),
                "dialogs" to TabInfo(4, "Dialogs", "dialogs"),
            )
        }
        var currentTabKey by remember { mutableStateOf("table") }

        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
        ) {
            Button(onClick = {
                ThemeManager.setIsDark(!ThemeManager.isDark)
            }) {
                Text("Change theme to ${if (ThemeManager.isDark) "light" else "dark"}!")
            }

            val tabsList = tabs.values.toList()
            TabsPanel(
                tabIndex = tabs[currentTabKey]?.index ?: 0,
                tabs = tabsList.map { it.title },
                callback = { i -> currentTabKey = tabsList[i].key }
            )

            Box(Modifier.weight(1f)) {
                when (currentTabKey) {
                    "buttons" -> ButtonsScreen()
                    "table" -> TableScreen()
                    "lazy_table" -> LazyTableScreen()
                    "edit" -> EditScreen()
                    "dialogs" -> DialogsScreen()
                }
            }
            StatusBar(progress = 0.5f, statusText = "Loading")
        }
    }
}
