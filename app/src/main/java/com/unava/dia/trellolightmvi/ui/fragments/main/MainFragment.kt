package com.unava.dia.trellolightmvi.ui.fragments.main

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.databinding.FragmentMainBinding
import com.unava.dia.trellolightmvi.ui.base.BaseFragment
import com.unava.dia.trellolightmvi.ui.fragments.board.BoardFragment
import com.unava.dia.trellolightmvi.util.RecyclerItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate),
        RecyclerItemClickListener.OnRecyclerViewItemClickListener {

    private var boardsListAdapter: BoardsListAdapter? = null
    override fun layoutId(): Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        binding.fab.setOnClickListener {
            replaceFragment(BoardFragment(-1), true)
        }
    }


    private fun setupRecyclerView() {
        val displayMetrics = requireContext().resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val columns = (dpWidth / (200 + 20)).toInt()

        binding.rvMain.layoutManager =
                StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL)
        binding.rvMain.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), this))
    }

    override fun onItemClick(parentView: View, childView: View, position: Int) {
        val board = boardsListAdapter!!.getItem(position)
        replaceFragment(BoardFragment(board.id!!), true)
    }
}