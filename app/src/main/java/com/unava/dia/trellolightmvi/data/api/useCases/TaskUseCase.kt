package com.unava.dia.trellolightmvi.data.api.useCases

import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvi.data.Task
import com.unava.dia.trellolightmvi.repository.ITaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskUseCase @Inject constructor(private var taskRepository: ITaskRepository) {

    fun getTask(id: Long): LiveData<Task> {
        return taskRepository.getTask(id)
    }

    suspend fun deleteTask(id: Long) {
        taskRepository.deleteTask(id)
    }

    suspend fun updateTask(task: Task) {
        taskRepository.updateTask(task)
    }

    suspend fun insertTask(task: Task) {
        taskRepository.insertTask(task)
    }

    fun getTaskAsync(taskId: Long): Task? {
        return taskRepository.getTaskAsync(taskId)
    }

}