package com.arquigames.prinsgl.cameras;

import android.util.Log;

import com.arquigames.prinsgl.Util;

/**
 * Created by usuario on 26/06/2016.
 */
public class OrthoGraphicCamera extends Camera {
    public static boolean DEBUG = false;
    private float zoom = 1;

    private float left ;
    private float right;
    private float top ;
    private float bottom ;

    private float near 	= 0.1f;
    private float far 	= 2000.0f;

    public OrthoGraphicCamera(){
        super();
        this.updateProjectionMatrix();
    }
    public OrthoGraphicCamera(float left,float right,float top,float bottom,float near,float far ){
        super();
        this.setLeft(left);
        this.setRight(right);
        this.setTop(top);
        this.setBottom(bottom);
        this.setNear(near);
        this.setFar(far);
        this.updateProjectionMatrix();
    }

    @Override
    public Camera clone() {

        try{
            OrthoGraphicCamera camera = new OrthoGraphicCamera();
            camera.copy(this);
            return camera;
        }catch(Exception e){
            if(DEBUG)Log.e("OrthoGraphicCamera","[CLONE] = "+ Util.exceptionToString(e));
            return null;
        }
    }
    @Override
    public void updateProjectionMatrix(){
        float dx = ( this.getRight() - this.getLeft()) / ( 2 * this.getZoom());
        float dy = ( this.getTop() - this.getBottom()) / ( 2 * this.getZoom());
        float cx = ( this.getRight() + this.getLeft()) / 2;
        float cy = ( this.getTop() + this.getBottom()) / 2;

        this.getProjectionMatrix().makeOrthographic( cx - dx, cx + dx, cy + dy, cy - dy, this.getNear(), this.getFar());
    }
    @Override
    public Camera copy(Camera camera) throws Exception {
        super.copy(camera);
        if(camera instanceof OrthoGraphicCamera){
            OrthoGraphicCamera source = (OrthoGraphicCamera)camera;
            this.setLeft(source.getLeft());
            this.setRight(source.getRight());
            this.setTop(source.getTop());
            this.setBottom(source.getBottom());
            this.setNear(source.getNear());
            this.setFar(source.getFar());
            this.setZoom(source.getZoom());
            this.updateProjectionMatrix();
        }
        return this;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public float getNear() {
        return near;
    }

    public void setNear(float near) {
        this.near = near;
    }

    public float getFar() {
        return far;
    }

    public void setFar(float far) {
        this.far = far;
    }
}
