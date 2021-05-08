package com.unava.dia.trellolightmvi.di

import com.unava.dia.trellolightmvi.di.subModules.MainViewModule
import com.unava.dia.trellolightmvi.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
/*
    @ContributesAndroidInjector(modules = [
        TaskViewModule::class
    ])
    internal abstract fun bindTaskActivity(): TaskActivity

    @ContributesAndroidInjector(modules = [
        BoardViewModule::class
    ])
    internal abstract fun bindBoardActivity(): BoardActivity

 */

    @ContributesAndroidInjector(modules = [
        MainViewModule::class
    ])
    internal abstract fun bindMainActivity(): MainActivity
}