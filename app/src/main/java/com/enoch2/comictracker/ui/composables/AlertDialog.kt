package com.enoch2.comictracker.ui.composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.enoch2.comictracker.R

@Composable
fun ComicTrackerAlertDialog(
    title: Int,
    text: @Composable()(() -> Unit),
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    confirmText: Int = R.string.yes,
    dismissText: Int = R.string.no
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(title)) },
        text = text,
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                content = { Text(stringResource(confirmText)) }
            )
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                content = { Text(stringResource(dismissText)) }
            )
        }
    )
}
