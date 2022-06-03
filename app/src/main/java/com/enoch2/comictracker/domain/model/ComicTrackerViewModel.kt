package com.enoch2.comictracker.domain.model

import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.enoch2.comictracker.data.repository.ComicRepositoryImpl
import com.enoch2.comictracker.data.source.ComicDatabase
import com.enoch2.comictracker.util.Filters
import com.enoch2.comictracker.util.Filters.*
import com.enoch2.comictracker.util.OrderType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ComicTrackerViewModel(context: Context) : ViewModel() {
    private val comicDao = ComicDatabase.getDataBase(context.applicationContext).getComicDao()
    private val repository = ComicRepositoryImpl(comicDao)

    fun getComics(filter: Filters, orderType: OrderType): Flow<List<Comic>> {
        return when (orderType) {
            OrderType.ASCENDING -> {
                // 0 -> ascending
                when (filter) {
                    ALL -> return repository.getAllOrdered(0)
                    READING -> return repository.getAllReadingOrdered(0)
                    COMPLETED -> return repository.getAllCompletedOrdered(0)
                    ON_HOLD -> return repository.getAllOnHoldOrdered(0)
                    DROPPED -> return repository.getAllDroppedOrdered(0)
                    PLAN_TO_READ -> return repository.getAllPTROrdered(0)
                }
            }
            OrderType.DESCENDING -> {
                when (filter) {
                    ALL -> return repository.getAllOrdered(1)
                    READING -> return repository.getAllReadingOrdered(1)
                    COMPLETED -> return repository.getAllCompletedOrdered(1)
                    ON_HOLD -> return repository.getAllOnHoldOrdered(1)
                    DROPPED -> return repository.getAllDroppedOrdered(1)
                    PLAN_TO_READ -> return repository.getAllPTROrdered(1)
                }
            }
            else -> repository.getAll()
        }
    }

    suspend fun getComic(comicId: Int): Comic {
        return withContext(viewModelScope.coroutineContext) {
            repository.getComic(comicId)
        }
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

    fun deleteComic(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteComic(id)
        }
    }

    fun deleteAllComic() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllComic()
        }
    }
}
