package com.enoch2.comictracker.domain.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enoch2.comictracker.data.repository.ComicRepositoryImpl
import com.enoch2.comictracker.data.source.ComicDatabase
import com.enoch2.comictracker.util.Filters
import com.enoch2.comictracker.util.Filters.*
import com.enoch2.comictracker.util.OrderType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ComicTrackerViewModel(context: Context) : ViewModel() {
    private val comicDao = ComicDatabase.getDataBase(context.applicationContext).getComicDao()
    private val repository = ComicRepositoryImpl(comicDao)

    fun getComics(filter: Filters, orderType: OrderType): Flow<List<Comic>> {
        return when (orderType) {
            OrderType.ASCENDING -> {
                when (filter) {
                    ALL -> {
                        return repository.getAllOrdered(0)  // 0 -> ascending
                    }
                    READING -> {
                        return repository.getAllReadingOrdered(0)
                    }
                    COMPLETED -> {
                        return repository.getAllCompletedOrdered(0)
                    }
                    ON_HOLD -> {
                        return repository.getAllOnHoldOrdered(0)
                    }
                    DROPPED -> {
                        return repository.getAllDroppedOrdered(0)
                    }
                    PLAN_TO_READ -> {
                        return repository.getAllPTROrdered(0)
                    }
                }
            }
            OrderType.DESCENDING -> {
                when (filter) {
                    ALL -> {
                        return repository.getAllOrdered(1)
                    }
                    READING -> {
                        return repository.getAllReadingOrdered(1)
                    }
                    COMPLETED -> {
                        return repository.getAllCompletedOrdered(1)
                    }
                    ON_HOLD -> {
                        return repository.getAllOnHoldOrdered(1)
                    }
                    DROPPED -> {
                        return repository.getAllDroppedOrdered(1)
                    }
                    PLAN_TO_READ -> {
                        return repository.getAllPTROrdered(1)
                    }
                }
            }
            else -> repository.getAll()
        }
    }

    fun getComic(comicId: Int?): Flow<Comic> {
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
            delay(5000L)
            repository.deleteAllComic()
        }
    }
}
