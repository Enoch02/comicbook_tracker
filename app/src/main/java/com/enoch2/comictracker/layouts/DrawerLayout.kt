package com.enoch2.comictracker.layouts

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.enoch2.comictracker.R
import com.enoch2.comictracker.router.Router
import com.enoch2.comictracker.router.Screen

@Composable
fun DrawerLayout(context: Context) {
    val drawerItems = listOf(
        stringResource(R.string.reading),
        stringResource(R.string.completed), stringResource(R.string.on_hold),
        stringResource(R.string.dropped), stringResource(R.string.about)
    )

    drawerItems.forEachIndexed { index, item ->
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                when (index) {
                    0 -> {
                        Toast
                            .makeText(context, "Reading!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    1 -> {
                        Toast
                            .makeText(context, "Completed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    2 -> {

                    }
                    3 -> {

                    }
                    4 -> {
                        Router.navigateTo(Screen.AboutScreen)
                    }
                }
            }
        ){ Text(item) }
    }
}