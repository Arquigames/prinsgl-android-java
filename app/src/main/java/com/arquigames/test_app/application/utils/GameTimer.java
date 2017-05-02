package com.arquigames.test_app.application.utils;

/**
 * Created by usuario on 28/09/2016.
 */

public class GameTimer {
    private float timer = 0f;
    private float factor = 0.004f;
    private static GameTimer obj;
    private GameTimer(float factor){
        this.factor = factor;
    }
    private GameTimer(){

    }
    public static GameTimer getInstance(float value){
        if(obj==null){
            obj = new GameTimer(value);
        }
        return obj;
    }
    public static GameTimer getInstance(){
        if(obj==null){
            obj = new GameTimer();
        }
        return obj;
    }
    public float getTimer(){
        return this.timer;
    }
    public void setTimer(float value){
        this.timer = value;
    }
    public void render(){
        this.timer +=this.factor;
    }
}
