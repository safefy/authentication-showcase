package th.co.knightfrank.kf_care_android.customer.components

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.BufferedInputStream
import java.io.File
import java.io.InputStream
import java.net.URL


class FileUtilities {
    companion object {
        fun getFileInputStream(contentResolver: ContentResolver, uri: Uri): InputStream =
                if (uri.toString().startsWith("http"))
                    URL(uri.toString()).openStream()
                else
                    BufferedInputStream(contentResolver.openInputStream(uri))


        fun deleteFile(contentResolver: ContentResolver, uriString: String): Boolean {
            val cursor = contentResolver.query(Uri.parse(uriString), Array(1) {
                MediaStore.Images.ImageColumns.DATA
            }, null, null, null)
            cursor.moveToFirst()
            val photoPath = cursor.getString(0)
            cursor.close()
            val file = File(photoPath)

            if (file.exists()) {
                Log.e("deleteFile", "exist")
            } else {
                Log.e("deleteFile", "not exist")
            }

            return file.delete()
        }
    }
}