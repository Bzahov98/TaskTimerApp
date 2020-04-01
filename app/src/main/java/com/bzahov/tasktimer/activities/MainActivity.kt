package com.bzahov.tasktimer.activities

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bzahov.tasktimer.R
import com.bzahov.tasktimer.fragments.AddEditFragment
import com.bzahov.tasktimer.model.Task
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), AddEditFragment.OnSaveClicked {
    // Whether or the activity is in 2-pane mode
    // i.e. running in landscape, or on a tablet.
    private var mTwoPane = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setUpFragments()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun setUpFragments() {
        mTwoPane = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val currentFragment = supportFragmentManager.findFragmentById(task_details_container.id)

        if (currentFragment != null) {
            showEditPane()
        } else {
            task_details_container.visibility = if (mTwoPane) View.INVISIBLE else View.GONE
            mainFragment.view?.visibility = View.VISIBLE
        }
    }

    private fun showEditPane() {
        task_details_container.visibility = View.VISIBLE
//      hide the left hand pane, if in single pane view
        mainFragment.view?.visibility = if (mTwoPane) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(task_details_container.id)
        if (fragment == null || mTwoPane) {
            super.onBackPressed()
        }else{
            removeEditPane(fragment)
        }

    }

    private fun removeEditPane(fragment: Fragment? = null) {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
        }
        // Set the visibility of the right hand pane to gone
        task_details_container.visibility = if (mTwoPane) View.INVISIBLE else View.GONE
        // and show the left hand pane
        mainFragment.view?.visibility = View.VISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    override fun onSaveClicked() {
        Log.d(TAG, "on save clicked")
        val currentFragment = supportFragmentManager.findFragmentById(task_details_container.id)
        removeEditPane(currentFragment)
        //mainFragment.view?.visibility = View.VISIBLE

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            //R.id.menumain_settings -> true
            R.id.menumain_addTask -> taskEditRequest(null)
            android.R.id.home -> {
                val fragment = supportFragmentManager.findFragmentById(task_details_container.id)
                removeEditPane(fragment)
            }
            //else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun taskEditRequest(task: Task?): Boolean {
        Log.d(TAG, "taskEditRequest: starts")

        // Create a new fragment to edit the task
        val newFragment = AddEditFragment.newInstance(task)
        supportFragmentManager.beginTransaction()
            .replace(task_details_container.id, newFragment)
            .commit()

        showEditPane()

        Log.d(TAG, "Exiting taskEditRequest")
        return true
    }


}
