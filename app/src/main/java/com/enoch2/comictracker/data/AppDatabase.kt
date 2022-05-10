package com.enoch2.comictracker.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Comic::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getComicDao() : ComicDao
}