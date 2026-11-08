package alexey.odintsov.uicomponents

import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.fromKeyword

@OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)
actual fun provideIcon(type: PointerIconType): PointerIcon {
    return when (type) {
        PointerIconType.Resize -> PointerIcon.fromKeyword("ew-resize")
        PointerIconType.Hand -> PointerIcon.Hand
    }
}
