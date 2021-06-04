package com.unava.dia.trellolightmvi.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideCoroutineContext(): CoroutineContext {
        val parentJob = Job()
        return parentJob + Dispatchers.Default
    }

    @Provides
    fun provideContext(application: Application): Context {
        return application
    }
}