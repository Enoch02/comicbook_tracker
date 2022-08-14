package com.enoch2.comictracker.ui.screen

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.enoch2.comictracker.R
import com.enoch2.comictracker.Screen
import com.enoch2.comictracker.domain.model.Comic
import com.enoch2.comictracker.domain.model.ComicTrackerViewModel
import com.enoch2.comictracker.domain.model.ComicTrackerViewModelFactory
import com.enoch2.comictracker.ui.composables.ComicTrackerTopBar
import com.enoch2.comictracker.ui.theme.White
import java.io.File

@Composable
fun ComicDetailScreen(
    navController: NavController,
    id: Int,
    context: Context
) {
    val viewModel: ComicTrackerViewModel = viewModel(
        factory = ComicTrackerViewModelFactory(context.applicationContext)
    )
    var comic by remember { mutableStateOf(Comic()) }
    LaunchedEffect(true) {
        comic = viewModel.getComic(id)
    }

    Scaffold(
        topBar = {
            ComicTrackerTopBar(
                title = "Comic Detail",
                navIcon = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                onClick = { navController.popBackStack() },
                actions = {
                    IconButton(
                        content = { Icon(Icons.Default.Edit, "edit", tint = White) },
                        onClick = {
                            navController.navigate(
                                Screen.EditComicScreen.withArgs(
                                    comic.title.toString(),
                                    comic.status.toString(),
                                    comic.rating.toString(),
                                    comic.issuesRead.toString(),
                                    comic.totalIssues.toString(),
                                    comic.id.toString(),
                                    comic.coverName.toString().ifEmpty {
                                        " "
                                    }
                                )
                            )
                        }
                    )
                    IconButton(
                        content = { Icon(Icons.Default.Delete, "delete", tint = White) },
                        onClick = {
                            navController.navigate(Screen.HomeScreen.route) {
                                popUpTo(Screen.HomeScreen.route)
                                viewModel.deleteComic(id)
                                viewModel.deleteOneCover(comic.coverName.toString())
                            }
                        }
                    )
                }
            )
        }
    ) {
        val constraints = ConstraintSet {
            val cover = createRefFor("cover")
            val status = createRefFor("status")
            val rating = createRefFor("rating")
            val issuesInfo = createRefFor("issues_info")

            constrain(cover) {
                top.linkTo(parent.top, 5.dp)
                bottom.linkTo(parent.bottom, 5.dp)
                start.linkTo(parent.start, 5.dp)
                end.linkTo(status.start)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }
            constrain(status) {
                top.linkTo(parent.top, 5.dp)
                bottom.linkTo(parent.bottom, 5.dp)
                start.linkTo(cover.end, 10.dp)
                end.linkTo(parent.end, 5.dp)
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
            }
            constrain(rating) {
                top.linkTo(status.bottom, 5.dp)
                bottom.linkTo(issuesInfo.top)
                start.linkTo(cover.end, 10.dp)
                end.linkTo(parent.end, 5.dp)
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
            }
            constrain(issuesInfo) {
                top.linkTo(rating.bottom, 10.dp)
                bottom.linkTo(parent.bottom, 5.dp)
                start.linkTo(cover.end, 10.dp)
                end.linkTo(parent.end, 5.dp)
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
            }
        }

        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    comic.title.toString(),
                    fontSize = 20.sp,
                    softWrap = true,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 10.dp
                        )
                )

                Spacer(Modifier.padding(vertical = 5.dp))

                Card(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    ConstraintLayout(constraints) {
                        val coverAlpha = if (comic.coverName?.isNotEmpty() == true) 1f else 0f
                        val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
                        val cover =
                            File(context.filesDir, comic.coverName.toString()).absolutePath

                        AsyncImage(
                            model = cover,
                            placeholder = painterResource(R.drawable.placeholder_image),
                            contentDescription = null,
                            error = painterResource(R.drawable.placeholder_image),
                            alignment = Alignment.TopStart,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .layoutId("cover")
                                .alpha(coverAlpha)
                        )
                        TextButton(
                            enabled = false,
                            onClick = {},
                            modifier = Modifier.layoutId("status"),
                            content = { Text(comic.status.toString(), color = textColor) }
                        )
                        TextButton(
                            enabled = false,
                            onClick = {},
                            modifier = Modifier.layoutId("rating"),
                            content = { Text("${comic.rating} / 10", color = textColor) }
                        )
                        TextButton(
                            enabled = false,
                            onClick = {},
                            modifier = Modifier.layoutId("issues_info"),
                            content = { Text(
                                "Issues Read: ${comic.issuesRead} / ${comic.totalIssues}",
                                color = textColor
                            ) }
                        )
                    }
                }

                Spacer(Modifier.padding(vertical = 5.dp))

                // TODO: Tap to edit
                Text(
                    text = stringResource(R.string.lorem),
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}
