package com.dummyproject.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.dummyproject.entity.FileBitmapModel
import com.dummyproject.utils.Permission.CAMERA
import com.dummyproject.utils.Permission.GALLERY
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object ImageUtil {
    fun takePhotoFromCamera(activity: Activity) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(intent, CAMERA)
    }

    fun choosePhotoFromGallery(activity: Activity) {
        val intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(intent, GALLERY)
    }

    fun phototakenFromGallery(data: Intent?, activity: Activity): FileBitmapModel? {
        var pathName = ""
        val fileBitmapModel = FileBitmapModel()
        var bm: Bitmap? = null
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(
                    activity.applicationContext.contentResolver, data.data
                )
                val uri = data.data
                if (bm != null) {
                    pathName = getRealPathFromURI(uri, activity)
                    val bm2 = modifyOrientation(bm, pathName)
                    val uri2 =
                        getImageUri(activity.applicationContext, bm2)
                    fileBitmapModel.setFile(
                        File(
                            getRealPathFromURI(
                                uri2,
                                activity
                            )
                        )
                    )
                    val bmOptions = BitmapFactory.Options()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(activity, "Please select valid image", Toast.LENGTH_SHORT).show()
            }
        }
        if (bm == null) Toast.makeText(activity, "Please select valid image", Toast.LENGTH_SHORT)
            .show() else {
            try {
                fileBitmapModel.setBitmap(modifyOrientation(bm, pathName))
                return fileBitmapModel
            } catch (e: Exception) {
            }
        }
        return null
    }

    fun getImageUri(inContext: Context, inImage: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        inImage!!.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    fun getRealPathFromURI(uri: Uri?, activity: Activity): String {
        val projection =
            arrayOf(MediaStore.Images.Media.DATA)
        val cursor =
            activity.managedQuery(uri, projection, null, null, null)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    @Throws(IOException::class)
    fun modifyOrientation(bitmap: Bitmap, image_absolute_path: String?): Bitmap {
        val ei = ExifInterface(image_absolute_path!!)
        val orientation =
            ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotate(bitmap, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotate(bitmap, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotate(bitmap, 270f)
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> flip(bitmap, true, false)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> flip(bitmap, false, true)
            else -> bitmap
        }
    }

    fun rotate(bitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }

    fun flip(bitmap: Bitmap, horizontal: Boolean, vertical: Boolean): Bitmap {
        val matrix = Matrix()
        matrix.preScale(if (horizontal) -1f else 1.toFloat(), if (vertical) -1f else 1.toFloat())
        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }

    fun getFileBitmapOfCameraResponse(data: Intent, activity: Activity): FileBitmapModel {
        val thumbnail = data.extras!!["data"] as Bitmap?
        val uri =
            getImageUri(activity.applicationContext, thumbnail)
        val path = getRealPathFromURI(uri, activity)
        return FileBitmapModel(File(path), thumbnail)
    }

    fun ScaleDownBitmap(
        originalImage: Bitmap,
        maxImageSize: Float,
        filter: Boolean
    ): Bitmap {
        val ratio = Math.min(
            maxImageSize / originalImage.width,
            maxImageSize / originalImage.height
        )
        return Bitmap.createScaledBitmap(
            originalImage,
            Math.round(ratio * originalImage.width.toFloat()),
            Math.round(ratio * originalImage.height.toFloat()), filter
        )
    }

    fun ScaleBitmap(originalImage: Bitmap, wantedWidth: Int, wantedHeight: Int): Bitmap {
        val output = Bitmap.createBitmap(wantedWidth, wantedHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val m = Matrix()
        m.setScale(
            wantedWidth.toFloat() / originalImage.width,
            wantedHeight.toFloat() / originalImage.height
        )
        canvas.drawBitmap(originalImage, m, Paint())
        return output
    }

    fun getResizedFileBitmapModel(fileBitmapModelOrig: FileBitmapModel): FileBitmapModel {
        // we'll start with the original picture already open to a file
        val imgFileOrig: File =
            fileBitmapModelOrig.getFile() //change "getPic()" for whatever you need to open the image file.
        val fileBitmapModel = FileBitmapModel()
        val b = BitmapFactory.decodeFile(imgFileOrig.absolutePath)
        // original measurements
        val origWidth = b.width
        val origHeight = b.height
        val destWidth = 600 //or the width you need
        val destHeight = 800
        if (origWidth <= destWidth || origHeight <= destHeight) {
            fileBitmapModel.setFile(imgFileOrig)
            fileBitmapModel.setBitmap(b)
            return fileBitmapModelOrig
        }
        if (origWidth > destWidth) {
            // picture is wider than we want it, we calculate its target height
            //int destHeight = origHeight / (origWidth / destWidth);
            // we create an scaled bitmap so it reduces the image, not just trim it
            val b2 = Bitmap.createScaledBitmap(b, destWidth, destHeight, false)
            val outStream = ByteArrayOutputStream()
            // compress to the format you want, JPEG, PNG...
            // 70 is the 0-100 quality percentage
            b2.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            // we save the file, at least until we have made use of it
            val f = File(
                Environment.getExternalStorageDirectory()
                    .toString() + File.separator + "base_line_layer_.jpg"
            )
            var resizedBitmap: Bitmap? = null
            try {
                f.createNewFile()
                //write the bytes in file
                val fo = FileOutputStream(f)
                val byteArray = outStream.toByteArray()
                fo.write(outStream.toByteArray())
                resizedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                // remember close de FileOutput
                fo.close()
            } catch (e: IOException) {
            }
            fileBitmapModel.setBitmap(resizedBitmap)
            fileBitmapModel.setFile(f)
        }
        return fileBitmapModel
    }
}
