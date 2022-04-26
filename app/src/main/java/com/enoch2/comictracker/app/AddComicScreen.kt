package com.enoch2.comictracker.app

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.enoch2.comictracker.R
import com.enoch2.comictracker.router.BackButtonHandler
import com.enoch2.comictracker.router.Router
import com.enoch2.comictracker.router.Screen

enum class Status {
    READING,
    COMPLETED,
    ON_HOLD,
    DROPPED,
    PLAN_TO_READ
}

@Composable
fun AddComicScreen() {
    BackButtonHandler {
        Router.navigateTo(Screen.HomeScreen)
    }
    Scaffold (
        topBar = { TopAppBar(title = { Text(stringResource(R.string.add_new_comic_title)) }) },
        content = { AddComicContent() }
    )
}

@Composable
private fun AddComicContent() {
    // TODO: Smoothen the rough edges and finish the layout and its functions
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(20.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 10.dp
    ) {
        var comicTitle by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.padding(5.dp)) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()) {
                    Text(stringResource(R.string.comic_title_txt))
                }
                    OutlinedTextField(
                        value = comicTitle,
                        onValueChange = { comicTitle = it },
                        modifier = Modifier.weight(2f)
                    )
                }

            Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()) {
                        Text(stringResource(R.string.status_txt))
                    }

                    val items = listOf("reading", "completed", "on hold", "dropped", "plan to read")
                    var isExpanded by remember { mutableStateOf(false) }
                    var selectedStatus by remember { mutableStateOf("") }
                    Column(modifier = Modifier.weight(2f)) {
                        OutlinedTextField(
                            value = selectedStatus,
                            onValueChange = { selectedStatus = it },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = false,
                            trailingIcon = {
                                IconButton(onClick = { isExpanded = !isExpanded }) {
                                    val icon = if (isExpanded)
                                        Icons.Filled.KeyboardArrowUp
                                    else
                                        Icons.Filled.KeyboardArrowDown
                                    Icon(icon, null)
                                }
                            }
                        )
                        DropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = !isExpanded },
                        ) {
                            items.forEach { item ->
                                DropdownMenuItem(onClick = {
                                    selectedStatus = item
                                    isExpanded = false
                                }) {
                                    Text(item)
                                }
                            }
                        }
                    }
                }

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()) {
                    Text(stringResource(R.string.rating)
                    )
                }

                // TODO: rating input goes here
            }
         }
    }
}
