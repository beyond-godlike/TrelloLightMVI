package com.unava.dia.trellolightmvi.di.subModules

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.unava.dia.trellolightmvi.data.api.AppDatabase
import com.unava.dia.trellolightmvi.data.api.dao.BoardDao
import com.unava.dia.trellolightmvi.data.api.dao.TaskDao
import com.unava.dia.trellolightmvi.data.api.repository.BoardRepository
import com.unava.dia.trellolightmvi.data.api.repository.TaskRepository
import com.unava.dia.trellolightmvi.data.api.useCases.BoardsUseCase
import com.unava.dia.trellolightmvi.data.api.useCases.TaskUseCase
import com.unava.dia.trellolightmvi.data.api.useCases.TasksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @ViewModelScoped
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    @ViewModelScoped
    fun provideContext(application: Application): Context {
        return application
    }
}