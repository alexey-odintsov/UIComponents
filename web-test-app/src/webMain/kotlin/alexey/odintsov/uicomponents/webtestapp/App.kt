package alexey.odintsov.uicomponents.webtestapp

import alexey.odintsov.uicomponents.StatusBar
import alexey.odintsov.uicomponents.tabs.TabsPanel
import alexey.odintsov.uicomponents.theme.ThemeManager
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

@Composable

fun App() {
    ThemeManager.AppTheme {
        var tabIndex by remember { mutableStateOf(1) }
        val tabs = remember {
            listOf("Buttons", "Table", "Edit", "Dialogs").toMutableStateList()
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

//            Box(Modifier.weight(1f)) {
//                when (tabIndex) {
//                    0 -> alexey.odintsov.uicomponents.testapp.ButtonsScreen()
//                    1 -> alexey.odintsov.uicomponents.testapp.TableScreen()
//                    2 -> alexey.odintsov.uicomponents.testapp.EditScreen()
//                    3 -> alexey.odintsov.uicomponents.testapp.DialogsScreen()
//                }
//            }
            StatusBar(progress = 0.5f, statusText = "Loading")
        }
    }
}

