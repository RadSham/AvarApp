package my.exam.avarapp.ui.info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.exam.avarapp.ui.theme.Gray

@Composable
fun InfoScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = infoText(),
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.Center),
            textAlign = TextAlign.Center
        )
        Text(
            buildAnnotatedString {
                append("Библиотека рагьизе ")
                withLink(
                    LinkAnnotation.Url(
                        "https://gofile.me/7AoF2/uGQoqQSlW",
                        TextLinkStyles(style = SpanStyle(color = Color.Blue))
                    )
                ) {
                    append("тIаде цуй")
                }
            },
            modifier = Modifier
                .padding(10.dp, bottom = 120.dp)
                .align(Alignment.BottomCenter),
            textAlign = TextAlign.Justify
        )
        Text(
            text = "VPN хIалтIизабичIого рагьиларо",
            modifier = Modifier
                .padding(10.dp, bottom = 100.dp)
                .align(Alignment.BottomCenter),
            color = Gray,
            fontSize = 14.sp,
            textAlign = TextAlign.Justify
        )
    }
}

fun infoText(): String {
    return "- \"Аварско-русский словарь\", Гимбатов М.М.;\n" +
            "- \"Русско-аварский словарь\", Алиханов С.З.;\n" +
            "- \"Русско-аварский разговорник\", Атаев Б.М.;\n" +
            "- \"Самоучитель аварского языка\", Атаев Б.М.;\n"
}
