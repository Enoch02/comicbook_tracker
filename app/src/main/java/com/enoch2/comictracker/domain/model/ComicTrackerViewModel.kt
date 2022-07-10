package com.enoch2.comictracker.domain.model

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import java.io.FileOutputStream

const val TAG = "FILE_UTILS"

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
        totalIssues: String,
        id: Int = 0,
        coverPath: String
    ): Boolean {
        val temp: Comic

        if (comicTitle.isEmpty())
            return false
        if (id == 0) {
            temp = Comic(
                comicTitle,
                selectedStatus,
                rating,
                if (issuesRead.isEmpty()) 0 else issuesRead.toInt(),
                if (totalIssues.isEmpty()) 0 else totalIssues.toInt(),
                coverName = coverPath
            )
        } else {
            temp = Comic(
                comicTitle,
                selectedStatus,
                rating,
                if (issuesRead.isEmpty()) 0 else issuesRead.toInt(),
                if (totalIssues.isEmpty()) 0 else totalIssues.toInt(),
                id,
                coverPath
            )
        }
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

    // TODO: Turn all functions below into suspend function
    fun copyCover(context: Context, coverUri: Uri): String {
        try {
            var fileName = ""
            coverUri.let {
                context.contentResolver.query(coverUri, null, null, null, null)
            }?.use { cursor ->
                val name = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                fileName = cursor.getString(name)
            }

            // TODO: Compress before or after copying..
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val files = context.filesDir.listFiles()?.asList()
                val coverFile = File(context.filesDir, fileName)
                val inputStream = context.contentResolver.openInputStream(coverUri)

                if (files?.contains(coverFile) == false) {
                    inputStream.use { fis ->
                        FileOutputStream(coverFile).use { fos ->
                            val buffer = ByteArray(1024)
                            var len: Int
                            while (fis?.read(buffer).also { len = it!! } != -1) {
                                fos.write(buffer, 0, len)
                            }
                        }
                    }
                }
                Log.w(TAG, "PATH TO COPIED FILE: ${coverFile.absolutePath}")
                return coverFile.name
            } else {
                TODO("I SUCK!")
            }
        } catch (e: Exception) {
            Log.e("TEST", "copyCover() -> $e")
        }
        return ""
    }

    fun deleteAllCovers(context: Context) {
        try {
            val files = context.filesDir.listFiles()?.toList()

            files?.forEach { file ->
                file.delete()
                Log.e(TAG, "${file.name} deleted!")
            }
        } catch (e: Exception) {
            Log.e(TAG, "deleteAllCovers() -> $e")
        }
    }

    fun deleteOneCover(context: Context, coverName: String) {
        try {
            if (coverName.isNotEmpty()) {
                val file = File(context.filesDir, coverName)
                file.delete()
                Log.e(TAG, "$coverName deleted!")
            }
        } catch (e: Exception) {
            Log.e(TAG, "deleteOneCover() -> $e")
        }
    }
}
