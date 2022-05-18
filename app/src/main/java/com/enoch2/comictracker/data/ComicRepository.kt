package com.enoch2.comictracker.data

class ComicRepository(private val comicDao: ComicDao) {
    suspend fun getAllComic(): List<Comic> {
        return comicDao.getAll()
    }

    suspend fun findComic(comicTitle: String): Comic {
        return comicDao.findByTitle(comicTitle)
    }

    suspend fun insertComic(comic: Comic) {
        comicDao.insertAll(comic)
    }

    suspend fun deleteComic(comic: Comic) {
        comicDao.delete(comic)
    }

    suspend fun deleteAllComic() {
        comicDao.deleteAll()
    }
}