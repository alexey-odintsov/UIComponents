package alexey.odintsov.uicomponents

import androidx.compose.ui.input.pointer.PointerIcon
import java.awt.Cursor

actual fun provideIcon(type: PointerIconType): PointerIcon {
    return when (type) {
        PointerIconType.Resize -> PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR))
        PointerIconType.Hand -> PointerIcon(Cursor(Cursor.HAND_CURSOR))
    }
}