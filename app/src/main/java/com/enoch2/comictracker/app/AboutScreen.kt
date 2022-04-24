package com.enoch2.comictracker.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.enoch2.comictracker.R
import com.enoch2.comictracker.router.BackButtonHandler
import com.enoch2.comictracker.router.Router
import com.enoch2.comictracker.router.Screen

@Composable
fun AboutScreen() {
    BackButtonHandler {
        Router.navigateTo(Screen.HomeScreen)
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.about)) })},
        content = { Content() }
    )
}

@Composable
private fun Content() {
    /* TODO: Find how to get app version and add it to the about screen
     probably at the bottom of the screen */
    // val appVersion
    Column(modifier = Modifier.fillMaxSize()) {

    }
}