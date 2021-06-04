package com.unava.dia.trellolightmvi.ui.fragments.board

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.unava.dia.trellolightmvi.MainCoroutineRule
import com.unava.dia.trellolightmvi.data.api.useCases.TasksUseCase
import com.unava.dia.trellolightmvi.repository.FakeBoardRepository
import com.unava.dia.trellolightmvi.repository.FakeTaskRepoitory
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BoardViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: BoardViewModel

    private val dispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(dispatcher)

    @Before
    fun setUp() {
        viewModel = BoardViewModel(TasksUseCase(FakeBoardRepository(), FakeTaskRepoitory()))
    }

    // https://stackoverflow.com/questions/61224047/unit-testing-coroutines-runblockingtest-this-job-has-not-completed-yet
    @Test
    fun `insert new board and make sure it was inserted`() {
        testScope.launch {
            viewModel.userIntent.send(BoardIntent.AddNewBoard("board"))
        }

        testScope.launch {
            viewModel.state.collect {
                when(it) {
                    is BoardState.CurrentBoard -> {
                        assertThat(viewModel.state).isEqualTo(BoardState.CurrentBoard(it.board))
                        assertThat(it.board.title).isEqualTo("board")
                    }
                }
            }
        }
    }

    @Test
    fun `delete inserted board`() {
        testScope.launch {
            viewModel.userIntent.send(BoardIntent.AddNewBoard("board"))
        }

        testScope.launch {
            viewModel.state.collect {
                when(it) {
                    is BoardState.CurrentBoard -> {
                        assertThat(viewModel.state).isEqualTo(BoardState.CurrentBoard(it.board))
                        assertThat(it.board.title).isEqualTo("board")
                        val boardId = it.board.id ?: -1

                        // check that we inserted the board and got its id
                        assertThat(boardId).isNotEqualTo(-1)
                        // delete this board by id
                        viewModel.userIntent.send(BoardIntent.DeleteBoard(boardId))
                    }
                }
            }
        }

        // make sure that after deleting our state changet to Finished
        testScope.launch {
            viewModel.state.collect {
                when(it) {
                    is BoardState.CurrentBoard -> {
                        assertThat(viewModel.state).isEqualTo(BoardState.Finished)
                    }
                }
            }
        }
    }
}