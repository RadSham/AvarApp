package com.example.avarapp.model

import com.example.avarapp.R

sealed class NavigationItem(var icon: Int, var title: String) {
    object Dictionary : NavigationItem(R.drawable.ic_dictionary, "Dictionary")
    object Info : NavigationItem( R.drawable.info, "Info")
}
