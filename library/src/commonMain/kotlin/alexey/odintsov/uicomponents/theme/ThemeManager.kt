package alexey.odintsov.uicomponents.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ThemeManager {
    var isDark = false
    private var currentTheme by mutableStateOf(SystemTheme(isDark))

    fun currentTheme(): Theme {
        return currentTheme
    }

    fun setIsDark(isDark: Boolean) {
        this.isDark = isDark
        currentTheme = SystemTheme(isDark)
    }

    @Composable
    fun AppTheme(content: @Composable () -> Unit) {
        ThemeApplier(theme = currentTheme) {
            content()
        }
    }

    @Composable
    fun CustomTheme(theme: Theme, content: @Composable () -> Unit) {
        ThemeApplier(theme = theme) {
            content()
        }
    }
}