package alexey.odintsov.kmp.uicomponents.theme

import androidx.compose.runtime.Composable

@Composable
actual fun PlatformLocalCompositionProvider(
    theme: Theme,
    content: @Composable (() -> Unit),
) {
    content()
}