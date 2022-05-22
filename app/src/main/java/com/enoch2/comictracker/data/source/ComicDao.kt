package com.enoch2.comictracker.data.source

import androidx.room.*
import com.enoch2.comictracker.domain.model.Comic
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicDao {
    @Query("SELECT * FROM comic")
    fun getAll(): Flow<List<Comic>>

    @Query("SELECT * FROM comic WHERE id LIKE :comicId")
    fun getComic(comicId: Int): Flow<Comic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comics: Comic)

    @Delete
    suspend fun delete(comic: Comic)

    @Query("DELETE from comic")
    suspend fun deleteAll()
}