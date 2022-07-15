package com.enoch2.comictracker.data.repository

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.util.Log
import com.enoch2.comictracker.domain.model.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class CoverRepository(val context: Context) {
    val latestPathList: Flow<Map<String, String?>> = flow {
        while (true) {
            val latestPathMap = mutableMapOf<String, String?>()
            context.filesDir?.listFiles()?.forEach {
                latestPathMap[it.name] = it.absolutePath
            }
            emit(latestPathMap.toMap())
            delay(5000)
        }
    }.flowOn(Dispatchers.IO)

    fun copyCover(coverUri: Uri): String {
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
                Log.w(TAG, "PATH TO COPIED FILE: ${coverFile.absolutePath}")
                return coverFile.name
            } else {
                TODO("I SUCK!")
            }
        } catch (e: Exception) {
            Log.e("TEST", "copyCover() -> $e")
        }
        return ""
    }

    fun deleteAllCovers(scope: CoroutineScope) {
        scope.launch {
            try {
                val files = context.filesDir.listFiles()?.toList()

                files?.forEach { file ->
                    file.delete()
                    Log.e(TAG, "${file.name} deleted!")
                }
            } catch (e: Exception) {
                Log.e(TAG, "deleteAllCovers() -> $e")
            }
        }
    }

    fun deleteOneCover(scope: CoroutineScope, coverName: String) {
        scope.launch {
            try {
                if (coverName.isNotEmpty()) {
                    val file = File(context.filesDir, coverName)
                    file.delete()
                    Log.e(TAG, "$coverName deleted!")
                }
            } catch (e: Exception) {
                Log.e(TAG, "deleteOneCover() -> $e")
            }
        }
    }
}
