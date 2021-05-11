package com.unava.dia.trellolightmvi.data.api.useCases

import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.data.api.repository.BoardRepository
import javax.inject.Inject
import javax.inject.Singleton

class BoardsUseCase @Inject constructor(private var boardRepository: BoardRepository) {
    fun findAllBoardsAsync(): LiveData<List<Board>>? {
        return boardRepository.getBoards()
    }

    fun findAllBoards() : List<Board> {
        return  boardRepository.getBoardsSync()
    }
}