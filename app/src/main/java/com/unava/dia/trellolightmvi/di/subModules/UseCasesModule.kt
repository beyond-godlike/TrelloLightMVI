package com.unava.dia.trellolightmvi.di.subModules

import com.unava.dia.trellolightmvi.data.api.repository.BoardRepository
import com.unava.dia.trellolightmvi.data.api.repository.TaskRepository
import com.unava.dia.trellolightmvi.data.api.useCases.BoardsUseCase
import com.unava.dia.trellolightmvi.data.api.useCases.TaskUseCase
import com.unava.dia.trellolightmvi.data.api.useCases.TasksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
//@InstallIn(ViewModelComponent::class)
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    // use cases
    @Provides
    fun provideBoardsUseCase(boardRepository: BoardRepository): BoardsUseCase {
        return BoardsUseCase(boardRepository)
    }

    @Provides
    fun provideTasksUseCase(boardRepository: BoardRepository, taskRepository: TaskRepository): TasksUseCase {
        return TasksUseCase(boardRepository, taskRepository)
    }

    @Provides
    fun provideTaskUseCase(taskRepository: TaskRepository): TaskUseCase {
        return TaskUseCase(taskRepository)
    }
}