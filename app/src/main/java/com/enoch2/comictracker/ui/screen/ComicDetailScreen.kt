package com.enoch2.comictracker.ui.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.enoch2.comictracker.R
import com.enoch2.comictracker.Screen
import com.enoch2.comictracker.domain.model.Comic
import com.enoch2.comictracker.domain.model.ComicTrackerViewModel
import com.enoch2.comictracker.domain.model.ComicTrackerViewModelFactory
import com.enoch2.comictracker.ui.composables.ComicTrackerTopBar

// TODO: Add a button to refresh cover
@Composable
fun ComicDetailScreen(
    navController: NavController,
    id: Int,
    context: Context,
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
                        content = { Icon(Icons.Default.Edit, "edit", tint = Color.White) },
                        onClick = {
                            navController.navigate(
                                Screen.EditComicScreen.withArgs(
                                    comic.title.toString(),
                                    comic.status.toString(),
                                    comic.rating.toString(),
                                    comic.issuesRead.toString(),
                                    comic.totalIssues.toString(),
                                    comic.id.toString()
                                )
                            )
                        }
                    )
                    IconButton(
                        content = { Icon(Icons.Default.Delete, "delete", tint = Color.White) },
                        onClick = {
                            navController.navigate(Screen.HomeScreen.route) {
                                popUpTo(Screen.HomeScreen.route)
                                viewModel.deleteComic(id)
                            }
                        }
                    )
                }
            )
        }
    ) {
        val constraints = ConstraintSet {
            val title = createRefFor("title")
            val cover = createRefFor("cover")
            val status = createRefFor("status")
            val rating = createRefFor("rating")
            val issuesInfo = createRefFor("issues_info")
            val space = createRefFor("space")
            val divider = createRefFor("divider")
            val descriptionBox = createRefFor("description")

            constrain(title) {
                top.linkTo(parent.top)
                bottom.linkTo(cover.top, 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
            constrain(cover) {
                top.linkTo(title.bottom)
                bottom.linkTo(divider.top, 10.dp)
                start.linkTo(parent.start, 10.dp)
                end.linkTo(status.start)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }
            constrain(status) {
                top.linkTo(title.bottom, 10.dp)
                bottom.linkTo(rating.top, 5.dp)
                start.linkTo(cover.end, 10.dp)
                end.linkTo(parent.end, 10.dp)
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
            }
            constrain(rating) {
                top.linkTo(status.bottom, 5.dp)
                bottom.linkTo(issuesInfo.top)
                start.linkTo(cover.end, 10.dp)
                end.linkTo(parent.end, 10.dp)
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
            }
            constrain(issuesInfo) {
                top.linkTo(rating.bottom, 10.dp)
                bottom.linkTo(space.top, 10.dp)
                start.linkTo(cover.end, 10.dp)
                end.linkTo(parent.end, 10.dp)
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
            }
            constrain(space) {
                top.linkTo(issuesInfo.bottom, 10.dp)
                bottom.linkTo(divider.top, 10.dp)
                start.linkTo(cover.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
            }
            constrain(divider) {
                top.linkTo(space.bottom)
                bottom.linkTo(descriptionBox.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(descriptionBox) {
                top.linkTo(divider.bottom, 10.dp)
                bottom.linkTo(parent.bottom, 10.dp)
                start.linkTo(parent.start, 10.dp)
                end.linkTo(parent.end, 10.dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        }

        Surface(modifier = Modifier.fillMaxSize()) {
            ConstraintLayout(constraints) {
                Column(
                    modifier = Modifier.layoutId("title"),
                    content = {
                        Text(
                            comic.title.toString(),
                            fontSize = 20.sp,
                            softWrap = true,
                            overflow = TextOverflow.Clip,
                            modifier = Modifier.padding(
                                horizontal = 20.dp,
                                vertical = 10.dp
                            )
                        )
                        Divider()
                    }
                )
                Image(
                    painterResource(R.drawable.placeholder_image),
                    "cover",
                    alignment = Alignment.TopStart,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.layoutId("cover")
                )
                TextButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.layoutId("status"),
                    content = { Text(comic.status.toString()) }
                )
                TextButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.layoutId("rating"),
                    content = { Text("${comic.rating} / 10") }
                )
                TextButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.layoutId("issues_info"),
                    content = { Text("Issues Read: ${comic.issuesRead} / ${comic.totalIssues}") }
                )
                Spacer(modifier = Modifier.layoutId("space"))
                Divider(modifier = Modifier.layoutId("divider"))
                Text(
                    "Add description here",
                    modifier = Modifier.layoutId("description"),
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}
