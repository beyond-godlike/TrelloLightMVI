package com.unava.dia.trellolightmvi.ui.fragments.board

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.data.Task
import com.unava.dia.trellolightmvi.databinding.FragmentBoardBinding
import com.unava.dia.trellolightmvi.ui.base.BaseFragment
import com.unava.dia.trellolightmvi.ui.fragments.task.TaskFragment
import com.unava.dia.trellolightmvi.ui.fragments.task.TasksListAdapter
import com.unava.dia.trellolightmvi.util.RecyclerItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardFragment(private val boardId: Int) : BaseFragment<FragmentBoardBinding>(FragmentBoardBinding::inflate),
        RecyclerItemClickListener.OnRecyclerViewItemClickListener {

    private var tasksListAdapter: TasksListAdapter? = null

    override fun layoutId(): Int = R.layout.fragment_main

    // boardId = -1 means we want to create new board
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btAddCard.setOnClickListener {
            // save board if new
            if (boardId == -1) {
                // TODO set board id
                //TODO insert board
            }
            replaceFragment(TaskFragment(boardId, -1), true)
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
        replaceFragment(TaskFragment(boardId, task.id!!), true)
    }
}