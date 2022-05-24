package com.enoch2.comictracker.data.repository

import com.enoch2.comictracker.data.source.ComicDao
import com.enoch2.comictracker.domain.model.Comic
import com.enoch2.comictracker.domain.repository.ComicRepository
import kotlinx.coroutines.flow.*

class ComicRepositoryImpl(private val dao: ComicDao): ComicRepository {
    override val comics = dao.getAll()

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

}