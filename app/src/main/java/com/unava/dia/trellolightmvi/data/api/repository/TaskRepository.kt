package com.unava.dia.trellolightmvi.data.api.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvi.data.Task
import com.unava.dia.trellolightmvi.data.api.AppDatabase
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TaskRepository @Inject constructor(private var context: Context, private var coroutineContext: CoroutineContext) {

    private val scope = CoroutineScope(coroutineContext)

    private val db: AppDatabase = AppDatabase.getAppDataBase(context)!!

    fun getTasks() = db.taskDao().getTasks()
    fun getTask(id: Int) = db.taskDao().getTask(id)

    fun getTaskAsync(id: Int): Task? = runBlocking(Dispatchers.Default) {
        return@runBlocking async { db.taskDao().getTaskAsync(id) }.await()
    }

    fun insertTask(title: String, description: String, boardId: Int) {
        scope.launch  { db.taskDao().insertTask(Task(title, description, boardId)) }
    }

    fun insertTask(task: Task) {
        scope.launch  { db.taskDao().insertTask(task) }
    }

    fun updateTask(task: Task) {
        scope.launch  { db.taskDao().updateTask(task) }
    }

    fun deleteTask(task: Task) {
        scope.launch  { db.taskDao().deleteTask(task) }
    }

    fun deleteTask(id: Int) {
        val task = getTaskAsync(id)
        scope.launch  { task?.let { db.taskDao().deleteTask(it) } }
    }


    fun findRepositoriesForBoard(boardId: Int): LiveData<List<Task>>? {
        return db.taskDao().getTasksForBoard(boardId)
    }

    fun findRepositoriesForBoardAsync(boardId: Int): List<Task>? = runBlocking(Dispatchers.Default) {
        return@runBlocking async { db.taskDao().getTasksForBoardAsync(boardId) }.await()
    }
}