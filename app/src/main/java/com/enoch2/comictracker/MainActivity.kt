package com.enoch2.comictracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.enoch2.comictracker.app.AboutScreen
import com.enoch2.comictracker.app.HomeScreen
import com.enoch2.comictracker.router.Router
import com.enoch2.comictracker.router.Screen
import com.enoch2.comictracker.ui.theme.ComicBookTrackerTheme
import com.enoch2.comictracker.util.doesDataExist

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComicBookTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Crossfade(targetState = Router.currentScreen) { screenState ->
                        when (screenState.value) {
                            is Screen.HomeScreen -> HomeScreen()
                            is Screen.AboutScreen -> AboutScreen()
                        }
                    }
                }
            }
        }
    }
}
