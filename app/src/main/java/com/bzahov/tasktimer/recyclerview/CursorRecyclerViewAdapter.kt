package com.bzahov.tasktimer.recyclerview

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import com.bzahov.tasktimer.R
import com.bzahov.tasktimer.contracts.TasksContract
import com.bzahov.tasktimer.model.Task
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.task_list_item.view.*

private const val TAG = "CursorRecyclerViewAdapt"

class TaskViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer{}
//    var name : TextView = containerView.findViewById(/*TODO add id*/tillName)
//}

class CursorRecyclerViewAdapter(private var cursor: Cursor?) : RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        Log.d(TAG,"OnCreateViewHolder new view requested")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list_item,parent,false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        Log.d(TAG,"onBindViewHolder starts")
        val cursor = cursor
        if(cursor== null || cursor.count == 0){
            Log.d(TAG,"onBindViewHolder providing instructions")
            //holder as ConstraintLayout // TODO: защо аз трябва да го cast-вам а във видеото няма нужда?
            //holder.tillName.setText(R.string.instructions_heading)
            holder.containerView.tillName.setText(R.string.instructions_heading)
            holder.containerView.tillDescription.setText(R.string.instructions_settings)
            holder.containerView.tillEdit.visibility = View.GONE
            holder.containerView.tillDelete.visibility = View.GONE
        } else {
            if(!cursor.moveToPosition(position)) {
                throw IllegalStateException("Couldn't move cursor to position $position")
            }

            // Create a Task object from the data in the cursor
            val task = Task(
                cursor.getString(cursor.getColumnIndex(TasksContract.Columns.TASK_NAME)),
                cursor.getString(cursor.getColumnIndex(TasksContract.Columns.TASK_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(TasksContract.Columns.TASK_SORT_ORDER)))
            // Remember that the id isn't set in the constructor
            task.id = cursor.getLong(cursor.getColumnIndex(TasksContract.Columns.ID))

//            holder as ConstraintLayout // TODO: защо аз трябва да го cast-вам а във видеото няма нужда?
            holder.containerView.tillName.text = task.name
            holder.containerView.tillDescription.text = task.description
            holder.containerView.tillEdit.visibility = View.VISIBLE       // TODO: add onClick
            holder.containerView.tillDelete.visibility = View.VISIBLE     // TODO: add onClick
        }

    }

    override fun getItemCount(): Int {
        Log.d(TAG,"onBindViewHolder get item count")
        val cursor = cursor
        val count = if(cursor == null || cursor.count == 0) {
            1
        }else{
            cursor.count
        }
        Log.d(TAG,"onBindViewHolder get item count $count")
        return count
    }

    fun swapCursor(newCursor:Cursor?) : Cursor?{
        if(newCursor === cursor){
            return null
        }

        val numItems = itemCount
        val oldCursor = cursor
       cursor = oldCursor
        if(newCursor !=null){
            notifyDataSetChanged()
        }else{
            notifyItemRangeRemoved(0,numItems)
        }
        return oldCursor
    }
}