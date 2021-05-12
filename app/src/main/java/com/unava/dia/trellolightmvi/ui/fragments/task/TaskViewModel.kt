package com.unava.dia.trellolightmvi.ui.fragments.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unava.dia.trellolightmvi.data.Task
import com.unava.dia.trellolightmvi.data.api.useCases.TaskUseCase
import com.unava.dia.trellolightmvi.ui.fragments.board.BoardIntent
import com.unava.dia.trellolightmvi.ui.fragments.board.BoardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class TaskViewModel @Inject constructor(private var useCase: TaskUseCase) : ViewModel() {

    val userIntent = Channel<TaskIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<TaskState>(TaskState.Idle)
    val state: StateFlow<TaskState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when(it) {
                    is TaskIntent.GetCurrentTask -> getTaskAsync(it.taskId)
                    is TaskIntent.SaveTask -> saveTask(it.task)
                    is TaskIntent.UpdateTask -> updateTask(it.taskId, it.title, it.description)
                    is TaskIntent.DeleteTask -> deleteTask(it.taskId)
                }
            }
        }
    }

    private fun getTaskAsync(taskId: Int) {
        viewModelScope.launch {
            _state.value = TaskState.CurrentTask(useCase.getTaskAsync(taskId))
        }
    }

    private fun saveTask(task: Task) {
        viewModelScope.launch {
            useCase.insertTask(task)
            _state.value = TaskState.Saved
        }
    }

    private fun updateTask(taskId: Int, title: String, description: String) {
        viewModelScope.launch {
            // find existing task by taskId
            // update it from fields
            val t = useCase.getTaskAsync(taskId)
            if(t != null) {
                t.title = title
                t.description = description
                useCase.updateTask(t)
                _state.value = TaskState.Saved
            } else {
                _state.value = TaskState.Error("task is null")
            }
        }
    }

    private fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            useCase.deleteTask(taskId)
            _state.value = TaskState.Deleted
        }
    }
}