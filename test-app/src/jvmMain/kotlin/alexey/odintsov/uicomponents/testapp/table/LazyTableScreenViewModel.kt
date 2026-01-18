package alexey.odintsov.uicomponents.testapp.table

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
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

    val selectedRowIndex = mutableStateOf<Int?>(null)

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Main + viewModelJob)
    val indices = mutableStateListOf<MessageOffset>()
    private val messages = mutableStateMapOf<Int, LogItem>()
    private val cachedMessages = mutableStateMapOf<Int, LogItem?>()


    init {
        repeat(TOTAL_MESSAGES_COUNT) { i ->
            val id = 23000 + i
            val message = ('A'..'z').map { it }.shuffled().subList(0, 40).joinToString("")
            val values = hashMapOf<String, String>(
                columns[Columns.ID.id].key to id.toString(),
                columns[Columns.TIMESTAMP.id].key to System.currentTimeMillis().toString(),
                columns[Columns.TAG.id].key to tags.random(),
                columns[Columns.LEVEL.id].key to levels.random(),
                columns[Columns.MESSAGE.id].key to message,
            )
            messages[i] = LogItem(id, values)
        }
        repeat(TOTAL_MESSAGES_COUNT) { i ->
            indices.add(MessageOffset(i, i))
        }
    }

    fun onColumnResized(key: String, delta: Float) {
        val column = columns.firstOrNull { it.key == key }
        val index = columns.indexOf(column)
        if (column != null) {
            val newSize = max(column.size + delta, 20f)
            columns[index] = column.copy(size = newSize)
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

}