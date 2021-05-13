package com.unava.dia.trellolightmvi.ui.fragments.board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.data.api.useCases.TasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class BoardViewModel @Inject constructor(private var useCase: TasksUseCase) : ViewModel() {

    // TODO extract to main view model
    val userIntent = Channel<BoardIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<BoardState>(BoardState.Idle)
    val state: StateFlow<BoardState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is BoardIntent.GetCurrentBoard -> getCurrentBoard(it.boardId)
                    is BoardIntent.GetTasks -> getTasks(it.boardId)
                    is BoardIntent.DeleteBoard -> deleteBoard(it.boardId)
                    is BoardIntent.AddNewBoard -> addNewBoard(it.name)
                    is BoardIntent.SaveBoard -> saveBoard(it.boardId, it.name)
                }
            }
        }
    }

    private fun saveBoard(boardId: Int, name: String) {
        viewModelScope.launch {
            _state.value = try {
                if (boardId == -1) {
                    useCase.insertBoard(Board(name))
                } else {
                    val b = useCase.getBoardAsync(boardId)
                    b?.title = name
                    useCase.updateBoard(b!!)
                }
                BoardState.Saved
            } catch (e: Exception) {
                BoardState.Error(e.localizedMessage)
            }
        }
    }

    private fun getCurrentBoard(boardId: Int) {
        viewModelScope.launch {
            _state.value = try {
                BoardState.CurrentBoard(useCase.getBoardAsync(boardId))
            } catch (e: Exception) {
                BoardState.Error(e.localizedMessage)
            }
        }
    }

    private fun getTasks(boardId: Int) {
        viewModelScope.launch {
            _state.value = try {
                BoardState.Tasks(useCase.findRepositoriesForBoardAsync(boardId)!!)
            } catch (e: Exception) {
                BoardState.Error(e.localizedMessage)
            }
        }
    }

    private fun addNewBoard(name: String) {
        viewModelScope.launch {
            _state.value = try {
                BoardState.BoardId(useCase.insertBoard(Board(name)))
            } catch (e: java.lang.Exception) {
                BoardState.Error(e.localizedMessage)
            }
        }
    }

    private fun deleteBoard(id: Int) {
        viewModelScope.launch {
            _state.value = try {
                useCase.deleteBoard(id)
                BoardState.Deleted
            } catch (e: java.lang.Exception) {
                BoardState.Error(e.localizedMessage)
            }
        }
    }

}