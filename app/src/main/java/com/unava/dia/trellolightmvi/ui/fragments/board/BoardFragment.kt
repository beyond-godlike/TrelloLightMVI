package com.unava.dia.trellolightmvi.ui.fragments.board

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.data.Task
import com.unava.dia.trellolightmvi.databinding.FragmentBoardBinding
import com.unava.dia.trellolightmvi.ui.base.BaseFragment
import com.unava.dia.trellolightmvi.ui.fragments.main.MainFragment
import com.unava.dia.trellolightmvi.ui.fragments.task.TaskFragment
import com.unava.dia.trellolightmvi.ui.fragments.task.TasksListAdapter
import com.unava.dia.trellolightmvi.util.RecyclerItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BoardFragment(private var boardId: Int) :
    BaseFragment<FragmentBoardBinding>(FragmentBoardBinding::inflate),
    RecyclerItemClickListener.OnRecyclerViewItemClickListener {

    private var tasksListAdapter: TasksListAdapter? = null

    @Inject
    lateinit var viewModel: BoardViewModel

    override fun layoutId(): Int = R.layout.fragment_board

    // boardId = -1 means we want to create new board

    override fun initView() {
        binding.btAddCard.setOnClickListener {
            // save board if new
            if (boardId == -1) {
                lifecycleScope.launch {
                    viewModel.userIntent.send(BoardIntent.AddNewBoard(binding.etBoardName.text.toString()))
                }
                // add task to existing board
            }
            replaceFragment(TaskFragment(boardId, -1))
        }

        binding.btDeleteBoard.setOnClickListener {
            lifecycleScope.launch {
                viewModel.userIntent.send(BoardIntent.DeleteBoard(boardId))
            }
        }

        binding.btSaveBoard.setOnClickListener {
            lifecycleScope.launch {
                viewModel.userIntent.send(BoardIntent.SaveBoard(boardId,
                    binding.etBoardName.text.toString()))
            }
        }
        // this board is not new
        if (boardId != -1) {
            lifecycleScope.launch {
                viewModel.userIntent.send(BoardIntent.GetCurrentBoard(boardId))
                viewModel.userIntent.send(BoardIntent.GetTasks(boardId))
            }
        }

    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is BoardState.Idle -> {
                    }
                    is BoardState.Error -> {
                        showToast(it.error!!)
                    }
                    is BoardState.BoardId -> {
                        boardId = it.id?.toInt() ?: -1
                    }
                    is BoardState.CurrentBoard -> {
                        renderBoard(it.board)
                    }
                    is BoardState.Tasks -> {
                        renderTaskList(it.tasks)
                    }
                    is BoardState.Deleted -> {
                        replaceFragment(MainFragment())
                    }
                    is BoardState.Saved -> {
                        replaceFragment(MainFragment())
                    }
                }
            }
        }
    }

    override fun setupRecyclerView() {
        binding.rvBoard.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvBoard.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), this))
    }

    private fun renderBoard(board: Board?) {
        binding.etBoardName.setText(board?.title)
    }

    private fun renderTaskList(list: List<Task>) {
        if (tasksListAdapter == null) {
            tasksListAdapter =
                TasksListAdapter(list.toMutableList())
            binding.rvBoard.adapter = tasksListAdapter
        }
        tasksListAdapter!!.addTasks(list)
    }

    override fun onItemClick(parentView: View, childView: View, position: Int) {
        val task = tasksListAdapter!!.getItem(position)
        replaceFragment(TaskFragment(boardId, task.id!!))
    }
}