package com.unava.dia.trellolightmvi.ui.fragments.main

import androidx.lifecycle.LiveData
import com.unava.dia.trellolightmvi.data.Board

sealed class MainState {
    object Idle : MainState()
    data class Boards(val boards: LiveData<List<Board>>?) : MainState()
    data class Error(val error: String?) : MainState()
}