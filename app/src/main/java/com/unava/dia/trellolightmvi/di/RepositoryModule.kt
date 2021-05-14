package com.unava.dia.trellolightmvi.di

import android.app.Application
import android.content.Context
import com.unava.dia.trellolightmvi.data.api.AppDatabase
import com.unava.dia.trellolightmvi.data.api.dao.BoardDao
import com.unava.dia.trellolightmvi.data.api.dao.TaskDao
import com.unava.dia.trellolightmvi.data.api.repository.BoardRepository
import com.unava.dia.trellolightmvi.data.api.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return AppDatabase.getAppDataBase(application.applicationContext)!!
    }

    @Provides
    fun provideBoardDao(appDatabase: AppDatabase): BoardDao {
        return appDatabase.boardDao()
    }

    @Provides
    fun provideTaskDao(appDatabase: AppDatabase): TaskDao {
        return appDatabase.taskDao()
    }

    @Provides
    fun provideBoardRepository(@ApplicationContext context: Context, coroutineContext: CoroutineContext): BoardRepository {
        return BoardRepository(context, coroutineContext)
    }

    @Provides
    fun provideTaskRepository(@ApplicationContext context: Context, coroutineContext: CoroutineContext): TaskRepository {
        return TaskRepository(context, coroutineContext)
    }
}