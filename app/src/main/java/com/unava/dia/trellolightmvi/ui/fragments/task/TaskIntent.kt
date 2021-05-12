package com.unava.dia.trellolightmvi.ui.fragments.task

import com.unava.dia.trellolightmvi.data.Task

sealed class TaskIntent {
    data class GetCurrentTask(val taskId: Int) : TaskIntent()
    data class SaveTask(val task: Task) : TaskIntent()
    data class UpdateTask(val taskId: Int, val title: String, val description: String) : TaskIntent()
    data class DeleteTask(val taskId: Int) : TaskIntent()
}