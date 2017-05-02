package com.arquigames.test_app.application.comp.touches.buttons;

import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by usuario on 22/06/2016.
 */
public class PauseBtn {
    private Integer screenWidth = null;
    private Integer screenHeight = null;

    private Integer touchMoveID = null;

    private Integer radiusArea = null;
    private Point center = null;

    private Paint drawPaintArea         = null;
    private Paint drawPaintAreaPaused    = null;

    private boolean paused = false;

    private static PauseBtn instance = null;

    private PauseBtn(Integer screenWidth, Integer screenHeight){
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.setCenter(new Point(0,0));


        this.setDrawPaintArea(new Paint());
        this.getDrawPaintArea().setARGB(120,255,255,255);
        this.getDrawPaintArea().setAntiAlias(true);
        this.getDrawPaintArea().setStrokeWidth(1);
        this.getDrawPaintArea().setStyle(Paint.Style.FILL); // change to fill
        this.getDrawPaintArea().setStrokeJoin(Paint.Join.ROUND);
        this.getDrawPaintArea().setStrokeCap(Paint.Cap.ROUND);

        this.setDrawPaintAreaPaused(new Paint());
        this.getDrawPaintAreaPaused().setARGB(120,255,0,0);
        this.getDrawPaintAreaPaused().setAntiAlias(true);
        this.getDrawPaintAreaPaused().setStrokeWidth(1);
        this.getDrawPaintAreaPaused().setStyle(Paint.Style.FILL); // change to fill
        this.getDrawPaintAreaPaused().setStrokeJoin(Paint.Join.ROUND);
        this.getDrawPaintAreaPaused().setStrokeCap(Paint.Cap.ROUND);

        this.computeDrawingArea();

    }
    public static PauseBtn getInstance(Integer screenWidth, Integer screenHeight){
        if(instance==null){
            instance = new PauseBtn(screenWidth,screenHeight);
        }
        return instance;
    }

    public Integer getTouchMoveID() {
        return touchMoveID;
    }

    public void setTouchMoveID(Integer touchMoveID) {
        this.touchMoveID = touchMoveID;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public Paint getDrawPaintArea() {
        return drawPaintArea;
    }

    public void setDrawPaintArea(Paint drawPaintArea) {
        this.drawPaintArea = drawPaintArea;
    }

    public Paint getDrawPaintAreaPaused() {
        return drawPaintAreaPaused;
    }

    private void setDrawPaintAreaPaused(Paint drawPaintAreaPaused) {
        this.drawPaintAreaPaused = drawPaintAreaPaused;
    }
    private void computeDrawingArea(){
        int rad = this.screenWidth/32 + 25;
        if(rad<15)rad = 15;
        if(rad>70)rad = 70;
        this.center.x = this.screenWidth - rad -40;
        this.center.y =  rad + 40;
        this.setRadiusArea(rad);
    }

    public Integer getRadiusArea() {
        return radiusArea;
    }

    private void setRadiusArea(Integer radiusArea) {
        this.radiusArea = radiusArea;
    }
}
