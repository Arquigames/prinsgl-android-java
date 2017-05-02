package com.arquigames.prinsgl;

import android.opengl.GLES20;

/**
 * Created by usuario on 12/08/2016.
 */
public class ShaderUtils {
    public static Integer paramsStaticValuesToGL(int p){
        if ( p == Constants.REPEAT_WRAPPING) return GLES20.GL_REPEAT;
        if ( p == Constants.CLAMP_TO_EDGE_WRAPPING) return GLES20.GL_CLAMP_TO_EDGE;
        if ( p == Constants.MIRRORED_REPEAT_WRAPPING) return GLES20.GL_MIRRORED_REPEAT;

        if ( p == Constants.NEAREST_FILTER) return GLES20.GL_NEAREST;
        if ( p == Constants.NEAREST_MIPMAP_NEAREST_FILTER) return GLES20.GL_NEAREST_MIPMAP_NEAREST;
        if ( p == Constants.NEAREST_MIPMAP_LINEAR_FILTER) return GLES20.GL_NEAREST_MIPMAP_LINEAR;

        if ( p == Constants.LINEAR_FILTER) return GLES20.GL_LINEAR;
        if ( p == Constants.LINEAR_MIPMAP_NEAREST_FILTER) return GLES20.GL_LINEAR_MIPMAP_NEAREST;
        if ( p == Constants.LINEAR_MIPMAP_LINEAR_FILTER) return GLES20.GL_LINEAR_MIPMAP_LINEAR;

        if ( p == Constants.UNSIGNED_BYTE_TYPE) return GLES20.GL_UNSIGNED_BYTE;
        if ( p == Constants.UNSIGNED_SHORT_4444_TYPE) return GLES20.GL_UNSIGNED_SHORT_4_4_4_4;
        if ( p == Constants.UNSIGNED_SHORT_5551_TYPE) return GLES20.GL_UNSIGNED_SHORT_5_5_5_1;
        if ( p == Constants.UNSIGNED_SHORT_565_TYPE) return GLES20.GL_UNSIGNED_SHORT_5_6_5;

        if ( p == Constants.BYTE_TYPE) return GLES20.GL_BYTE;
        if ( p == Constants.SHORT_TYPE) return GLES20.GL_SHORT;
        if ( p == Constants.UNSIGNED_SHORT_TYPE) return GLES20.GL_UNSIGNED_SHORT;
        if ( p == Constants.INT_TYPE) return GLES20.GL_INT;
        if ( p == Constants.UNSIGNED_INT_TYPE) return GLES20.GL_UNSIGNED_INT;
        if ( p == Constants.FLOAT_TYPE) return GLES20.GL_FLOAT;




        if ( p == Constants.ALPHA_FORMAT) return GLES20.GL_ALPHA;
        if ( p == Constants.RGB_FORMAT) return GLES20.GL_RGB;
        if ( p == Constants.RGBA_FORMAT) return GLES20.GL_RGBA;
        if ( p == Constants.LUMINANCE_FORMAT) return GLES20.GL_LUMINANCE;
        if ( p == Constants.LUMINANCE_ALPHA_FORMAT) return GLES20.GL_LUMINANCE_ALPHA;

        if ( p == Constants.ADD_EQUATION) return GLES20.GL_FUNC_ADD;
        if ( p == Constants.SUBTRACT_EQUATION) return GLES20.GL_FUNC_SUBTRACT;
        if ( p == Constants.REVERSE_SUBTRACT_EQUATION) return GLES20.GL_FUNC_REVERSE_SUBTRACT;

        if ( p == Constants.ZeroFactor ) return GLES20.GL_ZERO;
        if ( p == Constants.OneFactor ) return GLES20.GL_ONE;
        if ( p == Constants.SrcColorFactor ) return GLES20.GL_SRC_COLOR;
        if ( p == Constants.OneMinusSrcColorFactor ) return GLES20.GL_ONE_MINUS_SRC_COLOR;
        if ( p == Constants.SrcAlphaFactor ) return GLES20.GL_SRC_ALPHA;
        if ( p == Constants.OneMinusSrcAlphaFactor ) return GLES20.GL_ONE_MINUS_SRC_ALPHA;
        if ( p == Constants.DstAlphaFactor ) return GLES20.GL_DST_ALPHA;
        if ( p == Constants.OneMinusDstAlphaFactor ) return GLES20.GL_ONE_MINUS_DST_ALPHA;

        if ( p == Constants.DstColorFactor ) return GLES20.GL_DST_COLOR;
        if ( p == Constants.OneMinusDstColorFactor ) return GLES20.GL_ONE_MINUS_DST_COLOR;
        if ( p == Constants.SrcAlphaSaturateFactor ) return GLES20.GL_SRC_ALPHA_SATURATE;

        /*
        if ( p == Constants.RGB_S3TC_DXT1_FORMAT ) return GLES20.GL_COMPRESSED_RGB_S3TC_DXT1_EXT;
        if ( p == Constants.RGBA_S3TC_DXT1_FORMAT ) return GLES20.GL_COMPRESSED_RGBA_S3TC_DXT1_EXT;
        if ( p == Constants.RGBA_S3TC_DXT3_FORMAT ) return GLES20.GL_COMPRESSED_RGBA_S3TC_DXT3_EXT;
        if ( p == Constants.RGBA_S3TC_DXT5_FORMAT ) return GLES20.GL_COMPRESSED_RGBA_S3TC_DXT5_EXT;
        */


        return -1;
    }
}
