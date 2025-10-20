package alexey.odintsov.kmp.uicomponents.testapp

import alexey.odintsov.kmp.uicomponents.table.TableCell
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TableScreen() {
    TableCell(modifier = Modifier.fillMaxWidth()) {
        Text("Table cell")
    }

}