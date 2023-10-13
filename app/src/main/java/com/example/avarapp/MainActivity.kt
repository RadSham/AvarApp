package com.example.avarapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.avarapp.ui.AppBar
import com.example.avarapp.ui.BottomNavigationBar
import com.example.avarapp.ui.DrawerBody
import com.example.avarapp.ui.DrawerHeader
import com.example.avarapp.ui.MenuItem
import com.example.avarapp.ui.theme.AvarAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AvarAppTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        AppBar(onNavigationIconClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        })
                    },
                    //drag drawer by finger only if it open
//                    drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                    drawerContent = {
                        DrawerHeader()
                        DrawerBody(
                            items = listOf(
                                MenuItem("home", "Home", "Go to home", Icons.Default.Home),
                                MenuItem(
                                    "settings",
                                    "Settings",
                                    "Go to settings screen",
                                    Icons.Default.Settings
                                ),
                                MenuItem("help", "Help", "Get help", Icons.Default.Info)
                            ), onItemClick = {
                                when (it.id) {
                                    "settings" -> showToast("Setting")
                                    "help" -> showToast("help")
                                    "home" -> showToast("home")
                                }
                                println("Clicked on ${it.title}")
                            }
                        )
                    },
                    bottomBar = {
                        BottomNavigationBar(this, "Home")
                    }) { padding ->
                    Column(modifier = Modifier.padding(padding)) {
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}