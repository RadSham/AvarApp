package my.exam.avarapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.pradyotprakash.flashchat.view.login.LoginScreen
import com.project.pradyotprakash.flashchat.view.register.RegisterScreen
import my.exam.avarapp.model.CategoryPhraseEntity
import my.exam.avarapp.model.TutorialEntity
import my.exam.avarapp.model.WordEntity
import my.exam.avarapp.ui.account.AuthenticationOptionsScreen
import my.exam.avarapp.ui.chat.ChatScreen
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
            TutorialLessonScreen(
                navController,
                padding,
                tutorialListState,
                backstackEntry.arguments?.getInt("index")
            )
        }
        composable(NavScreen.Chat.route) {
            ChatScreen(
                authOptions = if(true) Action(navController).authOptions else Action(navController).authOptions,
                back = Action(navController).navigateBack, padding
            )
        }
        composable(NavScreen.AuthOptions.route) {
            AuthenticationOptionsScreen(
                register = Action(navController).register,
                login = Action(navController).login
            )
        }
        composable(NavScreen.Register.route) {
            RegisterScreen(
                chat = Action(navController).chat,
                back = Action(navController).navigateBack
            )
        }
        composable(NavScreen.Login.route) {
            LoginScreen(
                chat = Action(navController).chat,
                back = Action(navController).navigateBack
            )
        }
    }
}
