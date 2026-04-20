package alexey.odintsov.uicomponents.androidtestapp.components

import alexey.odintsov.uicomponents.StatusBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatusBarPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            Text("StatusBar Preview", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))
            StatusBar(progress = 0.5f, statusText = "Loading components...")
            StatusBar(progress = 0.8f, statusText = "Almost finished")
            StatusBar(progress = 0f, statusText = "Ready")
        }
    }
}