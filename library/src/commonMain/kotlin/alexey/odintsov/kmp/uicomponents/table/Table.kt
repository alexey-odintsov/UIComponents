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

enum class ColumnAlign {
    Left, Center, Right,
}

data class ColumnInfo(
    val key: String,
    val title: String,
    val size: Float = 0f,
    val weight: Float? = 1f,
    val visible: Boolean,
    val order: Int,
    val align: ColumnAlign = ColumnAlign.Left,
    val metaInfo: HashMap<String, String> = hashMapOf()
)

data class CellParams(
    val columnInfo: ColumnInfo,
    val background: Color? = null,
    val composable: @Composable RowScope.() -> Unit,
)

@TableDsl
class TableRowBuilder() {
    internal val cells = mutableListOf<CellParams>()

    fun cell(
        columnInfo: ColumnInfo,
        background: Color? = null,
        content: @Composable () -> Unit
    ) {
        cells += CellParams(
            columnInfo = columnInfo,
            background = background
        ) {
            content()
        }
    }
}

@Composable
fun <T> Table(
    items: List<T>,
    modifier: Modifier = Modifier,
    scrollState: LazyListState,
    rowModifier: Modifier = Modifier,
    resizable: Boolean = true,
    selectedRow: Int?,
    onRowSelected: (Int) -> Unit,
    columns: SnapshotStateList<ColumnInfo>,
    onColumnResized: (String, Float) -> Unit,
    header: @Composable TableRowBuilder.() -> Unit,
    headerWrapper: @Composable (content: @Composable () -> Unit) -> Unit = { content -> content() },
    rowWrapper: @Composable (index: Int, item: T, content: @Composable () -> Unit) -> Unit = { i, t, content -> content() },
    content: @Composable TableRowBuilder.(index: Int, item: T) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = modifier.border(1.dp, DividerDefaults.color),
        state = scrollState,
    ) {
        stickyHeader {
            headerWrapper {
                val rowBuilder = TableRowBuilder().apply { header() }
                Row(modifier = rowModifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                    rowBuilder.cells
                        .filter { it.columnInfo.visible }
                        .sortedBy { it.columnInfo.order }
                        .forEachIndexed { i, cellParams ->
                            CellWrapper(
                                column = columns.firstOrNull { it.key == cellParams.columnInfo.key }
                                    ?: ColumnInfo("", "", visible = false, order = 0),
                                background = cellParams.background
                            ) {
                                cellParams.composable(this)
                            }

                            if (i < rowBuilder.cells.lastIndex) {
                                ColumnResizerDivider(
                                    resizable = resizable,
                                    key = cellParams.columnInfo.key,
                                    onResized = onColumnResized,
                                )
                            }
                        }
                }
            }
            HorizontalDivider()
        }
        itemsIndexed(items) { i, item ->
            val rowBuilder = TableRowBuilder().apply { content(i, item) }

            rowWrapper(i, item) {
                Row(
                    modifier = rowModifier.fillMaxWidth().height(IntrinsicSize.Min)
                        .onKeyEvent(onKeyEvent = { e ->
                            if (e.type == KeyEventType.KeyDown) {
                                return@onKeyEvent when (e.key) {
                                    Key.S, Key.DirectionDown -> {
                                        if (i < items.lastIndex) {
                                            focusManager.moveFocus(FocusDirection.Down)
                                        }
                                        true
                                    }

                                    Key.W, Key.DirectionUp -> {
                                        if (i > 0) {
                                            focusManager.moveFocus(FocusDirection.Up)
                                        }
                                        true
                                    }

                                    else -> false
                                }
                            }
                            false
                        })
                        .onFocusChanged { state ->
                            if (state.isFocused) {
                                rowBuilder.cells.firstOrNull()?.let {
                                    onRowSelected(i)
                                }
                            }
                        }
                        .selectable(
                            selected = false,
                            onClick = {
                                rowBuilder.cells.firstOrNull()?.let {
                                    onRowSelected(i)
                                }
                            }
                        )
                ) {
                    rowBuilder.cells
                        .filter { it.columnInfo.visible }
                        .sortedBy { it.columnInfo.order }
                        .forEachIndexed { j, cellParams ->
                            CellWrapper(
                                column = columns.firstOrNull { it.key == cellParams.columnInfo.key }
                                    ?: ColumnInfo("","", visible = false, order = 0),
                                isSelected = i == selectedRow,
                                background = cellParams.background
                            ) {
                                cellParams.composable(this)
                            }
                            if (j < rowBuilder.cells.lastIndex) {
                                ColumnResizerDivider(
                                    resizable = false,
                                    key = cellParams.columnInfo.key,
                                )
                            }
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