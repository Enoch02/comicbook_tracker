package com.enoch2.comictracker.ui.composables

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import com.enoch2.comictracker.util.ComicInputMode
import kotlinx.coroutines.CoroutineScope

@Composable
fun ComicInputLayout(
    navController: NavController,
    context: Context,
    scope: CoroutineScope,
    comicTitle: String,
    selectedStatus: String,
    rating: Float,
    issuesRead: String,
    totalIssues: String,
    mode: ComicInputMode
) {
    val constraints = ConstraintSet {
        val text = createRefFor("text")
        val input = createRefFor("input")

        constrain(text) {
            top.linkTo(parent.top)
            bottom.linkTo(input.bottom)
            start.linkTo(parent.start)
            width = Dimension.percent(0.3f)
        }
        constrain(input) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            width = Dimension.percent(0.7f)
        }
    }
    val viewModel: ComicTrackerViewModel = viewModel(
        factory = ComicTrackerViewModelFactory(context.applicationContext)
    )
    var mComicTitle by remember { mutableStateOf(comicTitle) }
    var isExpanded by remember { mutableStateOf(false) }
    var mSelectedStatus by remember { mutableStateOf(selectedStatus) }
    var mRating by remember { mutableStateOf(rating) }
    var mIssuesRead by remember { mutableStateOf(issuesRead) }
    var mTotalIssues by remember { mutableStateOf(totalIssues) }

    if (mode == ComicInputMode.EDIT) {
        val oldComic = Comic(
            mComicTitle,
            mSelectedStatus,
            mRating.toInt(),
            mIssuesRead.toInt(),
            mTotalIssues.toInt()
        )
        viewModel.deleteComic(oldComic)
    }

    Column {
        ConstraintLayout(
            constraints, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                stringResource(R.string.comic_title_txt),
                textAlign = TextAlign.Center,
                modifier = Modifier.layoutId("text")
            )

            OutlinedTextField(
                value = mComicTitle,
                onValueChange = { mComicTitle = it },
                modifier = Modifier.layoutId("input"),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }
        Divider()

        val items = listOf("reading" , "completed", "on hold", "dropped" , "plan to read")

        ConstraintLayout(
            constraints, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                stringResource(R.string.status_txt),
                textAlign = TextAlign.Center,
                modifier = Modifier.layoutId("text")
            )

            Box(modifier = Modifier.layoutId("input")) {
                val disabledTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black
                OutlinedTextField(
                    value = mSelectedStatus,
                    onValueChange = { mSelectedStatus = it },
                    enabled = false,
                    trailingIcon = {
                        IconButton(onClick = { isExpanded = !isExpanded }) {
                            val icon = if (isExpanded)
                                Icons.Filled.KeyboardArrowUp
                            else
                                Icons.Filled.KeyboardArrowDown
                            Icon(icon, null)
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(disabledTextColor = disabledTextColor)
                )
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = !isExpanded }
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                mSelectedStatus = item
                                isExpanded = !isExpanded
                            },
                            content = { Text(item) }
                        )
                    }
                }
            }
        }
        Divider()

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            val (lText, input, rText) = createRefs()
            Text(
                stringResource(R.string.rating),
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(lText) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(0.3f)
                }
            )

            Slider(
                value = mRating,
                onValueChange = { mRating = it },
                valueRange = 0f..10f,
                modifier = Modifier.constrainAs(input) {
                    start.linkTo(lText.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(0.5f)
                }
            )
            Text(
                "${mRating.toInt()} / 10",
                textAlign = TextAlign.Center,
                maxLines = 1,
                fontSize = with(LocalDensity.current) { 15.dp.toSp() },
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.constrainAs(rText) {
                    start.linkTo(input.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(0.2f)
                }
            )
        }
        Divider()

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            val (text, issuesReadInput, totalIssuesInput) = createRefs()
            Text(
                stringResource(R.string.issues_read),
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(text) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    width = Dimension.percent(0.3f)
                }
            )

            OutlinedTextField(
                value = mIssuesRead,
                onValueChange = { mIssuesRead = it },
                label = { Text(stringResource(R.string.issues_read1)) },
                modifier = Modifier.constrainAs(issuesReadInput) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(text.end)
                    width = Dimension.percent(0.30f)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = mTotalIssues,
                onValueChange = { mTotalIssues = it },
                label = { Text(stringResource(R.string.total_issues)) },
                modifier = Modifier.constrainAs(totalIssuesInput) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(issuesReadInput.end, margin = 10.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                    width = Dimension.percent(0.30f)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Divider()

        Button(
            onClick = {
                when (mode) {
                    ComicInputMode.ADD -> {
                        /* TODO */
                    }
                    ComicInputMode.EDIT -> {
                        val comic = Comic(
                            mComicTitle,
                            mSelectedStatus,
                            mRating.toInt(),
                            mIssuesRead.toInt(),
                            mTotalIssues.toInt()
                        )
                        viewModel.addComic(comic)
                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(Screen.HomeScreen.route)
                            launchSingleTop = true
                        }
                    }
                }
            },
            content = { Text(text = stringResource(R.string.save_comic_data)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 20.dp)
        )
    }
}
