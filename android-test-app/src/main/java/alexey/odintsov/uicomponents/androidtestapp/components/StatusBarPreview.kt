package alexey.odintsov.uicomponents.androidtestapp.components

import alexey.odintsov.uicomponents.StatusBar
import alexey.odintsov.uicomponents.theme.ThemeManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StatusBarPreview() {
    ComponentPreviewWrapper(title = "StatusBar Preview") {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                "StatusBar Examples",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            StatusBar(progress = 0.5f, statusText = "Loading components...")
            StatusBar(progress = 0.8f, statusText = "Almost finished")
            StatusBar(progress = 0f, statusText = "Ready")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStatusBarPreview() {
    ThemeManager.AppTheme {
        StatusBarPreview()
    }
}
