package com.unava.dia.trellolightmvi.ui.fragments.board

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.data.Task
import com.unava.dia.trellolightmvi.databinding.FragmentBoardBinding
import com.unava.dia.trellolightmvi.ui.base.BaseFragment
import com.unava.dia.trellolightmvi.ui.fragments.main.MainFragment
import com.unava.dia.trellolightmvi.ui.fragments.main.MainViewModel
import com.unava.dia.trellolightmvi.ui.fragments.task.TaskFragment
import com.unava.dia.trellolightmvi.ui.fragments.task.TasksListAdapter
import com.unava.dia.trellolightmvi.util.RecyclerItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BoardFragment(private var boardId: Int) : BaseFragment<FragmentBoardBinding>(FragmentBoardBinding::inflate),
        RecyclerItemClickListener.OnRecyclerViewItemClickListener {

    private var tasksListAdapter: TasksListAdapter? = null

    @Inject
    lateinit var viewModel: BoardViewModel

    override fun layoutId(): Int = R.layout.fragment_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    // boardId = -1 means we want to create new board
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setupRecyclerView()

        binding.btAddCard.setOnClickListener {
            // save board if new
            if (boardId == -1) {
                boardId = viewModel.insertBoard(binding.etBoardName.text.toString())?.toInt() ?: -1
            }
            replaceFragment(TaskFragment(boardId, -1))
        }

        binding.btDeleteBoard.setOnClickListener{
            viewModel.deleteBoard(boardId)
            replaceFragment(MainFragment())
        }
        binding.btSaveBoard.setOnClickListener {
            if (boardId == -1) {
                viewModel.insertBoard(binding.etBoardName.text.toString())
                replaceFragment(MainFragment())
            } else {
                viewModel.getBoard(boardId).observe(viewLifecycleOwner, { b ->
                        if (b != null) {
                            b.title = binding.etBoardName.text.toString()
                            viewModel.updateBoard(b)
                            replaceFragment(MainFragment())
                        }
                    }
                )
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvBoard.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvBoard.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), this))
    }

    private fun updateTaskList(list: List<Task>) {
        if (list.isNotEmpty()) {
            if (tasksListAdapter == null) {
                tasksListAdapter =
                        TasksListAdapter(list.toMutableList())
                binding.rvBoard.adapter = tasksListAdapter
                tasksListAdapter?.addTasks(list)
            }
        }
    }

    override fun onItemClick(parentView: View, childView: View, position: Int) {
        val task = tasksListAdapter!!.getItem(position)
        replaceFragment(TaskFragment(boardId, task.id!!))
    }
}