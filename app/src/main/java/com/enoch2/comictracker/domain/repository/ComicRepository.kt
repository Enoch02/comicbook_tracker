package com.enoch2.comictracker.domain.repository

import com.enoch2.comictracker.domain.model.Comic
import com.enoch2.comictracker.data.source.ComicDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

interface ComicRepository {

    fun getComic(comicId: Int): Flow<Comic>

    suspend fun insertComic(comic: Comic)

    suspend fun deleteComic(comic: Comic)

    suspend fun deleteAllComic()

}
