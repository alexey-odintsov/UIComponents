package alexey.odintsov.kmp.uicomponents.table

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun ColumnResizerDivider(
    modifier: Modifier = Modifier,
    resizable: Boolean,
    key: String,
    onResized: ((String, Float) -> Unit) = { _, _ -> }
) {
    val finalModifier = if (resizable) {
        modifier
            .pointerHoverIcon(PointerIcon.Crosshair)
            .pointerInput("divider-$key") {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    onResized(key, dragAmount.x / 2f) // TODO: why is it 2x bigger?
                }
            }
    } else {
        modifier
    }

    VerticalDivider(modifier = finalModifier)
}