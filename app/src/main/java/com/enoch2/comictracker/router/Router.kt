package com.enoch2.comictracker.router

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    object HomeScreen: Screen()
    object AboutScreen: Screen()
    object AddComicScreen: Screen()
    object SettingScreen: Screen()
}

object Router {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.HomeScreen)

    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }
}
