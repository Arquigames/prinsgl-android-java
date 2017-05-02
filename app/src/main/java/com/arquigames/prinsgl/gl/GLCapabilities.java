package com.arquigames.prinsgl.gl;

import android.opengl.GLES20;
import android.util.Log;

import com.arquigames.prinsgl.maths.vectors.Vector2I;

/**
 * Created by usuario on 12/07/2016.
 */
public class GLCapabilities {

    private static String TAG = "GLCapabilities";

    private String precision = "";
    private Integer maxTextureImageUnits;
    private Integer maxVertexTextureImageUnits;
    private Integer maxCombinedTextureImageUnits;
    private Integer maxCubeMapTextureSize;
    private Integer maxFragmentUniformsVectors;
    private Integer maxRenderBufferSize;
    private Integer maxTextureSize;
    private Integer maxVaryingVectors;
    private Integer maxVertexAttribs;
    private Integer maxVertexUniformVectors;
    private Vector2I maxViewportDims;
    private Integer numCompressedTextureFormats;

    public GLCapabilities(){
    }
    public int getNumCompressedTextureFormats(){
        return this.numCompressedTextureFormats;
    }
    public Vector2I getMaxViewportDims(){
        return this.maxViewportDims;
    }
    public int getMaxVertexUniformVectors(){
        return this.maxVertexUniformVectors;
    }
    public int getMaxVertexAttribs(){
        return this.maxVertexAttribs;
    }
    public int getMaxVaryingVectors(){
        return this.maxVaryingVectors;
    }
    public int getMaxTextureSize(){
        return this.maxTextureSize;
    }
    public int getMaxRenderBufferSize(){
        return this.maxRenderBufferSize;
    }
    public int getMaxFragmentUniformsVectors(){
        return this.maxFragmentUniformsVectors;
    }
    public int getMaxCubeMapTextureSize(){
        return this.maxCubeMapTextureSize;
    }
    public String getPrecision(){
        return this.precision;
    }
    public int getMaxTextureImageUnits(){
        return this.maxTextureImageUnits;
    }
    public int getMaxVertexTextureImageUnits(){
        return this.maxVertexTextureImageUnits;
    }
    public int getMaxCombinedTextureImageUnits(){
        return this.maxCombinedTextureImageUnits;
    }

    public void init() {

        int[] range       = new int[4];
        int[] precision   = new int[2];
        GLES20.glGetShaderPrecisionFormat(GLES20.GL_VERTEX_SHADER,GLES20.GL_HIGH_FLOAT,range,0,precision,0);
        Log.e(TAG,"GL_HIGH_FLOAT (Vertex)-> range[0="+range[0]+",1="+range[1]+"]");
        Log.e(TAG,"GL_HIGH_FLOAT (Vertex)-> precision[0="+precision[0]);
        GLES20.glGetShaderPrecisionFormat(GLES20.GL_FRAGMENT_SHADER,GLES20.GL_HIGH_FLOAT,range,2,precision,1);
        Log.e(TAG,"GL_HIGH_FLOAT (Fragment)-> range[0="+range[2]+",1="+range[3]+"]");
        Log.e(TAG,"GL_HIGH_FLOAT (Fragment)-> precision[0="+precision[1]);
        if(precision[0]>0 && precision[1]>0){
            this.precision = "highp";
        }else{
            GLES20.glGetShaderPrecisionFormat(GLES20.GL_VERTEX_SHADER,GLES20.GL_MEDIUM_FLOAT,range,0,precision,0);
            Log.e(TAG,"GL_MEDIUM_FLOAT (Vertex)-> range[0="+range[0]+",1="+range[1]+"]");
            Log.e(TAG,"GL_MEDIUM_FLOAT (Vertex)-> precision[0="+precision[0]);
            GLES20.glGetShaderPrecisionFormat(GLES20.GL_FRAGMENT_SHADER,GLES20.GL_MEDIUM_FLOAT,range,2,precision,1);
            Log.e(TAG,"GL_MEDIUM_FLOAT (Fragment)-> range[0="+range[2]+",1="+range[3]+"]");
            Log.e(TAG,"GL_MEDIUM_FLOAT (Fragment)-> precision[0="+precision[1]);
            if(precision[0]>0 && precision[1]>0){
                this.precision = "mediump";
            }else{
                GLES20.glGetShaderPrecisionFormat(GLES20.GL_VERTEX_SHADER,GLES20.GL_LOW_FLOAT,range,0,precision,0);
                Log.e(TAG,"GL_LOW_FLOAT (Vertex)-> range[0="+range[0]+",1="+range[1]+"]");
                Log.e(TAG,"GL_LOW_FLOAT (Vertex)-> precision[0="+precision[0]);
                GLES20.glGetShaderPrecisionFormat(GLES20.GL_FRAGMENT_SHADER,GLES20.GL_LOW_FLOAT,range,2,precision,1);
                Log.e(TAG,"GL_LOW_FLOAT (Fragment)-> range[0="+range[2]+",1="+range[3]+"]");
                Log.e(TAG,"GL_LOW_FLOAT (Fragment)-> precision[0="+precision[1]);
                if(precision[0]>0 && precision[1]>0){
                    this.precision = "lowp";
                }else{
                    this.precision = "";
                }
            }
        }

        int[] element = new int[1];
        GLES20.glGetIntegerv(GLES20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS,element,0);
        if(element[0]>0){
            this.maxCombinedTextureImageUnits = element[0];
            Log.e(TAG, "GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS return -> " + element[0]);
        }else{
            Log.e(TAG,"GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS return -> "+element[0]);
        }
        GLES20.glGetIntegerv(GLES20.GL_MAX_CUBE_MAP_TEXTURE_SIZE,element,0);
        if(element[0]>0){
            this.maxCubeMapTextureSize = (element[0]);
            Log.e(TAG, "GL_MAX_CUBE_MAP_TEXTURE_SIZE return -> " + element[0]);
        }else{
            Log.e(TAG,"GL_MAX_CUBE_MAP_TEXTURE_SIZE return -> "+element[0]);
        }
        GLES20.glGetIntegerv(GLES20.GL_MAX_FRAGMENT_UNIFORM_VECTORS,element,0);
        if(element[0]>0){
            this.maxFragmentUniformsVectors = (element[0]);
            Log.e(TAG, "GL_MAX_FRAGMENT_UNIFORM_VECTORS return -> " + element[0]);
        }else{
            Log.e(TAG,"GL_MAX_FRAGMENT_UNIFORM_VECTORS return -> "+element[0]);
        }
        GLES20.glGetIntegerv(GLES20.GL_MAX_RENDERBUFFER_SIZE,element,0);
        if(element[0]>0){
            this.maxRenderBufferSize = (element[0]);
            Log.e(TAG, "GL_MAX_RENDERBUFFER_SIZE return -> " + element[0]);
        }else{
            Log.e(TAG,"GL_MAX_RENDERBUFFER_SIZE return -> "+element[0]);
        }
        GLES20.glGetIntegerv(GLES20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS,element,0);
        if(element[0]>0){
            this.maxVertexTextureImageUnits = element[0];
            Log.e(TAG, "GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS return -> " + element[0]);
        }else{
            Log.e(TAG,"GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS return -> "+element[0]);
        }
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_IMAGE_UNITS,element,0);
        if(element[0]>0){
            this.maxTextureImageUnits = element[0];
            Log.e(TAG, "MAX_TEXTURE_IMAGE_UNITS return -> " + element[0]);
        }else{
            Log.e(TAG, "MAX_TEXTURE_IMAGE_UNITS return -> " + element[0]);
        }
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE,element,0);
        if(element[0]>0){
            this.maxTextureSize = (element[0]);
            Log.e(TAG, "GL_MAX_TEXTURE_SIZE return -> " + element[0]);
        }else{
            Log.e(TAG,"GL_MAX_TEXTURE_SIZE return -> "+element[0]);
        }
        GLES20.glGetIntegerv(GLES20.GL_MAX_VARYING_VECTORS,element,0);
        if(element[0]>0){
            this.maxVaryingVectors = (element[0]);
            Log.e(TAG, "GL_MAX_VARYING_VECTORS return -> " + element[0]);
        }else{
            Log.e(TAG,"GL_MAX_VARYING_VECTORS return -> "+element[0]);
        }
        GLES20.glGetIntegerv(GLES20.GL_MAX_VERTEX_ATTRIBS,element,0);
        if(element[0]>0){
            this.maxVertexAttribs = (element[0]);
            Log.e(TAG, "GL_MAX_VERTEX_ATTRIBS return -> " + element[0]);
        }else{
            Log.e(TAG,"GL_MAX_VERTEX_ATTRIBS return -> "+element[0]);
        }
        GLES20.glGetIntegerv(GLES20.GL_MAX_VERTEX_UNIFORM_VECTORS,element,0);
        if(element[0]>0){
            this.maxVertexUniformVectors = (element[0]);
            Log.e(TAG, "GL_MAX_VERTEX_UNIFORM_VECTORS return -> " + element[0]);
        }else{
            Log.e(TAG,"GL_MAX_VERTEX_UNIFORM_VECTORS return -> "+element[0]);
        }
        int[] dimsViews = new int[2];
        GLES20.glGetIntegerv(GLES20.GL_MAX_VIEWPORT_DIMS,dimsViews,0);
        if(element[0]>0){
            this.maxViewportDims = new Vector2I(dimsViews[0],dimsViews[1]);
            Log.e(TAG, "GL_MAX_VIEWPORT_DIMS return -> " + this.maxViewportDims.toString());
        }else{
            Log.e(TAG,"GL_MAX_VIEWPORT_DIMS return -> "+dimsViews[0]+","+dimsViews[1]);
        }
        GLES20.glGetIntegerv(GLES20.GL_NUM_COMPRESSED_TEXTURE_FORMATS,element,0);
        if(element[0]>0){
            Log.e(TAG, "GL_NUM_COMPRESSED_TEXTURE_FORMATS return -> " + element[0]);
            this.numCompressedTextureFormats = (element[0]);
        }else{
            Log.e(TAG,"GL_NUM_COMPRESSED_TEXTURE_FORMATS return -> "+element[0]);
        }


    }
}
