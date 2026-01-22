package alexey.odintsov.uicomponents.edit

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle

class HighlightTransformation(private val highlightIndex: Int, private val color: Color) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val highlightedText = buildAnnotatedString {
            text.text.forEachIndexed { index, char ->
                if (index == highlightIndex) {
                    withStyle(SpanStyle(background = color)) {
                        append(char)
                    }
                } else {
                    append(char)
                }
            }
        }
        return TransformedText(highlightedText, OffsetMapping.Identity)
    }
}