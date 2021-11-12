package com.example.to_do_list

import androidx.lifecycle.LiveData

import androidx.lifecycle.ViewModel


class AddTaskViewModel(database: AppDatabase, taskId: Int) : ViewModel() {
    // COMPLETED (7) Create a getter for the task variable
    // COMPLETED (6) Add a task member variable for the TaskEntry object wrapped in a LiveData
    val task: LiveData<TaskEntry?>

    // COMPLETED (8) Create a constructor where you call loadTaskById of the taskDao to initialize the tasks variable
    // Note: The constructor should receive the database and the taskId
    init {
        task = database.taskDao()!!.loadTaskById(taskId)
    }
}