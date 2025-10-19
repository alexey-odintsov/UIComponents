package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.table.TableCell
import alexey.odintsov.kmp.uicomponents.theme.ThemeManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    ThemeManager.AppTheme {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = {
                ThemeManager.setIsDark(!ThemeManager.isDark)
            }) {
                Text("Change theme to ${if (ThemeManager.isDark) "light" else "dark"}!")
            }
            TableCell(modifier = Modifier.fillMaxWidth()) {
                Text("Table cell")
            }
        }
    }
}