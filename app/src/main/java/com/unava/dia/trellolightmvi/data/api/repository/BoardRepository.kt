package com.unava.dia.trellolightmvi.data.api.repository

import android.content.Context
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.data.api.AppDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

// inject context
class BoardRepository(context: Context) {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)


    private val db: AppDatabase = AppDatabase.getAppDataBase(context)!!

    fun getBoards() = db.boardDao().getBoards()
    fun getBoardsSync() = db.boardDao().getBoardsSync()

    fun getBoard(id: Int) = db.boardDao().getBoard(id)


    private fun getBoardAsync(id: Int): Board? = runBlocking(Dispatchers.Default) {
        return@runBlocking async { db.boardDao().getBoardAsync(id) }.await()
    }


    fun insertBoard(board: Board) : Long? = runBlocking(Dispatchers.Default) {
        return@runBlocking async { db.boardDao().insertBoard(board) }.await()
    }

    fun insertBoard(title: String) {
        scope.launch  { db.boardDao().insertBoard(Board(title)) }
    }

    fun updateBoard(board: Board) {
        scope.launch  { db.boardDao().updateBoard(board) }
    }

    fun deleteBoard(board: Board) {
        scope.launch  { db.boardDao().deleteBoard(board) }
    }

    fun deleteBoard(id: Int) {
        scope.launch  { db.boardDao().deleteBoard(getBoardAsync(id)!!) }
    }

    fun countAllBoards(): Int {
        return db.boardDao().countAllBoards()
    }
}