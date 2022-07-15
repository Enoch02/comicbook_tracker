package com.enoch2.comictracker.ui.screen

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.Screen
import com.enoch2.comictracker.domain.model.ComicTrackerViewModel
import com.enoch2.comictracker.domain.model.ComicTrackerViewModelFactory
import com.enoch2.comictracker.domain.model.SettingsViewModel
import com.enoch2.comictracker.ui.composables.ComicTrackerTopBar
import com.enoch2.comictracker.ui.theme.BlueGray400

@Composable
fun SettingScreen(
    navController: NavController,
    context: Context,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val comicViewModel: ComicTrackerViewModel = viewModel(
        factory = ComicTrackerViewModelFactory(context.applicationContext)
    )
    val alwaysDark by settingsViewModel.getDarkModeValue(context).collectAsState(initial = false)
    val askBeforeExit by settingsViewModel.getExitDialogValue(context).collectAsState(initial = false)

    Scaffold(
        topBar = {
            ComicTrackerTopBar(
                title = stringResource(R.string.settings),
                navIcon = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                onClick = { navController.popBackStack() }
            )
        },
        content = {
            val constraints = ConstraintSet {
                val text = createRefFor("text")
                val checkBox = createRefFor("checkBox")
                val desc = createRefFor("desc")

                constrain(text) {
                    start.linkTo(parent.start, 10.dp)
                    top.linkTo(parent.top, 10.dp)
                    width = Dimension.percent(0.9f)
                }
                constrain(checkBox) {
                    top.linkTo(parent.top, 10.dp)
                    end.linkTo(parent.end, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    width = Dimension.percent(0.1f)
                }
                constrain(desc) {
                    top.linkTo(text.bottom)
                    bottom.linkTo(parent.bottom, 10.dp)
                    start.linkTo(parent.start, 15.dp)
                }
            }
            var showDialog by remember { mutableStateOf(false) }
            Column {
                Card(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    ConstraintLayout(
                        constraints,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                settingsViewModel.switchDarkModeValue(context)
                            }
                    ) {
                        Text(
                            stringResource(R.string.always_dark),
                            fontSize = 20.sp,
                            modifier = Modifier.layoutId("text")
                        )
                        Checkbox(
                            checked = alwaysDark,
                            onCheckedChange = { settingsViewModel.switchDarkModeValue(context) },
                            modifier = Modifier.layoutId("checkBox")
                        )
                        Text(
                            stringResource(R.string.always_dark_desc),
                            fontSize = 12.sp,
                            color = BlueGray400,
                            modifier = Modifier.layoutId("desc")
                        )
                    }
                }

                Card(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    ConstraintLayout(
                        constraints,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                settingsViewModel.switchExitDialogValue(context)
                            }
                    ) {
                        Text(
                            stringResource(R.string.show_exit_dialog),
                            fontSize = 20.sp,
                            modifier = Modifier.layoutId("text")
                        )
                        Checkbox(
                            checked = askBeforeExit,
                            onCheckedChange = { settingsViewModel.switchExitDialogValue(context)  },
                            modifier = Modifier.layoutId("checkBox")
                        )
                        Text(
                            stringResource(R.string.show_exit_dialog_desc),
                            fontSize = 12.sp,
                            color = BlueGray400,
                            modifier = Modifier.layoutId("desc")
                        )
                    }
                }

                Card(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    // TODO: Finish up/ find better solution
                    val result = remember { mutableStateOf<Uri?>(null) }
                    val launcher =
                        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
                            result.value = it
                        }

                    Column {
                        TextButton(
                            onClick = {
                                launcher.launch("*/*")
                                if (result.value != null) {
                                    /* TODO */
                                }
                            },
                            content = { Text(stringResource(R.string.import_data)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        TextButton(
                            onClick = { /*TODO*/ },
                            content = { Text(stringResource(R.string.export_data)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        TextButton(
                            onClick = { showDialog = !showDialog },
                            content = { Text(stringResource(R.string.clear_data)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Card(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    TextButton(
                        onClick = { navController.navigate(Screen.AboutScreen.route) },
                        content = { Text(stringResource(R.string.about)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = !showDialog },
                        title = { Text(stringResource(R.string.clear_data)) },
                        text = {
                            Text(
                                stringResource(R.string.clear_data_dialog_body),
                                fontSize = 15.sp
                            )
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    comicViewModel.deleteAllComic()
                                    comicViewModel.deleteAllCovers()
                                    showDialog = !showDialog
                                },
                                content = { Text(stringResource(R.string.yes)) }
                            )
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showDialog = !showDialog },
                                content = { Text(stringResource(R.string.no)) }
                            )
                        }
                    )
                }
            }
        }
    )
}
