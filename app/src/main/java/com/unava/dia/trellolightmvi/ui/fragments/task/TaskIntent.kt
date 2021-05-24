package com.unava.dia.trellolightmvi.ui.fragments.task

import com.unava.dia.trellolightmvi.data.Task

sealed class TaskIntent {
    data class GetCurrentTask(val taskId: Long) : TaskIntent()
    data class SaveTask(val task: Task) : TaskIntent()
    data class UpdateTask(val taskId: Long, val title: String, val description: String) : TaskIntent()
    data class DeleteTask(val taskId: Long) : TaskIntent()
}