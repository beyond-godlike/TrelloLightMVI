package com.unava.dia.trellolightmvi.ui.fragments.main

import android.content.Context
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
import com.unava.dia.trellolightmvi.util.RecyclerItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate),
    RecyclerItemClickListener.OnRecyclerViewItemClickListener {

    private lateinit var viewModel: MainViewModel
    private var boardsListAdapter: BoardsListAdapter? = null
    private var listener: MainInteractionListener? = null

    override fun layoutId(): Int = R.layout.fragment_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun initView() {
        binding.fab.setOnClickListener {
            this.listener?.onCreateBoardClicked(-1)
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
                    boardsListAdapter = BoardsListAdapter(it.toMutableList())
                    binding.rvMain.adapter = boardsListAdapter
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
        this.listener?.onCreateBoardClicked(board.id!!)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement InteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface MainInteractionListener {
        fun onCreateBoardClicked(boardId: Long)
    }
}