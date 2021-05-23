package com.unava.dia.trellolightmvi.ui.fragments.task

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.data.Task
import com.unava.dia.trellolightmvi.databinding.FragmentTaskBinding
import com.unava.dia.trellolightmvi.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskFragment :
    BaseFragment<FragmentTaskBinding>(FragmentTaskBinding::inflate) {

    private lateinit var viewModel: TaskViewModel
    override fun layoutId(): Int = R.layout.fragment_task
    private var listener: TaskInteractionListener? = null

    var boardId: Long = -1
    var taskId: Long = -1 // taskId = -1 means we want to create new board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        boardId = arguments?.getLong("board_id")!!
        taskId = arguments?.getLong("task_id")!!
    }

    override fun initView() {
        if(taskId != -1L) {
            lifecycleScope.launch {
                viewModel.userIntent.send(TaskIntent.GetCurrentTask(taskId))
            }
        }

        binding.btDelete.setOnClickListener {
            lifecycleScope.launch {
                viewModel.userIntent.send(TaskIntent.DeleteTask(taskId))
            }
        }

        binding.btDone.setOnClickListener {
            lifecycleScope.launch {
                if (taskId == -1L) {
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

        binding.btDelete.setOnClickListener {
            lifecycleScope.launch {
                viewModel.userIntent.send(TaskIntent.DeleteTask(taskId))
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
                    is TaskState.Finished -> {
                        listener?.onTaskFinished(boardId)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TaskInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement InteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface TaskInteractionListener {
        fun onTaskFinished(boardId: Long)
    }
}