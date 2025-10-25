package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.StatusBar
import alexey.odintsov.kmp.uicomponents.tabs.TabsPanel
import alexey.odintsov.kmp.uicomponents.theme.ThemeManager
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
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    ThemeManager.AppTheme {
        var tabIndex by remember { mutableStateOf(0) }
        val tabs = remember {
            listOf("Buttons", "Table", "Edit").toMutableStateList()
        }

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
            TabsPanel(tabIndex, tabs, { i -> tabIndex = i })

            Box(Modifier.weight(1f)) {
                when (tabIndex) {
                    0 -> ButtonsScreen()
                    1 -> TableScreen()
                    2 -> EditScreen()
                }
            }
            StatusBar(progress = 0.5f, statusText = "Loading")
        }
    }
}