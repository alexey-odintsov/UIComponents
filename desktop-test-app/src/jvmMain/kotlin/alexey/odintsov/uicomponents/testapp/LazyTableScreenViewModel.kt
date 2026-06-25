package alexey.odintsov.uicomponents.testapp

import alexey.odintsov.uicomponents.table.ColumnAlign
import alexey.odintsov.uicomponents.table.ColumnInfo
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.math.max


data class MessageOffset(
    val id: Int,
    val offset: Int,
)

const val TOTAL_MESSAGES_COUNT = 10000

class LazyTableScreenViewModel : ViewModel() {
    val columns = mutableStateListOf(
        ColumnInfo(
            key = "id",
            title = "Id",
            size = 60f,
            weight = null,
            visible = true,
            order = 0,
            align = ColumnAlign.Center,
        ),
        ColumnInfo(
            key = "timestamp",
            title = "Timestamp",
            size = 100f,
            weight = null,
            visible = true,
            order = 1,
            align = ColumnAlign.Center,
            metaInfo = hashMapOf("Sortable" to "true"),
        ),
        ColumnInfo(
            key = "tag",
            title = "Tag",
            size = 60f,
            weight = null,
            visible = true,
            order = 2,
            align = ColumnAlign.Center
        ),
        ColumnInfo(
            key = "level",
            title = "Level",
            size = 30f,
            weight = null,
            visible = true,
            order = 3,
            align = ColumnAlign.Center
        ),
        ColumnInfo(
            key = "message",
            title = "Message",
            size = 0f,
            weight = 1f,
            visible = true,
            order = 4,
            align = ColumnAlign.Left, metaInfo = hashMapOf("Payload" to "true")
        ),
    )

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Main + viewModelJob)
    val selectedRowIndex = mutableStateOf<Int?>(null)
    val colors = mutableStateMapOf<Int, Color>()

    val tags = listOf("System", "Monitoring", "App")
    val levels = listOf("V", "D", "W", "E", "F")
    val indices = mutableStateListOf<MessageOffset>()
    private val messages = mutableStateMapOf<Int, LogItem>()
    private val cachedMessages = mutableStateMapOf<Int, LogItem?>()

    init {
        repeat(TOTAL_MESSAGES_COUNT) { i ->
            val id = i
            val message = ('A'..'z').map { it }.shuffled().subList(0, 40).joinToString("")
            val values = hashMapOf<String, String>(
                columns[0].key to id.toString(),
                columns[1].key to System.currentTimeMillis().toString(),
                columns[2].key to tags.random(),
                columns[3].key to levels.random(),
                columns[4].key to message,
            )
            messages[i] = LogItem(id.toString(), values)
        }
        repeat(TOTAL_MESSAGES_COUNT) { i ->
            indices.add(MessageOffset(i, i))
        }
    }

    fun loadMessagesInRange(first: Int, last: Int) {
        println("Loading messages from $first to $last")
        viewModelScope.launch {
            for (i in first..last) {
                val key = indices[i].id
                cachedMessages[key] = getMessage(key)
            }
        }
    }

    private fun getMessage(key: Int): LogItem? {
        return messages[key]
    }

    fun getMessageByIndex(index: Int): LogItem? {
        return cachedMessages[index]
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