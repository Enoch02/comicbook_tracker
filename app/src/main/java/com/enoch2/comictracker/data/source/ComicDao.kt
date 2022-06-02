package com.enoch2.comictracker.data.source

import androidx.room.*
import com.enoch2.comictracker.domain.model.Comic
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicDao {
    @Query("SELECT * FROM comic")
    fun getAll(): Flow<List<Comic>>

    @Query("SELECT * FROM comic")
    fun getAllTest(): List<Comic>

    @Query(
        "SELECT * FROM comic ORDER BY " +
                "CASE WHEN :order = 0 THEN title END ASC," +
                "CASE WHEN :order = 1 THEN title END DESC"
    )
    fun getAllOrdered(order: Int): Flow<List<Comic>>

    @Query(
        "SELECT * FROM comic WHERE status LIKE 'reading'" +
                "ORDER BY CASE WHEN :order = 0 THEN title END ASC," +
                "CASE WHEN :order = 1 THEN title END DESC"
    )
    fun getAllReadingOrdered(order: Int): Flow<List<Comic>>

    @Query(
        "SELECT * FROM comic WHERE status LIKE 'completed'" +
                "ORDER BY CASE WHEN :order = 0 THEN title END ASC," +
                "CASE WHEN :order = 1 THEN title END DESC"
    )
    fun getAllCompletedOrdered(order: Int): Flow<List<Comic>>

    @Query(
        "SELECT * FROM comic WHERE status LIKE 'on hold'" +
                "ORDER BY CASE WHEN :order = 0 THEN title END ASC," +
                "CASE WHEN :order = 1 THEN title END DESC"
    )
    fun getAllOnHoldOrdered(order: Int): Flow<List<Comic>>

    @Query(
        "SELECT * FROM comic WHERE status LIKE 'dropped'" +
                "ORDER BY CASE WHEN :order = 0 THEN title END ASC," +
                "CASE WHEN :order = 1 THEN title END DESC"
    )
    fun getAllDroppedOrdered(order: Int): Flow<List<Comic>>

    @Query(
        "SELECT * FROM comic WHERE status LIKE 'plan to read'" +
                "ORDER BY CASE WHEN :order = 0 THEN title END ASC," +
                "CASE WHEN :order = 1 THEN title END DESC"
    )
    fun getAllPTROrdered(order: Int): Flow<List<Comic>>

    @Query("SELECT * FROM comic WHERE id LIKE :comicId")
    suspend fun getComic(comicId: Int): Comic

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comic: Comic)

    @Query("DELETE from comic WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE from comic")
    suspend fun deleteAll()
}