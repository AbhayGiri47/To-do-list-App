package com.example.to_do_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory


class AddTaskViewModelFactory
// COMPLETED (3) Initialize the member variables in the constructor with the parameters received
    (
// COMPLETED (2) Add two member variables. One for the database and one for the taskId
    private val mDb: AppDatabase, private val mTaskId: Int,
) :
    NewInstanceFactory() {
    // COMPLETED (4) Uncomment the following method
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddTaskViewModel(mDb, mTaskId) as T
    }
}