package com.unava.dia.trellolightmvi.data.api.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.unava.dia.trellolightmvi.data.Board

@Dao
interface BoardDao {
    @Insert
    fun insertBoard(board: Board): Long?

    @Query("SELECT * from Board")
    fun getBoards() : LiveData<List<Board>>

    @Query("SELECT * from Board")
    fun getBoardsSync() : List<Board>

    @Query("SELECT * FROM Board WHERE id =:boardId")
    fun getBoard(boardId: Int): LiveData<Board>

    @Query("SELECT * FROM Board WHERE id =:boardId")
    fun getBoardAsync(boardId: Int): Board

    @Update
    fun updateBoard(board: Board)

    @Delete
    fun deleteBoard(board: Board)

    @Query("SELECT COUNT(*) FROM Board")
    fun countAllBoards(): Int
}