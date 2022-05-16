package com.enoch2.comictracker.ui.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.data.Comic
import com.enoch2.comictracker.model.ComicTrackerViewModel
import com.enoch2.comictracker.model.ComicTrackerViewModelFactory
import com.enoch2.comictracker.navigation.Screen
import com.enoch2.comictracker.ui.common_composables.ComicTrackerTopBar
import kotlinx.coroutines.cancel
import kotlinx.coroutines.yield

@Composable
fun ComicDetailScreen(
    navController: NavController,
    comicTitle: String?,
    context: Context
) {
    val viewModel: ComicTrackerViewModel = viewModel(
        factory = ComicTrackerViewModelFactory(context.applicationContext)
    )
    var comic by remember { mutableStateOf(Comic("", "", 0, 0, 0)) }

    LaunchedEffect(true) {
        comic = viewModel.findComic(comicTitle.toString())
    }

    Scaffold(
        topBar = {
            ComicTrackerTopBar(
                title = comicTitle!!,
                navIcon = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                onClick = { navController.popBackStack() },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.EditComicScreen.withArgs(
                            comic.title,
                            comic.status,
                            comic.rating.toString(),
                            comic.issuesRead.toString(),
                            comic.totalIssues.toString()
                        ))
                        }
                    ) {
                        Icon(Icons.Default.Edit, "edit", tint = Color.White)
                    }
                }
            )
        },
        content = {
            val constraints = ConstraintSet {
                val cover = createRefFor("cover")
                val status = createRefFor("status")
                val rating = createRefFor("rating")
                val issuesInfo = createRefFor("issues_info")
                val divider = createRefFor("divider")
                val descriptionBox = createRefFor("description")

                constrain(cover) {
                    top.linkTo(parent.top, 10.dp)
                    start.linkTo(parent.start, 10.dp)
                }
                constrain(status) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(rating.top, 5.dp)
                    start.linkTo(cover.end, 10.dp)
                    end.linkTo(parent.end, 10.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                constrain(rating) {
                    top.linkTo(status.bottom, 5.dp)
                    bottom.linkTo(issuesInfo.top)
                    start.linkTo(cover.end, 10.dp)
                    end.linkTo(parent.end, 10.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                constrain(issuesInfo) {
                    top.linkTo(rating.bottom, 30.dp)
                    bottom.linkTo(divider.top)
                    start.linkTo(cover.end, 10.dp)
                    end.linkTo(parent.end, 10.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                constrain(divider) {
                    top.linkTo(cover.bottom, 10.dp)
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
                    Image(
                        painterResource(R.drawable.placeholder_image),
                        comicTitle,
                        modifier = Modifier.layoutId("cover")
                    )
                    Button(

                        onClick = { /*TODO*/ },
                        modifier = Modifier.layoutId("status"),
                        content = { Text(comic.status) }
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.layoutId("rating"),
                        content = { Text("${comic.rating} / 10") }
                    )
                    Text(
                        "${comic.issuesRead} / ${comic.totalIssues}",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        modifier = Modifier.layoutId("issues_info")
                    )
                    Divider(modifier = Modifier.layoutId("divider"))
                    Text(
                        "Add description here",
                        modifier = Modifier.layoutId("description"),
                        textAlign = TextAlign.Left
                    )
                }
            }
        }
    )
}
