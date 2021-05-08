package com.unava.dia.trellolightmvi

import android.app.Activity
import androidx.multidex.MultiDexApplication
import com.unava.dia.trellolightmvi.di.AppComponent
import com.unava.dia.trellolightmvi.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TrelloLightApp : MultiDexApplication(), HasActivityInjector {
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    var appComponent: AppComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        this.initDagger()
        // TODO init db?
    }

    private fun initDagger() {
        this.appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        this.appComponent!!
            .inject(this)
    }
    override fun activityInjector(): AndroidInjector<Activity>? {
        return dispatchingActivityInjector
    }
}