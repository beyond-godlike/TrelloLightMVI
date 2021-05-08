package com.unava.dia.trellolightmvi.ui.main

import android.database.Observable
import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.mviBase.MviView
import com.unava.dia.trellolightmvi.ui.base.BaseActivity
import com.unava.dia.trellolightmvi.ui.fragments.board.BoardFragment
import com.unava.dia.trellolightmvi.ui.fragments.main.MainFragment
import com.unava.dia.trellolightmvi.ui.fragments.task.TaskFragment
import com.unava.dia.trellolightmvi.ui.main.MainViewModel_Factory.newInstance
import com.unava.dia.trellolightmvi.util.APP_ACTIVITY
import com.unava.dia.trellolightmvi.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), MviView<MainIntent, MainViewState> {

    private lateinit var viewModel: MainViewModel

    //private val clickIntent = PublishSubject.create<MainIntent.ClickIntent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP_ACTIVITY = this
        setupRecyclerView()
        this.bindViewModel()

        if (savedInstanceState == null) {
            replaceFragment(MainFragment(), false)
        }
    }

    override fun layoutId(): Int = R.layout.activity_main

    override fun bind() {
        //newsRv.layoutManager = GridLayoutManager(this, 1)
        //viewModel.processIntents(intents())
        //viewModel.states().observe(this, Observer { if (it != null) render(it) })
    }

    private fun bindViewModel() {
        this.viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //this.observeViewModel()
    }

    private fun setupRecyclerView() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun render(state: MainViewState) {
        with(state) {
            if (isLoading) {
                //progressBar.visible()
            } else {
                //progressBar.gone()
            }
            //if (!articles.isEmpty()) {
                //newsRv.adapter = NewsAdapter(articles, { clickItem -> clickIntent.onNext(HomeIntent.ClickIntent(clickItem)) })
            //}
        }
    }
}