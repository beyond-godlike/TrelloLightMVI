package com.unava.dia.trellolightmvi.ui.fragments.task

import com.unava.dia.trellolightmvi.data.Task

sealed class TaskState {
    object Idle : TaskState()
    data class TaskId(val id: Long?) : TaskState()
    data class CurrentTask(val task: Task?) : TaskState()
    data class Error(val error: String?) : TaskState()
    object Deleted : TaskState()
    object Saved : TaskState()
}