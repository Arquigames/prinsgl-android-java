package com.arquigames.test_app.application.comp.touches.buttons;

import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by usuario on 27/04/2016.
 */
public class AttackBtn {
    private Integer screenWidth = null;
    private Integer screenHeight = null;

    private Integer touchMoveID = null;

    private Integer radiusArea = null;
    private Point center = null;

    private Paint drawPaintArea         = null;
    private Paint drawPaintAreaHover    = null;

    private boolean hover = false;

    private static AttackBtn instance = null;

    private AttackBtn(Integer screenWidth, Integer screenHeight){
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

        this.setDrawPaintAreaHover(new Paint());
        this.getDrawPaintAreaHover().setARGB(120,255,0,0);
        this.getDrawPaintAreaHover().setAntiAlias(true);
        this.getDrawPaintAreaHover().setStrokeWidth(1);
        this.getDrawPaintAreaHover().setStyle(Paint.Style.FILL); // change to fill
        this.getDrawPaintAreaHover().setStrokeJoin(Paint.Join.ROUND);
        this.getDrawPaintAreaHover().setStrokeCap(Paint.Cap.ROUND);

        this.computeDrawingArea();

    }
    public static AttackBtn getInstance(Integer screenWidth, Integer screenHeight){
        if(instance==null){
            instance = new AttackBtn(screenWidth,screenHeight);
        }
        return instance;
    }
    private void computeDrawingArea(){
        int rad = this.screenWidth/32 + 25;
        if(rad<15)rad = 15;
        if(rad>70)rad = 70;
        this.center.x = this.screenWidth - rad -40;
        this.center.y = this.screenHeight - rad - 40;
        this.setRadiusArea(rad);
    }

    public Integer getRadiusArea() {
        return radiusArea;
    }

    private void setRadiusArea(Integer radiusArea) {
        this.radiusArea = radiusArea;
    }

    public Point getCenter() {
        return center;
    }

    private void setCenter(Point center) {
        this.center = center;
    }

    public Paint getDrawPaintAreaHover() {
        return drawPaintAreaHover;
    }

    private void setDrawPaintAreaHover(Paint drawPaintAreaHover) {
        this.drawPaintAreaHover = drawPaintAreaHover;
    }

    public Paint getDrawPaintArea() {
        return drawPaintArea;
    }

    private void setDrawPaintArea(Paint drawPaintArea) {
        this.drawPaintArea = drawPaintArea;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public Integer getTouchMoveID() {
        return touchMoveID;
    }

    public void setTouchMoveID(Integer touchMoveID) {
        this.touchMoveID = touchMoveID;
    }
}
