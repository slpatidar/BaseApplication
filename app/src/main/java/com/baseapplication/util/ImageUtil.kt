package com.baseapplication.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.DisplayMetrics
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Objects

/*
 this class is used to display image
* */
object ImageUtil {
    var TAG: String = ImageUtil::class.java.getSimpleName()
    var context: Context? = null

    ////data/user/0/com.app.teketeke/app_teke_teke/picture_1577368610295.jpg
    /*load image in imageView using glide*/
    fun loadImage(
        context: Context?,
        url: String,
        myImageView: AppCompatImageView?,
        driver_ic: Int
    ) {
        LogUtil.printLog(TAG, "url " + url)
        try {
            Glide.with((context)!!)
                .load(Uri.parse(url)).placeholder(driver_ic)
                .into((myImageView)!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    fun loadImage(context: Context?, rec: Int, imageView: AppCompatImageView?) {
        try {
            val requestOptions: RequestOptions = RequestOptions()
            requestOptions.centerCrop()
            requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            Glide.with((context)!!)
                .applyDefaultRequestOptions(requestOptions)
                .load(rec)
                .into((imageView)!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        try {
            val baos: ByteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
            val byteArrayImage: ByteArray = baos.toByteArray()
            return Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    @Throws(IOException::class)
    private fun rotateImageIfRequired(img: Bitmap, selectedImage: Uri): Bitmap {
        val exif: ExifInterface? = Objects.requireNonNull(selectedImage.getPath())
            ?.let { ExifInterface(it) }
        val orientation: Int =
            exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)!!
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> return rotateImage(img, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> return rotateImage(img, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> return rotateImage(img, 270)
            else -> return img
        }
    }

    private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
        val matrix: Matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedImg: Bitmap =
            Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true)
        img.recycle()
        return rotatedImg
    }

    fun saveImage(context: Context, uriPath: Uri): String {
        var imagePath: String = ""
        var bitmap: Bitmap?
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriPath)
            if (bitmap != null) {
                bitmap = checkExif(bitmap, uriPath.getPath())
                imagePath = saveToInternalStorage(bitmap, context)
            }
            //imageViewProfileImage.setImageBitmap(bitmap);
        } catch (e: Exception) {
            e.printStackTrace()
            bitmap = BitmapFactory.decodeFile(uriPath.getPath())
            if (bitmap != null) {
                bitmap = checkExif(bitmap, uriPath.getPath())
                imagePath = saveToInternalStorage(bitmap, context)
            }
        }
        return imagePath
    }

    fun getResizedBitmap(image: Bitmap?, maxSize: Int): Bitmap {
        var width: Int = image!!.getWidth()
        var height: Int = image.getHeight()
        val bitmapRatio: Float = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap((image), width, height, true)
    }

    //Draw Image on Canvas
    fun resizeImage(activity: Activity, bitmap: Bitmap, imageFilePath: String?): Bitmap? {
        try {
            val MAX_HEIGHT: Int = 1024
            val MAX_WIDTH: Int = 1024
            LogUtil.printLog(
                TAG,
                "bitmap getWidth: " + bitmap.getWidth() + " getHeight: " + bitmap.getHeight()
            )
            if ((bitmap.getWidth() < MAX_WIDTH) || (bitmap.getHeight() < MAX_HEIGHT) || (bitmap.getWidth() == 1080 && bitmap.getHeight() == 1920) || (bitmap.getWidth() <= 2304 && bitmap.getHeight() <= 4096)) {

//                Display display = activity.getWindowManager().getDefaultDisplay();
//                int displayWidth = display.getWidth();
                val displaymetrics: DisplayMetrics = DisplayMetrics()
                activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics)
                val displayWidth: Int = displaymetrics.widthPixels
                val screenHeight: Int = displaymetrics.heightPixels
                val options: BitmapFactory.Options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                val rect: Rect = Rect()
                rect.set(0, 0, 0, 0)
                val width: Int = options.outWidth
                if (width > displayWidth) {
                    val widthRatio: Int = Math.round(width.toFloat() / displayWidth.toFloat())
                    options.inSampleSize = widthRatio
                }
                options.inJustDecodeBounds = false
                val nh: Int = (bitmap.getHeight() * (512.0 / bitmap.getWidth())).toInt()
                return Bitmap.createScaledBitmap(bitmap, 512, nh, true)
            } else {
                val mBitmap: Bitmap = rotateImageIfRequired(bitmap, Uri.parse(imageFilePath))
                val nh: Int = (bitmap.getHeight() * (512.0 / bitmap.getWidth())).toInt()
                return Bitmap.createScaledBitmap(mBitmap, 512, nh, true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getIMGSizeFromUri(uri: Uri): IntArray {
        var size: IntArray = IntArray(2)
        try {
            val options: BitmapFactory.Options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(File(uri.getPath()).getAbsolutePath(), options)
            val imageHeight: Int = options.outHeight
            val imageWidth: Int = options.outWidth
            size = intArrayOf(imageWidth, imageHeight)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    fun checkExif(scaledBitmap: Bitmap, filePath: String?): Bitmap? {
        //check the rotation of the image and display it properly
        val exif: ExifInterface
        var bitmap: Bitmap? = null
        try {
            exif = ExifInterface((filePath)!!)
            val orientation: Int = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            val matrix: Matrix = Matrix()
            if (orientation == 6) {
                matrix.postRotate(90f)
            } else if (orientation == 3) {
                matrix.postRotate(180f)
            } else if (orientation == 8) {
                matrix.postRotate(270f)
            }
            bitmap = Bitmap.createBitmap(
                scaledBitmap, 0, 0,
                scaledBitmap.getWidth(), scaledBitmap.getHeight(),
                matrix, true
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun saveToInternalStorage(bitmapImage: Bitmap?, context: Context?): String {
        val converetdImage: Bitmap =
            getResizedBitmap(bitmapImage, Constants.DEFAULT_IMAGE_SIZE)
        val cw: ContextWrapper = ContextWrapper(context)
        // path to /data/data/yourapp/app_data/imageDir
        val directory: File =
            cw.getDir(Constants.Companion.INTERNAL_FOLDER_NAME, Context.MODE_PRIVATE)
        // Create imageDir
        val imageFileName: String = "picture_" + System.currentTimeMillis() + ".jpg"
        val mypath: File = File(directory, imageFileName)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            converetdImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return mypath.getAbsolutePath()
    }
}
