package com.enoch2.comictracker.ui.common_composables

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ComicTrackerTopBar(
    title: String,
    navIcon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit = { },
    actions: @Composable RowScope.()-> Unit= {}
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(navIcon, contentDescription)
            }
        },
        actions = actions
    )
}