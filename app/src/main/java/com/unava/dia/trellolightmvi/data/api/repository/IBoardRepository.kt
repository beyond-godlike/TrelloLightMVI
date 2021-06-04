package com.unava.dia.trellolightmvi.data.api.repository

import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvi.data.Board

interface IBoardRepository {
    suspend fun insertBoard(board : Board) : Long?
    suspend fun updateBoard(board: Board)
    suspend fun deleteBoard(id: Long)

    fun getBoards() : LiveData<List<Board>>
    fun getBoard(id: Long) : LiveData<Board>
}