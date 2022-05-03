package com.enoch2.comictracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.enoch2.comictracker.app.AboutScreen
import com.enoch2.comictracker.app.AddComicScreen
import com.enoch2.comictracker.app.HomeScreen
import com.enoch2.comictracker.app.SettingScreen
import com.enoch2.comictracker.router.Router
import com.enoch2.comictracker.router.Screen
import com.enoch2.comictracker.ui.theme.ComicBookTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            val listState = rememberLazyListState()

            ComicBookTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Crossfade(targetState = Router.currentScreen) { screenState ->
                        when (screenState.value) {
                            is Screen.HomeScreen -> HomeScreen(context, scaffoldState, scope, listState)
                            is Screen.AboutScreen -> AboutScreen()
                            is Screen.AddComicScreen -> AddComicScreen()
                            is Screen.SettingScreen -> SettingScreen()
                        }
                    }
                }
            }
        }
    }
}
