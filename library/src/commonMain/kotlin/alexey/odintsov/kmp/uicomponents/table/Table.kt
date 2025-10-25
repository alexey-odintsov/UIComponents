package alexey.odintsov.kmp.uicomponents.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@DslMarker
annotation class TableDsl

data class ColumnInfo(
    val title: String,
    val size: Float = 0f,
    val weight: Float? = 1f,
    val visible: Boolean,
)

data class CellContext(
    val columnKey: String,
    val composable: @Composable RowScope.() -> Unit,
)

@TableDsl
class TableRowBuilder(val columns: SnapshotStateList<ColumnInfo>) {
    internal val cells = mutableListOf<CellContext>()

    fun cell(
        modifier: Modifier = Modifier,
        columnKey: String,
        background: Color? = null,
        content: @Composable () -> Unit
    ) {
        cells += CellContext(columnKey, {
            val column = columns.firstOrNull { it.title == columnKey }
            Box(
                modifier = modifier.fillMaxHeight()
                    .then(
                        when {
                            column == null -> Modifier.weight(1f)
                            column.size > 0f -> Modifier.width(column.size.dp)
                            column.weight != null -> Modifier.weight(column.weight)
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
        })
    }
}

@Composable
fun <T> Table(
    items: List<T>,
    modifier: Modifier = Modifier,
    scrollState: LazyListState,
    rowModifier: Modifier = Modifier,
    resizable: Boolean = true,
    columns: SnapshotStateList<ColumnInfo>,
    onColumnResized: (String, Float) -> Unit,
    header: @Composable TableRowBuilder.() -> Unit,
    content: @Composable TableRowBuilder.(index: Int, item: T) -> Unit,
) {
    LazyColumn(
        modifier = modifier.border(1.dp, DividerDefaults.color),
        state = scrollState,
    ) {
        stickyHeader {
            val rowBuilder = TableRowBuilder(columns).apply { header() }
            Row(modifier = rowModifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                rowBuilder.cells.forEachIndexed { i, cellContent ->
                    cellContent.composable(this) // runs with the real RowScope
                    if (i < rowBuilder.cells.lastIndex) {
                        ColumnResizerDivider(
                            resizable = resizable,
                            key = cellContent.columnKey,
                            onResized = onColumnResized,
                        )
                    }
                }
            }
            HorizontalDivider()
        }
        itemsIndexed(items) { i, item ->
            val rowBuilder = TableRowBuilder(columns).apply { content(i, item) }

            Row(modifier = rowModifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                rowBuilder.cells.forEachIndexed { j, cellContent ->
                    cellContent.composable(this)
                    if (j < rowBuilder.cells.lastIndex) {
                        ColumnResizerDivider(
                            resizable = false,
                            key = cellContent.columnKey,
                        )
                    }
                }
            }
            HorizontalDivider()
        }
    }
}

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