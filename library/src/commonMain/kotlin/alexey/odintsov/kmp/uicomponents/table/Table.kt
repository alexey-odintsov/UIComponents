package alexey.odintsov.kmp.uicomponents.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@DslMarker
annotation class TableDsl

data class ColumnInfo(
    val title: String,
    val size: Float = 0f,
    val weight: Float? = 1f,
    val visible: Boolean,
    val order: Byte,
)

data class CellParams<K>(
    val columnInfo: ColumnInfo,
    val rowKey: K?,
    val background: Color? = null,
    val composable: @Composable RowScope.() -> Unit,
)

@TableDsl
class TableRowBuilder<K>() {
    internal val cells = mutableListOf<CellParams<K>>()

    fun cell(
        modifier: Modifier = Modifier,
        columnInfo: ColumnInfo,
        rowKey: K?,
        background: Color? = null,
        content: @Composable () -> Unit
    ) {
        cells += CellParams(
            columnInfo = columnInfo,
            rowKey = rowKey,
            background = background
        ) {
            content()
        }
    }
}

@Composable
fun <T, K> Table(
    items: List<T>,
    modifier: Modifier = Modifier,
    scrollState: LazyListState,
    rowModifier: Modifier = Modifier,
    resizable: Boolean = true,
    selectedRow: K?,
    onRowSelected: (K) -> Unit,
    columns: SnapshotStateList<ColumnInfo>,
    onColumnResized: (String, Float) -> Unit,
    header: @Composable TableRowBuilder<K>.() -> Unit,
    content: @Composable TableRowBuilder<K>.(index: Int, item: T) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = modifier.border(1.dp, DividerDefaults.color)
            .onKeyEvent(onKeyEvent = { e ->
                if (e.type == KeyEventType.KeyDown) {
                    return@onKeyEvent when (e.key) {
                        Key.S, Key.DirectionDown -> {
                            focusManager.moveFocus(FocusDirection.Down)
                            true
                        }

                        Key.W, Key.DirectionUp -> {
                            focusManager.moveFocus(FocusDirection.Up)
                            true
                        }

                        else -> false
                    }
                }
                false
            }),
        state = scrollState,
    ) {
        stickyHeader {
            val rowBuilder = TableRowBuilder<K>().apply { header() }
            Row(modifier = rowModifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                rowBuilder.cells.sortedBy{ it.columnInfo.order }.forEachIndexed { i, cellParams ->
                    CellWrapper(
                        column = columns.firstOrNull { it.title == cellParams.columnInfo.title }
                            ?: ColumnInfo("", visible = false, order = 0),
                        background = cellParams.background
                    ) {
                        cellParams.composable(this)
                    }

                    if (i < rowBuilder.cells.lastIndex) {
                        ColumnResizerDivider(
                            resizable = resizable,
                            key = cellParams.columnInfo.title,
                            onResized = onColumnResized,
                        )
                    }
                }
            }
            HorizontalDivider()
        }
        itemsIndexed(items) { i, item ->
            val rowBuilder = TableRowBuilder<K>().apply { content(i, item) }

            Row(
                modifier = rowModifier.fillMaxWidth().height(IntrinsicSize.Min)
                    .onFocusChanged { state ->
                        if (state.isFocused) {
                            rowBuilder.cells.firstOrNull()?.let {
                                if (it.rowKey != null) {
                                    onRowSelected(it.rowKey)
                                }
                            }
                        }
                    }
                    .selectable(
                        selected = false,
                        onClick = {
                            rowBuilder.cells.firstOrNull()?.let {
                                if (it.rowKey != null) {
                                    onRowSelected(it.rowKey)
                                }
                            }
                        }
                    )
            ) {
                rowBuilder.cells.sortedBy{ it.columnInfo.order }.forEachIndexed { j, cellParams ->
                    CellWrapper(
                        column = columns.firstOrNull { it.title == cellParams.columnInfo.title }
                            ?: ColumnInfo("", visible = false, order = 0),
                        isSelected = cellParams.rowKey == selectedRow,
                        background = cellParams.background
                    ) {
                        cellParams.composable(this)
                    }
                    if (j < rowBuilder.cells.lastIndex) {
                        ColumnResizerDivider(
                            resizable = false,
                            key = cellParams.columnInfo.title,
                        )
                    }
                }
            }
            HorizontalDivider()
        }
    }
}

@Composable
fun RowScope.CellWrapper(
    modifier: Modifier = Modifier,
    column: ColumnInfo,
    background: Color?,
    isSelected: Boolean = false,
    composable: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxHeight()
            .then(
                when {
                    column.size > 0f -> Modifier.width(column.size.dp)
                    column.weight != null -> Modifier.weight(column.weight)
                    else -> Modifier.weight(1f)
                }
            )
            .then(
                when {
                    isSelected -> Modifier.background(Color.Gray)
                    background != null -> Modifier.background(
                        background
                    )

                    else -> Modifier.background(
                        MaterialTheme.colorScheme.surface
                    )
                }
            )
            .padding(2.dp)
    ) {
        composable()
    }
}