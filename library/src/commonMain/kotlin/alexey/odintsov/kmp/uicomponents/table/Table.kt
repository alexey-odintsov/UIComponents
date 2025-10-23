package alexey.odintsov.kmp.uicomponents.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@DslMarker
annotation class TableDsl

@TableDsl
class TableRowBuilder {
    internal val cells = mutableListOf<@Composable RowScope.() -> Unit>()

    fun cell(
        modifier: Modifier = Modifier,
        size: Dp? = null,
        weight: Float? = null,
        background: Color? = null,
        content: @Composable () -> Unit
    ) {
        cells += {
            Box(
                modifier = modifier.fillMaxHeight()
                    .then(
                        when {
                            size != null -> Modifier.width(size)
                            weight != null -> Modifier.weight(weight)
                            else -> Modifier.weight(1f)
                        }
                    )
                    .then(
                        if (background != null) Modifier.background(background) else Modifier.background(
                            MaterialTheme.colorScheme.surface
                        )
                    )
                    .padding(2.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun <T> Table(
    items: List<T>,
    columns: Int,
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    header: @Composable TableRowBuilder.() -> Unit,
    content: @Composable TableRowBuilder.(index: Int, item: T) -> Unit
) {
    LazyColumn(modifier = modifier.border(1.dp, DividerDefaults.color)) {
        stickyHeader {
            val rowBuilder = TableRowBuilder().apply { header() }
            Row(modifier = rowModifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                rowBuilder.cells.forEachIndexed { i, cellContent ->
                    cellContent() // runs with the real RowScope
                    if (i < rowBuilder.cells.lastIndex) VerticalDivider()
                }
            }
            HorizontalDivider()
        }
        itemsIndexed(items) { i, item ->
            val rowBuilder = TableRowBuilder().apply { content(i, item) }

            Row(modifier = rowModifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                rowBuilder.cells.forEachIndexed { j, cellContent ->
                    cellContent()
                    if (j < rowBuilder.cells.lastIndex) VerticalDivider()
                }

                // Fill missing cells if fewer than columns
                if (rowBuilder.cells.size < columns) {
                    repeat(columns - rowBuilder.cells.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            HorizontalDivider()
        }
    }
}