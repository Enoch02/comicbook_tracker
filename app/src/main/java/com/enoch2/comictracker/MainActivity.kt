package com.enoch2.comictracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.enoch2.comictracker.ui.screen.*
import com.enoch2.comictracker.ui.theme.ComicBookTrackerTheme


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
                    val navController = rememberNavController()
                    val context = LocalContext.current
                    val scaffoldState = rememberScaffoldState()
                    val scope = rememberCoroutineScope()
                    val listState = rememberLazyListState()

                    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
                        composable(Screen.AboutScreen.route) {
                            AboutScreen(navController)
                        }

                        composable(Screen.AddComicScreen.route) {
                            AddComicScreen(
                                navController,
                                context,
                                scope
                            )
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
                                navController,
                                entry.arguments?.getString("comicTitle"),
                                context
                            )
                        }

                        composable(
                            route = Screen.EditComicScreen.route +
                                    "/{comicTitle}/{status}/{rating}/{issuesRead}/{totalIssues}/{id}",
                            arguments = listOf(
                                navArgument("comicTitle") {
                                    type = NavType.StringType
                                    nullable = true
                                },
                                navArgument("status") {
                                    type = NavType.StringType
                                    nullable = true
                                },
                                navArgument("rating") {
                                    type = NavType.FloatType
                                },
                                navArgument("issuesRead") {
                                    type = NavType.StringType
                                    nullable = true
                                },
                                navArgument("totalIssues") {
                                    type = NavType.StringType
                                    nullable = true
                                },
                                navArgument("id") {
                                    type = NavType.StringType
                                    nullable = true
                                }
                            )
                        ) { entry ->
                            EditComicScreen(
                                navController,
                                context,
                                entry.arguments?.getString("comicTitle"),
                                entry.arguments?.getString("status"),
                                entry.arguments?.getFloat("rating"),
                                entry.arguments?.getString("issuesRead"),
                                entry.arguments?.getString("totalIssues"),
                                entry.arguments?.getString("id")
                            )
                        }

                        composable(Screen.FilterScreen.route) {
                            FilterScreen()
                        }

                        composable(Screen.HomeScreen.route) {
                            HomeScreen(
                                navController,
                                context,
                                scaffoldState,
                                scope,
                                listState
                            )
                        }

                        composable(Screen.SettingScreen.route) {
                            SettingScreen(
                                navController,
                                context
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object AboutScreen : Screen("about_screen")
    object AddComicScreen : Screen("add_comic_screen")
    object FilterScreen : Screen("filter_screen")
    object SettingScreen : Screen("setting_screen")
    object ComicDetailScreen: Screen("comic_detail_screen")
    object EditComicScreen: Screen("edit_comic_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
