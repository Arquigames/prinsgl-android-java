package com.arquigames.test_app.application.comp.touches;

/**
 * Created by usuario on 28/09/2016.
 */

public class TouchHandler {
    private static TouchHandler obj;
    private int width;
    private int height;
    private TouchHandler(int width,int height){
        this.width= width;
        this.height= height;
    }
    public static TouchHandler getInstance(int width, int height){
        if(obj==null){
            obj = new TouchHandler(width,height);
        }
        return obj;
    }
    public void isntall(){
        //TODO
    }
    public void onTouch(){
        //TODO
    }
}
