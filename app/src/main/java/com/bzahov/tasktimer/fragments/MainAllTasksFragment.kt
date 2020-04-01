package com.bzahov.tasktimer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bzahov.tasktimer.R
import com.bzahov.tasktimer.recyclerview.CursorRecyclerViewAdapter
import com.bzahov.tasktimer.viewmodel.TaskTimerViewModel
import kotlinx.android.synthetic.main.fragment_main_alltasks.*
import kotlinx.android.synthetic.main.task_list_item.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainAllTasksFragment : Fragment() {
    private val viewModel by lazy { ViewModelProviders.of(activity!!).get(TaskTimerViewModel::class.java) }
    private val mAdapter = CursorRecyclerViewAdapter(null)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_alltasks, container, false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cursor.observe(this, Observer { cursor -> mAdapter.swapCursor(cursor) })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allTasksRecycleView.layoutManager = LinearLayoutManager(context)
        allTasksRecycleView.adapter = mAdapter
        buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }


}
