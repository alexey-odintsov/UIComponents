package alexey.odintsov.uicomponents.theme

import androidx.compose.runtime.Composable

@Composable
actual fun PlatformLocalCompositionProvider(
    theme: Theme,
    content: @Composable (() -> Unit),
) {
    content()
}