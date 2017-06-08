package com.arquigames.prinsgl.cameras;

import android.util.Log;

import com.arquigames.prinsgl.maths.MathUtils;
import com.arquigames.prinsgl.Util;

/**
 * Created by usuario on 26/06/2016.
 */
public class PerspectiveCamera extends Camera {
    public static boolean DEBUG = false;
    private float focalLength = 10f;
    private float zoom 	= 1f;

    private float fov 	= 50f;
    private float aspect = 1f;
    private float near 	= 0.1f;
    private float far 	= 2000f;

    private float fullWidth = 0;
    private float fullHeight = 0;
    private float x = 0;
    private float y = 0;

    private float width = 0;
    private float height = 0;

    public PerspectiveCamera(){
        super();
        this.updateProjectionMatrix();
    }


    public PerspectiveCamera(float fov, float aspect, float near, float far){
        super();
        this.setFov(fov);
        this.setAspect(aspect);
        this.setNear(near);
        this.setFar(far);
        this.updateProjectionMatrix();
    }
    public void setLens(float focalLength,float frameHeight ){
        /**
         * Uses Focal Length (in mm) to estimate and set FOV
         * 35mm (full-frame) camera is used if frame size is not specified;
         * Formula based on http://www.bobatkins.com/photography/technical/field_of_view.html
         */
        this.setFov(2 * MathUtils.radToDeg( (float) Math.atan( frameHeight / ( focalLength * 2 ) ) ));
        this.updateProjectionMatrix();
    }
    public void setViewOffset(float fullWidth,float fullHeight,float x,float y,float width,float height ){
        /**
         * Sets an offset in a larger frustum. This is useful for multi-window or
         * multi-monitor/multi-machine setups.
         *
         * For example, if you have 3x2 monitors and each monitor is 1920x1080 and
         * the monitors are in grid like this
         *
         *   +---+---+---+
         *   | A | B | C |
         *   +---+---+---+
         *   | D | E | F |
         *   +---+---+---+
         *
         * then for each monitor you would call it like this
         *
         *   var w = 1920;
         *   var h = 1080;
         *   var fullWidth = w * 3;
         *   var fullHeight = h * 2;
         *
         *   --A--
         *   camera.setOffset( fullWidth, fullHeight, w * 0, h * 0, w, h );
         *   --B--
         *   camera.setOffset( fullWidth, fullHeight, w * 1, h * 0, w, h );
         *   --C--
         *   camera.setOffset( fullWidth, fullHeight, w * 2, h * 0, w, h );
         *   --D--
         *   camera.setOffset( fullWidth, fullHeight, w * 0, h * 1, w, h );
         *   --E--
         *   camera.setOffset( fullWidth, fullHeight, w * 1, h * 1, w, h );
         *   --F--
         *   camera.setOffset( fullWidth, fullHeight, w * 2, h * 1, w, h );
         *
         *   Note there is no reason monitors have to be the same size or in a grid.
         */
        this.setFullWidth(fullWidth);
        this.setFullHeight(fullHeight);
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);

        this.updateProjectionMatrix();
    }
    @Override
    public void updateProjectionMatrix(){
        float fov = MathUtils.radToDeg( 2 * (float) Math.atan( Math.tan( MathUtils.degToRad(this.getFov()) * 0.5 ) / this.getZoom()) );
        if ( this.getFullWidth() >0 ){
            float aspect = this.getFullWidth() / this.getFullHeight();
            float top = (float) Math.tan( MathUtils.degToRad( fov * 0.5f ) ) * this.getNear();
            float bottom = - top;
            float left = aspect * bottom;
            float right = aspect * top;
            float width = Math.abs( right - left );
            float height = Math.abs( top - bottom );

            this.getProjectionMatrix().makeFrustum(
                    left + this.getX() * width / this.getFullWidth(),
                    left + ( this.getX() + this.getWidth()) * width / this.getFullWidth(),
                    top - ( this.getY() + this.getHeight()) * height / this.getFullHeight(),
                    top - this.getY() * height / this.getFullHeight(),
                    this.getNear(),
                    this.getFar()
            );
        } else {
            this.getProjectionMatrix().makePerspective( fov, this.getAspect(), this.getNear(), this.getFar());
        }
    }
    public PerspectiveCamera copy(PerspectiveCamera source) throws Exception{
        super.copy(source);
        this.setFocalLength(source.getFocalLength());
        this.setZoom(source.getZoom());

        this.setFov(source.getFov());
        this.setAspect(source.getAspect());
        this.setNear(source.getNear());
        this.setFar(source.getFar());
        return this;
    }
    public void setAspect(float aspect) {
        this.aspect = aspect;
    }
    @Override
    public Camera copy(Camera camera) throws Exception {
        super.copy(camera);
        if(camera instanceof PerspectiveCamera){
            PerspectiveCamera perspectiveCamera = (PerspectiveCamera)camera;
            this.setFocalLength(perspectiveCamera.getFocalLength());
            this.setZoom(perspectiveCamera.getZoom());
            this.setFov(perspectiveCamera.getFov());
            this.setAspect(perspectiveCamera.getAspect());
            this.setNear(perspectiveCamera.getNear());
            this.setFar(perspectiveCamera.getFar());
            this.setFullHeight(perspectiveCamera.getFullHeight());
            this.setFullWidth(perspectiveCamera.getFullWidth());
            this.setX(perspectiveCamera.getX());
            this.setY(perspectiveCamera.getY());
            this.setWidth(perspectiveCamera.getWidth());
            this.setHeight(perspectiveCamera.getHeight());
            this.updateProjectionMatrix();
        }
        return this;
    }
    @Override
    public Camera clone() {

        try{
            PerspectiveCamera camera = new PerspectiveCamera();
            camera.copy(this);
            return camera;
        }catch(Exception e){
            if(DEBUG)Log.e("PerspectiveCamera","[CLONE] = "+ Util.exceptionToString(e));
            return null;
        }
    }

    public float getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(float focalLength) {
        this.focalLength = focalLength;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public float getAspect() {
        return aspect;
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

    public float getFullWidth() {
        return fullWidth;
    }

    public void setFullWidth(float fullWidth) {
        this.fullWidth = fullWidth;
    }

    public float getFullHeight() {
        return fullHeight;
    }

    public void setFullHeight(float fullHeight) {
        this.fullHeight = fullHeight;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
