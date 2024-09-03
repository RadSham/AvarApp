package my.exam.avarapp.ui.tutorial

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import my.exam.avarapp.model.TutorialEntity

@Composable
fun TutorialLessonScreen(
    navController: NavHostController,
    padding: PaddingValues,
    tutorialEntity: MutableState<List<TutorialEntity>>,
    index: Int?
) {
    Box(modifier = Modifier.padding(padding)) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = tutorialEntity.value[index!!].tutorialchapter)
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.secondary,
                    elevation = 10.dp
                )
            }) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                TutorialLessonScreenTextAndTables(tutorialEntity.value[index!!])
            }
        }
    }
}

@Composable
fun TutorialLessonScreenTextAndTables(tutorialEntity: TutorialEntity) {
    val tablesStrings = tutorialEntity.tutorialtables.split("|||")
    val textsList = tutorialEntity.tutoriallesson.split("|table|")
    val customTextSelectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colors.secondary,
        backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.4f)
    )
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        SelectionContainer {
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                for (t in 0 until textsList.lastIndex) {
                    Text(
                        text = textsList[t].prependIndent("     "),
                        textAlign = TextAlign.Start
                    )
                    TutorialLessonScreenTable(tablesStrings[t])
                }
                Text(
                    text = textsList[textsList.lastIndex].prependIndent("     "),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Composable
fun TutorialLessonScreenTable(tableString: String) {
    val rowStrings = tableString.split("||")
    Column(modifier = Modifier.fillMaxWidth()) {
        for (rs in rowStrings) {
            val rowWords = rs.split("|")
            val rowWeight = 100 / (rowWords.size + 1).toFloat()
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
                    .border(1.dp, Color.Black),
                Arrangement.SpaceBetween
            ) {
                for (rowWord in rowWords) {
                    TableCell(rowWord, rowWeight)
                    Divider(
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxHeight()  //fill the max height
                            .width(1.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
            .padding(1.dp),
        style = LocalTextStyle.current.copy(hyphens = Hyphens.Auto)
    )
}


