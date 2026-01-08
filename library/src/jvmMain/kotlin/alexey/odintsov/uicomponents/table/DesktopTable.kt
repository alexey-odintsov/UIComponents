package alexey.odintsov.uicomponents.table

import alexey.odintsov.uicomponents.table.ColumnInfo
import alexey.odintsov.uicomponents.table.DefaultCellContent
import alexey.odintsov.uicomponents.table.DefaultHeaderCellContent
import alexey.odintsov.uicomponents.table.DefaultHeaderRowWrapContent
import alexey.odintsov.uicomponents.table.DefaultRowWrapContent
import alexey.odintsov.uicomponents.table.Table2
import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun <T> DesktopTable(
    modifier: Modifier = Modifier,
    items: SnapshotStateList<T>,
    scrollState: LazyListState = rememberLazyListState(),
    horizontalScrollState: ScrollState,
    wrapContent: Boolean = true,
    maxWidth: Dp = 3000.dp,
    columns: SnapshotStateList<ColumnInfo>,
    resizable: Boolean = true,
    onColumnResized: (key: String, size: Float) -> Unit = { _, _ -> },
    headerRowWrapContent: @Composable (modifier: Modifier, content: @Composable RowScope.() -> Unit) -> Unit = ::DefaultHeaderRowWrapContent,
    headerCellContent: @Composable (column: ColumnInfo) -> Unit = ::DefaultHeaderCellContent,
    rowWrapContent: @Composable (modifier: Modifier, index: Int, item: T, content: @Composable RowScope.() -> Unit) -> Unit = ::DefaultRowWrapContent,
    cellContent: @Composable (index: Int, column: ColumnInfo, T) -> Unit = ::DefaultCellContent,
) {
    Box(modifier) {
        Table2(
            modifier = if (wrapContent) {
                Modifier.fillMaxHeight()
            } else {
                Modifier.fillMaxHeight().horizontalScroll(horizontalScrollState).width(maxWidth)
            },
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
        if (!wrapContent) {
            HorizontalScrollbar(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
                adapter = rememberScrollbarAdapter(
                    scrollState = horizontalScrollState
                )
            )
        }
    }
}