package com.enoch2.comictracker.data

import androidx.room.*

@Dao
interface ComicDao {
    @Query("SELECT * FROM comic")
    suspend fun getAll(): List<Comic>

    @Query("SELECT * FROM comic WHERE title LIKE :comicTitle LIMIT 1")
    suspend fun findByTitle(comicTitle: String): Comic

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg comics: Comic)

    @Delete
    suspend fun delete(comic: Comic)

    @Query("DELETE from comic")
    suspend fun deleteAll()

    @Update
    suspend fun update(comic: Comic)
}