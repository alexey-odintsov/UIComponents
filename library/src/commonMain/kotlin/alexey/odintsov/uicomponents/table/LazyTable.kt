package alexey.odintsov.uicomponents.table

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Renders table with resizable columns.
 */
@Composable
fun <T> LazyTable(
    modifier: Modifier = Modifier,
    items: SnapshotStateList<T>,
    scrollState: LazyListState = rememberLazyListState(),
    columns: SnapshotStateList<ColumnInfo>,
    resizable: Boolean = true,
    onColumnResized: (key: String, size: Float) -> Unit = { _, _ -> },
    headerRowWrapContent: @Composable (modifier: Modifier, content: @Composable RowScope.() -> Unit) -> Unit = ::DefaultHeaderRowWrapContent,
    headerCellContent: @Composable (column: ColumnInfo) -> Unit = ::DefaultHeaderCellContent,
    rowWrapContent: @Composable (modifier: Modifier, index: Int, item: T, content: @Composable RowScope.() -> Unit) -> Unit = ::DefaultRowWrapContent,
    cellContent: @Composable (index: Int, column: ColumnInfo, T) -> Unit = ::DefaultCellContent,
) {
    Column(modifier = modifier.border(1.dp, DividerDefaults.color)) {
        headerRowWrapContent(Modifier.height(IntrinsicSize.Min)) {
            columns
                .filter { it.visible }
                .sortedBy { it.order }
                .forEach { column ->
                    Box(
                        modifier = getSizeModifier(column)
                    ) {
                        headerCellContent(column)
                    }
                    ColumnResizerDivider(
                        modifier = Modifier.fillMaxHeight(),
                        resizable = resizable,
                        key = column.key,
                        onResized = onColumnResized,
                    )
                }
        }
        HorizontalDivider()
        LazyColumn(
            state = scrollState,
        ) {
            itemsIndexed(items) { i, item ->
                rowWrapContent(Modifier.fillMaxWidth().height(IntrinsicSize.Min), i, item) {
                    columns
                        .filter { it.visible }
                        .sortedBy { it.order }
                        .forEach { column ->
                            Box(
                                modifier = getSizeModifier(column)
                            ) {
                                cellContent(i, column, item)
                            }
                            VerticalDivider(Modifier.fillMaxHeight())
                        }
                }
                HorizontalDivider()
            }
        }
    }
}


private fun RowScope.getSizeModifier(column: ColumnInfo): Modifier {
    return when {
        column.size > 0f -> Modifier.width(column.size.dp)
        column.weight != null -> Modifier.weight(column.weight)
        else -> Modifier.weight(1f)
    }
}


@Preview
@Composable
private fun PreviewLazyTable() {
    data class LogItem(
        val key: Int,
        val data: Map<String, String>,
    )

    data class MessageOffset(
        val id: Int,
        val offset: Long,
    )

    val columns = mutableStateListOf(
        ColumnInfo(
            key = "timestamp",
            title = "Timestamp",
            size = 100f,
            weight = null,
            visible = true,
            order = 0,
            align = ColumnAlign.Left,
            metaInfo = hashMapOf("Sortable" to "true"),
        ),
        ColumnInfo(
            key = "tag",
            title = "Tag",
            size = 60f,
            weight = null,
            visible = true,
            order = 1
        ),
        ColumnInfo(
            key = "level",
            title = "Level",
            size = 30f,
            weight = null,
            visible = true,
            order = 2,
            align = ColumnAlign.Center
        ),
        ColumnInfo(
            key = "message",
            title = "Message",
            size = 0f,
            weight = 1f,
            visible = true,
            order = 3,
            align = ColumnAlign.Left, metaInfo = hashMapOf("Payload" to "true")
        ),
    )
    val tags = listOf("System", "Monitoring", "App")
    val levels = listOf("V", "D", "W", "E", "F")
    val indicesMap = mutableStateMapOf<Int, LogItem>()
    val items = mutableStateListOf<MessageOffset>()
    repeat(100) { i ->
        val message = ('A'..'z').map { it }.shuffled().subList(0, 40).joinToString("")
        val values = hashMapOf<String, String>(
            columns[0].key to System.currentTimeMillis().toString(),
            columns[1].key to tags.random(),
            columns[2].key to levels.random(),
            columns[3].key to message,
        )
        val offset = MessageOffset(i, i * 10 + 100L)
        indicesMap[i] = LogItem(i, values)
    }

    LazyTable<MessageOffset>(
        items = items,
        columns = columns,
    )
}
