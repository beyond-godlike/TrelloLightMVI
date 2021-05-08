package com.unava.dia.trellolightmvi.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.mviBase.MviView
import com.unava.dia.trellolightmvi.ui.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MviView<MainIntent, MainViewState> {

    @Inject
    lateinit var viewModel: MainViewModel

    //private val clickIntent = PublishSubject.create<MainIntent.ClickIntent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.bind()
        this.bindViewModel()
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun layoutId(): Int = R.layout.activity_main

    override fun bind() {
        //rv.layoutManager = GridLayoutManager(this, 1)
        //viewModel.processIntents(intents())
        //viewModel.states().observe(this, Observer { if (it != null) render(it) })
    }

    private fun bindViewModel() {
        this.viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        //this.observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
            //rv.adapter = NewsAdapter(boards, { clickItem -> clickIntent.onNext(HomeIntent.ClickIntent(clickItem)) })
            //}
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}