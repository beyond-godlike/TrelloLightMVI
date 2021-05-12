package com.unava.dia.trellolightmvi.ui.fragments.board

import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.data.Task

sealed class BoardState {
    object Idle : BoardState()
    data class  BoardId(val id: Long?) : BoardState()
    data class CurrentBoard(val board: Board?) : BoardState()
    data class Tasks(val tasks: List<Task>) : BoardState()
    data class Error(val error: String?) : BoardState()
    object Deleted : BoardState()
    object Saved : BoardState()
}