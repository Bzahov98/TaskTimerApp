package com.bzahov.tasktimer.databaseHelpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.bzahov.tasktimer.contracts.TasksContract

/**
 * Basic database class for the app only used by [appProvider]
 */

private const val DATABASE_NAME = "TaskTimer.db"
private const val DATABASE_VERSION = 3

internal class AppDatabase private constructor(context: Context) : SQLiteOpenHelper(


    context, DATABASE_NAME, null,
    DATABASE_VERSION
) {
    val TAG = "AppDatabase"
    init {
        Log.d(TAG, "Appdatabase init")
    }

    override fun onCreate(db: SQLiteDatabase) {
        /*CREATE TABLE tasks(_id integer primary key not null, name text not null, description text, sortOrder integer);
        insert into tasks(name,description) values('TaskTimer','Task timer -')
        insert into tasks(name,description,sortOrder) values('Android java','android java cource',2)
        insert into tasks(name,description,sortOrder) values('Android kotlin','android kotlin course',0)*/
        val ssql = createTasksTable()
        val insertSql = insertIntoTasksTable("TaskTimer", "TaskTimerApp", 0)
        val insertSql2 = insertIntoTasksTable("AndroidJava", "Android Java Course", 2)
        val insertSql3 = insertIntoTasksTable("AndroidKotlin", "Android Kotlim Course", 4)

        db.execSQL(ssql)
        db.execSQL(insertSql)
        db.execSQL(insertSql2)
        db.execSQL(insertSql3)
    }

    fun insertIntoTasksTable(name: String, description: String, sortOrder: Int): String {
        val tableName = TasksContract.TABLE_NAME
        val ssql = """Insert into $tableName 
                (name,description,sortOrder)
                Values('$name','$description',$sortOrder)"""
            .replaceIndent(" ")
        Log.d(TAG, "On Create: " + ssql)
        return ssql
    }

    private fun createTasksTable(): String {
        val ssql = """Create table ${TasksContract.TABLE_NAME}(
                ${TasksContract.Columns.ID} integer primary key not null,
                ${TasksContract.Columns.TASK_NAME} text not null,
                ${TasksContract.Columns.TASK_DESCRIPTION} text,
                ${TasksContract.Columns.TASK_SORT_ORDER} integer)""".replaceIndent(" ")
        Log.d(TAG, "On Create: " + ssql)
        return ssql
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "On upgrade starts")
        when (oldVersion) {
            1 -> {
                // upgrade logic from version 1
            }
            else -> throw IllegalArgumentException("on upgrade with unknown version b ")
        }
    }

    companion object : SingletonHolder<AppDatabase, Context>(::AppDatabase)
    /*companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: AppDatabase(context).also { instance = it }
            }
    }*/
}
