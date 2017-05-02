package com.arquigames.prinsgl;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLES20;
import android.util.Log;

import com.arquigames.prinsgl.gl.renderer.GLRenderer;
import com.arquigames.prinsgl.maths.MathUtils;
import com.arquigames.prinsgl.textures.Texture;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by usuario on 13/08/2016.
 */
public class TextureUtils {
    private static String TAG = "TextureUtils";
    public static void loadImageOnTextureFromAssetsPath(Texture texture, GLRenderer glRenderer) {
        if(!texture.getSourceFile().isEmpty()){
            texture.setImage(TextureUtils.getBitmapFromAssetPath(glRenderer.getContext(),texture.getSourceFile()));
        }
    }

    public static void loadImageOnTextureFromResourceID(Texture texture, GLRenderer glRenderer) {
        if(texture.getResourceID()>0)texture.setImage(TextureUtils.getImageFromResourceID(glRenderer.getContext().getResources(),texture.getResourceID()));
    }
    public static Bitmap getImageFromResourceID(Resources resources,int resourceID){
        Bitmap image = BitmapFactory.decodeResource(resources, resourceID);
        if(GLRenderer.DEBUG && image!=null)Log.e(TAG,"image size(width="+image.getWidth()+",height="+image.getHeight()+")");
        if(image!=null){
            image = TextureUtils.convertToBitmapOpenGL(image);
        }
        return image;
    }
    public static Bitmap convertToBitmapOpenGL(Bitmap bitmap){
        Matrix matrix = new Matrix();
        matrix.postScale(1.0f, -1.0f);
        if(bitmap!=null){
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),matrix,true );
            if(GLRenderer.DEBUG && bitmap!=null)Log.e(TAG,"new from createBitmap -> image size(width="+bitmap.getWidth()+",height="+bitmap.getHeight()+")");
        }
        return bitmap;
    }
    public static Texture getTextureFromResource(Resources resources, int resourceID){
        Bitmap image = TextureUtils.getImageFromResourceID(resources,resourceID);
        Texture texture = new Texture();
        texture.setResourceID(resourceID);
        texture.setImage(image);
        return texture;
    }
    public static Bitmap getBitmapFromAssetPath(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
            if(GLRenderer.DEBUG) Log.e(TAG,Util.exceptionToString(e));
        }
        if(bitmap!=null){
            bitmap = TextureUtils.convertToBitmapOpenGL(bitmap);
        }

        return bitmap;
    }
    public static int getTextureUnit(GLRenderer glRenderer) {

        int textureUnit = glRenderer.getGlState().getUsedTextureUnits();

        int maxTextureUnits = glRenderer.getGlCapabilities().getMaxTextureImageUnits();
        if(maxTextureUnits>0){
            if(textureUnit >= maxTextureUnits){
                if(GLRenderer.DEBUG)Log.e(TAG,"trying to get texture unit that exceed max texture units.");
            }else{
                return textureUnit;
            }
        }else{
            Log.e("TextureUtils","OpenGLCapabilities->getMaxTextureImageUnits return undesired value");
        }
        return -1;

    }
    public static boolean textureNeedsPowerOfTwo(Texture texture){
        if ( texture.getWrapS() != Constants.CLAMP_TO_EDGE_WRAPPING || texture.getWrapT() != Constants.CLAMP_TO_EDGE_WRAPPING) return true;
        if ( texture.getMinFilter() != Constants.NEAREST_FILTER && texture.getMinFilter() != Constants.LINEAR_FILTER) return true;

        return false;
    }
    public static void clampToMaxSizeOpenGL(Texture texture, GLRenderer glRenderer){
        if(glRenderer.getGlCapabilities().getMaxTextureSize()>0){
            if(
                 texture.getImage()!=null && (
                 texture.getImage().getWidth()> glRenderer.getGlCapabilities().getMaxTextureSize()  ||
                 texture.getImage().getHeight()> glRenderer.getGlCapabilities().getMaxTextureSize() )
            ){
                float scale = glRenderer.getGlCapabilities().getMaxTextureSize() * 1f/ Math.max(texture.getImage().getWidth(),texture.getImage().getHeight());
                int newWidth = Math.round(texture.getImage().getWidth() * scale);
                int newHeight = Math.round(texture.getImage().getHeight() * scale);
                if(GLRenderer.DEBUG) Log.e(TAG,"MAKING SCALE BITMAP");
                Bitmap image = Bitmap.createScaledBitmap(texture.getImage(),newWidth,newHeight,true);
                texture.setImage(image);
            }
        }else{
            if(GLRenderer.DEBUG)Log.e(TAG,"getMaxTextureSize return less than 0 value");
        }
    }
    public static void makePowerOfTwo(Texture texture) {

        int newWidth = MathUtils.nearestPowerOfTwo(texture.getImage().getWidth());
        int newHeight = MathUtils.nearestPowerOfTwo(texture.getImage().getHeight());
        if(newWidth<16 || newHeight<16){
            Log.e(TAG,"makePowerOfTwo-> new Width o Height is less than 16px");
        }else{
            Bitmap image = Bitmap.createScaledBitmap(texture.getImage(),newWidth,newHeight,true);
            texture.setImage(image);
        }
    }
    public static int filterFallback (int f ) {
        if(
                        f == Constants.NEAREST_FILTER ||
                        f == Constants.NEAREST_MIPMAP_NEAREST_FILTER ||
                        f == Constants.NEAREST_MIPMAP_LINEAR_FILTER
                ){
            return GLES20.GL_NEAREST;
        }
        return GLES20.GL_LINEAR;
    }
}
