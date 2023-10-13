package com.example.avarapp.ui

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem (
    val id:String,
    val title:String,
    val contentDescriptor: String,
    val icon: ImageVector
)