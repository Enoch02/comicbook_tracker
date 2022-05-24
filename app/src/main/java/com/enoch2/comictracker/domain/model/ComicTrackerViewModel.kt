package com.enoch2.comictracker.domain.model

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enoch2.comictracker.data.repository.ComicRepositoryImpl
import com.enoch2.comictracker.data.source.ComicDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ComicTrackerViewModel(context: Context) : ViewModel() {
    private val comicDao = ComicDatabase.getDataBase(context.applicationContext).getComicDao()
    private val repository = ComicRepositoryImpl(comicDao)
    private var _filter = mutableStateOf("reading")
    private var _comics = repository.comics.map { comic -> comic.filter { it.status == _filter.value  } }
    var comics = _comics

    fun getComic(comicId: Int): Flow<Comic> {
        return repository.getComic(comicId)
    }

    fun addComic(
        comicTitle: String,
        selectedStatus: String,
        rating: Int,
        issuesRead: String,
        totalIssues: String
    ): Boolean {
        if (comicTitle.isEmpty())
            return false

        val temp = Comic(
            comicTitle,
            selectedStatus,
            rating,
            if (issuesRead.isEmpty()) 0 else issuesRead.toInt(),
            if (totalIssues.isEmpty()) 0 else totalIssues.toInt()
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.insertComic(temp)
        }
        return true
    }

    fun addComic(
        comicTitle: String,
        selectedStatus: String,
        rating: Int,
        issuesRead: String,
        totalIssues: String,
        id: Int
    ): Boolean {
        if (comicTitle.isEmpty())
            return false

        val temp = Comic(
            comicTitle,
            selectedStatus,
            rating,
            if (issuesRead.isEmpty()) 0 else issuesRead.toInt(),
            if (totalIssues.isEmpty()) 0 else totalIssues.toInt(),
            id
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.insertComic(temp)
        }
        return true
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

    fun changeFilter(newFilter: String) {
        _filter.value = newFilter
    }
}
