package alexey.odintsov.kmp.uicomponents.table

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun <T> DesktopTable(
    modifier: Modifier = Modifier,
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
    Box(modifier) {
        Table2(
            modifier = modifier,
            items = items,
            columns = columns,
            resizable = resizable,
            scrollState = scrollState,
            onColumnResized = onColumnResized,
            headerRowWrapContent = headerRowWrapContent,
            headerCellContent = headerCellContent,
            rowWrapContent = rowWrapContent,
            cellContent = cellContent,
        )
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState = scrollState)
        )
    }
}