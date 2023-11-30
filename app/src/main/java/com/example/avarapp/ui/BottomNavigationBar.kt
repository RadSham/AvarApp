package com.example.avarapp.ui

import android.app.Activity
import android.content.Intent
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.avarapp.DictionaryActivity
import com.example.avarapp.model.NavigationItem

@Composable
fun BottomNavigationBar(context: Activity, selectedItemTitle: String) {
    val items = listOf(
        NavigationItem.Dictionary,
        NavigationItem.Info
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary, contentColor = MaterialTheme.colors.onPrimary    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = MaterialTheme.colors.onPrimary.copy(0.4f),
                alwaysShowLabel = false,
                selected = (selectedItemTitle == item.title),
                onClick = {
                    /* Add code later */
                    if (item.title != selectedItemTitle)
                        when (item.title) {


                            "Dictionary" -> context.startActivity(
                                Intent(
                                    context,
                                    DictionaryActivity::class.java
                                )
                            )
                            /*"Info" -> context.startActivity(
                                Intent(
                                    context,
                                    MainActivity::class.java
                                )
                            )*/
                        }
                }
            )
        }
    }
}