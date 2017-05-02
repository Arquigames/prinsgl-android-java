package com.arquigames.test_app.application.comp.touches.buttons;

import android.graphics.Paint;
import android.graphics.Point;

import com.arquigames.prinsgl.maths.MathUtils;
import com.arquigames.prinsgl.maths.vectors.Vector2;


/**
 * Created by usuario on 26/04/2016.
 */
public class MoveBtn {
    private Integer screenWidth = null;
    private Integer screenHeight = null;
    private Integer radiusLimit = null;
    private Integer radiusArea = null;

    private Point center = null;
    private Point center2 = null;

    private Paint drawPaintLimit    = null;
    private Paint drawPaintArea     = null;

    private Integer touchMoveID = null;

    private int maxDistance = 0;

    private static MoveBtn instance = null;

    private MoveBtn(Integer screenWidth, Integer screenHeight){
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.setCenter(new Point(0,0));
        this.setCenter2(new Point(0,0));

        this.setDrawPaintLimit(new Paint());
        this.getDrawPaintLimit().setARGB(120,255,255,255);
        this.getDrawPaintLimit().setAntiAlias(true);
        this.getDrawPaintLimit().setStrokeWidth(10);
        this.getDrawPaintLimit().setStyle(Paint.Style.STROKE); // change to fill
        this.getDrawPaintLimit().setStrokeJoin(Paint.Join.ROUND);
        this.getDrawPaintLimit().setStrokeCap(Paint.Cap.ROUND);

        this.setDrawPaintArea(new Paint());
        //this.getDrawPaintArea().setColor(Color.WHITE);
        this.getDrawPaintArea().setARGB(120,255,255,255);
        this.getDrawPaintArea().setAntiAlias(true);
        this.getDrawPaintArea().setStrokeWidth(1);
        this.getDrawPaintArea().setStyle(Paint.Style.FILL); // change to fill
        this.getDrawPaintArea().setStrokeJoin(Paint.Join.ROUND);
        this.getDrawPaintArea().setStrokeCap(Paint.Cap.ROUND);

        this.computeDrawingArea();
        this.computeRadiusLimit();

    }
    public static MoveBtn getInstance(Integer screenWidth, Integer screenHeight){
        if(instance==null){
            instance = new MoveBtn(screenWidth,screenHeight);
        }
        return instance;
    }
    private void computeRadiusLimit(){
        int length = this.screenWidth>this.screenHeight ? this.screenHeight/4:this.screenWidth/4;
        if(length<60)length=60;
        if(length>150)length=150;
        this.setRadiusLimit(length);
    }
    private void computeDrawingArea(){
        int rad = this.screenWidth/32;
        if(rad<15)rad = 15;
        if(rad>70)rad = 70;
        this.setRadiusArea(rad);
    }

    public Integer getRadiusLimit() {
        return radiusLimit;
    }

    private void setRadiusLimit(Integer radiusLimit) {
        this.radiusLimit = radiusLimit;
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

    public void setCenter(Point center) {
        this.center = center;
    }
    public void setCenter2(Point center) {
        this.center2 = center;
    }

    public Paint getDrawPaintLimit() {
        return drawPaintLimit;
    }

    public void setDrawPaintLimit(Paint drawPaintLimit) {
        this.drawPaintLimit = drawPaintLimit;
    }

    public Paint getDrawPaintArea() {
        return drawPaintArea;
    }

    public void setDrawPaintArea(Paint drawPaintArea) {
        this.drawPaintArea = drawPaintArea;
    }

    public Integer getTouchMoveID() {
        return touchMoveID;
    }

    public void setTouchMoveID(Integer touchMoveID) {
        this.touchMoveID = touchMoveID;
    }

    public Point getCenter2() {
        return center2;
    }

    public void updateProperties() {
        Vector2 vector2 = new Vector2(this.center2.x - this.center.x, this.center2.y - this.center.y);
        vector2.normalize();
        double distance = MathUtils.distance(this.center,this.center2);
        if(distance>this.radiusLimit){
            distance = this.radiusLimit;
        }
        vector2.multiplyScalar((float)distance);
        this.setMaxDistance((int) distance);
        this.center2.x = this.center.x + Math.round(vector2.getX());
        this.center2.y = this.center.y + Math.round(vector2.getY());
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    private void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }
}
