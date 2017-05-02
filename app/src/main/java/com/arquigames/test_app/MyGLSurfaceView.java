package com.arquigames.test_app;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.arquigames.prinsgl.egl.config.MultiSampleConfigChooser;

/**
 * Created by usuario on 04/08/2016.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    private boolean render_continuously = true;

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context,attrs);
        setEGLContextClientVersion(2);
        mRenderer = new MyGLRenderer(this);
        setEGLConfigChooser(new MultiSampleConfigChooser());
        setRenderer(mRenderer);
        this.setPreserveEGLContextOnPause(true);
        if(this.render_continuously){
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }else{
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
    }

    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new MyGLRenderer(this);
        setEGLConfigChooser(new MultiSampleConfigChooser());
        setRenderer(mRenderer);
        this.setPreserveEGLContextOnPause(true);
        if(this.render_continuously){
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }else{
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
    }

}