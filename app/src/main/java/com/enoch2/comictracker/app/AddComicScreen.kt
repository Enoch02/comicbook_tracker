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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.enoch2.comictracker.R
import com.enoch2.comictracker.Status
import com.enoch2.comictracker.router.BackButtonHandler
import com.enoch2.comictracker.router.Router
import com.enoch2.comictracker.router.Screen

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
    var comicTitle by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(5.dp)) {
        Surface() {
            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f).fillMaxSize()
                ) {
                    Text(stringResource(R.string.comic_title_txt))
                }
                OutlinedTextField(
                    value = comicTitle,
                    onValueChange = { comicTitle = it },
                    modifier = Modifier.weight(2f)
                )
            }
        }

        Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))

        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Text(stringResource(R.string.status_txt))
            }

            val items = mapOf(
                "reading" to Status.READING,
                "completed" to Status.COMPLETED,
                "on hold" to Status.ON_HOLD,
                "dropped" to Status.DROPPED,
                "plan to read" to Status.PLAN_TO_READ
            )
            var isExpanded by remember { mutableStateOf(false) }
            var selectedStatus by remember { mutableStateOf("") }
            Column(modifier = Modifier.weight(2f)) {
                OutlinedTextField(
                    value = selectedStatus,
                    onValueChange = { selectedStatus = it },
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
                        DropdownMenuItem(
                            onClick = {
                                selectedStatus = item.key
                                isExpanded = false
                            },
                            content = { Text(item.key) }
                        )
                    }
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    stringResource(R.string.rating)
                )
            }
            var sliderPosition by remember { mutableStateOf(0f) }
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = 0f..10f,
                steps = 1,
                modifier = Modifier.weight(1f)
            )
            Text(
                sliderPosition.toInt().toString(),
                modifier = Modifier.weight(1f)
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    // TODO: Save input here
                },
                content = {
                    Text(stringResource(R.string.save_btn))
                }
            )
        }
    }
}
