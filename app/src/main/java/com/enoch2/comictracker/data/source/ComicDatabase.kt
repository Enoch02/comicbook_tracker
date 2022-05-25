package com.enoch2.comictracker.data.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enoch2.comictracker.domain.model.Comic

@Database(entities = [Comic::class], version = 1)
abstract class ComicDatabase : RoomDatabase() {
    abstract fun getComicDao() : ComicDao

    companion object {
        private var instance: ComicDatabase? = null

        @Synchronized
        fun getDataBase(context: Context): ComicDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ComicDatabase::class.java,
                    "comics"
                ).build()
            }
            return instance!!
        }
    }
}