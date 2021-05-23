package com.unava.dia.trellolightmvi.data.api.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.unava.dia.trellolightmvi.data.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task): Long?

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * from tasks")
    fun getTasks() : LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE id =:taskId")
    fun getTask(taskId: Long): LiveData<Task>

    @Query("SELECT * FROM tasks WHERE boardId =:boardId")
    fun getTasksForBoard(boardId: Long): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE boardId =:boardId")
    fun getTasksForBoardAsync(boardId: Long) : List<Task>

    @Query("SELECT * FROM tasks WHERE id =:taskId")
    fun getTaskAsync(taskId: Long): Task
}