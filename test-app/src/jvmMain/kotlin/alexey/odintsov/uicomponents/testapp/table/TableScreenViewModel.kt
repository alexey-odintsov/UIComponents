package alexey.odintsov.uicomponents.testapp.table

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlin.math.max

class TableScreenViewModel : ViewModel() {

    val selectedRowIndex = mutableStateOf<Int?>(null)
    val colors = mutableStateMapOf<Int, Color>()

    val items = (1..100).map {
        val message = ('A'..'z').map { it }.shuffled().subList(0, 40).joinToString("")
        val values = hashMapOf<String, String>(
            columns[0].key to System.currentTimeMillis().toString(),
            columns[1].key to tags.random(),
            columns[2].key to levels.random(),
            columns[3].key to message,
        )
        LogItem(message, values)
    }.toMutableStateList()

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