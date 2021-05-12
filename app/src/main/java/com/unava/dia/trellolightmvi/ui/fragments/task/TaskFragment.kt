package com.unava.dia.trellolightmvi.ui.fragments.task

import androidx.lifecycle.lifecycleScope
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.data.Task
import com.unava.dia.trellolightmvi.databinding.FragmentTaskBinding
import com.unava.dia.trellolightmvi.ui.base.BaseFragment
import com.unava.dia.trellolightmvi.ui.fragments.board.BoardFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TaskFragment(private var boardId: Int, private var taskId: Int) :
    BaseFragment<FragmentTaskBinding>(FragmentTaskBinding::inflate) {

    @Inject
    lateinit var viewModel: TaskViewModel

    override fun layoutId(): Int = R.layout.fragment_task

    override fun initView() {
        // taskId = -1 means new task
        binding.btDelete.setOnClickListener {
            lifecycleScope.launch {
                viewModel.userIntent.send(TaskIntent.DeleteTask(taskId))
            }
        }

        binding.btDone.setOnClickListener {
            lifecycleScope.launch {
                if (taskId == -1) {
                    viewModel.userIntent.send(TaskIntent.SaveTask(
                        Task(binding.etTitle.text.toString(),
                            binding.etDesc.text.toString(),
                            boardId)))
                } else {
                    viewModel.userIntent.send(TaskIntent.UpdateTask(
                        boardId,
                        binding.etTitle.text.toString(),
                        binding.etDesc.text.toString()))
                }
            }
        }
    }

    override fun setupRecyclerView() {

    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is TaskState.Idle -> {
                    }
                    is TaskState.Error -> {
                        showToast(it.error!!)
                    }
                    is TaskState.CurrentTask -> {
                        renderTask(it.task)
                    }
                    is TaskState.Deleted -> {
                        replaceFragment(BoardFragment(boardId))
                    }
                    is TaskState.Saved -> {
                        replaceFragment(BoardFragment(boardId))
                    }
                }
            }
        }
    }

    private fun renderTask(task: Task?) {
        if (task != null) {
            binding.etTitle.setText(task.title)
            binding.etDesc.setText(task.description)
        }
    }
}