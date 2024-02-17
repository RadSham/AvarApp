package my.exam.avarapp.model

import my.exam.avarapp.R


sealed class NavItem(var route: String, var icon: Int) {
    object Dictionary : NavItem("Dictionary", R.drawable.ic_dictionary)
    object Info : NavItem("Info", R.drawable.info)
}
