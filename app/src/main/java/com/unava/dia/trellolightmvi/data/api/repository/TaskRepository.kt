package com.unava.dia.trellolightmvi.data.api.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvi.data.Task
import com.unava.dia.trellolightmvi.data.api.AppDatabase
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TaskRepository @Inject constructor(
    private var context: Context,
    private var coroutineContext: CoroutineContext,
) : ITaskRepository {

    private val scope = CoroutineScope(coroutineContext)

    private val db: AppDatabase = AppDatabase.getAppDataBase(context)!!

    override fun getTasks() = db.taskDao().getTasks()
    override fun getTask(id: Long) = db.taskDao().getTask(id)

    fun getTaskAsync(id: Long): Task = runBlocking(Dispatchers.Default) {
        return@runBlocking withContext(Dispatchers.Default) {
            db.taskDao().getTaskAsync(id)
        }
    }

    override suspend fun insertTask(task: Task) {
        scope.launch { db.taskDao().insertTask(task) }
    }

    override suspend fun updateTask(task: Task) {
        scope.launch { db.taskDao().updateTask(task) }
    }

    override suspend fun deleteTask(id: Long) {
        val task = getTaskAsync(id)
        scope.launch { task.let { db.taskDao().deleteTask(it) } }
    }


    override fun findRepositoriesForBoard(boardId: Long): LiveData<List<Task>> {
        return db.taskDao().getTasksForBoard(boardId)
    }

    fun findRepositoriesForBoardAsync(boardId: Long): List<Task> =
        runBlocking(Dispatchers.Default) {
            return@runBlocking withContext(Dispatchers.Default) {
                db.taskDao().getTasksForBoardAsync(boardId)
            }
        }
}