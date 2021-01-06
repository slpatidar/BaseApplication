package com.sp.base.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import androidx.appcompat.widget.AppCompatImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/*
 this class is used to display image
* */
public class ImageUtil {
    static String TAG = ImageUtil.class.getSimpleName();
    static Context context;

    ////data/user/0/com.app.teketeke/app_teke_teke/picture_1577368610295.jpg
    /*load image in imageView using glide*/
    public static void loadImage(Context context, String url, AppCompatImageView myImageView, int driver_ic) {
        LogUtil.printLog(TAG, "url " + url);
        try {
            Glide.with(context)
                    .load(Uri.parse(url)).placeholder(driver_ic)
                    .into(myImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("CheckResult")
    public static void loadImage(Context context, int rec, AppCompatImageView imageView) {
        try {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.centerCrop();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            Glide.with(context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(rec)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] byteArrayImage = baos.toByteArray();
            return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {
        ExifInterface exif = new ExifInterface(Objects.requireNonNull(selectedImage.getPath()));

        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public static String saveImage(Context context, Uri uriPath) {
        String imagePath = "";
        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriPath);
            if (bitmap != null) {
                bitmap = checkExif(bitmap, uriPath.getPath());
                imagePath = saveToInternalStorage(bitmap, context);
            }
            //imageViewProfileImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            bitmap = BitmapFactory.decodeFile(uriPath.getPath());
            if (bitmap != null) {
                bitmap = checkExif(bitmap, uriPath.getPath());
                imagePath = saveToInternalStorage(bitmap, context);
            }
        }
        return imagePath;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    //Draw Image on Canvas
    public static Bitmap resizeImage(Activity activity, Bitmap bitmap, String imageFilePath) {
        try {
            int MAX_HEIGHT = 1024;
            int MAX_WIDTH = 1024;

            LogUtil.printLog(TAG,"bitmap getWidth: " + bitmap.getWidth() + " getHeight: " + bitmap.getHeight());

            if (bitmap.getWidth() < MAX_WIDTH || bitmap.getHeight() < MAX_HEIGHT || bitmap.getWidth() == 1080 && bitmap.getHeight() == 1920 || bitmap.getWidth() <= 2304 && bitmap.getHeight() <= 4096) {

//                Display display = activity.getWindowManager().getDefaultDisplay();
//                int displayWidth = display.getWidth();
                DisplayMetrics displaymetrics = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                int displayWidth = displaymetrics.widthPixels;
                int screenHeight = displaymetrics.heightPixels;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                Rect rect = new Rect();
                rect.set(0, 0, 0, 0);
                int width = options.outWidth;
                if (width > displayWidth) {
                    int widthRatio = Math.round((float) width / (float) displayWidth);
                    options.inSampleSize = widthRatio;
                }
                options.inJustDecodeBounds = false;
                int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                return Bitmap.createScaledBitmap(bitmap, 512, nh, true);
            } else {
                Bitmap mBitmap = rotateImageIfRequired(bitmap, Uri.parse(imageFilePath));
                int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                return Bitmap.createScaledBitmap(mBitmap, 512, nh, true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int[] getIMGSizeFromUri(Uri uri) {
        int[] size = new int[2];
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;
            size = new int[]{ imageWidth,imageHeight};
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
    public static Bitmap checkExif(Bitmap scaledBitmap, String filePath) {
        //check the rotation of the image and display it properly
        ExifInterface exif;
        Bitmap bitmap = null;
        try {
            exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            bitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(),
                    matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String saveToInternalStorage(Bitmap bitmapImage, Context context) {

        Bitmap converetdImage = getResizedBitmap(bitmapImage, Constants.DEFAULT_IMAGE_SIZE);
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(Constants.INTERNAL_FOLDER_NAME, Context.MODE_PRIVATE);
        // Create imageDir
        String imageFileName = "picture_" + System.currentTimeMillis() + ".jpg";
        File mypath = new File(directory, imageFileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            converetdImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

}
