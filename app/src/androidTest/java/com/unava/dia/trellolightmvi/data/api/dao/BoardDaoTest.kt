package com.unava.dia.trellolightmvi.data.api.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.data.api.AppDatabase
import com.unava.dia.trellolightmvi.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class BoardDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: BoardDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.boardDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertBoard() = runBlockingTest {
        val board = Board("title")
        dao.insertBoard(board)

        val allBoards = dao.getBoards().getOrAwaitValue()
        Truth.assertThat(allBoards).contains(board)
    }


    @Test
    fun deleteBoard() = runBlockingTest {
        val board = Board("tttttt")
        val insertedId = dao.insertBoard(board)
        board.id = insertedId
        dao.deleteBoard(board)

        val allBoards = dao.getBoards().getOrAwaitValue()
        Truth.assertThat(allBoards).doesNotContain(board)
    }

    @Test
    fun updateBoard() = runBlockingTest {
        val board = Board("board")
        val boardId = dao.insertBoard(board)
        board.title = "2222"
        board.id = boardId
        dao.updateBoard(board)

        val updatedBoard = dao.getBoard(boardId!!).getOrAwaitValue()
        Truth.assertThat(updatedBoard.title).isEqualTo("2222")
    }
}