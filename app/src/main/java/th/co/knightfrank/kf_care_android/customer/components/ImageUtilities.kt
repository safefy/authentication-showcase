package th.co.knightfrank.kf_care_android.customer.components

//import android.util.Base64
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ImageUtilities {
    companion object {
        fun getJPEGByteArray(contentResolver: ContentResolver,
                             uri: Uri,
                             desiredWidth: Int,
                             desiredHeight: Int,
                             quality: Int): ByteArray {
            val inputStream1 = FileUtilities.getFileInputStream(contentResolver, uri)
            val inputStream2 = FileUtilities.getFileInputStream(contentResolver, uri)
            val widthHeight = getImageSize(inputStream1, desiredWidth, desiredHeight)
            val bitmap = decodeImageSubSampling(inputStream = inputStream2,
                    desiredWidth = desiredWidth,
                    desiredHeight = desiredHeight,
                    originalWidth = widthHeight.first,
                    originalHeight = widthHeight.second)
            return compressToJPEGByteArray(bitmap, quality)
        }

        fun getImageSize(inputStream: InputStream,
                         desiredWidth: Int,
                         desiredHeight: Int): Pair<Int, Int> {
            if (desiredWidth > 0 && desiredHeight > 0) {
                val decodeBoundsOptions = BitmapFactory.Options()
                decodeBoundsOptions.inJustDecodeBounds = true
                BitmapFactory.decodeStream(inputStream, null, decodeBoundsOptions)
                return Pair(decodeBoundsOptions.outWidth, decodeBoundsOptions.outHeight)
            }
            return Pair(0, 0)
        }

        fun decodeImageSubSampling(inputStream: InputStream,
                                   desiredWidth: Int,
                                   desiredHeight: Int,
                                   originalWidth: Int,
                                   originalHeight: Int): Bitmap {
            val sampleSize = Math.max(1, Math.max(originalWidth / desiredWidth, originalHeight / desiredHeight));
            val decodeBitmapOptions = BitmapFactory.Options()
            decodeBitmapOptions.inSampleSize = sampleSize
            decodeBitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565 // Uses 2-bytes instead of default 4 per pixel

            val bmp = BitmapFactory.decodeStream(inputStream, null, decodeBitmapOptions)

            val ratio = Math.min(desiredWidth / bmp.width.toFloat(),
                    desiredHeight / bmp.height.toFloat())

            val w: Int
            val h: Int

            Log.e("decodeImageSubSampling", "orinalWidth : " + originalWidth + " orinalHeight : " + originalHeight)

            if (originalWidth > originalHeight) {
                //landscape
                w = (bmp.width.toFloat() * ratio).toInt()
                h = (bmp.height.toFloat() * ratio).toInt()
            } else {
                //portrait
                w = (bmp.height.toFloat() * ratio).toInt()
                h = (bmp.width.toFloat() * ratio).toInt()
            }

//            val w = (bmp.width.toFloat() * ratio).toInt()
//            val h = (bmp.height.toFloat() * ratio).toInt()
            return Bitmap.createScaledBitmap(bmp, w, h, true)
        }

        fun compressToJPEGByteArray(bitmap: Bitmap,
                                    quality: Int): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
            return stream.toByteArray()
        }

        fun encoderJPEGByteArrayToBase64Camera(contentResolver: ContentResolver,
                                               uri: Uri,
                                               desiredWidth: Int,
                                               desiredHeight: Int,
                                               quality: Int): String {
            val inputStream1 = FileUtilities.getFileInputStream(contentResolver, uri)
            val inputStream2 = FileUtilities.getFileInputStream(contentResolver, uri)
            val widthHeight = getImageSize(inputStream1, desiredWidth, desiredHeight)

            var bitmap = decodeImageSubSampling(inputStream = inputStream2,
                    desiredWidth = desiredWidth,
                    desiredHeight = desiredHeight,
                    originalWidth = widthHeight.first,
                    originalHeight = widthHeight.second)

            var orientation = 0
            val ei = ExifInterface(uri.path)
            orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> bitmap = rotate(bitmap!!, 90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> bitmap = rotate(bitmap!!, 180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> bitmap = rotate(bitmap!!, 270f)
                else -> {
                    bitmap = rotate(bitmap!!, orientation.toFloat())
                }
            }
            val bytesArray = compressToJPEGByteArray(bitmap, quality)

            //return java.util.Base64.getEncoder().encodeToString(bytesArray) //Base64.encodeToString(bytesArray, Base64.DEFAULT)
            return Base64.encodeToString(bytesArray, Base64.DEFAULT)
        }

        fun encoderJPEGByteArrayToBase64Gallery(contentResolver: ContentResolver,
                                                uri: Uri,
                                                desiredWidth: Int,
                                                desiredHeight: Int,
                                                quality: Int): String {
            val inputStream1 = FileUtilities.getFileInputStream(contentResolver, uri)
            val inputStream2 = FileUtilities.getFileInputStream(contentResolver, uri)
            val widthHeight = getImageSize(inputStream1, desiredWidth, desiredHeight)

            var bitmap = decodeImageSubSampling(inputStream = inputStream2,
                    desiredWidth = desiredWidth,
                    desiredHeight = desiredHeight,
                    originalWidth = widthHeight.first,
                    originalHeight = widthHeight.second)

            val bytesArray = compressToJPEGByteArray(bitmap, quality)

            //return java.util.Base64.getEncoder().encodeToString(bytesArray) //Base64.encodeToString(bytesArray, Base64.DEFAULT)
            return Base64.encodeToString(bytesArray, Base64.DEFAULT)
        }

        fun decoderBase64ToBitmap(base64String: String): Bitmap {
            val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            return decodedImage
        }

        fun rotate(src: Bitmap, degree: Float): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(degree)
            return Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        }

        fun encoderImageURLToBase64(urlString: String): String {
            val ulrn = URL(urlString)
            val con = ulrn.openConnection() as HttpURLConnection
            val inputStream = con.inputStream

            val bmp = BitmapFactory.decodeStream(inputStream)
            val bytesArray = ImageUtilities.compressToJPEGByteArray(bmp, 100)

            return Base64.encodeToString(bytesArray, Base64.DEFAULT)
        }
    }
}