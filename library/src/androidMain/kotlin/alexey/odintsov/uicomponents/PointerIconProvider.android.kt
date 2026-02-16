package alexey.odintsov.uicomponents

import androidx.compose.ui.input.pointer.PointerIcon

actual fun provideIcon(type: PointerIconType): PointerIcon {
    return when (type) {
        PointerIconType.Resize -> PointerIcon.Hand
        PointerIconType.Hand -> PointerIcon.Hand
    }
}