package alexey.odintsov.uicomponents.theme

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp

@Composable
actual fun PlatformLocalCompositionProvider(
    theme: Theme,
    content: @Composable (() -> Unit),
) {
    val scrollbar = ScrollbarStyle(
        minimalHeight = 16.dp,
        thickness = 8.dp,
        shape = MaterialTheme.shapes.small,
        hoverDurationMillis = 300,
        unhoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        hoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.50f)
    )

    Surface(tonalElevation = 0.dp) { // defines bg tone from primary color and dark theme bg
        CompositionLocalProvider(
            LocalScrollbarStyle provides scrollbar,
            LocalMinimumInteractiveComponentSize provides 0.dp,
            content = content
        )
    }
}
