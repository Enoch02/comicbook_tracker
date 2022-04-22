package com.enoch2.comictracker.app

import androidx.compose.runtime.Composable
import com.enoch2.comictracker.router.BackButtonHandler
import com.enoch2.comictracker.router.Router
import com.enoch2.comictracker.router.Screen

@Composable
fun AboutScreen() {
    BackButtonHandler {
        Router.navigateTo(Screen.HomeScreen)
    }
}