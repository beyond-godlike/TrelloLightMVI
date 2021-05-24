package com.unava.dia.trellolightmvi.data.api.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.data.Task
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
class TaskDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: TaskDao
    private lateinit var boardDao: BoardDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.taskDao()
        boardDao = database.boardDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertTask() = runBlockingTest {
        val board = Board("tttttt")
        val boardId = boardDao.insertBoard(board)
        board.id = boardId

        val task = Task("title", "decription", boardId!!)
        dao.insertTask(task)

        val allTasks = dao.getTasks().getOrAwaitValue()
        assertThat(allTasks).contains(task)
    }


    @Test
    fun deleteTask() = runBlockingTest {
        val board = Board("tttttt")
        val boardId = boardDao.insertBoard(board)
        board.id = boardId

        val task = Task("title", "decription", boardId!!)
        val taskId = dao.insertTask(task)
        task.id = taskId
        dao.deleteTask(task)

        val allTasks = dao.getTasks().getOrAwaitValue()
        assertThat(allTasks).doesNotContain(task)
    }

    @Test
    fun updateTask() = runBlockingTest {
        val board = Board("tttttt")
        val boardId = boardDao.insertBoard(board)
        board.id = boardId


        val task = Task("title", "decription", boardId!!)
        val taskId = dao.insertTask(task)
        task.id = taskId
        task.title = "2222"
        dao.updateTask(task)

        val updatedTask = dao.getTask(taskId!!).getOrAwaitValue()
        assertThat(updatedTask.title).isEqualTo("2222")
    }
}