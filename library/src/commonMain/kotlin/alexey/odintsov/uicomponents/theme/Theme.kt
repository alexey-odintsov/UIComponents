package alexey.odintsov.uicomponents.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface Theme {
    fun isDark(): Boolean

    fun colorScheme(): ColorScheme

    fun colors(): Colors

    @Composable
    fun typography(): Typography

    @Composable
    fun shapes(): Shapes
}

data class Colors(
    val logRow: Color,
    val onLogRow: Color,
)