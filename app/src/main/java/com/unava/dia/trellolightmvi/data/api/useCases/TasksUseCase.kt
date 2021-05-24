package com.unava.dia.trellolightmvi.data.api.useCases

import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.data.Task
import com.unava.dia.trellolightmvi.data.api.repository.BoardRepository
import com.unava.dia.trellolightmvi.data.api.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TasksUseCase @Inject constructor(
    private var boardRepository: BoardRepository,
    private var taskRepository: TaskRepository
) {

    fun findAllTasksAsync(): LiveData<List<Task>>? {
        return taskRepository.getTasks()
    }

    fun getBoard(id: Long): LiveData<Board> {
        return boardRepository.getBoard(id)
    }

    fun getBoardAsync(id: Long): Board? {
        return boardRepository.getBoardAsync(id)
    }

    fun deleteBoard(id: Long) {
        boardRepository.deleteBoard(id)
    }

    fun updateBoard(board: Board) {
        boardRepository.updateBoard(board)
    }

    fun insertBoard(board: Board) : Long? {
        return boardRepository.insertBoard(board)
    }

    fun findRepositoriesForBoardAsync(boardId: Long): List<Task>? {
        return taskRepository.findRepositoriesForBoardAsync(boardId)
    }
    fun findRepositoriesForBoard(boardId: Long): LiveData<List<Task>>? {
        return taskRepository.findRepositoriesForBoard(boardId)
    }
}