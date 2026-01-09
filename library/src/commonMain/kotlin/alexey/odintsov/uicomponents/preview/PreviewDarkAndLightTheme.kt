package alexey.odintsov.uicomponents.preview

import alexey.odintsov.uicomponents.theme.SystemTheme
import alexey.odintsov.uicomponents.theme.ThemeManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun PreviewDarkAndLightTheme(
    isVertical: Boolean,
    spacing: Dp = 10.dp,
    content: @Composable () -> Unit
) {
    if (isVertical) {
        Column(verticalArrangement = Arrangement.spacedBy(spacing)) {
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
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
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