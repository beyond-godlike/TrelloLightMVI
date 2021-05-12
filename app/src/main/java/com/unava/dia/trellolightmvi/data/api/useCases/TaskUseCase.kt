package com.unava.dia.trellolightmvi.data.api.useCases

import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvi.data.Task
import com.unava.dia.trellolightmvi.data.api.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskUseCase @Inject constructor(private var taskRepository: TaskRepository) {

    fun getTask(id: Int): LiveData<Task> {
        return taskRepository.getTask(id)
    }

    fun deleteTask(id: Int) {
        taskRepository.deleteTask(id)
    }

    fun updateTask(task: Task) {
        taskRepository.updateTask(task)
    }

    fun insertTask(task: Task) {
        taskRepository.insertTask(task)
    }

    fun getTaskAsync(taskId: Int) : Task? {
        return taskRepository.getTaskAsync(taskId)
    }

}