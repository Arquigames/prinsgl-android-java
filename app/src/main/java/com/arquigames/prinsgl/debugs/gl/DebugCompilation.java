package com.arquigames.prinsgl.debugs.gl;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by usuario on 12/07/2016.
 */
public class DebugCompilation {

        public static boolean shaderStatus(int shader) {
        int[] isCompiled = new int[1];
        GLES20.glGetShaderiv(shader,GLES20.GL_COMPILE_STATUS,isCompiled,0);
        if(isCompiled[0]==GLES20.GL_FALSE){
            String infoLog = GLES20.glGetShaderInfoLog(shader);
            Log.e("COMPILE_SHADER_STATUS",infoLog);
            return false;
        }
        return true;
    }

    public static boolean programStatus(int mProgram) {
        int[] isCompiled = new int[1];
        GLES20.glGetProgramiv(mProgram,GLES20.GL_LINK_STATUS,isCompiled,0);
        if(isCompiled[0]==GLES20.GL_FALSE){
            String infoLog = GLES20.glGetProgramInfoLog(mProgram);
            Log.e("COMPILE_PROGRAM_STATUS",infoLog);
            return false;
        }
        return true;
    }
}