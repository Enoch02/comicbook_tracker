package com.enoch2.comictracker.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.enoch2.comictracker.BuildConfig
import com.enoch2.comictracker.R
import com.enoch2.comictracker.ui.composables.ComicTrackerTopBar

const val VERSION_NAME = BuildConfig.VERSION_NAME

@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(
        topBar = {
            ComicTrackerTopBar(
                title = stringResource(R.string.about),
                navIcon = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                onClick = { navController.popBackStack() }
            )
        },
        content = {
            val context = LocalContext.current
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Enoch02"))

            Box(
                contentAlignment = Alignment.Center,
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = "app icon",
                        alignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        "version $VERSION_NAME",
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp),
                        thickness = 0.5.dp,
                    )
                    Text(
                        stringResource(R.string.designed_by_txt),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp)
                    )
                    Text(
                        stringResource(R.string.github),
                        textAlign = TextAlign.Center,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    SelectionContainer {
                        Text(
                            stringResource(R.string.github_link),
                            textAlign = TextAlign.Center,
                            color = Color.Blue,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { context.startActivity(intent) }
                        )
                    }
                }
            }
        }
    )
}
