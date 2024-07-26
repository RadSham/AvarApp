package my.exam.avarapp.navigation

import androidx.navigation.NavHostController
import my.exam.avarapp.R

/**
 * A set of destination used in the whole application
 */
sealed class NavScreen(var route: String, var icon: Int) {
    object Dictionary : NavScreen("Dictionary", R.drawable.ic_dictionary)
    object Phrasebook : NavScreen("Phrasebook", R.drawable.ic_phrasebook)
    object Info : NavScreen("Info", R.drawable.ic_info)
    object Tutorial : NavScreen("Tutorial", R.drawable.ic_tutorial)
    object TutorialLesson : NavScreen("TutorialLesson", R.drawable.ic_tutorial)
    object Chat : NavScreen("Chat", R.drawable.ic_chat)
    object AuthOptions : NavScreen("ChatAuthOptions", R.drawable.ic_accounts)
    object Login : NavScreen("ChatLogin", R.drawable.ic_accounts)
    object Register : NavScreen("ChatRegister", R.drawable.ic_accounts)
    object Account : NavScreen("ChatAccount", R.drawable.ic_accounts)
}

/**
 * Set of routes which will be passed to different composable so that
 * the routes which are required can be taken.
 */
class Action(navController: NavHostController) {
    val chat: () -> Unit = {
        navController.navigate(NavScreen.Chat.route) {
            popUpTo(NavScreen.Login.route) {
                inclusive = true
            }
            popUpTo(NavScreen.Register.route) {
                inclusive = true
            }
            popUpTo(NavScreen.Account.route) {
                inclusive = true
            }
            popUpTo(NavScreen.AuthOptions.route) {
                inclusive = true
            }
        }
    }
    val login: () -> Unit = { navController.navigate(NavScreen.Login.route) }
    val register: () -> Unit = { navController.navigate(NavScreen.Register.route) }
    val authOptions: () -> Unit = { navController.navigate(NavScreen.AuthOptions.route) }
    val account: () -> Unit = { navController.navigate(NavScreen.Account.route) }
    val navigateBack: () -> Unit = { navController.popBackStack() }
}