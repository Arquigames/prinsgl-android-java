package com.arquigames.prinsgl.gl.renderer;

import android.opengl.GLES20;
import android.util.Log;

import com.arquigames.prinsgl.gl.attributes.BufferAttribute;
import com.arquigames.prinsgl.Util;


/**
 * Created by usuario on 02/07/2016.
 */
public class GLIndexedBufferRenderer implements IBufferRenderer {

    private static String TAG  ="GLIndexedBufferRenderer";

    private int mode = GLES20.GL_TRIANGLES;
    private int type = -1;

    private boolean requireBufferAttribute = false;

    private BufferAttribute bufferAttribute = null;

    public GLIndexedBufferRenderer(){
    }
    public void setIndex(BufferAttribute index){
        if(
            index.getShortArray()!=null &&
            index.getShortArray().length>0 &&
            (
                index.getGlType()== GLES20.GL_SHORT ||
                index.getGlType()== GLES20.GL_UNSIGNED_SHORT
            )
        ){
            this.setType(GLES20.GL_UNSIGNED_SHORT);
            if(this.requireBufferAttribute)this.bufferAttribute = index;
        }else{
            this.mode = GLES20.GL_TRIANGLES;
        }
    }
    @Override
    public void render(int start,int count){
        if(GLRenderer.DEBUG) Log.e(TAG,"render(start="+start+",count="+count+")");
        if(this.requireBufferAttribute){
            this.bufferAttribute.getBuffer().rewind();
            if(GLRenderer.DEBUG){
                Log.e(TAG,"Indices =["+ Util.print(this.bufferAttribute.getShortArray())+"]");
            }
            GLES20.glDrawElements(
                    this.getMode(),
                    count,
                    this.bufferAttribute.getGlType(),
                    this.bufferAttribute.getBuffer().position(start)
            );
            this.bufferAttribute = null;
            this.requireBufferAttribute = false;

        }else{
            GLES20.glDrawElements(
                    this.mode,
                    count,
                    this.getType(),
                    start * 2
            );
        }
    }

    @Override
    public int getMode() {
        return mode;
    }

    @Override
    public GLIndexedBufferRenderer clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("cannot clone GLIndexedBufferRenderer");
    }

    @Override
    public void setMode(int mode) {
        if(GLRenderer.DEBUG) Log.e(TAG,"setMode = "+mode);
        this.mode = mode;
    }

    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }

    public boolean isRequireBufferAttribute() {
        return requireBufferAttribute;
    }

    public void setRequireBufferAttribute(boolean requireBufferAttribute) {
        this.requireBufferAttribute = requireBufferAttribute;
    }
}
