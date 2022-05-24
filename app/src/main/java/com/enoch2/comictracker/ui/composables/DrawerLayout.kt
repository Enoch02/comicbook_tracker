package com.enoch2.comictracker.ui.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.domain.model.Comic
import com.enoch2.comictracker.domain.model.ComicTrackerViewModel
import com.enoch2.comictracker.domain.model.ComicTrackerViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerLayout(context: Context) {
    val viewModel: ComicTrackerViewModel = viewModel(
        factory = ComicTrackerViewModelFactory(context.applicationContext)
    )
    val drawerItems = listOf(
        stringResource(R.string.reading), stringResource(R.string.completed),
        stringResource(R.string.on_hold), stringResource(R.string.dropped)
    )

    FilterLabel()
    val hmm = viewModel.comics.collectAsState(initial = emptyList()).value.toString()
    drawerItems.forEachIndexed { index, item ->
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            content = { Text(item) },
            onClick = {
                when (index) {
                    0 -> {
                        viewModel.changeFilter("reading")
                        Toast.makeText(context, hmm, Toast.LENGTH_LONG).show()
                    }
                    1 -> {
                        viewModel.changeFilter("on hold")
                    }
                    2 -> {

                    }
                    3 -> {

                    }
                }
            }
        )
    }
}
