package com.enoch2.comictracker.layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.enoch2.comictracker.R

@Composable
fun ComicInfoLayout(comicTitle: String, issuesRead: String, totalIssues: String, status: String) {
    val constraints = ConstraintSet {
        val cover = createRefFor("cover")
        val title = createRefFor("title")
        val divider = createRefFor("divider")
        val progress = createRefFor("progress")
        val statusC = createRefFor("status")

        constrain(cover) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }
        constrain(title) {
            top.linkTo(parent.top, 5.dp)
            start.linkTo(cover.end, 20.dp)
            end.linkTo(parent.end, 20.dp)
            width = Dimension.fillToConstraints
        }
        constrain(divider) {
            top.linkTo(title.bottom, margin = 10.dp)
            bottom.linkTo(progress.top)
            start.linkTo(cover.end, 10.dp)
            end.linkTo(parent.end, 10.dp)
            width = Dimension.fillToConstraints
        }
        constrain(progress) {
            top.linkTo(divider.bottom, 10.dp)
            start.linkTo(cover.end, 10.dp)
            end.linkTo(statusC.start)
            width = Dimension.percent(.45f)
        }
        constrain(statusC) {
            top.linkTo(divider.bottom, 10.dp)
            start.linkTo(progress.end)
            end.linkTo(parent.end)
            width = Dimension.percent(.45f)
        }
    }

    ConstraintLayout(constraints, modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Max)) {
        //TODO: replace with the coil version
        Image(
            painter = painterResource(R.drawable.placeholder_image),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.layoutId("cover")
        )
        Text(
            comicTitle,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.layoutId("title")
        )
        Divider(modifier = Modifier.layoutId("divider"))
        Text(
            "Progress: $issuesRead / $totalIssues",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .layoutId("progress")
        )
        Text(
            "Status: $status",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .layoutId("status")
                .padding(end = 10.dp)
        )
    }
}