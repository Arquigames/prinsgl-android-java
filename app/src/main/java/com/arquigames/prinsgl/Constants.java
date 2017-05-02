package com.arquigames.prinsgl;

import android.opengl.GLES20;
import android.os.Build;

import java.lang.reflect.Field;

/**
 * Created by usuario on 12/07/2016.
 */
public class Constants {
    public static final int LINE_CAP_ROUND = 0;
    public static final int LINE_CAP_BUT = 1;
    public static final int LINE_CAP_SQUARE = 2;
    public static final int LINE_JOIN_BEVEL = 0;
    public static final int LINE_JOIN_ROUND = 1;
    public static final int LINE_JOIN_MITER = 2;
    public static int min_sdk = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    public static int current_sdk = Build.VERSION.SDK_INT;

    public static final int MOUSE_LEFT = 0;
    public static final int MOUSE_MIDDLE = 1;
    public static final int MOUSE_RIGHT = 2;

    // GL STATE CONSTANTS

    public static final int CULL_FACE_NONE = 0;
    public static final int CULL_FACE_BACK = 1;
    public static final int CULL_FACE_FRONT = 2;
    public static final int CULL_FACE_FRONT_BACK = 3;

    public static final int FRONT_FACE_DIRECTION_CW = 0;
    public static final int FRONT_FACE_DIRECTION_CCW = 1;

    // SHADOWING TYPES

    public static final int BASIC_SHADOW_MAP = 0;
    public static final int PCF_SHADOW_MAP = 1;
    public static final int PCF_SOFT_SHADOW_MAP = 2;

    // MATERIAL CONSTANTS

    // side

    public static final int FACE_FRONT_SIDE = 0;
    public static final int FACE_BACK_SIDE = 1;
    public static final int FACE_DOUBLE_SIDE = 2;

    // shading

    public static final int FLAT_SHADING = 1;
    public static final int SMOOTH_SHADING = 2;

    // colors

    public static final int NO_COLORS = 0;
    public static final int FACE_COLORS = 1;
    public static final int VERTEX_COLORS = 2;

    // blending modes

    public static final int NO_BLENDING = 1;
    public static final int NORMAL_BLENDING = 2;
    public static final int ADDITIVE_BLENDING = 3;
    public static final int SUBTRACTIVE_BLENDING = 4;
    public static final int MULTIPLY_BLENDING = 5;
    public static final int CUSTOM_BLENDING = 6;

    // custom blending equations
    // (numbers start from 100 not to clash with other
    // mappings to OpenGL constants defined in Texture.js)

    public static final int ADD_EQUATION = 100;
    public static final int SUBTRACT_EQUATION = 101;
    public static final int REVERSE_SUBTRACT_EQUATION = 102;
    public static final int MIN_EQUATION = 103;
    public static final int MAX_EQUATION = 104;

    // custom blending destination factors

    public static final int ZeroFactor = 200;
    public static final int OneFactor = 201;
    public static final int SrcColorFactor = 202;
    public static final int OneMinusSrcColorFactor = 203;
    public static final int SrcAlphaFactor = 204;
    public static final int OneMinusSrcAlphaFactor = 205;
    public static final int DstAlphaFactor = 206;
    public static final int OneMinusDstAlphaFactor = 207;

    // custom blending source factors

    //public static int ZeroFactor = 200;
    //public static int OneFactor = 201;
    //public static int SrcAlphaFactor = 204;
    //public static int OneMinusSrcAlphaFactor = 205;
    //public static int DstAlphaFactor = 206;
    //public static int OneMinusDstAlphaFactor = 207;
    public static final int DstColorFactor = 208;
    public static final int OneMinusDstColorFactor = 209;
    public static final int SrcAlphaSaturateFactor = 210;

    // depth modes

    public static final int NEVER_DEPTH = 0;
    public static final int ALWAYS_DEPTH = 1;
    public static final int LESS_DEPTH = 2;
    public static final int LESS_EQUAL_DEPTH = 3;
    public static final int EQUAL_DEPTH = 4;
    public static final int GREATER_EQUAL_DEPTH = 5;
    public static final int GREATER_DEPTH = 6;
    public static final int NOT_EQUAL_DEPTH = 7;


    // TEXTURE CONSTANTS

    public static final int MULTIPLY_OPERATION = 0;
    public static final int MIX_OPERATION = 1;
    public static final int ADD_OPERATION = 2;

    // Mapping modes

    public static final int UV_MAPPING = 300;

    public static final int CUBE_REFLECTION_MAPPING = 301;
    public static final int CUBE_REFRACTION_MAPPING = 302;

    public static final int EQUIRECTANGULAR_REFLECTION_MAPPING = 303;
    public static final int EQUIRECTANGULAR_REFRACTION_MAPPING = 304;

    public static final int SPHERICAL_REFLECTION_MAPPING = 305;

    // Wrapping modes

    public static final int REPEAT_WRAPPING = 1000;
    public static final int CLAMP_TO_EDGE_WRAPPING = 1001;
    public static final int MIRRORED_REPEAT_WRAPPING = 1002;

    // Filters

    public static final int NEAREST_FILTER = 1003;
    public static final int NEAREST_MIPMAP_NEAREST_FILTER = 1004;
    public static final int NEAREST_MIPMAP_LINEAR_FILTER = 1005;
    public static final int LINEAR_FILTER = 1006;
    public static final int LINEAR_MIPMAP_NEAREST_FILTER = 1007;
    public static final int LINEAR_MIPMAP_LINEAR_FILTER = 1008;

    // Data types

    public static final int UNSIGNED_BYTE_TYPE = 1009;
    public static final int BYTE_TYPE = 1010;
    public static final int SHORT_TYPE = 1011;
    public static final int UNSIGNED_SHORT_TYPE = 1012;
    public static final int INT_TYPE = 1013;
    public static final int UNSIGNED_INT_TYPE = 1014;
    public static final int FLOAT_TYPE = 1015;
    public static final int HALF_FLOAT_TYPE = 1025;

    // Pixel types

    public static final int UNSIGNED_SHORT_4444_TYPE = 1016;
    public static final int UNSIGNED_SHORT_5551_TYPE = 1017;
    public static final int UNSIGNED_SHORT_565_TYPE = 1018;

    // Pixel formats

    public static final int ALPHA_FORMAT = 1019;
    public static final int RGB_FORMAT = 1020;
    public static final int RGBA_FORMAT = 1021;
    public static final int LUMINANCE_FORMAT = 1022;
    public static final int LUMINANCE_ALPHA_FORMAT = 1023;
    // public static int RGBE_FORMAT handled as public static int RGBA_FORMAT in shaders
    public static final int RGBE_FORMAT = RGBA_FORMAT; //1024;

    // DDS / ST3C Compressed texture formats

    public static final int RGB_S3TC_DXT1_FORMAT = 2001;
    public static final int RGBA_S3TC_DXT1_FORMAT = 2002;
    public static final int RGBA_S3TC_DXT3_FORMAT = 2003;
    public static final int RGBA_S3TC_DXT5_FORMAT = 2004;


    // PVRTC compressed texture formats

    public static final int RGB_PVRTC_4BPPV1_Format = 2100;
    public static final int RGB_PVRTC_2BPPV1_Format = 2101;
    public static final int RGBA_PVRTC_4BPPV1_Format = 2102;
    public static final int RGBA_PVRTC_2BPPV1_Format = 2103;

    // ETC compressed texture formats

    public static final int RGB_ETC1_Format = 2151;

    // Loop styles for AnimationAction

    public static final int LoopOnce = 2200;
    public static final int LoopRepeat = 2201;
    public static final int LoopPingPong = 2202;

    // Interpolation

    public static final int InterpolateDiscrete = 2300;
    public static final int InterpolateLinear = 2301;
    public static final int InterpolateSmooth = 2302;

    // Interpolant ending modes

    public static final int ZeroCurvatureEnding = 2400;
    public static final int ZeroSlopeEnding = 2401;
    public static final int WrapAroundEnding = 2402;

    // Triangle Draw modes

    public static final int TRIANGLES_DRAW_MODE = 0;
    public static final int TRIANGLE_STRIP_DRAW_MODE = 1;
    public static final int TRIANGLE_FAN_DRAW_MODE = 2;

    public static final int DEFAULT_MAPPING = UV_MAPPING;

    public static final String MATERIAL_PHONG_VERTEX_PATH = "shaders/material/phong/vertex.glsl";
    public static final String MATERIAL_PHONG_FRAGMENT_PATH = "shaders/material/phong/fragment.glsl";

    public static final String MATERIAL_BASIC_VERTEX_PATH = "shaders/material/basic/vertex.glsl";
    public static final String MATERIAL_BASIC_FRAGMENT_PATH = "shaders/material/basic/fragment.glsl";

    public static Integer get(String key) throws Exception {

        int _value = -1;
        switch (key){
            case "NormalBlending":
                _value = Constants.NORMAL_BLENDING;
                break;
        }
        if(_value!=-1)return _value;

        Constants obj = new Constants();
        Field[] declaredFields = Constants.class.getDeclaredFields();
        for (Field field : declaredFields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                if(key.equals(field.getName())){
                    return field.getInt(obj);
                }
            }
        }
        throw new Exception("Missing value");
    }
    public String getGLName(int name){
        String str = null;
        switch(name){
            case GLES20.GL_FLOAT_VEC2:
                str = "GL_FLOAT_VEC2";
                break;
            case GLES20.GL_FLOAT_VEC3:
                str = "GL_FLOAT_VEC3";
                break;
            case GLES20.GL_FLOAT_VEC4:
                str = "GL_FLOAT_VEC4";
                break;
            case GLES20.GL_FLOAT_MAT2:
                str = "GL_FLOAT_MAT2";
                break;
            case GLES20.GL_FLOAT_MAT3:
                str = "GL_FLOAT_MAT3";
                break;
            case GLES20.GL_FLOAT_MAT4:
                str = "GL_FLOAT_MAT4";
                break;
            case GLES20.GL_FLOAT:
                str = "GL_FLOAT";
                break;
            case GLES20.GL_LOW_FLOAT:
                str = "GL_LOW_FLOAT";
                break;
            case GLES20.GL_MEDIUM_FLOAT:
                str = "GL_MEDIUM_FLOAT";
                break;
            case GLES20.GL_HIGH_FLOAT:
                str = "GL_HIGH_FLOAT";
                break;
            case GLES20.GL_ELEMENT_ARRAY_BUFFER:
                str = "GL_ELEMENT_ARRAY_BUFFER";
                break;
            case GLES20.GL_ARRAY_BUFFER:
                str = "GL_ARRAY_BUFFER";
                break;
        }
        return str;
    }

}