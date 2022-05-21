package com.enoch2.comictracker.ui.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.Screen

@Composable
fun DrawerLayout(navController: NavController, context: Context) {
    val drawerItems = listOf(
        stringResource(R.string.reading), stringResource(R.string.completed),
        stringResource(R.string.on_hold), stringResource(R.string.dropped),
        stringResource(R.string.about)
    )

    FilterLabel()
    drawerItems.forEachIndexed { index, item ->
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            content = { Text(item) },
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
                    4 -> navController.navigate(Screen.AboutScreen.route)
                }
            }
        )
        if (index == 3) {
            Divider(modifier = Modifier.padding(top = 10.dp))
        }
    }
}
