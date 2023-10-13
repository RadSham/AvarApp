package com.example.avarapp.model

import com.example.avarapp.R

sealed class NavigationItem(var icon: Int, var title: String) {
    object Home : NavigationItem(R.drawable.ic_home, "Home")
    object Dictionary : NavigationItem(R.drawable.ic_dictionary, "Dictionary")
    object MyAds : NavigationItem( R.drawable.ic_person, "My ads")
    object Events : NavigationItem(R.drawable.ic_new_ads, "Events")
}
