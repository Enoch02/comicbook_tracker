package com.enoch2.comictracker.domain.repository

import com.enoch2.comictracker.domain.model.Comic
import kotlinx.coroutines.flow.Flow

interface ComicRepository {
    fun getAll(): Flow<List<Comic>>

    suspend fun getComic(comicId: Int): Comic

    suspend fun insertComic(comic: Comic)

    suspend fun deleteComic(id: Int)

    suspend fun deleteAllComic()

}
