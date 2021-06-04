package com.unava.dia.trellolightmvi.data.api.repository

import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvi.data.Task

interface ITaskRepository {
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(id: Long)

    fun getTasks() : LiveData<List<Task>>
    fun getTask(id: Long) : LiveData<Task>
    fun findRepositoriesForBoard(boardId: Long) : LiveData<List<Task>>
}