package com.example.to_do_list

import androidx.lifecycle.LiveData

import android.app.Application
import android.util.Log

import androidx.lifecycle.AndroidViewModel


class MainViewModel(application: Application?) : AndroidViewModel(application!!) {
    // COMPLETED (3) Create a getter for the tasks variable
    // COMPLETED (2) Add a tasks member variable for a list of TaskEntry objects wrapped in a LiveData
    val tasks: LiveData<List<TaskEntry?>?>

    companion object {
        // Constant for logging
        private val TAG = MainViewModel::class.java.simpleName
    }

    init {
        // COMPLETED (4) In the constructor use the loadAllTasks of the taskDao to initialize the tasks variable
        val database = AppDatabase.getInstance(getApplication())
        Log.d(TAG, "Actively retrieving the tasks from the DataBase")
        tasks = database!!.taskDao()!!.loadAllTasks()
    }
}