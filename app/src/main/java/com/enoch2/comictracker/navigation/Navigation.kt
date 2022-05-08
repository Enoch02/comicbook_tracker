package com.enoch2.comictracker.navigation

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.enoch2.comictracker.ui.screen.*

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()


    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.AboutScreen.route) {
            AboutScreen(navController)
        }

        composable(Screen.AddComicScreen.route) {
            AddComicScreen(navController)
        }

        composable(
            route = Screen.ComicDetailScreen.route + "/{comicTitle}",
            arguments = listOf(
                navArgument("comicTitle") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { entry ->
            ComicDetailScreen(
                navController = navController,
                comicTitle = entry.arguments?.getString("comicTitle")
            )
        }

        composable(Screen.FilterScreen.route) {
            FilterScreen()
        }

        composable(Screen.MainScreen.route) {
            MainScreen(navController, context, scaffoldState, scope, listState)
        }

        composable(Screen.SettingScreen.route) {
            SettingScreen(navController, context)
        }
    }
}