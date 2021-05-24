package com.unava.dia.trellolightmvi.ui.fragments.board

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.data.Task
import com.unava.dia.trellolightmvi.databinding.FragmentBoardBinding
import com.unava.dia.trellolightmvi.ui.base.BaseFragment
import com.unava.dia.trellolightmvi.ui.fragments.task.TasksListAdapter
import com.unava.dia.trellolightmvi.util.RecyclerItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class BoardFragment :
    BaseFragment<FragmentBoardBinding>(FragmentBoardBinding::inflate),
    RecyclerItemClickListener.OnRecyclerViewItemClickListener {

    private var tasksListAdapter: TasksListAdapter? = null
    private lateinit var viewModel: BoardViewModel
    var listener: BoardInteractionListener? = null
    var boardId: Long = -1 // boardId = -1 means we want to create new board

    @Inject
    lateinit var coroutineContext: CoroutineContext
    private lateinit var scope: CoroutineScope

    override fun layoutId(): Int = R.layout.fragment_board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BoardViewModel::class.java)
        boardId = arguments?.getLong("board_id")!!
        scope = CoroutineScope(coroutineContext)
    }

    override fun initView() {
        showToast(boardId.toString())
        binding.btAddCard.setOnClickListener {
            // save board if new
            if (boardId == -1L) {
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
        if (boardId != -1L) {
            scope.launch {
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
                        boardId = it.id ?: -1L
                    }
                    is BoardState.CurrentBoard -> {
                        renderBoard(it.board)
                    }
                    is BoardState.Tasks -> {
                        renderTaskList(it.tasks)
                    }
                    is BoardState.Finished -> {
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

    private fun renderTaskList(list: LiveData<List<Task>>?) {
        list?.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                tasksListAdapter = TasksListAdapter(it.toMutableList())
                binding.rvBoard.adapter = tasksListAdapter
            }
        })
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

    interface BoardInteractionListener {
        fun onNavigateToTaskFragment(boardId: Long, taskId: Long)
        fun onBoardFinished()
    }
}