package com.baseapplication.util

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore

object PathUtil {
    private val TAG: String = PathUtil::class.java.getSimpleName()
    fun getPathFromUri(context: Context, uri: Uri): String? {
        val isKitKat: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId: String = DocumentsContract.getDocumentId(uri)
                val split: Array<String> =
                    docId.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                val type: String = split.get(0)
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split.get(1)
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                try {
                    val id: String = DocumentsContract.getDocumentId(uri)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id)
                    )
                    return getDataColumn(context, contentUri, null, null)
                } catch (e: Exception) {
                    LogUtil.printLog(TAG, "Exceptions: " + e.toString())
                }
            } else if (isMediaDocument(uri)) {
                try {
                    val docId: String = DocumentsContract.getDocumentId(uri)
                    val split: Array<String> =
                        docId.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                    val type: String = split.get(0)
                    var contentUri: Uri? = null
                    if (("image" == type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if (("video" == type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if (("audio" == type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection: String = "_id=?"
                    val selectionArgs: Array<String> = arrayOf(
                        split.get(1)
                    )
                    return getDataColumn(context, contentUri, selection, selectionArgs)
                } catch (e: Exception) {
                    LogUtil.printLog(TAG, "Exceptions: " + e.toString())
                }
            }
        } else if ("content".equals(uri.getScheme(), ignoreCase = true)) {
            try {
                // Return the remote address
                if (isGooglePhotosUri(uri)) return uri.getLastPathSegment()
                return getDataColumn(context, uri, null, null)
            } catch (e: Exception) {
                LogUtil.printLog(TAG, "Exceptions: " + e.toString())
            }
        } else if ("file".equals(uri.getScheme(), ignoreCase = true)) {
            return uri.getPath()
        }
        return null
    }

    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column: String = "_data"
        val projection: Array<String> = arrayOf(column)
        try {
            cursor = context.getContentResolver().query(
                (uri)!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val index: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } catch (e: Exception) {
            LogUtil.printLog(TAG, "Exceptions: " + e.toString())
        } finally {
            if (cursor != null) cursor.close()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return ("com.android.externalstorage.documents" == uri.getAuthority())
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return ("com.android.providers.downloads.documents" == uri.getAuthority())
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return ("com.android.providers.media.documents" == uri.getAuthority())
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGooglePhotosUri(uri: Uri): Boolean {
        return ("com.google.android.apps.photos.content" == uri.getAuthority())
    }
}
