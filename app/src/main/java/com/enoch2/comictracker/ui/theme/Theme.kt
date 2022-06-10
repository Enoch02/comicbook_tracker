package com.enoch2.comictracker.ui.theme

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.enoch2.comictracker.domain.model.SettingsViewModel

private val DarkColorPalette = darkColors(
    primary = BlueGray400,
    primaryVariant = BlueGrayDark,
    secondary = IndigoDark
)

private val LightColorPalette = lightColors(
    primary = BlueGray400,
    primaryVariant = BlueGrayDark,
    secondary = IndigoLight,
    background = BlueGray100,
    surface = Color.White,
    onPrimary = Color.White

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ComicBookTrackerTheme(
    context: Context,
    darkTheme: Boolean = isSystemInDarkTheme(),
    settingsViewModel: SettingsViewModel = viewModel(),
    content: @Composable () -> Unit
) {
    val alwaysDark by settingsViewModel.getDarkModeValue(context).collectAsState(initial = false)
    val colors = if (darkTheme || alwaysDark) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}