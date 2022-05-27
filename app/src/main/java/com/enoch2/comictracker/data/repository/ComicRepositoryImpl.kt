package com.enoch2.comictracker.data.repository

import com.enoch2.comictracker.data.source.ComicDao
import com.enoch2.comictracker.domain.model.Comic
import com.enoch2.comictracker.domain.repository.ComicRepository
import kotlinx.coroutines.flow.Flow

class ComicRepositoryImpl(private val dao: ComicDao) : ComicRepository {

    fun getAll(): Flow<List<Comic>> {
        return dao.getAll()
    }

    override fun getComic(comicId: Int): Flow<Comic> {
        return dao.getComic(comicId)
    }

    override suspend fun insertComic(comic: Comic) {
        dao.insert(comic)
    }

    override suspend fun deleteComic(comic: Comic) {
        dao.delete(comic)
    }

    override suspend fun deleteAllComic() {
        dao.deleteAll()
    }

    fun getAllOrdered(order: Int): Flow<List<Comic>> {
        return dao.getAllOrdered(order)
    }

    fun getAllReadingOrdered(order: Int): Flow<List<Comic>> {
        return dao.getAllReadingOrdered(order)
    }

    fun getAllCompletedOrdered(order: Int): Flow<List<Comic>> {
        return  dao.getAllCompletedOrdered(order)
    }

    fun getAllOnHoldOrdered(order: Int): Flow<List<Comic>> {
        return dao.getAllOnHoldOrdered(order)
    }

    fun getAllDroppedOrdered(order: Int): Flow<List<Comic>> {
        return dao.getAllDroppedOrdered(order)
    }

    fun getAllPTROrdered(order: Int): Flow<List<Comic>> {
        return dao.getAllPTROrdered(order)
    }
}
