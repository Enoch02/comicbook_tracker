package com.enoch2.comictracker.data.source

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.enoch2.comictracker.domain.model.Comic
import junit.framework.TestCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComicDatabaseTest: TestCase() {
    private lateinit var db: ComicDatabase
    private lateinit var dao: ComicDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ComicDatabase::class.java).build()
        dao = db.getComicDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @OptIn(FlowPreview::class)
    @Test
    fun writeAndReadComic() = runBlocking {
        val comic = Comic("Foo", id = 5)
        dao.insert(comic)
        val gComic = dao.getAllTest()
        assert(gComic.contains(comic))
    }
}