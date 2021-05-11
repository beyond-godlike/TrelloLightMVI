package com.unava.dia.trellolightmvi.ui.fragments.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unava.dia.trellolightmvi.data.api.useCases.BoardsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private var useCase: BoardsUseCase) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.GetBoards -> getBoards()
                }
            }
        }
    }

    private fun getBoards() {
        viewModelScope.launch {
            _state.value = try {
                MainState.Boards(useCase.findAllBoardsAsync())
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
        }
    }
}