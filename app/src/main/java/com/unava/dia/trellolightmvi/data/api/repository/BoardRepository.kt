package com.unava.dia.trellolightmvi.data.api.repository

import android.content.Context
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.data.api.AppDatabase
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class BoardRepository @Inject constructor(
    private var context: Context,
    private var coroutineContext: CoroutineContext,
) {
    private val scope = CoroutineScope(coroutineContext)

    private val db: AppDatabase = AppDatabase.getAppDataBase(context)!!

    fun getBoards() = db.boardDao().getBoards()
    fun getBoardsSync() = db.boardDao().getBoardsSync()

    fun getBoard(id: Long) = db.boardDao().getBoard(id)


    fun getBoardAsync(id: Long): Board = runBlocking(Dispatchers.Default) {
        return@runBlocking withContext(Dispatchers.Default) {
            db.boardDao().getBoardAsync(id)
        }
    }

    fun insertBoard(board: Board): Long? = runBlocking(Dispatchers.Default) {
        return@runBlocking withContext(Dispatchers.Default) {
            db.boardDao().insertBoard(board)
        }
    }

    fun updateBoard(board: Board) {
        scope.launch { db.boardDao().updateBoard(board) }
    }

    fun deleteBoard(id: Long) {
        scope.launch { db.boardDao().deleteBoard(getBoardAsync(id)) }
    }
}