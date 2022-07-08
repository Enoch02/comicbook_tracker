package com.enoch2.comictracker.ui.composables

import android.content.Context
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.enoch2.comictracker.R
import com.enoch2.comictracker.ui.theme.Typography
import java.io.File

@Composable
fun ComicInfoLayout(
    context: Context,
    comicTitle: String,
    issuesRead: Int,
    totalIssues: Int,
    status: String,
    coverPath: String,
    modifier: Modifier = Modifier
) {
    val constraints = ConstraintSet {
        val cover = createRefFor("cover")
        val title = createRefFor("title")
        val divider = createRefFor("divider")
        val info = createRefFor("info")

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
            bottom.linkTo(info.top)
            start.linkTo(cover.end, 10.dp)
            end.linkTo(parent.end, 10.dp)
            width = Dimension.fillToConstraints
        }
        constrain(info) {
            top.linkTo(divider.bottom, 10.dp)
            start.linkTo(cover.end, 20.dp)
            end.linkTo(parent.end, 20.dp)
            bottom.linkTo(parent.bottom, 10.dp)
            width = Dimension.fillToConstraints
        }
    }

    ConstraintLayout(
        constraints,
        modifier
    ) {
        val cover = File(coverPath).absolutePath

        AsyncImage(
            model = cover,
            placeholder = painterResource(R.drawable.placeholder_image),
            contentDescription = null,
            error = painterResource(R.drawable.placeholder_image),
            contentScale = ContentScale.Fit,
            modifier = Modifier.layoutId("cover"),
            filterQuality = FilterQuality.Low
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
            "Progress: $issuesRead / $totalIssues   Status: $status",
            maxLines = 1,
            // Prevents the font from being resized by the system
            fontSize = with(LocalDensity.current) { Typography.body2.fontSize.value.dp.toSp() },
            modifier = Modifier.layoutId("info")
        )
    }
}