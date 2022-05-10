package com.enoch2.comictracker.ui.layouts

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.navigation.Screen

@Composable
fun DrawerLayout(navController: NavController, context: Context) {
    val drawerItems = listOf(
        stringResource(R.string.reading),
        stringResource(R.string.completed), stringResource(R.string.on_hold),
        stringResource(R.string.dropped), stringResource(R.string.about)
    )

    Text("Filters")
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
                    4 -> {
                        navController.navigate(Screen.AboutScreen.route)
                    }
                }
            }
        )
        if (index == 3) {
            Divider()
        }
    }
}