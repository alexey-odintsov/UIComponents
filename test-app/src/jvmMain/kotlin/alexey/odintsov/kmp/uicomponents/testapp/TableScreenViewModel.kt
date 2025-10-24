package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.table.ColumnInfo
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import kotlin.math.max

class TableScreenViewModel : ViewModel() {
    val columns = mutableStateMapOf(
        "Message" to ColumnInfo("Message", 0f, 1f, true),
        "Tag" to ColumnInfo("Tag", 60f, null, true),
        "Timestamp" to ColumnInfo("Timestamp", 100f, null, true),
    )

    val tags = listOf("System", "Monitoring", "App")
    val items = (1..100).map {
        val tag = tags.random()
        val message = ('A'..'z').map { it }.shuffled().subList(0, 40).joinToString("")
        LogItem(System.currentTimeMillis(), tag, message)
    }

    fun onColumnResized(columnKey: String, delta: Float) {
        val column = columns[columnKey]
        if (column != null) {
            val newSize = max(column.size + delta, 20f)
            columns[columnKey] = column.copy(size = newSize)
        }
    }

}