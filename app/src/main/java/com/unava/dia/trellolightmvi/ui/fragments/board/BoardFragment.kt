package com.unava.dia.trellolightmvi.ui.fragments.board

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
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

@AndroidEntryPoint
class BoardFragment :
    BaseFragment<FragmentBoardBinding>(FragmentBoardBinding::inflate),
    RecyclerItemClickListener.OnRecyclerViewItemClickListener {

    private var tasksListAdapter: TasksListAdapter? = null
    private lateinit var viewModel: BoardViewModel
    private var listener: BoardInteractionListener? = null
    var boardId: Int = -1 // boardId = -1 means we want to create new board

    override fun layoutId(): Int = R.layout.fragment_board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BoardViewModel::class.java)
        boardId = arguments?.getInt("board_id")!!
    }

    override fun initView() {
        binding.btAddCard.setOnClickListener {
            // save board if new
            if (boardId == -1) {
                lifecycleScope.launch {
                    viewModel.userIntent.send(BoardIntent.AddNewBoard(binding.etBoardName.text.toString()))
                }
                // add task to existing board
            }
            this.listener?.onNavigateToTaskFragment(boardId, -1)
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
                        listener?.onBoardFinished()
                    }
                    is BoardState.Saved -> {
                        listener?.onBoardFinished()
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
    }

    override fun onItemClick(parentView: View, childView: View, position: Int) {
        val task = tasksListAdapter!!.getItem(position)
        listener?.onNavigateToTaskFragment(boardId, task.id!!)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BoardInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement InteractionListener")
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface BoardInteractionListener  {
        fun onNavigateToTaskFragment(boardId: Int, taskId: Int)
        fun onBoardFinished()
    }
}