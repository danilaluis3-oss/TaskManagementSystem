package com.example.todoapp.data.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskItem)

    @Update
    suspend fun update(task: TaskItem)

    @Delete
    suspend fun delete(task: TaskItem)

    @Query("SELECT * FROM tasks ORDER BY dueDate ASC")
    fun getAllTasks(): Flow<List<TaskItem>>

    @Query("SELECT * FROM tasks WHERE dueDate = :date")
    suspend fun getTasksForDate(date: String): List<TaskItem>

}