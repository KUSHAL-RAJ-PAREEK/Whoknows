package com.krp.whoknows.roomdb

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64

/**
 * Created by KUSHAL RAJ PAREEK on 14,March,2025
 */

object ImageConverter {

    fun uriToBase64(context: Context, uri: Uri?): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri!!) ?: return null
            val bytes = inputStream.readBytes()
            inputStream.close()
            Base64.encodeToString(bytes, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun base64ToBitmap(encodedString: String): Bitmap? {
        return try {
            val bytes = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun byteArrayToBase64(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun base64ToByteArray(encodedString: String): ByteArray {
        return Base64.decode(encodedString, Base64.DEFAULT)
    }
}