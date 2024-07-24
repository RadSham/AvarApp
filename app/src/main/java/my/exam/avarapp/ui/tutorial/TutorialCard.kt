package my.exam.avarapp.ui.tutorial

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import my.exam.avarapp.model.NavScreen
import my.exam.avarapp.model.TutorialEntity

@Composable
fun TutorialCard(
    navController: NavHostController,
    tutorialEntity: TutorialEntity,
    indexOfTutorialEntity: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .shadow(
                2.dp,
                ambientColor = MaterialTheme.colors.secondary,
                spotColor = MaterialTheme.colors.secondary
            ),
        shape = RoundedCornerShape(3.dp),
    ) {
        Box(modifier = Modifier.clickable(
            onClick = {
                //pass to TutorialLesson screen
                navController.navigate(NavScreen.TutorialLesson.route + "/" + indexOfTutorialEntity)
            }
        )) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .defaultMinSize(minHeight = 48.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.secondaryVariant,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = tutorialEntity.tutorialchapter)
            }
        }
    }
}