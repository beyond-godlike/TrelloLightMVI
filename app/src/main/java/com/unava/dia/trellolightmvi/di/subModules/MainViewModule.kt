package com.unava.dia.trellolightmvi.di.subModules

import androidx.lifecycle.ViewModel
import com.unava.dia.trellolightmvi.ui.main.MainViewModel
import dagger.Binds
import dagger.Module

@Module
abstract class MainViewModule {
    @Binds
    abstract fun bindMainViewModel(viewModel: MainViewModel) : ViewModel
}