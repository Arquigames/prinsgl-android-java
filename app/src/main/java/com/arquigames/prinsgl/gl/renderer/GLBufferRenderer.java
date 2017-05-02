package com.arquigames.prinsgl.gl.renderer;


import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by usuario on 27/06/2016.
 */
public class GLBufferRenderer implements IBufferRenderer {
    private static String TAG = "GLBufferRenderer";
    private int mode = GLES20.GL_TRIANGLES;

    public GLBufferRenderer(){
    }
    @Override
    public void render(int start,int count){
        if(GLRenderer.DEBUG) Log.e(TAG,"render(start="+start+",count="+count+")");
        GLES20.glDrawArrays(this.getMode(),start,count);
    }
    @Override
    public int getMode() {
        return mode;
    }

    @Override
    public void setMode(int mode) {
        if(GLRenderer.DEBUG) Log.e(TAG,"setMode = "+mode);
        this.mode = mode;
    }
}