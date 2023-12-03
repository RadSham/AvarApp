package com.example.avarapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.avarapp.model.NavItem
import com.example.avarapp.model.WordEntity

@Composable
fun NavigationSetup(
    navController: NavHostController,
    padding: PaddingValues,
    expanded: MutableState<Boolean>,
    selectedIndex: MutableState<Int>,
    languages: List<String>,
    query: MutableState<String>,
    wordsListState: MutableState<List<WordEntity>>,
    dialog: MutableState<Boolean>,
) {
    NavHost(navController, startDestination = NavItem.Dictionary.route) {
        composable(NavItem.Dictionary.route) {
            DictionaryScreen(padding, expanded, selectedIndex, languages, query, wordsListState,dialog)
        }
        composable(NavItem.Info.route) {
            InfoScreen()
        }
    }
}