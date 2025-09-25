/*
 * Copyright (c) 2025 Ashwin Rao (@ashwinravrao)
 */

package com.ashwinrao.packup.util.io

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment.DIRECTORY_PICTURES
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun createFile(context: Context): File {
    val dir = context.getExternalFilesDir(DIRECTORY_PICTURES) ?: context.filesDir
    if (!dir.exists()) dir.mkdirs()

    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val filename = "IMG_$timeStamp.jpg"

    return File(dir, filename)
}

fun saveBitmapToFile(context: Context, bitmap: Bitmap, onSuccess: (Uri) -> Unit, onError: (Exception) -> Unit) {
    try {
        val file = createFile(context)
        FileOutputStream(file).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
        val uri = Uri.fromFile(file)
        onSuccess(uri)
    } catch (e: Exception) {
        onError(e)
    }
}
