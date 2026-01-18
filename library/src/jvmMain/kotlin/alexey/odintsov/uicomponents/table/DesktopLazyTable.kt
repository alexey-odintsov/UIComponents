package alexey.odintsov.uicomponents.table

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun <T> DesktopLazyTable(
    modifier: Modifier = Modifier,
    items: SnapshotStateList<T>,
    listState: LazyListState = rememberLazyListState(),
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
    requestLoadMessagesInRange: (Int, Int) -> Unit,
) {
    LaunchedEffect(listState.firstVisibleItemIndex, listState.layoutInfo.visibleItemsInfo.size) {
        val firstVisible = listState.firstVisibleItemIndex
        val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: firstVisible
        requestLoadMessagesInRange(firstVisible, lastVisible)
    }

    Box(modifier) {
        LazyTable(
            modifier = if (wrapContent) {
                Modifier.fillMaxHeight()
            } else {
                Modifier.fillMaxHeight().horizontalScroll(horizontalScrollState).width(maxWidth)
            },
            items = items,
            columns = columns,
            resizable = resizable,
            listState = listState,
            onColumnResized = onColumnResized,
            headerRowWrapContent = headerRowWrapContent,
            headerCellContent = headerCellContent,
            rowWrapContent = rowWrapContent,
            cellContent = cellContent,
        )
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState = listState)
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