package com.enoch2.comictracker.ui.composables

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.enoch2.comictracker.R
import com.enoch2.comictracker.domain.model.ComicTrackerViewModel
import com.enoch2.comictracker.domain.model.ComicTrackerViewModelFactory
import com.enoch2.comictracker.util.ComicInputMode

@Composable
fun ComicInputLayout(
    navController: NavController,
    context: Context,
    topBarTitle: String,
    comicTitle: String,
    selectedStatus: String,
    rating: Float,
    issuesRead: String,
    totalIssues: String,
    id: Int? = null,
    mode: ComicInputMode,
    coverName: String = ""
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
    var mComicTitle by rememberSaveable { mutableStateOf(comicTitle) }
    var mIsExpanded by rememberSaveable { mutableStateOf(false) }
    var mSelectedStatus by rememberSaveable { mutableStateOf(selectedStatus) }
    var mRating by rememberSaveable { mutableStateOf(rating) }
    var mIssuesRead by rememberSaveable { mutableStateOf(issuesRead) }
    var mTotalIssues by rememberSaveable { mutableStateOf(totalIssues) }
    var mCoverPath by rememberSaveable { mutableStateOf(coverName) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            imageUri = it.data?.data
        }
    val intent = Intent(
        Intent.ACTION_OPEN_DOCUMENT,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    )
        .apply {
            type = "image/jpeg"
            addCategory(Intent.CATEGORY_OPENABLE)
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(topBarTitle) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        content = { Icon(Icons.Default.ArrowBack, "back") })
                },
                actions = {
                    IconButton(
                        onClick = { launcher.launch(intent) },
                        content = { Icon(Icons.Default.Image, null, tint = Color.White) }
                    )
                }
            )
        }
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
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
                        onValueChange = { mComicTitle = it.replace("/", "") },
                        modifier = Modifier.layoutId("input"),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Sentences,
                            autoCorrect = true
                        )
                    )
                }
                Divider()

                val items = listOf("reading", "completed", "on hold", "dropped", "plan to read")

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
                        val disabledTextColor =
                            if (isSystemInDarkTheme()) Color.White else Color.Black

                        OutlinedTextField(
                            value = mSelectedStatus,
                            onValueChange = { mSelectedStatus = it },
                            enabled = false,
                            trailingIcon = {
                                IconButton(onClick = { mIsExpanded = !mIsExpanded }) {
                                    val icon = if (mIsExpanded)
                                        Icons.Filled.KeyboardArrowUp
                                    else
                                        Icons.Filled.KeyboardArrowDown
                                    Icon(icon, null)

                                    DropdownMenu(
                                        expanded = mIsExpanded,
                                        onDismissRequest = { mIsExpanded = !mIsExpanded },
                                        modifier = Modifier.fillMaxWidth(0.5f),
                                        content = {
                                            items.forEach { item ->
                                                DropdownMenuItem(
                                                    onClick = {
                                                        mSelectedStatus = item
                                                        mIsExpanded = false
                                                    },
                                                    content = {
                                                        Text(
                                                            item,
                                                            textAlign = TextAlign.Center,
                                                            modifier = Modifier.fillMaxWidth()
                                                        )
                                                    }
                                                )
                                            }
                                        }
                                    )
                                }
                            },
                            colors = TextFieldDefaults.textFieldColors(disabledTextColor = disabledTextColor),
                            modifier = Modifier.fillMaxWidth()
                        )
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
                            ComicInputMode.EDIT -> {
                                if (viewModel.addComic(
                                        mComicTitle,
                                        mSelectedStatus,
                                        mRating.toInt(),
                                        mIssuesRead,
                                        mTotalIssues,
                                        id!!,
                                        mCoverPath
                                    )
                                ) {
                                    navController.popBackStack()
                                } else Toast.makeText(context, "Enter a title", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            ComicInputMode.ADD -> {
                                if (viewModel.addComic(
                                        mComicTitle,
                                        mSelectedStatus,
                                        mRating.toInt(),
                                        mIssuesRead,
                                        mTotalIssues,
                                        coverPath = mCoverPath
                                    )
                                ) {
                                    navController.popBackStack()
                                } else Toast.makeText(context, "Enter a title", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    },
                    content = { Text(text = stringResource(R.string.save_comic_data)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 20.dp)
                )

                if (imageUri != null) {
                    ComicTrackerAlertDialog(
                        title = R.string.image_preview_txt,
                        text = {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                AsyncImage(
                                    model = imageUri.toString(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(150.dp)
                                        .width(100.dp)
                                )
                            }
                        },
                        onConfirm = {
                            imageUri?.let { mCoverPath = viewModel.copyCover(it) }
                            imageUri = null
                        },
                        confirmText = R.string.continue_txt,
                        onDismiss = {
                            launcher.launch(intent)
                        },
                        dismissText = R.string.select_another
                    )
                }
            }
        }
    }
}
