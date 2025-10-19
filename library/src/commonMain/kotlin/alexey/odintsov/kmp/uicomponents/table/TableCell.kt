package alexey.odintsov.kmp.uicomponents.table

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TableCell(
    modifier: Modifier = Modifier,
    backgroundColor: Color? = null,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = backgroundColor ?: Color.Transparent,
    ) {
        content()
    }
}