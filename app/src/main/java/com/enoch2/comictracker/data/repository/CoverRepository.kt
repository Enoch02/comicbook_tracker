package com.enoch2.comictracker.data.repository

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

private const val TAG = "COVER_REPO"

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
        var coverFileName = ""
        coverUri.let {
            context.contentResolver.query(coverUri, null, null, null, null)
        }?.use { cursor ->
            val name = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            coverFileName = cursor.getString(name)
        }
        val files = context.filesDir.listFiles()?.asList()
        val fileNames = files?.map { it.name }

        if (fileNames != null) {
            if (!fileNames.contains(coverFileName)) {
                val coverFile = File(context.filesDir, coverFileName)
                val coverInputStream = context.contentResolver.openInputStream(coverUri)
                val coverOutputStream = FileOutputStream(coverFile)

                coverInputStream.use { fis ->
                    coverOutputStream.use { fos ->
                        val buffer = ByteArray(1024)
                        var len: Int
                        while (fis?.read(buffer).also { len = it!! } != -1) {
                            fos.write(buffer, 0, len)
                        }
                    }
                }
                Log.d(TAG, "PATH TO COPIED FILE: ${coverFile.absolutePath}")
                return coverFile.name
            }
        }
        return coverFileName
    }

    fun deleteAllCovers(scope: CoroutineScope) {
        scope.launch {
            val files = context.filesDir.listFiles()?.toList()

            files?.forEach { file ->
                file.delete()
                Log.d(TAG, "${file.name} deleted!")
            }
        }
    }

    fun deleteOneCover(scope: CoroutineScope, coverName: String) {
        scope.launch {
            try {
                if (coverName.isNotEmpty()) {
                    val file = File(context.filesDir, coverName)
                    file.delete()
                    Log.d(TAG, "$coverName deleted!")
                }
            } catch (e: Exception) {
                Log.e(TAG, "deleteOneCover() -> $e")
            }
        }
    }
}
