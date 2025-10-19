package alexey.odintsov.kmp.uicomponents.theme

import androidx.compose.runtime.Composable

@Composable
expect fun PlatformLocalCompositionProvider(
    theme: Theme,
    content: @Composable () -> Unit,
)