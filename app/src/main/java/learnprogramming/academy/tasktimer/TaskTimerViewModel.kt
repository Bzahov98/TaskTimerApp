package learnprogramming.academy.tasktimer

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.database.Cursor
import android.util.Log

/**
 * Created by timbuchalka for the Android Pie using Kotlin course
 * from www.learnprogramming.academy
 */
private const val TAG = "TaskTimerViewModel"

class TaskTimerViewModel (application: Application) : AndroidViewModel(application) {

    private val databaseCursor = MutableLiveData<Cursor>()
    val cursor: LiveData<Cursor>
        get() = databaseCursor

    init {
        Log.d(TAG, "TaskTimerViewModel: created")
        loadTasks()
    }

    private fun loadTasks() {
        val projection = arrayOf(TasksContract.Columns.ID,
                TasksContract.Columns.TASK_NAME,
                TasksContract.Columns.TASK_DESCRIPTION,
                TasksContract.Columns.TASK_SORT_ORDER)
        // <order by> Tasks.SortOrder, Tasks.Name
        val sortOrder = "${TasksContract.Columns.TASK_SORT_ORDER}, ${TasksContract.Columns.TASK_NAME}"

        val cursor = getApplication<Application>().contentResolver.query(
                TasksContract.CONTENT_URI,
                projection, null, null,
                sortOrder)
        databaseCursor.postValue(cursor)
    }
}