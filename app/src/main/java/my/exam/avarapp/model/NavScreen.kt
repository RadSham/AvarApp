package my.exam.avarapp.model

import my.exam.avarapp.R


sealed class NavScreen(var route: String, var icon: Int) {
    object Dictionary : NavScreen("Dictionary", R.drawable.ic_dictionary)
    object Phrasebook : NavScreen("Phrasebook", R.drawable.phrasebook)
    object Info : NavScreen("Info", R.drawable.info)
    object Tutorial : NavScreen("Tutorial", R.drawable.tutorial)
    object TutorialLesson : NavScreen("TutorialLesson", R.drawable.tutorial)
}
