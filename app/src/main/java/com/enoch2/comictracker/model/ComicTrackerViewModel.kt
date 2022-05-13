package com.enoch2.comictracker.model

import android.app.Application
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.enoch2.comictracker.data.Comic
import com.enoch2.comictracker.data.ComicDatabase
import com.enoch2.comictracker.data.ComicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComicTrackerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ComicRepository
    private lateinit var allComic: List<Comic>
    val comics = allComic.toMutableStateList()
    init {
        val comicDao = ComicDatabase.getDataBase(application).getComicDao()
        repository = ComicRepository(comicDao)
        viewModelScope.launch {
            allComic = repository.getAllComic()
        }
    }

    fun findComic(comicTitle: String): Comic {
        lateinit var temp: Comic

        viewModelScope.launch(Dispatchers.IO) {
            temp = repository.findComic(comicTitle)
        }
        return temp
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