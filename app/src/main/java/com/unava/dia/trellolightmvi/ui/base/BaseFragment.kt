package com.unava.dia.trellolightmvi.ui.base

import android.app.Application
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment(layout:Int) : Fragment(layout) {

    @Inject
    lateinit var appContext: Application

    @LayoutRes
    abstract fun layoutId(): Int
}