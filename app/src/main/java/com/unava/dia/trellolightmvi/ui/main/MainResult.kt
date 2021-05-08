package com.unava.dia.trellolightmvi.ui.main

import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.mviBase.MviResult

sealed class MainResult : MviResult {
    sealed class LoadHomeResult : MainResult() {
        data class Success(val newsList: List<Board>) : LoadHomeResult()
        data class Failure(val errorMessage: String) : LoadHomeResult()
        object InFlight : LoadHomeResult()
    }

    data class ClickResult(val article: Board) : MainResult()
}