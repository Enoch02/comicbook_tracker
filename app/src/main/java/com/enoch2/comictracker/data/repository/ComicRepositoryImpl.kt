package com.enoch2.comictracker.data.repository

import com.enoch2.comictracker.data.source.ComicDao
import com.enoch2.comictracker.domain.model.Comic
import com.enoch2.comictracker.domain.repository.ComicRepository
import kotlinx.coroutines.flow.Flow

class ComicRepositoryImpl(private val dao: ComicDao): ComicRepository {
    override val comics = dao.getAll()

    override suspend fun getComic(comicTitle: String): Comic {
        return dao.getByTitle(comicTitle)
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