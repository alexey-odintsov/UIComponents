package alexey.odintsov.uicomponents.edit

import alexey.odintsov.uicomponents.preview.PreviewDarkAndLightTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties

@Composable
fun AutoCompleteEditText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    items: List<String>,
    isError: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onEnterClicked: () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        CustomEditText(
            value = value,
            onValueChange = {
                expanded = true
                onValueChange(it)
            },
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp),
            modifier = Modifier.fillMaxWidth()
                .onKeyEvent { e ->
                    when (e.key) {
                        Key.Enter if e.type == KeyEventType.KeyDown -> {
                            expanded = false
                            onEnterClicked()
                            true
                        }

                        Key.Enter -> {
                            true
                        }

                        else -> {
                            false
                        }
                    }
                },
            singleLine = singleLine,
            minLines = 1,
            maxLines = 1,
            interactionSource = interactionSource,
            isError = isError,
            label = label,
            visualTransformation = visualTransformation,
        )

        val filteringOptions = items.filter { it.contains(value, ignoreCase = true) }
        if (filteringOptions.isNotEmpty()) {
            DropdownMenu(
                modifier = Modifier.focusable(false).fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background).heightIn(0.dp, 200.dp),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
                properties = PopupProperties(focusable = false)
            ) {
                filteringOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .focusable(false)
                            .height(16.dp),
                        onClick = {
                            onValueChange(selectionOption)
                            expanded = false
                        },
                        text = {
                            Text(
                                text = selectionOption,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp,
                                lineHeight = 12.sp,
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewAutoCompleteEditText() {
    val value = remember { mutableStateOf("Text") }
    PreviewDarkAndLightTheme(true) {
        Column {
            AutoCompleteEditText(
                value = value.value,
                onValueChange = { v ->
                    value.value = v
                },
                onEnterClicked = {},
                isError = true,
                visualTransformation = HighlightTransformation(4, MaterialTheme.colorScheme.onError),
                items = mutableStateListOf("test", "abc", "def"),
            )
            AutoCompleteEditText(
                value = value.value,
                onValueChange = { v ->
                    value.value = v
                },
                onEnterClicked = {},
                isError = false,
                visualTransformation = HighlightTransformation(4, MaterialTheme.colorScheme.onError),
                items = mutableStateListOf("test", "abc", "def"),
            )
        }
    }
}