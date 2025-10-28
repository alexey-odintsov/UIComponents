package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.table.ColumnInfo
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlin.math.max

class TableScreenViewModel : ViewModel() {
    val columns = mutableStateListOf(
        ColumnInfo("Timestamp", 100f, null, true),
        ColumnInfo("Tag", 60f, null, true),
        ColumnInfo("Message", 0f, 1f, true),
    )

    val selectedRow = mutableStateOf<Int>(0)

    val tags = listOf("System", "Monitoring", "App")
    val items = (1..100).map {
        val tag = tags.random()
        val message = ('A'..'z').map { it }.shuffled().subList(0, 40).joinToString("")
        LogItem(System.currentTimeMillis(), tag, message)
    }

    fun onRowSelected(i: Int) {
        selectedRow.value = i
    }

    fun onColumnResized(columnKey: String, delta: Float) {
        val column = columns.firstOrNull { it.title == columnKey }
        val index = columns.indexOf(column)
        if (column != null) {
            val newSize = max(column.size + delta, 20f)
            columns[index] = column.copy(size = newSize)
        }
    }

}