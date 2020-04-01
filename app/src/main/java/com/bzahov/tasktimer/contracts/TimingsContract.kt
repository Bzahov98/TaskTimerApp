package com.bzahov.tasktimer.contracts

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns
import com.bzahov.tasktimer.databaseHelpers.CONTENT_AUTHORITY
import com.bzahov.tasktimer.databaseHelpers.CONTENT_AUTHORITY_URI

object TimingsContract{
    internal const val TABLE_NAME = "timings"
    val CONTENT_URI : Uri = Uri.withAppendedPath(
        CONTENT_AUTHORITY_URI,
        TasksContract.TABLE_NAME
    )

    const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$CONTENT_AUTHORITY.${TimingsContract.TABLE_NAME}"
    const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$CONTENT_AUTHORITY.${TimingsContract.TABLE_NAME}"

    object Columns{
        const val ID = BaseColumns._ID
        const val TIMING_TASK_ID ="taskId"
        const val TIMING_START_TIME ="startTime"
        const val TIMING_DURATION ="duration"

    }
    fun getId(uri: Uri):Long{
        return ContentUris.parseId(uri)
    }
    fun buildFromId(id: Long):Uri{
        return ContentUris.withAppendedId(CONTENT_URI,id)
    }
}