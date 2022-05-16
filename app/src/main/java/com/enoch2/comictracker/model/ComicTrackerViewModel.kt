package com.enoch2.comictracker.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enoch2.comictracker.data.Comic
import com.enoch2.comictracker.data.ComicDatabase
import com.enoch2.comictracker.data.ComicRepository
import kotlinx.coroutines.*

class ComicTrackerViewModel(context: Context) : ViewModel() {
    //TODO: Find a way to update the list only when new data is added
    private val comicDao = ComicDatabase.getDataBase(context.applicationContext).getComicDao()
    private val repository = ComicRepository(comicDao)

    suspend fun getAllComic(): List<Comic> {
        val comics =
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                repository.getAllComic()
            }
        return comics
    }

    suspend fun findComic(comicTitle: String): Comic {
        val result = viewModelScope.async {
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
