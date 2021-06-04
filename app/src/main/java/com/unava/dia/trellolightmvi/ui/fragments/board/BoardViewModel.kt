package com.unava.dia.trellolightmvi.ui.fragments.board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.data.api.useCases.TasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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

    private fun saveBoard(boardId: Long, name: String) {
        viewModelScope.launch {
            _state.value = try {
                if (boardId == -1L) {
                    useCase.insertBoard(Board(name))
                } else {
                    val b = useCase.getBoardAsync(boardId)
                    b?.title = name
                    useCase.updateBoard(b!!)
                }
                BoardState.Finished
            } catch (e: Exception) {
                BoardState.Error(e.localizedMessage)
            }
        }
    }

    private fun getCurrentBoard(boardId: Long) {
        viewModelScope.launch {
            _state.value = try {
                BoardState.CurrentBoard(useCase.getBoardAsync(boardId)!!)
            } catch (e: Exception) {
                BoardState.Error(e.localizedMessage)
            }
        }
    }

    private fun getTasks(boardId: Long) {
        viewModelScope.launch {
            _state.value = BoardState.Tasks(useCase.findRepositoriesForBoard(boardId))
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

    private fun deleteBoard(id: Long) {
        viewModelScope.launch {
            _state.value = try {
                useCase.deleteBoard(id)
                BoardState.Finished
            } catch (e: java.lang.Exception) {
                BoardState.Error(e.localizedMessage)
            }
        }
    }

}