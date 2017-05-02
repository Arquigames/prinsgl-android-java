package com.arquigames.test_app;

import android.opengl.GLSurfaceView;
import android.util.Log;

import com.arquigames.test_app.application.Components;
import com.arquigames.prinsgl.ProgramUtils;
import com.arquigames.prinsgl.Scene;
import com.arquigames.prinsgl.Util;
import com.arquigames.prinsgl.cameras.PerspectiveCamera;
import com.arquigames.prinsgl.loaders.threejs.ThreeJsonLoader;
import com.arquigames.prinsgl.maths.vectors.Vector3;
import com.arquigames.prinsgl.gl.renderer.GLRenderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";


    private PerspectiveCamera camera;
    private Scene scene;


    private GLRenderer glRenderer;

    private MyGLSurfaceView parent;

    private Components components;

    //private Vector3 vecScaled;


    public MyGLRenderer(MyGLSurfaceView parent){
        this.parent = parent;
    }
    public MyGLSurfaceView getParent(){
        return this.parent;
    }
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLRenderer.DEBUG = false;
        ProgramUtils.DEBUG = false;
        ThreeJsonLoader.DEBUG = true;
        this.glRenderer = new GLRenderer(this.parent.getContext());
        this.glRenderer.clearColor();
        this.glRenderer.loadCapabilities();
        this.glRenderer.loadDefaultState();
        this.scene = new Scene();
        this.camera = new PerspectiveCamera();
        //--------------------------------------------//
        this.components = new Components(this);
        //--------------------------------------------//
        //TODO
        this.components.load(this.scene,this.camera,this.glRenderer);
    }

    @Override
    public void onDrawFrame(GL10 unused) {

        this.glRenderer.clearBufferState();
        try {
            this.glRenderer.render(this.scene,this.camera);
            this.components.render();

        } catch (Exception e) {
            if(GLRenderer.DEBUG)Log.e(TAG,""+Util.exceptionToString(e));
        }


    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {

        this.glRenderer.setViewport(0,0,width,height);

        float aspect = (1f*width)/height;
        camera.setAspect(aspect);
        camera.getPosition().set(0f, 0f, 25f);
        camera.lookAt(new Vector3(0f, 0f, 0f));
        camera.setFov(30f);
        camera.setNear(1f);
        camera.setFar(1000f);
        camera.updateProjectionMatrix();
        camera.updateMatrixWorld(true);

        this.components.onSurfaceChanged(width,height);

    }

}