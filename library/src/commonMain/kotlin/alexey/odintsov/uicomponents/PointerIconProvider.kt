package alexey.odintsov.uicomponents

import androidx.compose.ui.input.pointer.PointerIcon

enum class PointerIconType {
    Resize,
    Hand,
}

expect fun provideIcon(type: PointerIconType): PointerIcon