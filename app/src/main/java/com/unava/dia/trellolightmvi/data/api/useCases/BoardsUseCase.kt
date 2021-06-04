package com.unava.dia.trellolightmvi.data.api.useCases

import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.repository.IBoardRepository
import javax.inject.Inject

class BoardsUseCase @Inject constructor(private var boardRepository: IBoardRepository) {
    fun findAllBoardsAsync(): LiveData<List<Board>>? {
        return boardRepository.getBoards()
    }

    fun findAllBoards() : List<Board> {
        return  boardRepository.getBoardsSync()
    }
}