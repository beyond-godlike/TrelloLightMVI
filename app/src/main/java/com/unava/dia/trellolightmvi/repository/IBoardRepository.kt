package com.unava.dia.trellolightmvi.repository

import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvi.data.Board

interface IBoardRepository {
    suspend fun insertBoard(board : Board) : Long?
    suspend fun updateBoard(board: Board)
    suspend fun deleteBoard(id: Long)

    fun getBoardAsync(id: Long): Board
    fun getBoardsSync() : List<Board>

    fun getBoards() : LiveData<List<Board>>
    fun getBoard(id: Long) : LiveData<Board>
}