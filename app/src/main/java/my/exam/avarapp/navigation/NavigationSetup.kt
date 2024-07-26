package my.exam.avarapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import my.exam.avarapp.ui.account.LoginScreen
import my.exam.avarapp.ui.account.RegisterScreen
import my.exam.avarapp.ShowToast
import my.exam.avarapp.model.CategoryPhraseEntity
import my.exam.avarapp.model.TutorialEntity
import my.exam.avarapp.model.WordEntity
import my.exam.avarapp.ui.account.AccountScreen
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
    paddingValues: PaddingValues,
    wordsListState: MutableState<List<WordEntity>>,
    categoryPhraseEntityListState: MutableState<List<CategoryPhraseEntity>>,
    tutorialListState: MutableState<List<TutorialEntity>>,
    showToast: ShowToast,
) {
    NavHost(navController, startDestination = NavScreen.Dictionary.route) {
        composable(NavScreen.Dictionary.route) {
            DictionaryScreen(
                paddingValues, wordsListState
            )
        }
        composable(NavScreen.Phrasebook.route) {
            PhrasebookScreen(paddingValues, categoryPhraseEntityListState)
        }
        composable(NavScreen.Info.route) {
            InfoScreen()
        }

        composable(NavScreen.Tutorial.route) {
            TutorialScreen(navController, paddingValues, tutorialListState)
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
                paddingValues,
                tutorialListState,
                backstackEntry.arguments?.getInt("index")
            )
        }
        composable(NavScreen.Chat.route) {
            ChatScreen(
                authOptions = Action(navController).authOptions,
                account = Action(navController).account,
                back = Action(navController).navigateBack,
                paddingValues = paddingValues,
                showToast = showToast
            )
        }
        composable(NavScreen.AuthOptions.route) {
            AuthenticationOptionsScreen(
                paddingValues,
                register = Action(navController).register,
                login = Action(navController).login,
                back = Action(navController).navigateBack
            )
        }
        composable(NavScreen.Register.route) {
            RegisterScreen(
                chat = Action(navController).chat,
                back = Action(navController).navigateBack,
                showToast = showToast
            )
        }
        composable(NavScreen.Login.route) {
            LoginScreen(
                chat = Action(navController).chat,
                back = Action(navController).navigateBack,
                showToast = showToast
            )
        }
        composable(NavScreen.Account.route) {
            AccountScreen(Action(navController).navigateBack)
        }
    }
}
