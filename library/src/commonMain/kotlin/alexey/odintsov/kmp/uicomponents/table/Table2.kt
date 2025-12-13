package alexey.odintsov.kmp.uicomponents.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Renders table with resizable columns.
 */
@Composable
fun <T> Table2(
    items: SnapshotStateList<T>,
    scrollState: LazyListState,
    columns: SnapshotStateList<ColumnInfo>,
    resizable: Boolean = true,
    onColumnResized: (key: String, size: Float) -> Unit,
    headerRowWrapContent: @Composable (content: @Composable RowScope.() -> Unit) -> Unit = ::DefaultHeaderRowWrapContent,
    headerCellContent: @Composable (column: ColumnInfo) -> Unit,
    rowWrapContent: @Composable (index: Int, item: T, content: @Composable RowScope.() -> Unit) -> Unit = ::DefaultRowWrapContent,
    cellContent: @Composable (index: Int, column: ColumnInfo, T) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.border(1.dp, DividerDefaults.color),
        state = scrollState,
    ) {
        stickyHeader {
            headerRowWrapContent {
                columns.forEach { column ->
                    Row(
                        modifier = getSizeModifier(column)
                    ) {
                        headerCellContent(column)
                        ColumnResizerDivider(
                            modifier = Modifier.height(20.dp), // todo fill max height
                            resizable = resizable,
                            key = column.key,
                            onResized = onColumnResized,
                        )
                    }
                }
            }
            HorizontalDivider()
        }
        itemsIndexed(items) { i, item ->
            rowWrapContent(i, item) {
                columns.forEach { column ->
                    Box(
                        modifier = getSizeModifier(column)
                    ) {
                        cellContent(i, column, item)
                    }
                }
            }
            HorizontalDivider()
        }
    }
}

@Composable
fun DefaultHeaderRowWrapContent(content: @Composable RowScope.() -> Unit) {
    Row(Modifier.background(Color.White).fillMaxWidth()) {
        content()
    }
}

@Composable
fun <T> DefaultRowWrapContent(index: Int, item: T, content: @Composable RowScope.() -> Unit) {
    Row {
        content()
    }
}

private fun RowScope.getSizeModifier(column: ColumnInfo): Modifier {
    return when {
        column.size > 0f -> Modifier.width(column.size.dp)
        column.weight != null -> Modifier.weight(column.weight)
        else -> Modifier.weight(1f)
    }
}