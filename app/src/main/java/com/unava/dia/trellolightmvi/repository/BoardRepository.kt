package com.unava.dia.trellolightmvi.repository

import android.content.Context
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.data.api.AppDatabase
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class BoardRepository @Inject constructor(
    private var context: Context,
    private var coroutineContext: CoroutineContext,
) : IBoardRepository {
    private val scope = CoroutineScope(coroutineContext)

    private val db: AppDatabase = AppDatabase.getAppDataBase(context)!!

    override fun getBoards() = db.boardDao().getBoards()
    override fun getBoardsSync() = db.boardDao().getBoardsSync()

    override fun getBoard(id: Long) = db.boardDao().getBoard(id)


    override fun getBoardAsync(id: Long): Board = runBlocking(Dispatchers.Default) {
        return@runBlocking withContext(Dispatchers.Default) {
            db.boardDao().getBoardAsync(id)
        }
    }

    override suspend fun insertBoard(board: Board): Long? = runBlocking(Dispatchers.Default) {
        return@runBlocking withContext(Dispatchers.Default) {
            db.boardDao().insertBoard(board)
        }
    }

    override suspend fun updateBoard(board: Board) {
        scope.launch { db.boardDao().updateBoard(board) }
    }

    override suspend fun deleteBoard(id: Long) {
        scope.launch { db.boardDao().deleteBoard(getBoardAsync(id)) }
    }
}