package com.vs.library.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.opengl.GLES10;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by chends on 2017/7/26.
 */

public class BitmapUtil {

    public static Cursor getAllMediaPhotos(final Context context) {
        final Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = null;
        try{
            cursor = context.getContentResolver().query(images, null, null,
                    null, MediaStore.Images.Media.DATE_MODIFIED + " DESC");
        }catch(Exception e){
            e.printStackTrace();
        }

        return cursor;
    }

    /**
     * 获取手机图片缩略图路径
     * @param context
     * @return null or HashMap<Integer, String>
     */
    public static HashMap<Integer, String> getThumbnailIdPathMap(Context context){
        final String[] projection = new String[] {
                MediaStore.Images.Thumbnails._ID,
                MediaStore.Images.Thumbnails.IMAGE_ID,
                MediaStore.Images.Thumbnails.DATA };
        final Uri images = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        Cursor cursor = null;
        try{
            HashMap<Integer,String> hash = new HashMap<Integer, String>();
            cursor = context.getContentResolver().query(images, projection, null, null, MediaStore.Images.Thumbnails._ID + " DESC");
            if (cursor != null && cursor.moveToFirst()) {
                int imageID;
                String imagePath;
                do {
                    imageID = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID));
                    imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                    hash.put(imageID, "file://" + imagePath);
                } while (cursor.moveToNext());
            }
            return hash;
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(cursor != null) {
                try{
                    cursor.close();
                }catch (Throwable e){}
            }
        }
        return null;
    }

    public static Bitmap getBitmapFromDrawableRes(Context context, int res) {
        if(res != 0) {
            try {
                Drawable drawable = CompatibilityUtil.getDrawable(context, res);
                return drawableToBitmap(drawable);
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap =null;
        if(drawable!=null){
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            bitmap = drawableToBitmap(drawable, width, height);
        }

        return bitmap;
    }

    public static Bitmap drawableToBitmap(Drawable drawable, int w, int h) {
        Bitmap bitmap =null;
        if(drawable!=null){
            float minScale = Math.min((w * 1f) / drawable.getIntrinsicWidth(), (h * 1f) / drawable.getIntrinsicHeight());

            w = (int)Math.floor(drawable.getIntrinsicWidth() * minScale);
            h = (int)Math.floor(drawable.getIntrinsicHeight() * minScale);

            try {
                bitmap = Bitmap.createBitmap(w, h, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(bitmap);
                canvas.scale(minScale, minScale);
                drawable.setBounds(0,0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                drawable.draw(canvas);
            }catch(OutOfMemoryError e){}
        }

        return bitmap;
    }

    /**
     * 保存图片到相册
     * @param context
     * @param bmp
     */
    public static boolean saveImageToGallery(Context context, Bitmap bmp, File savePath, String fileName) {
        // 首先保存图片
        File saveFile = new File(savePath, fileName);
        if(saveFile.exists()) {
            saveFile.delete();
        }
        FileOutputStream fos = null;
        try {
            if (saveFile.createNewFile()) {
                //保存图片
                fos = new FileOutputStream(saveFile);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();

                // 其次把文件插入到系统图库
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                                saveFile.getAbsolutePath(), fileName, null);

                // 最后通知图库更新
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + saveFile.getAbsolutePath())));
                return true;
            }
        }catch(IOException e) {
        }finally {
            if(fos != null) {
                try {
                    fos.close();
                }catch(Throwable t) {}
            }
        }

        return false;
    }

    public static Bitmap getScaleLogo(Bitmap logo, int w, int h){
        if(logo == null)return null;
        Matrix matrix = new Matrix();
        float scaleFactor = Math.min(w * 1.0f / 5 / logo.getWidth(), h * 1.0f / 5 /logo.getHeight());
        matrix.postScale(scaleFactor,scaleFactor);
        Bitmap result = Bitmap.createBitmap(logo, 0, 0, logo.getWidth(),   logo.getHeight(), matrix, true);
        return result;
    }

    /**
     * 给 drawable 着色
     *
     * @param drawable 需要着色的 drawable 对象
     * @param colors   ColorStateList 对象，代表需要着色的颜色
     * @return
     */
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        //这里需要对 drawable 对象执行 mutate() 操作
        //该操作能防止一个屏幕里多次使用同一个图片，对其中一个图片操作时影响其他图片
        //当然，你也可以在getResource().getDrawable()的时候就执行
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public static int calculateSampleSize(int width, int height, int totalPixel) {
        int ratio = 1;

        if (width > 0 && height > 0) {
            ratio = (int) Math.sqrt((float) (width * height) / totalPixel);
            if (ratio < 1) {
                ratio = 1;
            }
        }

        return ratio;
    }

    public static int calculateSampleSize(int width, int height, int reqWidth, int reqHeight) {
        // can't proceed
        if (width <= 0 || height <= 0) {
            return 1;
        }
        // can't proceed
        if (reqWidth <= 0 && reqHeight <= 0) {
            return 1;
        } else if (reqWidth <= 0) {
            reqWidth = (int) (width * reqHeight / (float)height + 0.5f) ;
        } else if (reqHeight <= 0) {
            reqHeight = (int) (height * reqWidth / (float)width + 0.5f);
        }

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            if (inSampleSize == 0) {
                inSampleSize = 1;
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger
            // inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down
            // further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }

        return inSampleSize;
    }

    public static final int adjustSampleSizeWithTexture(int sampleSize, int width, int height) {
        int textureSize = getTextureSize();

        if ((textureSize > 0) && ((width > sampleSize) || (height > sampleSize))) {
            while ((width / (float)sampleSize) > textureSize || (height / (float)sampleSize) > textureSize) {
                sampleSize++;
            }

            // 2的指数对齐
            sampleSize = roundup2n(sampleSize);
        }

        return sampleSize;
    }

    private static int textureSize = 0;
    //存在第二次拿拿不到的情况，所以把拿到的数据用一个static变量保存下来
    public static final int getTextureSize() {
        if (textureSize > 0) {
            return textureSize;
        }

        int[] params = new int[1];
        GLES10.glGetIntegerv(GLES10.GL_MAX_TEXTURE_SIZE, params, 0);
        textureSize = params[0];

        return textureSize;
    }
    // 将x向上对齐到2的幂指数
    private static final int roundup2n(int x) {
        if ((x & (x - 1)) == 0) {
            return x;
        }
        int pos = 0;
        while (x > 0) {
            x >>= 1;
            ++pos;
        }
        return 1 << pos;
    }

    public static boolean saveBitmap(Bitmap bitmap, String path, boolean recyle) {
        if (bitmap == null || TextUtils.isEmpty(path)) {
            return false;
        }

        BufferedOutputStream bos = null;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            return true;

        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
            if (recyle) {
                bitmap.recycle();
            }
        }
    }

    public static Bitmap rotateBitmapInNeeded(String path, Bitmap srcBitmap) {
        if (TextUtils.isEmpty(path) || srcBitmap == null) {
            return null;
        }

        ExifInterface localExifInterface;
        try {
            localExifInterface = new ExifInterface(path);
            int rotateInt = localExifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            float rotate = getImageRotate(rotateInt);
            if (rotate != 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                Bitmap dstBitmap = Bitmap.createBitmap(srcBitmap, 0, 0,
                        srcBitmap.getWidth(), srcBitmap.getHeight(), matrix,
                        false);
                if (dstBitmap == null) {
                    return srcBitmap;
                } else {
                    if (srcBitmap != null && !srcBitmap.isRecycled()) {
                        srcBitmap.recycle();
                    }
                    return dstBitmap;
                }
            } else {
                return srcBitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return srcBitmap;
        }
    }
    /**
     * 获得旋转角度
     *
     * @param rotate
     * @return
     */
    public static float getImageRotate(int rotate) {
        float f;
        if (rotate == 6) {
            f = 90.0F;
        } else if (rotate == 3) {
            f = 180.0F;
        } else if (rotate == 8) {
            f = 270.0F;
        } else {
            f = 0.0F;
        }

        return f;
    }

    /**
     * 获取图片类型
     *
     * @param path 图片绝对路径
     * @return 图片类型image/jpeg image/png
     */
    public static String getImageType(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return options.outMimeType;
    }
}
