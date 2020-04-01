package com.bzahov.tasktimer.fragments


import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bzahov.tasktimer.R
import com.bzahov.tasktimer.contracts.TasksContract
import com.bzahov.tasktimer.model.Task
import kotlinx.android.synthetic.main.fragment_add_edit.*

/**
 * <u>A simple [Fragment] subclass X--as-the-second-destination-X-in-the navigation.--X</u>
 * * Activities that contain this fragment must implement the
 * [AddEditFragment.OnSaveClicked] interface
 * to handle interaction events.
 * Use the [AddEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_TASK = "task"
private const val TAG = "AddeditFrag,emt"

class AddEditFragment : Fragment() {

    private var task: Task? = null
    private var listener: OnSaveClicked? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = arguments?.getParcelable(ARG_TASK)
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onSaveClicked()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSaveClicked) {
            listener = context
        } else {
            throw RuntimeException("${context}Must implement OnFragIterList")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            val task = task
            if (task != null) {
                Log.d(TAG, "onnViewCreated for task ${task.id}")
                addEditName.setText(task.name)
                addEditDescriptuion.setText(task.description)
                addEditSort.setText(Integer.toString(task.sortOrder))
            } else {
                Log.d(TAG, "No task, add new record, Not editing an existing one")
            }
        }
        button_second.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private fun saveTask() {
        // update database if there is changes
        val sortOrder = if (addEditSort.text.isNotEmpty()) {
            Integer.parseInt(addEditSort.text.toString())
        } else {
            0
        }
        val values = ContentValues()
        val task = task
        if(task!=null){
            Log.d(TAG,"saveTask")
            val name = addEditName.text.toString()
            if(name != task.name){
                values.put(TasksContract.Columns.TASK_NAME, name)
            }
            val description = addEditDescriptuion.text.toString()

            if(description != task.description){
                values.put(TasksContract.Columns.TASK_DESCRIPTION, description)
            }

            if(sortOrder != task.sortOrder){
                values.put(TasksContract.Columns.TASK_SORT_ORDER, sortOrder)
            }

            if(values.size() != 0){
                activity?.contentResolver?.update(TasksContract.buildUriFromId(task.id), values,null,null)
            }
        } else {
            Log.d(TAG,"saveTask: adding new task")
            val nameField = addEditName.text
            if(nameField.isNotEmpty()){
                values.put(TasksContract.Columns.TASK_NAME,nameField.toString())
                val descriptionField = addEditDescriptuion.text
                if(descriptionField.isNotEmpty()){
                    values.put(TasksContract.Columns.TASK_DESCRIPTION, descriptionField.toString())
                }
                values.put(TasksContract.Columns.TASK_SORT_ORDER,sortOrder)

                activity?.contentResolver?.insert(TasksContract.CONTENT_URI,values)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "On actvity created")
        if (listener is AppCompatActivity) {
            val actionBar = (listener as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
        }
        addEditSave.setOnClickListener {
            saveTask()
            listener?.onSaveClicked()
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnSaveClicked {
        fun onSaveClicked()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param task The task to be edited, or null to add a new task.
         * @return A new instance of fragment AddEditFragment.
         */
        @JvmStatic
        fun newInstance(task: Task?) =
            AddEditFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_TASK, task)
                }
            }
    }

//fun createFrag(task: Task) {
//    val args = Bundle()
//    args.putParcelable(ARG_TASK, task)
//    val fragment = AddEditFragment()
//    fragment.arguments = args
//}
//
//fun createFrag2(task: Task) {
//    val fragment = AddEditFragment().apply {
//        arguments = Bundle().apply {
//            putParcelable(ARG_TASK, task)
//        }
//    }
//}
//
//fun simpler(task: Task) {
//    val fragment = AddEditFragment.newInstance(task)
//}

}
