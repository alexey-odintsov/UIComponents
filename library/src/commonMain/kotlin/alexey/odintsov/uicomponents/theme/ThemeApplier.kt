package alexey.odintsov.uicomponents.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


/**
 * Applies a theme on top of MaterialTheme
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ThemeApplier(
    theme: Theme,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = theme.colorScheme(),
        typography = theme.typography(),
        shapes = theme.shapes()
    ) {

        PlatformLocalCompositionProvider(
            theme = theme,
            content = content,
        )
    }
}
