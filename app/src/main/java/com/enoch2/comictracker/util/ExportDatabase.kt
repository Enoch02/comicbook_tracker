package com.enoch2.comictracker.util

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.enoch2.comictracker.R
import com.enoch2.comictracker.domain.model.Comic
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File
import java.io.FileInputStream

fun exportDatabase(exportFileUri: Uri, context: Context, comics: List<Comic>) {
    val csvFile = File(context.filesDir, "comicDatabase.csv")
    csvFile.createNewFile()

    csvWriter().open(csvFile, append = false) {
        writeRow(
            listOf(
                "title",
                "status",
                "rating",
                "issues_read",
                "total_issues",
                "cover_name"
            )
        )
        comics.forEach { comic ->
            writeRow(
                listOf(
                    comic.title,
                    comic.status,
                    comic.rating,
                    comic.issuesRead,
                    comic.totalIssues,
                    comic.coverName
                )
            )
        }
    }

    val inputStream = FileInputStream(csvFile)
    val outputStream = context.contentResolver.openOutputStream(exportFileUri)

    inputStream.use { fis ->
        outputStream.use { fos ->
            val buffer = ByteArray(1024)
            var len: Int
            while (fis.read(buffer).also { len = it } != -1) {
                fos?.write(buffer, 0, len)
            }
        }
    }

    val notificationId = 1
    val openFileIntent = Intent(Intent.ACTION_VIEW, exportFileUri)
    val openFilePendingIntent = PendingIntent.getActivity(context, 0, openFileIntent, PendingIntent.FLAG_IMMUTABLE)
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.icon)
        .setBadgeIconType(NotificationCompat.BADGE_ICON_NONE)
        .setContentTitle("Your data has been exported.")
        .setContentText("Tap to open the file.")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(openFilePendingIntent)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
    csvFile.delete()
}
