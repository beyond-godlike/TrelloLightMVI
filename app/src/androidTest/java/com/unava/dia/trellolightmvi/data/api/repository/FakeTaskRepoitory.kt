package com.unava.dia.trellolightmvi.data.api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.unava.dia.trellolightmvi.data.Task

class FakeTaskRepoitory : ITaskRepository {
    private val tasks = mutableListOf<Task>()
    private val task: Task? = null

    private val observableTasks = MutableLiveData<List<Task>>(tasks)
    private val observableTasksForBoard = MutableLiveData<List<Task>>(tasks)
    private val observableTask = MutableLiveData<Task>(task)


    private fun refreshLiveData() {
        observableTasks.postValue(tasks)
        observableTasksForBoard.postValue(findRepositoriesForBoard(task!!.boardId).value)
        observableTask.postValue(task)
    }

    override suspend fun insertTask(task: Task) {
        tasks.add(task)
        refreshLiveData()
    }

    override suspend fun updateTask(task: Task) {
        val id = task.id!!.toInt() + 1
        tasks.removeAt(id)
        tasks[id] = task
        refreshLiveData()
    }

    override suspend fun deleteTask(id: Long) {
        tasks.removeAt(id.toInt()+1)
        refreshLiveData()
    }

    override fun getTasks(): LiveData<List<Task>> {
        return observableTasks
    }

    override fun getTask(id: Long): LiveData<Task> {
        return observableTask
    }

    override fun findRepositoriesForBoard(boardId: Long): LiveData<List<Task>> {
        return observableTasksForBoard
    }
}