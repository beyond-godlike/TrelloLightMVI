package com.unava.dia.trellolightmvi.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.ui.base.BaseActivity
import com.unava.dia.trellolightmvi.ui.fragments.board.BoardFragment
import com.unava.dia.trellolightmvi.ui.fragments.main.MainFragment
import com.unava.dia.trellolightmvi.ui.fragments.task.TaskFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), MainFragment.MainInteractionListener, BoardFragment.BoardInteractionListener,
   TaskFragment.TaskInteractionListener

    {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(this, R.id.frameContainer)
    }

    override fun layoutId(): Int = R.layout.activity_main

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onCreateBoardClicked(boardId: Long) {
        val bundle = Bundle()
        bundle.putLong("board_id", boardId)
        navController.navigate(R.id.boardFragment, bundle)
    }

    override fun onNavigateToTaskFragment(boardId: Long, taskId: Long) {
        val bundle = Bundle()
        bundle.putLong("board_id", boardId)
        bundle.putLong("task_id", taskId)
        navController.navigate(R.id.taskFragment, bundle)
    }

    override fun onBoardFinished() {
        navController.navigate(R.id.mainFragment)
    }

    override fun onTaskFinished(boardId: Long) {
        val bundle = Bundle()
        bundle.putLong("board_id", boardId)
        navController.navigate(R.id.boardFragment, bundle)
    }

    override fun onBackPressed() {
        navController.popBackStack()
    }

}