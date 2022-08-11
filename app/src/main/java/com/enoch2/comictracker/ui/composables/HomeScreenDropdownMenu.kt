package com.enoch2.comictracker.ui.composables

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.Screen
import com.enoch2.comictracker.domain.model.ComicTrackerViewModel
import com.enoch2.comictracker.domain.model.ComicTrackerViewModelFactory
import com.enoch2.comictracker.ui.theme.White
import com.enoch2.comictracker.util.OrderType

@Composable
fun HomeScreenDropDownMenu(context: Context, navController: NavController, comicIds: List<Int>) {
    var showMenu by remember { mutableStateOf(false) }
    var showInnerMenu by remember { mutableStateOf(false) }
    val viewModel: ComicTrackerViewModel = viewModel(
        factory = ComicTrackerViewModelFactory(context.applicationContext)
    )

    IconButton(
        onClick = {
            showMenu = !showMenu
        },
        content = {
            Icon(
                Icons.Default.MoreVert,
                stringResource(R.string.menu),
                tint = White
            )

            val mainMenuItems = listOf(
                stringResource(R.string.sort),
                stringResource(R.string.settings)
            )
            val selectionMenuItems = listOf(
                stringResource(R.string.select_all),
                stringResource(R.string.deselect_all)
            )
            if (viewModel.selectedIds.isEmpty()) {
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = !showMenu },
                    content = {
                        mainMenuItems.forEachIndexed { index, item ->
                            DropdownMenuItem(
                                contentPadding = PaddingValues(horizontal = 15.dp),
                                onClick = {
                                    if (index == 0) {
                                        showInnerMenu = !showInnerMenu
                                        showMenu = !showMenu
                                    } else {
                                        navController.navigate(Screen.SettingScreen.route)
                                    }
                                },
                                content = {
                                    Text(item, Modifier.weight(2f))
                                    if (index == 0)
                                        Icon(
                                            Icons.Outlined.ArrowRight,
                                            null,
                                            Modifier.weight(1f)
                                        )
                                }
                            )
                        }
                    }
                )
            } else {
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = !showMenu },
                    content = {
                        selectionMenuItems.forEachIndexed { index, item ->
                            DropdownMenuItem(
                                contentPadding = PaddingValues(horizontal = 15.dp),
                                onClick = {
                                    showMenu = if (index == 0) {
                                        viewModel.selectAllIds(comicIds)
                                        !showMenu
                                    } else {
                                        viewModel.deselectAllIds()
                                        !showMenu
                                    }
                                },
                                content = {
                                    Text(item)
                                }
                            )
                        }
                    }
                )
            }
        }
    )

    val innerMenuItems = listOf(
        stringResource(R.string.ascending),
        stringResource(R.string.descending),
        stringResource(R.string.none)
    )
    var selected by rememberSaveable { mutableStateOf(innerMenuItems[0]) }
    val onSelectionChange = { text: String ->
        selected = text
    }
    // for changing sort order
    DropdownMenu(
        expanded = showInnerMenu,
        onDismissRequest = {
            if (showMenu) showMenu = !showMenu
            showInnerMenu = !showInnerMenu
        },
        content = {
            innerMenuItems.forEachIndexed { index, item ->
                DropdownMenuItem(
                    contentPadding = PaddingValues(horizontal = 15.dp),
                    onClick = {
                        when (index) {
                            0 -> {
                                viewModel.order = OrderType.ASCENDING
                                showInnerMenu = !showInnerMenu
                                onSelectionChange(item)
                            }
                            1 -> {
                                viewModel.order = OrderType.DESCENDING
                                showInnerMenu = !showInnerMenu
                                onSelectionChange(item)
                            }
                            else -> {
                                viewModel.order = OrderType.NONE
                                showInnerMenu = !showInnerMenu
                                onSelectionChange(item)
                            }
                        }
                    }
                ) {
                    Text(item, modifier = Modifier.weight(2f))
                    RadioButton(
                        modifier = Modifier.weight(1f),
                        selected = item == selected,
                        onClick = {
                            if (index == 0) {
                                viewModel.order = OrderType.ASCENDING
                                showInnerMenu = !showInnerMenu
                                onSelectionChange(item)
                            } else {
                                viewModel.order = OrderType.DESCENDING
                                showInnerMenu = !showInnerMenu
                                onSelectionChange(item)
                            }
                        }
                    )
                }
            }
        }
    )
}