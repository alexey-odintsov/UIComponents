package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.table.ColumnAlign
import alexey.odintsov.kmp.uicomponents.table.ColumnInfo
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlin.math.max

class TableScreenViewModel : ViewModel() {
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
        ColumnInfo(key = "tag", title = "Tag", size = 60f, weight = null, visible = true, order = 1),
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

    val selectedRowIndex = mutableStateOf<Int?>(null)
    val colors = mutableStateMapOf<Int, Color>()

    val tags = listOf("System", "Monitoring", "App")
    val levels = listOf("V", "D", "W", "E", "F")
    val items = (1..100).map {
        val message = ('A'..'z').map { it }.shuffled().subList(0, 40).joinToString("")
        val values = hashMapOf<String, String>(
            columns[0].key to System.currentTimeMillis().toString(),
            columns[1].key to tags.random(),
            columns[2].key to levels.random(),
            columns[3].key to message,
            )
        LogItem(message, values)
    }

    fun changeColor(index: Int, color: Color) {
        colors[index] = color
    }

    fun onRowSelected(index: Int) {
        selectedRowIndex.value = index
    }

    fun onColumnResized(key: String, delta: Float) {
        val column = columns.firstOrNull { it.key == key }
        val index = columns.indexOf(column)
        if (column != null) {
            val newSize = max(column.size + delta, 20f)
            columns[index] = column.copy(size = newSize)
        }
    }

}