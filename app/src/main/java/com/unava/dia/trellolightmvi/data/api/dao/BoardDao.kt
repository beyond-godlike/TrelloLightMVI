package com.unava.dia.trellolightmvi.data.api.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.unava.dia.trellolightmvi.data.Board

@Dao
interface BoardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBoard(board: Board): Long?

    @Update
    suspend fun updateBoard(board: Board)

    @Delete
    suspend fun deleteBoard(board: Board)

    @Query("SELECT * from boards")
    fun getBoards() : LiveData<List<Board>>

    @Query("SELECT * from boards")
    fun getBoardsSync() : List<Board>

    @Query("SELECT * FROM boards WHERE id =:boardId")
    fun getBoard(boardId: Long): LiveData<Board>

    @Query("SELECT * FROM boards WHERE id =:boardId")
    fun getBoardAsync(boardId: Long): Board
}