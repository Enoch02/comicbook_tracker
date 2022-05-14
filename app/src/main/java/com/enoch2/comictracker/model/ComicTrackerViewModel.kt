package com.enoch2.comictracker.model

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.enoch2.comictracker.data.Comic
import com.enoch2.comictracker.data.ComicDatabase
import com.enoch2.comictracker.data.ComicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ComicTrackerViewModel(context: Context) : ViewModel() {
    private val comicDao = ComicDatabase.getDataBase(context.applicationContext).getComicDao()
    private val repository = ComicRepository(comicDao)

    suspend fun getAllComic(): List<Comic> {
        val result = viewModelScope.async {
            repository.getAllComic()
        }
        return result.await()
    }

    suspend fun findComic(comicTitle: String): Comic {
        val result = viewModelScope.async(Dispatchers.IO) {
            repository.findComic(comicTitle)
        }
        return result.await()
    }

    fun addComic(comic: Comic) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertComic(comic)
        }
    }

    fun deleteComic(comic: Comic) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteComic(comic)
        }
    }

    fun deleteAllComic() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllComic()
        }
    }
}
