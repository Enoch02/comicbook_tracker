package com.enoch2.comictracker.util

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.util.Log
import java.io.File
import java.io.FileOutputStream

fun copyCover(context: Context, coverUri: Uri): String {
    try {
        var fileName = ""
        coverUri.let {
            context.contentResolver.query(coverUri, null, null, null, null)
        }?.use { cursor ->
            val name = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            fileName = cursor.getString(name)
        }

        // TODO: Compress before or after copying..
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val files = context.filesDir.listFiles()?.asList()
            val coverFile = File(context.filesDir, fileName)
            val inputStream = context.contentResolver.openInputStream(coverUri)

            if (files?.contains(coverFile) == false) {
                inputStream.use { fis ->
                    FileOutputStream(coverFile).use { fos ->
                        val buffer = ByteArray(1024)
                        var len: Int
                        while (fis?.read(buffer).also { len = it!! } != -1) {
                            fos.write(buffer, 0, len)
                        }
                    }
                }
            }
            Log.w("TEST", "PATH: ${coverFile.absolutePath}")
            return coverFile.absolutePath
        } else {
            TODO("I SUCK!")
        }
    } catch (e: Exception) {
        Log.e("TEST", "cacheCover $e")
    }
    return ""
}