package com.bzahov.tasktimer.viewmodel

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bzahov.tasktimer.contracts.TasksContract

private val TAG = "tasktimerview model"

class TaskTimerViewModel(application: Application) : AndroidViewModel(application) {
    private val databasCursor = MutableLiveData<Cursor>()
    val cursor: LiveData<Cursor>
        get() = databasCursor
    init {
        loadTask()
    }

    private fun loadTask() {
        val projection = arrayOf(
            TasksContract.Columns.ID,
            TasksContract.Columns.TASK_NAME,
            TasksContract.Columns.TASK_DESCRIPTION,
            TasksContract.Columns.TASK_SORT_ORDER
        )
        val sortOrder =
            "${TasksContract.Columns.TASK_SORT_ORDER},${TasksContract.Columns.TASK_NAME}"
        val cursor = getApplication<Application>().contentResolver.query(
            TasksContract.CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )
        databasCursor.postValue(cursor)
    }
}