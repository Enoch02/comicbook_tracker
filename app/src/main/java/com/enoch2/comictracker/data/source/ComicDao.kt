package com.enoch2.comictracker.data.source

import androidx.room.*
import com.enoch2.comictracker.domain.model.Comic
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicDao {
    @Query("SELECT * FROM comic")
    fun getAll(): Flow<List<Comic>>

    @Query("SELECT * FROM comic ORDER BY " +
            "CASE WHEN :order = 0 THEN title END ASC," +
            "CASE WHEN :order = 1 THEN title END DESC")
    fun getAllOrdered(order: Int): Flow<List<Comic>>

    @Query("SELECT * FROM comic WHERE status LIKE 'reading'" +
            "ORDER BY CASE WHEN :order = 0 THEN title END ASC," +
            "CASE WHEN :order = 1 THEN title END DESC")
    fun getAllReadingOrdered(order: Int): Flow<List<Comic>>

    @Query("SELECT * FROM comic WHERE status LIKE 'on hold'" +
            "ORDER BY CASE WHEN :order = 0 THEN title END ASC," +
            "CASE WHEN :order = 1 THEN title END DESC")
    fun getAllOnHoldOrdered(order: Int): Flow<List<Comic>>

    @Query("SELECT * FROM comic WHERE id LIKE :comicId")
    fun getComic(comicId: Int): Flow<Comic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comics: Comic)

    @Delete
    suspend fun delete(comic: Comic)

    @Query("DELETE from comic")
    suspend fun deleteAll()
}