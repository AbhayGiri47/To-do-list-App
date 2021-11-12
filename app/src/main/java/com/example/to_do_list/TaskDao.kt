package com.example.to_do_list

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER BY priority")
    fun loadAllTasks(): LiveData<List<TaskEntry?>?>

    @Insert
    fun insertTask(taskEntry: TaskEntry?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTask(taskEntry: TaskEntry?)

    @Delete
    fun deleteTask(taskEntry: TaskEntry?)


    // COMPLETED (1) Create a Query method named loadTaskById that receives an int id and returns a TaskEntry Object
    // The query for this method should get all the data for that id in the task table
    @Query("SELECT * FROM task WHERE id = :id")
    fun loadTaskById(id: Int): LiveData<TaskEntry?>
}