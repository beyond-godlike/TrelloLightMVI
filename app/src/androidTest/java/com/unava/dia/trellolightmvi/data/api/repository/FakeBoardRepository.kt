package com.unava.dia.trellolightmvi.data.api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.unava.dia.trellolightmvi.data.Board

class FakeBoardRepository : IBoardRepository {
    private val boards = mutableListOf<Board>()
    private val board: Board? = null

    private val observableBoards = MutableLiveData<List<Board>>(boards)
    private val observableBoard = MutableLiveData<Board>(board)

    private fun refreshLiveData() {
        observableBoards.postValue(boards)
        observableBoard.postValue(board)
    }

    override suspend fun insertBoard(board: Board): Long? {
        boards.add(board)
        refreshLiveData()
        val id = boards.indexOf(board)
        return (id - 1).toLong()
    }

    override suspend fun updateBoard(board: Board) {
        val id = board.id!!.toInt() + 1
        boards.removeAt(id)
        boards[id] = board
        refreshLiveData()
    }

    override suspend fun deleteBoard(id: Long) {
        boards.removeAt((id + 1).toInt())
        refreshLiveData()
    }

    override fun getBoards(): LiveData<List<Board>> {
        return observableBoards
    }

    override fun getBoard(id: Long): LiveData<Board> {
        return observableBoard
    }
}