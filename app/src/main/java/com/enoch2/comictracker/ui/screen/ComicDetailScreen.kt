package com.enoch2.comictracker.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.data.Comic
import com.enoch2.comictracker.data.ComicDao
import com.enoch2.comictracker.ui.common_composables.ComicTrackerTopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ComicDetailScreen(
    navController: NavController,
    comicTitle: String?,
    comicDao: ComicDao,
) {
    Scaffold(
        topBar = {
            ComicTrackerTopBar(
                title = comicTitle!!,
                navIcon = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                onClick = { navController.popBackStack() },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Edit, "edit")
                    }
                }
            )
        },
        content = {
            var comic by remember { mutableStateOf(Comic(
                "",
                "",
                0,
                0,
                0
            )) }

            LaunchedEffect(true) {
                comic = comicDao.findByTitle(comicTitle.toString())
            }

            val constraints = ConstraintSet {
                val cover = createRefFor("cover")
                val status = createRefFor("status")
                val rating = createRefFor("rating")
                val issuesInfo = createRefFor("issues_info")

                constrain(cover) {
                    top.linkTo(parent.top, 10.dp)
                    start.linkTo(parent.start, 10.dp)
                }
                constrain(status) {
                    top.linkTo(parent.top, 10.dp)
                    start.linkTo(cover.end, 5.dp)
                    end.linkTo(parent.end, 10.dp)
                    width = Dimension.fillToConstraints
                }
                constrain(rating) {
                    top.linkTo(status.bottom, 10.dp)
                    start.linkTo(cover.end, 5.dp)
                    end.linkTo(parent.end, 10.dp)
                    width = Dimension.fillToConstraints
                }
                constrain(issuesInfo) {

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
                        modifier = Modifier.layoutId("issues_info")
                    )
                }
            }
        }
    )
}
