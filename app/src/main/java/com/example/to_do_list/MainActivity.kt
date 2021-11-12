package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.TaskAdapter.ItemClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders





class MainActivity : AppCompatActivity(), ItemClickListener {
    // Member variables for the adapter and RecyclerView
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: TaskAdapter? = null

    private var mDb: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the RecyclerView to its corresponding view
        mRecyclerView = findViewById(R.id.recyclerViewTasks)

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView?.setLayoutManager(LinearLayoutManager(this))

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = TaskAdapter(this, this)
        mRecyclerView?.setAdapter(mAdapter)
        val decoration = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
        mRecyclerView!!.addItemDecoration(decoration)


        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            // Called when a user swipes left or right on a ViewHolder
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                // Here is where you'll implement swipe to delete
                // COMPLETED (1) Get the diskIO Executor from the instance of AppExecutors and
                // call the diskIO execute method with a new Runnable and implement its run method
                // Here is where you'll implement swipe to delete
                // COMPLETED (1) Get the diskIO Executor from the instance of AppExecutors and
                // call the diskIO execute method with a new Runnable and implement its run method
                AppExecutors.instance?.diskIO()
                    ?.execute(Runnable { // COMPLETED (3) get the position from the viewHolder parameter
                        val position = viewHolder.adapterPosition
                        val tasks = mAdapter!!.getTasks()
                        // COMPLETED (4) Call deleteTask in the taskDao with the task at that position
                        mDb!!.taskDao()!!.deleteTask(tasks!![position])

                    })
            }
        }).attachToRecyclerView(mRecyclerView)

        /*
         Set the Floating Action Button (FAB) to its corresponding View.
         Attach an OnClickListener to it, so that when it's clicked, a new intent will be created
         to launch the AddTaskActivity.
         */
        val fabButton = findViewById<FloatingActionButton>(R.id.fab)
        fabButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // Create a new intent to start an AddTaskActivity
                val addTaskIntent = Intent(this@MainActivity, AddTaskActivity::class.java)
                startActivity(addTaskIntent)
            }

        })

        mDb = AppDatabase.getInstance(applicationContext)
        setupViewModel()

    }


    private fun setupViewModel() {

        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.tasks.observe(this,
            { taskEntries ->
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel")
                mAdapter!!.setTasks(taskEntries)
            })
    }


    override fun onItemClickListener(itemId: Int) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        // COMPLETED (2) Launch AddTaskActivity with itemId as extra for the key AddTaskActivity.EXTRA_TASK_ID

        val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
        intent.putExtra(AddTaskActivity.EXTRA_TASK_ID, itemId)
        startActivity(intent)
    }

    companion object {
        // Constant for logging
        private val TAG = MainActivity::class.java.simpleName
    }
}