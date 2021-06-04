package com.unava.dia.trellolightmvi.di

import com.unava.dia.trellolightmvi.repository.BoardRepository
import com.unava.dia.trellolightmvi.repository.TaskRepository
import com.unava.dia.trellolightmvi.data.api.useCases.BoardsUseCase
import com.unava.dia.trellolightmvi.data.api.useCases.TaskUseCase
import com.unava.dia.trellolightmvi.data.api.useCases.TasksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
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