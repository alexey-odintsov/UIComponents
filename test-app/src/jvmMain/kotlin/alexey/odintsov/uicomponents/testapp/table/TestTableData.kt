package alexey.odintsov.uicomponents.testapp.table

import alexey.odintsov.uicomponents.table.ColumnAlign
import alexey.odintsov.uicomponents.table.ColumnInfo
import androidx.compose.runtime.mutableStateListOf

val tags = listOf("System", "Monitoring", "App")
val levels = listOf("V", "D", "W", "E", "F")

val columns = mutableStateListOf(
    ColumnInfo(
        key = "timestamp",
        title = "Timestamp",
        size = 100f,
        weight = null,
        visible = true,
        order = 0,
        align = ColumnAlign.Center,
        metaInfo = hashMapOf("Sortable" to "true"),
    ),
    ColumnInfo(
        key = "tag",
        title = "Tag",
        size = 60f,
        weight = null,
        visible = true,
        order = 1,
        align = ColumnAlign.Center
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