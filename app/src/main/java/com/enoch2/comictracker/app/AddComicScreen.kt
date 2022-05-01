package com.enoch2.comictracker.app

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.enoch2.comictracker.R
import com.enoch2.comictracker.router.BackButtonHandler
import com.enoch2.comictracker.router.Router
import com.enoch2.comictracker.router.Screen
import com.enoch2.comictracker.util.saveData

@Composable
fun AddComicScreen() {
    BackButtonHandler {
        Router.navigateTo(Screen.HomeScreen)
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_new_comic_title)) },
                navigationIcon = {
                    IconButton(
                        onClick = { Router.navigateTo(Screen.HomeScreen) },
                        content = { Icon(Icons.Default.ArrowBack, null) })
                })
            },
        content = { AddComicContent() },
    )
}

@Composable
private fun AddComicContent() {
    val constraints = ConstraintSet {
        val text = createRefFor("text")
        val input = createRefFor("input")

        constrain(text) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            bottom.linkTo(input.bottom)
            width = Dimension.percent(0.3f)
        }
        constrain(input) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            width = Dimension.percent(0.7f)
        }
    }
    var comicTitle by remember { mutableStateOf("") }
    val items = listOf("reading" , "completed", "on hold", "dropped" , "plan to read")
    var isExpanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf("reading") }
    var rating by remember { mutableStateOf(0f) }
    var issuesRead by remember { mutableStateOf("") }
    var totalIssues by remember { mutableStateOf("") }

    Column {
        ConstraintLayout(constraints, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Text(
                stringResource(R.string.comic_title_txt),
                textAlign = TextAlign.Center,
                modifier = Modifier.layoutId("text")
            )
            OutlinedTextField(
                value = comicTitle,
                onValueChange = { comicTitle = it },
                modifier = Modifier.layoutId("input"),
                shape = RectangleShape,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words)
            )
        }
        MyDivider()
        ConstraintLayout(constraints, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Text(
                stringResource(R.string.status_txt),
                textAlign = TextAlign.Center,
                modifier = Modifier.layoutId("text")
            )
            Box(modifier = Modifier.layoutId("input")) {
                val disabledTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black
                OutlinedTextField(
                    value = selectedStatus,
                    onValueChange = { selectedStatus = it },
                    shape = RectangleShape,
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
                                selectedStatus = item
                                isExpanded = false
                            },
                            content = { Text(item) }
                        )
                    }
                }
            }
        }
        MyDivider()
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            val (lText, input, rText) = createRefs()
            Text(
                stringResource(R.string.rating),
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(lText) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(0.2f)
                }
            )
            Slider(
                value = rating,
                onValueChange = { rating = it },
                valueRange = 0f..10f,
                modifier = Modifier.constrainAs(input) {
                    start.linkTo(lText.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(0.6f)
                }
            )
            Text(
                "${rating.toInt()}/10",
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.constrainAs(rText) {
                    start.linkTo(input.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(0.2f)
                }
            )
        }
        MyDivider()
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
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
                value = issuesRead,
                onValueChange = { issuesRead = it },
                label = { Text(stringResource(R.string.issues_read1)) },
                modifier = Modifier.constrainAs(issuesReadInput) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(text.end)
                    width = Dimension.percent(0.30f)
                },
                shape = RectangleShape,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    // TODO: Add other colors when i change the theme
                )
            )
            OutlinedTextField(
                value = totalIssues,
                onValueChange = { totalIssues = it },
                label = { Text(stringResource(R.string.total_issues)) },
                modifier = Modifier.constrainAs(totalIssuesInput) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(issuesReadInput.end, margin = 10.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                    width = Dimension.percent(0.30f)
                },
                shape = RectangleShape,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                )
            )
        }
        MyDivider()
        val context = LocalContext.current
        Button(
            onClick = {
                when {
                    comicTitle == "" -> {
                        Toast.makeText(context, "Add a comic title", Toast.LENGTH_SHORT).show()
                    }
                    issuesRead == "" -> { issuesRead = "0" }
                    totalIssues == "" -> {
                        Toast.makeText(context, "How many issues does $comicTitle have?",
                            Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        saveData(
                            context, comicTitle, selectedStatus,
                            rating.toInt(), issuesRead, totalIssues
                        )
                        Router.navigateTo(Screen.HomeScreen)
                        Toast.makeText(context, "Comic Saved!", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            content = { Text(text = stringResource(R.string.save_comic_data)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
}

@Composable
fun MyDivider() {
    val dividerColor = if (isSystemInDarkTheme()) Color.Gray else Color.Black

    Divider(
        color = dividerColor,
        thickness = 0.5.dp,
        modifier = Modifier.padding(10.dp)
    )
}
