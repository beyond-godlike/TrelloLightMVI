package com.unava.dia.trellolightmvi.ui.fragments.task

import android.os.Bundle
import android.view.View
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskFragment(boardId: Int, taskId: Int) : BaseFragment(R.layout.fragment_task) {

    override fun layoutId(): Int = R.layout.fragment_main
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // taskId = -1 means new task
    }
}