package com.bzahov.tasktimer.databaseHelpers

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.bzahov.tasktimer.contracts.TasksContract


val contentResolver: ContentResolver? = null //  only to resolve errors
private const val TAG = "sss"

// Extracted test database  functionality from mainActivity
class TestDatabase(context: Context) {

    fun testDatabase() {
        contentResolver!!//  only to resolve errors
        testInsert("name1", "descr1", 1)
        testInsert("name2", "descr2", 2)
        testInsert("name3", "descr3", 3)
//
//        testUpdate()
//
//        testUpdateTwo()
//
//
//        testDeleteTwo()


        val projection =
            arrayOf(TasksContract.Columns.TASK_NAME, TasksContract.Columns.TASK_SORT_ORDER)
        val sortColumn = TasksContract.Columns.TASK_SORT_ORDER

        //        val cursor = contentResolver.query(TasksContract.buildUriFromId(2),
        val cursor = contentResolver.query(
            TasksContract.CONTENT_URI,
            null,
            null,
            null,
            sortColumn
        )
        Log.d(TAG, "*****************************")
        cursor.use {
            while (it!!.moveToNext()) {
                // Cycle through all records
                with(cursor!!) {
                    val id = getLong(0)
                    val name = getString(1)
                    val description = getString(2)
                    val sortOrder = getString(3)
                    val result =
                        "ID: $id | Name: $name| Description: $description | Sort order: $sortOrder."
                    android.util.Log.d(TAG, "onCreate: reading data $result")
                }
            }
        }
        Log.d(TAG, "*****************************")

        }
    }

    private fun testDelete(id: Long) {

        contentResolver!!//  only to resolve errors

        val taskUri = TasksContract.buildUriFromId(id)
        val rowsAffected = contentResolver.delete(taskUri, null, null)
        Log.d(TAG, "Number of rows deleted is $rowsAffected")
    }

    private fun testDeleteTwo() {

        contentResolver!!//  only to resolve errors

        val selection = TasksContract.Columns.TASK_DESCRIPTION + " = ?"
        val selectionArgs = arrayOf("For deletion")
        val rowsAffected = contentResolver.delete(
            TasksContract.CONTENT_URI,
            selection,
            selectionArgs
        )

        Log.d(TAG, "Number of rows deleted is $rowsAffected")
    }


    private fun testUpdateTwo() {

        contentResolver!!//  only to resolve errors
        val values = ContentValues().apply {
            put(TasksContract.Columns.TASK_SORT_ORDER, 999)
            put(TasksContract.Columns.TASK_DESCRIPTION, "For deletion")
        }

        val selection = TasksContract.Columns.TASK_SORT_ORDER + " = ?"
        val selectionArgs = arrayOf("99")
        val rowsAffected = contentResolver.update(
            TasksContract.CONTENT_URI,
            values,
            selection,
            selectionArgs
        )

        Log.d(TAG, "Number of rows updated is $rowsAffected")
    }


    private fun testUpdate() {

        contentResolver!!//  only to resolve errors

        val values = ContentValues().apply {
            put(TasksContract.Columns.TASK_NAME, "Content Provider")
            put(TasksContract.Columns.TASK_DESCRIPTION, "Record content providers videos")
        }

        val taskUri = TasksContract.buildUriFromId(4)
        val rowsAffected = contentResolver.update(taskUri, values, null, null)
        Log.d(TAG, "Number of rows updated is $rowsAffected")
    }

    private fun testInsert(taskName: String, description: String, order: Int) {
        contentResolver!!//  only to resolve errors

        val values = ContentValues().apply {
            put(TasksContract.Columns.TASK_NAME, taskName)
            put(TasksContract.Columns.TASK_DESCRIPTION, description)
            put(TasksContract.Columns.TASK_SORT_ORDER, order)
        }

        val uri = contentResolver.insert(TasksContract.CONTENT_URI, values)
        Log.d(TAG, "New row id (in uri) is $uri")
        Log.d(TAG, "id (in uri) is ${TasksContract.getId(uri!!)}")
    }