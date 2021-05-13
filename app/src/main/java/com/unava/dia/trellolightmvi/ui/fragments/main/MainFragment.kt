package com.unava.dia.trellolightmvi.ui.fragments.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.data.Board
import com.unava.dia.trellolightmvi.databinding.FragmentMainBinding
import com.unava.dia.trellolightmvi.ui.base.BaseFragment
import com.unava.dia.trellolightmvi.ui.fragments.board.BoardFragment
import com.unava.dia.trellolightmvi.ui.fragments.board.BoardViewModel
import com.unava.dia.trellolightmvi.util.RecyclerItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate),
    RecyclerItemClickListener.OnRecyclerViewItemClickListener {

    private lateinit var viewModel: MainViewModel
    private var boardsListAdapter: BoardsListAdapter? = null
    override fun layoutId(): Int = R.layout.fragment_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun initView() {
        binding.fab.setOnClickListener {
            replaceFragment(BoardFragment(-1))
        }
        lifecycleScope.launch {
            viewModel.userIntent.send(MainIntent.GetBoards)
        }
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {
                    }
                    is MainState.Boards -> {
                        renderList(it.boards)
                    }
                    is MainState.Error -> {
                        showToast(it.error!!)
                    }
                }
            }
        }
    }

    private fun renderList(boards: LiveData<List<Board>>?) {
        boards?.observe(
            viewLifecycleOwner, {
                if (it.isNotEmpty()) {
                    binding.rvMain.visibility = View.VISIBLE
                    if (boardsListAdapter == null) {
                        boardsListAdapter =
                            BoardsListAdapter(it.toMutableList())
                        binding.rvMain.adapter = boardsListAdapter

                    } else
                        boardsListAdapter!!.addBoards(it)
                } else
                    binding.rvMain.visibility = View.GONE
            }
        )
    }

    override fun setupRecyclerView() {
        val displayMetrics = requireContext().resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val columns = (dpWidth / (200 + 20)).toInt()

        binding.rvMain.layoutManager =
            StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL)
        binding.rvMain.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), this))
    }

    override fun onItemClick(parentView: View, childView: View, position: Int) {
        val board = boardsListAdapter!!.getItem(position)
        replaceFragment(BoardFragment(board.id!!))
    }
}