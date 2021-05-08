package com.unava.dia.trellolightmvi.ui.main

import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.mviBase.MviViewState

data class MainViewState(val isLoading: Boolean,
                         val errorMessage: String,
                         val isError: Boolean,
                         val boards: List<Board>
) : MviViewState {

    companion object {
        fun idle(): MainViewState {
            return MainViewState(
                isLoading = false,
                isError = false,
                errorMessage = "",
                boards = emptyList()

            )
        }
    }
}