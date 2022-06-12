package com.capstone.caltrack

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

fun showSnackbar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        .show()
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempFile(context)
    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()
    return myFile
}

private fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(currentTimestamp, ".jpg", storageDir)
}

@SuppressLint("ConstantLocale")
val currentTimestamp: String = SimpleDateFormat(
    "ddMMyyHHmmssSS",
    Locale.getDefault()
).format(System.currentTimeMillis())

private fun getRandomString(len: Int = 20): String {
    val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return List(len) { alphabet.random() }.joinToString("")
}

fun getDefaultFileName(): String {
    return "STORY-${getRandomString()}.jpg"
}
fun createFile(
    application: Application,
    folder: String = "story",
    filename: String = getDefaultFileName()
): File {
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, folder).apply { mkdirs() }
    }
    val outputDirectory = if (
        mediaDir != null && mediaDir.exists()
    ) mediaDir else application.filesDir
    return File(outputDirectory, filename)
}

fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false): Bitmap {
    val matrix = Matrix()
    return if (isBackCamera) {
        matrix.postRotate(90f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    } else {
        matrix.postRotate(-90f)
        matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }
}