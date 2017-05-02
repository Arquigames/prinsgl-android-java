package com.arquigames.prinsgl;

import android.graphics.Bitmap;

import com.arquigames.prinsgl.maths.MathUtils;

/**
 * Created by usuario on 13/08/2016.
 */
public class ImageUtils {
    private static String TAG= "ImageUtils";
    public static boolean isPowerOfTwo( Bitmap image ) {
        return image!=null && MathUtils.isPowerOfTwo( image.getWidth() ) && MathUtils.isPowerOfTwo( image.getHeight() );

    }
}
