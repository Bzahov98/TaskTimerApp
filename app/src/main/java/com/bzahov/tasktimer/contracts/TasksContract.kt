package com.bzahov.tasktimer.contracts

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns
import com.bzahov.tasktimer.databaseHelpers.CONTENT_AUTHORITY
import com.bzahov.tasktimer.databaseHelpers.CONTENT_AUTHORITY_URI

object TasksContract{
    internal const val TABLE_NAME = "tasks"
    // tasks fields
    /**
     * The URI to access task Table
     * */

    val CONTENT_URI : Uri = Uri.withAppendedPath(
        CONTENT_AUTHORITY_URI,
        TABLE_NAME
    )

    const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"
    const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"

    object Columns{
        const val ID = BaseColumns._ID
        const val TASK_NAME ="name"
        const val TASK_DESCRIPTION ="description"
        const val TASK_SORT_ORDER ="sortOrder"

    }

    fun getId(uri: Uri):Long{
        return ContentUris.parseId(uri)
    }
    fun buildUriFromId(id: Long): Uri {
        return ContentUris.withAppendedId(CONTENT_URI, id)
    }



}