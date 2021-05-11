package com.unava.dia.trellolightmvi.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.mviBase.MviView
import com.unava.dia.trellolightmvi.ui.base.BaseActivity
import com.unava.dia.trellolightmvi.ui.fragments.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            replaceFragment(MainFragment(), false)
        }
    }

    override fun layoutId(): Int = R.layout.activity_main

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onBackPressed() {

    }
}