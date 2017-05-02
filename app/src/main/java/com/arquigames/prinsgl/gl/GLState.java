package com.arquigames.prinsgl.gl;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.os.Build;
import android.util.Log;
import android.util.SparseBooleanArray;

import com.arquigames.prinsgl.Constants;
import com.arquigames.prinsgl.ImageUtils;
import com.arquigames.prinsgl.ShaderUtils;
import com.arquigames.prinsgl.TextureUtils;
import com.arquigames.prinsgl.gl.renderer.GLRenderer;
import com.arquigames.prinsgl.maths.Color;
import com.arquigames.prinsgl.maths.vectors.Vector4I;
import com.arquigames.prinsgl.textures.Texture;

import java.nio.ByteBuffer;

/**
 * Created by usuario on 10/08/2016.
 */
public class GLState {
    private static String TAG = "GLState";
    private float currentLineWidth = 0f;

    private Vector4I currentViewport = null;
    private Color clearColor;
    private Color currentClearColor;
    private float clearAlpha = 0;
    private float currentClearAlpha = 0;

    private SparseBooleanArray statesEnabled;
    private float currentClearDepth = -1;
    private int currentClearStencil = -1;
    private int currentDepthFunc = -1;
    private boolean currentDepthMask = false;
    private boolean currentColorMask = false;
    private int currentStencilFunc= -1;
    private int currentStencilRef = -1;
    private int currentStencilMask = -1;
    private int currentStencilFail = -1;
    private int currentStencilZFail = -1;
    private int currentStencilZPass = -1;
    private int currentCullFace = -1;
    private Boolean currentFlipSided= null;
    private float currentPolygonOffsetFactor = -1;
    private float currentPolygonOffsetUnits = -1;
    private boolean currentScissorTest;
    private int currentTextureSlot = -1;
    private int currentBlending = -1;
    private boolean currentPremultipledAlpha;
    private int currentBlendEquation = -1;
    private int currentBlendEquationAlpha = -1;
    private int currentBlendSrc = -1;
    private int currentBlendDst = -1;
    private int currentBlendSrcAlpha = -1;
    private int currentBlendDstAlpha = -1;
    private int usedTextureUnits = 0;

    public GLState(){
        this.currentViewport = new Vector4I();
        this.clearColor = new Color(0,0,0);
        this.currentClearColor = new Color(0,0,0);
        this.statesEnabled = new SparseBooleanArray();
    }
    public void setupDefaultState(){
        this.clearDepth(1);
        this.enable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);

        GLES20.glFrontFace(GLES20.GL_CCW);
        this.enable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);
        this.enable( GLES20.GL_BLEND );
        GLES20.glBlendEquation(GLES20.GL_FUNC_ADD);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
    }
    public void enable( int id ) {
        boolean bool;
        bool = this.statesEnabled.get(id);
        if(!bool){
            if(GLRenderer.DEBUG)Log.e(TAG,"enable()-> glEnable(id="+id+")");
            GLES20.glEnable(id);
            this.statesEnabled.put(id,true);
        }
    }
    public void disable( int id ) {
        boolean bool;
        bool = this.statesEnabled.get(id);
        if (bool) {
            if(GLRenderer.DEBUG)Log.e(TAG,"disable()-> glDisable(id="+id+")");
            GLES20.glDisable(id);
            this.statesEnabled.put(id,false);
        }
    }
    public void clearStencil(int stencil){
        if ( this.currentClearStencil != stencil ) {
            if(GLRenderer.DEBUG)Log.e(TAG,"clearStencil()-> glClearStencil(stencil="+stencil+")");
            GLES20.glClearStencil(stencil);
            this.currentClearStencil = stencil;
        }
    }
    public void clearDepth(float depth){
        if ( this.currentClearDepth != depth ) {
            if(GLRenderer.DEBUG)Log.e(TAG,"clearDepth()-> glClearDepthf(depth="+depth+")");
            GLES20.glClearDepthf(depth);
            this.currentClearDepth = depth;
        }
    }
    public void setLineWidth ( float width ) {

        if ( width != this.currentLineWidth && width>0 ) {
            if(GLRenderer.DEBUG) Log.e(TAG,"setLineWidth()-> "+width);
            GLES20.glLineWidth(width);
            this.currentLineWidth = width;
        }
    }

    public void setViewport(int startW, int startH, int width, int height) {
        Vector4I vector4I = new Vector4I(startW,startH,width,height);
        if(!vector4I.equals(this.currentViewport)){
            GLES20.glViewport(startW, startH, width, height);
            this.currentViewport = vector4I;
        }
    }
    public void clearColor(float r,float g, float b, float a){
        this.clearAlpha = a;
        this.clearColor.set(r,g,b);
        if(!this.currentClearColor.equals(this.clearColor)){
            GLES20.glClearColor(r, g, b, a);
            this.currentClearAlpha = this.clearAlpha;
            this.currentClearColor.copy(this.clearColor);
        }
    }
    public void clear(boolean color,boolean depth,boolean stencil){
        int bits = 0;
        if(color){
            bits |=GLES20.GL_COLOR_BUFFER_BIT;
        }
        if(depth){
            bits |=GLES20.GL_DEPTH_BUFFER_BIT;
        }
        if(stencil){
            bits |=GLES20.GL_STENCIL_BUFFER_BIT;
        }
        GLES20.glClear(bits);
    }
    public void setDepthFunc(int depthFunc){
        if ( this.currentDepthFunc != depthFunc ) {

            if ( depthFunc>=0 ) {

                switch ( depthFunc ) {

                    case Constants.NEVER_DEPTH:
                        if(GLRenderer.DEBUG)Log.e(TAG,"setDepthFunc()-> glDepthFunc(GL_NEVER)");
                        GLES20.glDepthFunc(GLES20.GL_NEVER);
                        break;

                    case Constants.ALWAYS_DEPTH:
                        if(GLRenderer.DEBUG)Log.e(TAG,"setDepthFunc()-> glDepthFunc(GL_ALWAYS)");
                        GLES20.glDepthFunc(GLES20.GL_ALWAYS);
                        break;

                    case Constants.LESS_DEPTH:
                        if(GLRenderer.DEBUG)Log.e(TAG,"setDepthFunc()-> glDepthFunc(GL_LESS)");
                        GLES20.glDepthFunc(GLES20.GL_LESS);
                        break;

                    case Constants.LESS_EQUAL_DEPTH:
                        if(GLRenderer.DEBUG)Log.e(TAG,"setDepthFunc()-> glDepthFunc(GL_LEQUAL)");
                        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
                        break;

                    case Constants.EQUAL_DEPTH:
                        if(GLRenderer.DEBUG)Log.e(TAG,"setDepthFunc()-> glDepthFunc(GL_EQUAL)");
                        GLES20.glDepthFunc(GLES20.GL_EQUAL);
                        break;

                    case Constants.GREATER_EQUAL_DEPTH:
                        if(GLRenderer.DEBUG)Log.e(TAG,"setDepthFunc()-> glDepthFunc(GL_GEQUAL)");
                        GLES20.glDepthFunc(GLES20.GL_GEQUAL);
                        break;

                    case Constants.GREATER_DEPTH:
                        if(GLRenderer.DEBUG)Log.e(TAG,"setDepthFunc()-> glDepthFunc(GL_GREATER)");
                        GLES20.glDepthFunc(GLES20.GL_GREATER);
                        break;

                    case Constants.NOT_EQUAL_DEPTH:
                        if(GLRenderer.DEBUG)Log.e(TAG,"setDepthFunc()-> glDepthFunc(GL_NOTEQUAL)");
                        GLES20.glDepthFunc(GLES20.GL_NOTEQUAL);
                        break;

                    default:
                        if(GLRenderer.DEBUG)Log.e(TAG,"setDepthFunc()-> default glDepthFunc(GL_LEQUAL)");
                        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
                }

            } else {
                if(GLRenderer.DEBUG)Log.e(TAG,"setDepthFunc()-> default(depthFunc="+depthFunc+") glDepthFunc(GL_LEQUAL)");
                GLES20.glDepthFunc(GLES20.GL_LEQUAL);
            }
            this.currentDepthFunc = depthFunc;
        }
    }
    public void setDepthTest(boolean depthTest){
        if ( depthTest ) {
            if(GLRenderer.DEBUG)Log.e(TAG,"setDepthTest()-> this.enable(GL_DEPTH_TEST)");
            this.enable( GLES20.GL_DEPTH_TEST );

        } else {
            if(GLRenderer.DEBUG)Log.e(TAG,"setDepthTest()-> this.disable(GL_DEPTH_TEST)");
            this.disable( GLES20.GL_DEPTH_TEST );

        }
    }
    public void setDepthMask(boolean depthWrite){
        if ( this.currentDepthMask != depthWrite ) {
            if(GLRenderer.DEBUG)Log.e(TAG,"setDepthWrite()-> glDepthMask("+depthWrite+")");
            GLES20.glDepthMask(depthWrite);
            this.currentDepthMask = depthWrite;

        }
    }
    public void setColorMask ( boolean colorMask ) {
        if ( this.currentColorMask != colorMask ) {
            if(GLRenderer.DEBUG)Log.e(TAG,"setColorWrite()-> glColorMask("+colorMask+" x4)");
            GLES20.glColorMask(colorMask, colorMask, colorMask, colorMask);
            this.currentColorMask = colorMask;

        }

    }
    public void setStencilFunc( int stencilFunc, int stencilRef, int stencilMask ) {

        if ( this.currentStencilFunc != stencilFunc ||
                this.currentStencilRef 	!= stencilRef 	||
                this.currentStencilMask != stencilMask ) {
            if(GLRenderer.DEBUG)Log.e(TAG,"setStencilFunc()-> glStencilFunc(stencilFunc="+stencilFunc+", stencilRef="+stencilRef+", stencilMask="+stencilMask+")");
            GLES20.glStencilFunc(stencilFunc, stencilRef, stencilMask);

            this.currentStencilFunc = stencilFunc;
            this.currentStencilRef  = stencilRef;
            this.currentStencilMask = stencilMask;

        }

    }
    public void setStencilOp ( int stencilFail, int stencilZFail, int stencilZPass ) {

        if ( this.currentStencilFail	 != stencilFail 	||
                this.currentStencilZFail != stencilZFail ||
                this.currentStencilZPass != stencilZPass ) {
            if(GLRenderer.DEBUG)Log.e(TAG,"setStencilOp()-> glStencilOp(stencilFail="+stencilFail+", stencilZFail="+stencilZFail+", stencilZPass="+stencilZPass+")");
            GLES20.glStencilOp(stencilFail, stencilZFail, stencilZPass);

            this.currentStencilFail  = stencilFail;
            this.currentStencilZFail = stencilZFail;
            this.currentStencilZPass = stencilZPass;

        }

    }
    public void setStencilTest( boolean stencilTest ) {
        if ( stencilTest ) {
            if(GLRenderer.DEBUG)Log.e(TAG,"setStencilTest()-> this.enable(GL_STENCIL_TEST)");
            this.enable( GLES20.GL_STENCIL_TEST );
        } else {
            if(GLRenderer.DEBUG)Log.e(TAG,"setStencilTest()-> this.disable(GL_STENCIL_TEST)");
            this.disable( GLES20.GL_STENCIL_TEST );
        }
    }
    public void setStencilMask( int stencilMask ) {
        if ( this.currentStencilMask != stencilMask ) {
            if(GLRenderer.DEBUG)Log.e(TAG,"setStencilMask()-> glStencilMask("+stencilMask+")");
            GLES20.glStencilMask(stencilMask);
            this.currentStencilMask = stencilMask;
        }
    }
    public void setFlipSided ( boolean flipSided ) {

        if(this.currentFlipSided==null){
            if ( flipSided ) {
                if(GLRenderer.DEBUG)Log.e("GLState","setFlipSided()-> GL_CW");
                GLES20.glFrontFace(GLES20.GL_CW);
            } else {
                if(GLRenderer.DEBUG)Log.e("GLState","setFlipSided()-> GL_CCW");
                GLES20.glFrontFace(GLES20.GL_CCW);
            }
            this.currentFlipSided = flipSided;
            return;
        }

        if ( this.currentFlipSided != flipSided ) {

            if ( flipSided ) {
                if(GLRenderer.DEBUG)Log.e("GLState","setFlipSided()-> GL_CW");
                GLES20.glFrontFace(GLES20.GL_CW);
            } else {
                if(GLRenderer.DEBUG)Log.e("GLState","setFlipSided()-> GL_CCW");
                GLES20.glFrontFace(GLES20.GL_CCW);
            }
            this.currentFlipSided = flipSided;
        }

    }
    public void setCullFace ( int cullFace ) {

        if ( cullFace != Constants.CULL_FACE_NONE) {
            if(GLRenderer.DEBUG)Log.e(TAG,"setCullFace()-> enable GL_CULL_FACE");
            this.enable( GLES20.GL_CULL_FACE );
            if ( cullFace != this.currentCullFace ) {
                if ( cullFace == Constants.CULL_FACE_BACK) {
                    if(GLRenderer.DEBUG)Log.e(TAG,"setCullFace()-> GL_BACK");
                    GLES20.glCullFace(GLES20.GL_BACK );
                } else if ( cullFace == Constants.CULL_FACE_FRONT) {

                    if(GLRenderer.DEBUG)Log.e(TAG,"setCullFace()-> GL_FRONT");
                    GLES20.glCullFace( GLES20.GL_FRONT );
                } else {
                    if(GLRenderer.DEBUG)Log.e(TAG,"setCullFace()-> GL_FRONT_AND_BACK");
                    GLES20.glCullFace( GLES20.GL_FRONT_AND_BACK );
                }
            }

        } else {

            if(GLRenderer.DEBUG)Log.e(TAG,"setCullFace()-> disable GL_CULL_FACE");
            this.disable( GLES20.GL_CULL_FACE );

        }

        this.currentCullFace = cullFace;

    }
    public void setPolygonOffset  ( boolean polygonOffset, float factor, int units ) {

        if ( polygonOffset ) {

            if(GLRenderer.DEBUG)Log.e(TAG,"setPolygonOffset()-> enable GL_POLYGON_OFFSET_FILL");
            this.enable( GLES20.GL_POLYGON_OFFSET_FILL );

        } else {

            if(GLRenderer.DEBUG)Log.e(TAG,"setPolygonOffset()-> disable GL_POLYGON_OFFSET_FILL");
            this.disable( GLES20.GL_POLYGON_OFFSET_FILL );

        }

        if ( polygonOffset && ( this.currentPolygonOffsetFactor != factor || this.currentPolygonOffsetUnits != units ) ) {

            if(GLRenderer.DEBUG)Log.e(TAG,"setPolygonOffset()-> glPolygonOffset(factor="+factor+",units="+units+")");
            GLES20.glPolygonOffset(factor, units);

            this.currentPolygonOffsetFactor = factor;
            this.currentPolygonOffsetUnits = units;

        }

    }
    public void setScissorTest( boolean scissorTest ) {

        this.currentScissorTest = scissorTest;

        if ( scissorTest ) {
            if(GLRenderer.DEBUG)Log.e(TAG,"setScissorTest()-> this.enable(GL_SCISSOR_TEST)");
            this.enable( GLES20.GL_SCISSOR_TEST );

        } else {
            if(GLRenderer.DEBUG)Log.e(TAG,"setScissorTest()-> this.disable(GL_SCISSOR_TEST)");
            this.disable( GLES20.GL_SCISSOR_TEST );

        }

    }
    public void compressedTexImage2D(int target,
                                     int level,
                                     int internalformat,
                                     int width,
                                     int height,
                                     int border,
                                     int imageSize,
                                     java.nio.Buffer data){

        GLES20.glCompressedTexImage2D(
                target,
                level,
                internalformat,
                width,
                height,
                border,
                imageSize,
                data
        );
    }
    public void texImage2D(
            int target,
            int level,
            int internalformat,
            int width,
            int height,
            int border,
            int format,
            int type,
            java.nio.Buffer pixels) {

        GLES20.glTexImage2D(
                target,
                level,
                internalformat,
                width,
                height,
                border,
                format,
                type,
                pixels);

        //GLES20.glBindBuffer(target,0);

    }
    public void activeTexture( int slot,GLRenderer glRenderer ) {

        if ( slot <0 ) slot = GLES20.GL_TEXTURE0 + glRenderer.getGlCapabilities().getMaxTextureImageUnits() - 1;

        if(this.currentTextureSlot!=slot){
            if(GLRenderer.DEBUG)Log.e(TAG,"activeTexture()-> slot="+slot);
            GLES20.glActiveTexture(slot);
            this.currentTextureSlot = slot;
        }
        if(GLRenderer.DEBUG) Log.e(TAG,"active texture = "+slot);

    }
    public void bindTexture ( int type, int texture, GLRenderer glRenderer ) {

        if ( this.currentTextureSlot <0) {
            this.activeTexture(-1,glRenderer);
        }
        if(GLRenderer.DEBUG)Log.e(TAG,"bindTexture()-> type="+type+",texture="+texture);
        GLES20.glBindTexture(type, texture);
    }
    public void setBlending(
            int blending,
            int blendEquation,
            int blendSrc,
            int blendDst,
            int blendEquationAlpha,
            int blendSrcAlpha,
            int blendDstAlpha,
            boolean premultipliedAlpha
    ){
        if(GLRenderer.DEBUG)Log.e(TAG,"setBlending()-> premultipliedAlpha = "+premultipliedAlpha);
        if ( blending != Constants.NO_BLENDING) {
            if(GLRenderer.DEBUG)Log.e(TAG,"setBlending()-> this.enable(GL_BLEND)");
            this.enable( GLES20.GL_BLEND );

        } else {
            if(GLRenderer.DEBUG)Log.e(TAG,"setBlending()-> this.disable(GL_BLEND)");
            this.disable( GLES20.GL_BLEND );
            this.currentBlending = blending; // no blending, that is
            return;

        }

        if ( blending != this.currentBlending ) {

            if ( blending == Constants.ADDITIVE_BLENDING) {

                if ( premultipliedAlpha ) {

                    GLES20.glBlendEquationSeparate( GLES20.GL_FUNC_ADD, GLES20.GL_FUNC_ADD );
                    GLES20.glBlendFuncSeparate( GLES20.GL_ONE, GLES20.GL_ONE, GLES20.GL_ONE, GLES20.GL_ONE );

                } else {

                    GLES20.glBlendEquation( GLES20.GL_FUNC_ADD );
                    GLES20.glBlendFunc( GLES20.GL_SRC_ALPHA, GLES20.GL_ONE );

                }

            } else if ( blending == Constants.SUBTRACTIVE_BLENDING) {

                if ( premultipliedAlpha ) {

                    GLES20.glBlendEquationSeparate( GLES20.GL_FUNC_ADD, GLES20.GL_FUNC_ADD );
                    GLES20.glBlendFuncSeparate( GLES20.GL_ZERO, GLES20.GL_ZERO, GLES20.GL_ONE_MINUS_SRC_COLOR, GLES20.GL_ONE_MINUS_SRC_ALPHA );

                } else {

                    GLES20.glBlendEquation( GLES20.GL_FUNC_ADD );
                    GLES20.glBlendFunc( GLES20.GL_ZERO, GLES20.GL_ONE_MINUS_SRC_COLOR );

                }

            } else if ( blending == Constants.MULTIPLY_BLENDING) {

                if ( premultipliedAlpha ) {

                    GLES20.glBlendEquationSeparate( GLES20.GL_FUNC_ADD, GLES20.GL_FUNC_ADD );
                    GLES20.glBlendFuncSeparate(GLES20.GL_ZERO, GLES20.GL_SRC_COLOR, GLES20.GL_ZERO, GLES20.GL_SRC_ALPHA );

                } else {

                    GLES20.glBlendEquation( GLES20.GL_FUNC_ADD );
                    GLES20.glBlendFunc( GLES20.GL_ZERO, GLES20.GL_SRC_COLOR );

                }

            } else {

                if ( premultipliedAlpha ) {

                    GLES20.glBlendEquationSeparate( GLES20.GL_FUNC_ADD, GLES20.GL_FUNC_ADD );
                    GLES20.glBlendFuncSeparate( GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA, GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA );

                } else {

                    GLES20.glBlendEquationSeparate( GLES20.GL_FUNC_ADD, GLES20.GL_FUNC_ADD );
                    GLES20.glBlendFuncSeparate( GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA, GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA );

                }

            }

            this.currentBlending = blending;
            this.currentPremultipledAlpha = premultipliedAlpha;

        }

        if ( blending == Constants.CUSTOM_BLENDING) {

            blendEquationAlpha = blendEquationAlpha>=0 ? blendEquationAlpha : blendEquation;
            blendSrcAlpha = blendSrcAlpha>=0 ? blendSrcAlpha : blendSrc;
            blendDstAlpha = blendDstAlpha>=0 ? blendDstAlpha : blendDst;

            if ( blendEquation != this.currentBlendEquation || blendEquationAlpha != this.currentBlendEquationAlpha ) {

                GLES20.glBlendEquationSeparate( ShaderUtils.paramsStaticValuesToGL( blendEquation ), ShaderUtils.paramsStaticValuesToGL( blendEquationAlpha ) );

                this.currentBlendEquation = blendEquation;
                this.currentBlendEquationAlpha = blendEquationAlpha;

            }

            if ( blendSrc != this.currentBlendSrc || blendDst != this.currentBlendDst || blendSrcAlpha != this.currentBlendSrcAlpha || blendDstAlpha != this.currentBlendDstAlpha ) {

                GLES20.glBlendFuncSeparate( ShaderUtils.paramsStaticValuesToGL( blendSrc ), ShaderUtils.paramsStaticValuesToGL( blendDst ), ShaderUtils.paramsStaticValuesToGL( blendSrcAlpha ), ShaderUtils.paramsStaticValuesToGL( blendDstAlpha ) );

                this.currentBlendSrc = blendSrc;
                this.currentBlendDst = blendDst;
                this.currentBlendSrcAlpha = blendSrcAlpha;
                this.currentBlendDstAlpha = blendDstAlpha;

            }

        } else {

            this.currentBlendEquation = -1;
            this.currentBlendSrc = -1;
            this.currentBlendDst = -1;
            this.currentBlendEquationAlpha = -1;
            this.currentBlendSrcAlpha = -1;
            this.currentBlendDstAlpha = -1;

        }
    }

    public void resetUsedTextureUnit() {
        this.usedTextureUnits = 0;
    }
    public void setUsedTextureUnits(int value){
        this.usedTextureUnits = value;
    }
    public int getUsedTextureUnits(){
        return this.usedTextureUnits;
    }

    public boolean setTexture(Texture texture, int textureUnit, GLRenderer glRenderer) {

        if(texture.getVersion()!=texture.getLastVersion()){
            texture.setLastVersion(texture.getVersion());
            return this.uploadTexture(texture,textureUnit,glRenderer);

        }
        int bufferLocation = texture.getBufferLocation();
        if(!this.isTextureBuffer(bufferLocation)){
            return this.uploadTexture(texture,textureUnit,glRenderer);
        }
        if(bufferLocation>=0){
            this.activeTexture(GLES20.GL_TEXTURE0 + textureUnit,glRenderer);
            this.bindTexture(GLES20.GL_TEXTURE_2D, bufferLocation,glRenderer);
            return true;
        }
        return false;

    }

    private boolean uploadTexture(Texture texture, int textureUnit,GLRenderer glRenderer) {

        int bufferLocation = this.genTextureBuffer();
        if(bufferLocation>=0 && texture.getImage()!=null){
            texture.setBufferLocation(bufferLocation);
            this.activeTexture(GLES20.GL_TEXTURE0 + textureUnit,glRenderer);
            this.bindTexture(GLES20.GL_TEXTURE_2D, bufferLocation,glRenderer);

            GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, texture.getUnpackAlignment());
            TextureUtils.clampToMaxSizeOpenGL(texture,glRenderer);
            if(
                TextureUtils.textureNeedsPowerOfTwo(texture) &&
                !ImageUtils.isPowerOfTwo(texture.getImage())
            ){
                if(GLRenderer.DEBUG)Log.e(TAG,"IMAGE TEXTURE ->  MAKING POWER OF TWO");
                TextureUtils.makePowerOfTwo(texture);
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"IMAGE TEXTURE IS POWER OF TWO");
            }
            boolean isPowerOfTwoImage = ImageUtils.isPowerOfTwo(texture.getImage());
            Bitmap.Config img_config = texture.getImage().getConfig();
            if(GLRenderer.DEBUG)Log.e(TAG,"TEXTURE FORMAT(config) = "+img_config);
            if(img_config == Bitmap.Config.ARGB_8888){
                texture.setFormat(Constants.RGBA_FORMAT);
            }else{
                if(img_config==Bitmap.Config.RGB_565){
                    texture.setFormat(Constants.RGB_FORMAT);
                }else{
                    texture.setFormat(Constants.RGB_FORMAT);
                }
            }
            if(GLRenderer.DEBUG)Log.e(TAG, "TEXTURE_FORMAT = " + texture.getFormat());
            int
                    glFormat    = ShaderUtils.paramsStaticValuesToGL(texture.getFormat()),
                    glType      = ShaderUtils.paramsStaticValuesToGL(texture.getType());

            this.setTextureParameters(GLES20.GL_TEXTURE_2D, texture, isPowerOfTwoImage);

            if(GLRenderer.DEBUG){
                Log.e(TAG,"allocating image getByteCount() = "+texture.getImage().getByteCount());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Log.e(TAG,"allocating image getAllocationByteCount() = "+texture.getImage().getAllocationByteCount());
                }
                Log.e(TAG,"allocating image getRowBytes() = "+texture.getImage().getRowBytes());
            }
            ByteBuffer pixelsBuffer = ByteBuffer.allocate(texture.getImage().getByteCount());
            texture.getImage().copyPixelsToBuffer(pixelsBuffer);
            pixelsBuffer.position(0);
            texture.setBuffer(pixelsBuffer);

            if(GLRenderer.DEBUG)Log.e(TAG, "-------------------------");
            if(GLRenderer.DEBUG)Log.e(TAG, "Image ByteCount = "+texture.getImage().getByteCount() + "");
            if(GLRenderer.DEBUG)Log.e(TAG, "Image format = "+glFormat + "");
            if(GLRenderer.DEBUG)Log.e(TAG,"Image width = "+texture.getImage().getWidth()+"");
            if(GLRenderer.DEBUG)Log.e(TAG,"Image height = "+texture.getImage().getHeight()+"");
            if(GLRenderer.DEBUG)Log.e(TAG,"Image type = "+glType+"");

            this.texImage2D(
                    GLES20.GL_TEXTURE_2D,
                    0,
                    glFormat,
                    texture.getImage().getWidth(),
                    texture.getImage().getHeight(),
                    0,
                    glFormat,
                    glType,
                    pixelsBuffer
            );
            if(GLRenderer.DEBUG)Log.e(TAG, "IMAGE TEXTURE VIOLA!");
            return true;

        }

        return false;
    }
    public void setTextureParameters(int textureType, Texture texture, boolean isPowerOfTwoImage){
        if ( isPowerOfTwoImage ) {

            GLES20.glTexParameteri(textureType, GLES20.GL_TEXTURE_WRAP_S, ShaderUtils.paramsStaticValuesToGL(texture.getWrapS()));
            GLES20.glTexParameteri(textureType, GLES20.GL_TEXTURE_WRAP_T, ShaderUtils.paramsStaticValuesToGL(texture.getWrapT()));

            GLES20.glTexParameteri(textureType, GLES20.GL_TEXTURE_MAG_FILTER, ShaderUtils.paramsStaticValuesToGL(texture.getMagFilter()));
            GLES20.glTexParameteri(textureType, GLES20.GL_TEXTURE_MIN_FILTER, ShaderUtils.paramsStaticValuesToGL(texture.getMinFilter()));

        } else {

            GLES20.glTexParameteri(textureType, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(textureType, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            if ( texture.getWrapS()!= Constants.CLAMP_TO_EDGE_WRAPPING || texture.getWrapT()!= Constants.CLAMP_TO_EDGE_WRAPPING) {

                if(GLRenderer.DEBUG)Log.e(TAG," Texture is not power of two. Texture.wrapS and Texture.wrapT should be set to Constants.CLAMP_TO_EDGE_WRAPPING.");

            }

            GLES20.glTexParameteri(textureType, GLES20.GL_TEXTURE_MAG_FILTER, TextureUtils.filterFallback(texture.getMagFilter()));
            GLES20.glTexParameteri(textureType, GLES20.GL_TEXTURE_MIN_FILTER, TextureUtils.filterFallback(texture.getMinFilter()));

            if ( texture.getMinFilter() != Constants.NEAREST_FILTER && texture.getMinFilter()!= Constants.LINEAR_FILTER) {

                if(GLRenderer.DEBUG)Log.e(TAG,"Texture is not power of two. Texture.minFilter should be set to Constants.NEAREST_FILTER or Constants.LINEAR_FILTER.");

            }

        }
    }

    public boolean isTextureBuffer(int textureLocation){
        return GLES20.glIsTexture(textureLocation);
    }
    public int genTextureBuffer(){
        int[] buffer = new int[1];
        GLES20.glGenTextures(1,buffer,0);
        if(buffer[0]>0){
            if(GLRenderer.DEBUG)Log.e(TAG,"gen new texture buffer = "+buffer[0]);
            return buffer[0];
        }
        return -1;
    }
}
