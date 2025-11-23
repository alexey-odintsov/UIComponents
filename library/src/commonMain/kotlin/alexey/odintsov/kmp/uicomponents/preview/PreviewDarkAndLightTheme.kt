package alexey.odintsov.kmp.uicomponents.preview

import alexey.odintsov.kmp.uicomponents.theme.SystemTheme
import alexey.odintsov.kmp.uicomponents.theme.ThemeManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun PreviewDarkAndLightTheme(isVertical: Boolean, content: @Composable () -> Unit) {
    if (isVertical) {
        Column {
            Box(Modifier.weight(0.5f)) {
                ThemeManager.CustomTheme(SystemTheme(true)) {
                    content()
                }
            }
            Box(Modifier.weight(0.5f)) {
                ThemeManager.CustomTheme(SystemTheme(false)) {
                    content()
                }
            }
        }
    } else {
        Row {
            Box(Modifier.weight(0.5f)) {
                ThemeManager.CustomTheme(SystemTheme(true)) {
                    content()
                }
            }
            Box(Modifier.weight(0.5f)) {
                ThemeManager.CustomTheme(SystemTheme(false)) {
                    content()
                }
            }
        }
    }
}