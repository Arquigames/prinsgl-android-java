package com.arquigames.test_app.application.utils;

import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.arquigames.test_app.MainActivity;
import com.arquigames.prinsgl.Util;
import com.arquigames.prinsgl.cameras.Camera;
import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 28/09/2016.
 */

public class GameScreen {
    private Integer screenWidth = null;
    private Integer screenHeight = null;

    private static String TAG = "GameScreen";
    private static GameScreen screen=null;
    private MainActivity activity=null;
    private float density = 1.5f;

    private Vector3 vectorLeftTop 		= new Vector3(-1f, 1f,0.5f);
    private Vector3 vectorLeftBottom 	= new Vector3(-1f,-1f,0.5f);
    private Vector3 vectorRightBottom 	= new Vector3( 1,-1f,0.5f);
    private Vector3 vectorRightTop 		= new Vector3( 1f, 1f,0.5f);

    private Vector3 vectorleftTopPosition 		= new Vector3();
    private Vector3 vectorleftBottomPosition    = new Vector3();
    private Vector3 vectorrightTopPosition 		= new Vector3();
    private Vector3 vectorrightBottomPosition 	= new Vector3();

    private boolean computedSquare3DPositions = false;

    private GameScreen(MainActivity activity){
        this.setActivity(activity);
    }
    public String toString(){
        String str = "";
        str+="vectorleftTopPosition="+ getVectorleftTopPosition().toString();
        str+=" ,vectorleftBottomPosition="+ getVectorleftBottomPosition().toString();
        str+=" ,vectorrightTopPosition="+ getVectorrightTopPosition().toString();
        str+=" ,vectorrightBottomPosition="+ getVectorrightBottomPosition().toString();
        return str;
    }
    public static GameScreen getInstance(MainActivity activity){
        if(screen==null){
            screen = new GameScreen(activity);
        }
        return screen;
    }

    public void compute(){
        if(this.getActivity() ==null)return;
        Point size = new Point();
        DisplayMetrics dm = this.getActivity().getResources().getDisplayMetrics();
        this.setDensity(dm.density);
        WindowManager w = this.getActivity().getWindowManager();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            w.getDefaultDisplay().getSize(size);
            this.setScreenWidth(size.x);
            this.setScreenHeight(size.y);
        }else{
            Display d = w.getDefaultDisplay();
            this.setScreenWidth(d.getWidth());
            this.setScreenHeight(d.getHeight());
        }
        Log.e(TAG,"screenWidth = "+this.screenWidth+", screenHeight = "+this.screenHeight);
    }

    public Integer getScreenWidth() {
        if(this.screenWidth==null)this.compute();
        return this.screenWidth;
    }

    private void setScreenWidth(Integer screenWidth) {
        this.screenWidth = screenWidth;
    }

    public Integer getScreenHeight() {
        if(this.screenHeight==null)this.compute();
        return this.screenHeight;
    }

    private void setScreenHeight(Integer screenHeight) {
        this.screenHeight = screenHeight;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public MainActivity getActivity() {
        return activity;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    public void computeSquare3DPositions(Camera camera){



        try{
            //---------------------------------------------------------
            //LEFT TOP
            //---------------------------------------------------------
            Vector3 temp_vector = this.vectorLeftTop.clone();
            temp_vector.unproject(camera);
            temp_vector.sub(camera.getPosition(),null);
            temp_vector.normalize();

            float t = (float)((-1)*camera.getPosition().getZ()/temp_vector.getZ());
            float x = camera.getPosition().getX() + t*temp_vector.getX();
            float y = camera.getPosition().getY() + t*temp_vector.getY();
            float z = 0f;
            this.getVectorleftTopPosition().set(x,y,z);
            //---------------------------------------------------------
            //LEFT BOTTOM
            //---------------------------------------------------------
            temp_vector = this.vectorLeftBottom.clone();
            temp_vector.unproject(camera);
            temp_vector.sub(camera.getPosition(),null);
            temp_vector.normalize();

            t = (-1)*camera.getPosition().getZ()/temp_vector.getZ();
            x = camera.getPosition().getX() + t*temp_vector.getX();
            y = camera.getPosition().getY() + t*temp_vector.getY();
            z = 0f;

            this.getVectorleftBottomPosition().set(x,y,z);
            //---------------------------------------------------------
            //RIGHT BOTTOM
            //---------------------------------------------------------
            temp_vector = this.vectorRightBottom.clone();
            temp_vector.unproject(camera);
            temp_vector.sub(camera.getPosition(),null);
            temp_vector.normalize();

            t = (-1)*camera.getPosition().getZ()/temp_vector.getZ();
            x = camera.getPosition().getX() + t*temp_vector.getX();
            y = camera.getPosition().getY() + t*temp_vector.getY();
            z = 0;

            this.getVectorrightBottomPosition().set(x,y,z);
            //---------------------------------------------------------
            //RIGHT TOP
            //---------------------------------------------------------
            temp_vector = this.vectorRightTop.clone();
            temp_vector.unproject(camera);
            temp_vector.sub(camera.getPosition(),null);
            temp_vector.normalize();

            t = (-1)*camera.getPosition().getZ()/temp_vector.getZ();
            x = camera.getPosition().getX() + t*temp_vector.getX();
            y = camera.getPosition().getY() + t*temp_vector.getY();
            z = 0;

            this.getVectorrightTopPosition().set(x,y,z);

            this.setComputedSquare3DPositions(true);
        }catch(Exception e){
            Log.e(TAG,"computeSquare3DPositions()-> "+ Util.exceptionToString(e));
        }

    }

    public boolean isPositionOut(Vector3 position){
        boolean value =
                        position.getX() < this.vectorleftTopPosition.getX() ||
                        position.getX() > this.vectorrightTopPosition.getX() ||
                        position.getY() < this.vectorleftBottomPosition.getY() ||
                        position.getY() > this.vectorleftTopPosition.getY()
        ;

        return value;
    }
    public boolean isPositionXOut(Vector3 position){
        boolean value =
                        position.getX() < this.vectorleftTopPosition.getX() ||
                        position.getX() > this.vectorrightTopPosition.getX()
        ;

        return value;
    }
    public boolean isPositionYOut(Vector3 position){
        boolean value =
                    position.getY() < this.vectorleftBottomPosition.getY() ||
                        position.getY() > this.vectorleftTopPosition.getY()
        ;

        return value;
    }
    public boolean isPositionZOut(Vector3 position){
        //TODO
        return true;
    }

    public boolean isComputedSquare3DPositions() {
        return computedSquare3DPositions;
    }

    private void setComputedSquare3DPositions(boolean computedSquare3DPositions) {
        this.computedSquare3DPositions = computedSquare3DPositions;
    }

    public Vector3 getVectorleftTopPosition() {
        return vectorleftTopPosition;
    }

    public void setVectorleftTopPosition(Vector3 vectorleftTopPosition) {
        this.vectorleftTopPosition = vectorleftTopPosition;
    }

    public Vector3 getVectorleftBottomPosition() {
        return vectorleftBottomPosition;
    }

    public void setVectorleftBottomPosition(Vector3 vectorleftBottomPosition) {
        this.vectorleftBottomPosition = vectorleftBottomPosition;
    }

    public Vector3 getVectorrightTopPosition() {
        return vectorrightTopPosition;
    }

    public void setVectorrightTopPosition(Vector3 vectorrightTopPosition) {
        this.vectorrightTopPosition = vectorrightTopPosition;
    }

    public Vector3 getVectorrightBottomPosition() {
        return vectorrightBottomPosition;
    }

    public void setVectorrightBottomPosition(Vector3 vectorrightBottomPosition) {
        this.vectorrightBottomPosition = vectorrightBottomPosition;
    }
}
