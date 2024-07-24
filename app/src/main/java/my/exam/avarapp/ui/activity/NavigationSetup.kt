package my.exam.avarapp.ui.activity

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import my.exam.avarapp.model.CategoryPhraseEntity
import my.exam.avarapp.model.NavScreen
import my.exam.avarapp.model.TutorialEntity
import my.exam.avarapp.model.WordEntity
import my.exam.avarapp.ui.tutorial.TutorialLessonScreen
import my.exam.avarapp.ui.tutorial.TutorialScreen
import my.exam.avarapp.ui.dictionary.DictionaryScreen
import my.exam.avarapp.ui.info.InfoScreen
import my.exam.avarapp.ui.phrasebook.PhrasebookScreen

@Composable
fun NavigationSetup(
    navController: NavHostController,
    padding: PaddingValues,
    wordsListState: MutableState<List<WordEntity>>,
    categoryPhraseEntityListState: MutableState<List<CategoryPhraseEntity>>,
    tutorialListState: MutableState<List<TutorialEntity>>,
) {
    NavHost(navController, startDestination = NavScreen.Dictionary.route) {
        composable(NavScreen.Dictionary.route) {
            DictionaryScreen(
                padding, wordsListState
            )
        }
        composable(NavScreen.Phrasebook.route) {
            PhrasebookScreen(padding, categoryPhraseEntityListState)
        }
        composable(NavScreen.Info.route) {
            InfoScreen()
        }

        composable(NavScreen.Tutorial.route) {
            TutorialScreen(navController, padding, tutorialListState)
        }

        composable(
            route = NavScreen.TutorialLesson.route + "/{index}",
            arguments = listOf(navArgument("index") {
                type = NavType.IntType
            }
            )
        ) { backstackEntry ->
            TutorialLessonScreen(navController, padding, tutorialListState, backstackEntry.arguments?.getInt("index"))
        }
    }
}
