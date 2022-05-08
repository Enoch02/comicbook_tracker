package com.enoch2.comictracker.navigation

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object AboutScreen : Screen("about_screen")
    object AddComicScreen : Screen("add_comic_screen")
    object FilterScreen : Screen("filter_screen")
    object SettingScreen : Screen("setting_screen")
    object ComicDetailScreen: Screen("comic_detail_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}