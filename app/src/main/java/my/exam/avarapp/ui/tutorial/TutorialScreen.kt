package my.exam.avarapp.ui.tutorial

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import my.exam.avarapp.model.TutorialEntity

@Composable
fun TutorialScreen(
    navController: NavHostController,
    padding: PaddingValues,
    tutorialListState: MutableState<List<TutorialEntity>>
) {
    Column(modifier = Modifier.padding(padding)) {
        Text(
            text = "САМОУЧИТЕЛЬ АВАРСКОГО ЯЗЫКА",
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .wrapContentHeight(align = Alignment.CenterVertically),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.secondary,
        )
        LazyColumn(Modifier.fillMaxSize()) {
            itemsIndexed(tutorialListState.value) { _, tutorialEntity ->
                TutorialCard(
                    navController,
                    tutorialEntity,
                    tutorialListState.value.indexOf(tutorialEntity)
                )
            }
        }
    }
}

