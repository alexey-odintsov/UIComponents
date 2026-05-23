package alexey.odintsov.uicomponents.table

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TableTextCell(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Left,
    isHeader: Boolean = false,
    textColor: Color? = null,
    wrapContent: Boolean = false,
) {
    val evaluatedTextColor = textColor ?: Color.Unspecified

    if (wrapContent) {
        Text(
            modifier = Modifier.padding(end = 1.dp).then(modifier),
            textAlign = textAlign,
            fontSize = 10.sp,
            lineHeight = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight(if (isHeader) 600 else 400),
            softWrap = true,
            text = text,
            color = evaluatedTextColor,
        )
    } else {
        Text(
            modifier = Modifier.padding(end = 1.dp).then(modifier),
            maxLines = 1,
            textAlign = textAlign,
            fontSize = 10.sp,
            lineHeight = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight(if (isHeader) 600 else 400),
            text = text,
            color = evaluatedTextColor,
        )
    }
}