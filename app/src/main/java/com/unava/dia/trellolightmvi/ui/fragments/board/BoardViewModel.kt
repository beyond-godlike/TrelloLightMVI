package com.unava.dia.trellolightmvi.ui.fragments.board

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.data.Task
import com.unava.dia.trellolightmvi.data.api.useCases.TasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class BoardViewModel @Inject constructor(private var useCase: TasksUseCase) : ViewModel() {
    fun getTasks() : LiveData<List<Task>>? {
        return this.useCase.findAllTasksAsync()
    }

    fun getBoard(id: Int) : LiveData<Board> {
        return this.useCase.getBoard(id)
    }

    fun deleteBoard(id: Int) {
        this.useCase.deleteBoard(id)
    }

    fun updateBoard(board: Board) {
        this.useCase.updateBoard(board)
    }

    fun insertBoard(text: String) : Long? {
        return useCase.insertBoard(Board(text))
    }
    fun findReposForTask (boardId: Int) : LiveData<List<Task>>? {
        return this.useCase.findRepositoriesForTask(boardId)
    }

}