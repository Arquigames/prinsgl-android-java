package com.arquigames.test_app.application.comp.touches;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.arquigames.test_app.application.comp.touches.buttons.AttackBtn;
import com.arquigames.test_app.application.comp.touches.buttons.MoveBtn;
import com.arquigames.test_app.application.comp.touches.buttons.PauseBtn;
import com.arquigames.test_app.application.utils.GameScreen;
import com.arquigames.prinsgl.maths.vectors.Vector2;
import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 29/09/2016.
 */

public class TouchControlView extends View {
    /*
    // setup initial color
    private final int paintColor = Color.BLACK;
    // defines paint and canvas
    private Paint drawPaint;

    // Store circles to draw each time the user touches down
    private List<Point> circlePoints;
    */

    private GameScreen screen = null;
    private MoveBtn moveButton = null;
    private AttackBtn attackButton = null;
    private PauseBtn pauseButton = null;
    private Activity activity;

    private Vector3 directionMove;

    public TouchControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.directionMove = new Vector3();
    }
    public boolean isPaused(){
        return this.pauseButton!=null && this.pauseButton.isPaused();
    }
    public boolean canAttack(){
        if(this.attackButton!=null && this.attackButton.isHover()){
            return true;
        }
        return false;
    }
    public boolean canMove(){
        if(this.moveButton!=null && this.moveButton.getTouchMoveID()!=null){
            return true;
        }
        return false;
    }
    public Vector3 getVectorMove(){
        if(this.canMove()){
            this.directionMove.setX(this.moveButton.getCenter2().x - this.moveButton.getCenter().x);
            this.directionMove.setY(this.moveButton.getCenter2().y - this.moveButton.getCenter().y);
            return this.directionMove;
        }
        return null;
    }
    public void configure(Activity activity){
        this.activity = activity;
        this.screen         = GameScreen.getInstance(null);
        this.moveButton     = MoveBtn.getInstance(this.screen.getScreenWidth(),this.screen.getScreenHeight());
        this.attackButton   = AttackBtn.getInstance(this.screen.getScreenWidth(),this.screen.getScreenHeight());
        this.pauseButton    = PauseBtn.getInstance(this.screen.getScreenWidth(),this.screen.getScreenHeight());
    }

    /*
    // Setup paint with color and stroke styles
    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.FILL); // change to fill
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }
    */
    // Draw each circle onto the view
    @Override
    protected void onDraw(Canvas canvas) {
        /*
        for (Point p : circlePoints) {
            canvas.drawCircle(p.x, p.y, 5, drawPaint);
        }
        */
        if(this.moveButton==null)return;
        if(this.attackButton==null)return;
        if(this.pauseButton==null)return;
        if(this.moveButton.getCenter().x>0 && this.moveButton.getCenter().y>0){
            if(this.moveButton.getTouchMoveID()!=null){
                canvas.drawCircle(
                        this.moveButton.getCenter().x,
                        this.moveButton.getCenter().y,
                        this.moveButton.getRadiusArea(),
                        this.moveButton.getDrawPaintArea()
                );
                canvas.drawCircle(
                        this.moveButton.getCenter2().x,
                        this.moveButton.getCenter2().y,
                        this.moveButton.getRadiusArea(),
                        this.moveButton.getDrawPaintArea()
                );
                canvas.drawCircle(
                        this.moveButton.getCenter().x,
                        this.moveButton.getCenter().y,
                        this.moveButton.getMaxDistance(),
                        this.moveButton.getDrawPaintLimit()
                );
            }
        }
        if(this.attackButton.getCenter().x>0 && this.attackButton.getCenter().y>0){
            if(!this.attackButton.isHover()){
                canvas.drawCircle(
                        this.attackButton.getCenter().x,
                        this.attackButton.getCenter().y,
                        this.attackButton.getRadiusArea(),
                        this.attackButton.getDrawPaintArea()
                );
            }else{
                canvas.drawCircle(
                        this.attackButton.getCenter().x,
                        this.attackButton.getCenter().y,
                        this.attackButton.getRadiusArea(),
                        this.attackButton.getDrawPaintAreaHover()
                );
            }
        }
        if(this.pauseButton.getCenter().x>0 && this.pauseButton.getCenter().y>0){
            if(!this.pauseButton.isPaused()){
                canvas.drawCircle(
                        this.pauseButton.getCenter().x,
                        this.pauseButton.getCenter().y,
                        this.pauseButton.getRadiusArea(),
                        this.pauseButton.getDrawPaintArea()
                );
            }else{
                canvas.drawCircle(
                        this.pauseButton.getCenter().x,
                        this.pauseButton.getCenter().y,
                        this.pauseButton.getRadiusArea(),
                        this.pauseButton.getDrawPaintAreaPaused()
                );
            }
        }
    }
    // Append new circle each time user presses on screen
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(this.moveButton==null)return true;
        if(this.attackButton==null)return true;
        if(this.pauseButton==null)return true;

        /*
        if(this.activity!=null){
            ImageView imageView = (ImageView)this.activity.findViewById(R.id.portada_img);
            if(imageView!=null && imageView.getVisibility()==View.VISIBLE){
                imageView.setVisibility(View.INVISIBLE);
                return true;
            }
        }
        */

        /**/
        int index;
        int totalPointer    = -1;
        float touchX = -1;
        float touchY = -1;

        int actionIndex     = MotionEventCompat.getActionIndex(event);
        int pointerID       = MotionEventCompat.getPointerId(event,actionIndex);


        int action  = 0;
        action = MotionEventCompat.getActionMasked(event);

        touchX = MotionEventCompat.getX(event,actionIndex);
        touchY = MotionEventCompat.getY(event,actionIndex);

        Vector2 vector2 = new Vector2(
                this.attackButton.getCenter().x - (int)touchX,
                this.attackButton.getCenter().y - (int)touchY
        );

        Vector2 vectorPaused = new Vector2(
                this.pauseButton.getCenter().x - (int)touchX,
                this.pauseButton.getCenter().y - (int)touchY
        );

        switch (action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                //MainActivity.audioHandler.play("try_again");
                if(touchX<this.screen.getScreenWidth()/2){
                    if(this.moveButton.getTouchMoveID()==null){
                        this.moveButton.setTouchMoveID(pointerID);
                        this.moveButton.getCenter().x = Math.round(touchX);
                        this.moveButton.getCenter().y = Math.round(touchY);

                        this.moveButton.getCenter2().x = this.moveButton.getCenter().x;
                        this.moveButton.getCenter2().y = this.moveButton.getCenter().y;
                    }
                }else{
                    if(vectorPaused.length()<this.pauseButton.getRadiusArea()){
                        this.pauseButton.setPaused(!this.pauseButton.isPaused());
                    }
                }
                if(vector2.length()<this.attackButton.getRadiusArea()){
                    if(this.attackButton.getTouchMoveID()==null){
                        this.attackButton.setHover(true);
                        this.attackButton.setTouchMoveID(pointerID);
                    }
                }else{
                    if(this.attackButton.getTouchMoveID()!=null && this.attackButton.getTouchMoveID()==pointerID){
                        this.attackButton.setHover(false);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //-----------
                totalPointer = MotionEventCompat.getPointerCount(event);
                for(index= 0 ; index<totalPointer;index++){
                    pointerID = MotionEventCompat.getPointerId(event,index);
                    touchX = MotionEventCompat.getX(event,index);
                    touchY = MotionEventCompat.getY(event,index);
                    if(touchX<this.screen.getScreenWidth()/2){
                        if(
                                this.moveButton.getTouchMoveID()!=null &&
                                        this.moveButton.getTouchMoveID()==pointerID
                                ){
                            this.moveButton.getCenter2().x = Math.round(touchX);
                            this.moveButton.getCenter2().y = Math.round(touchY);
                            //Log.e("MoveButton",this.moveButton.getCenter2().toString());
                        }
                    }else{
                        if(
                                this.moveButton.getTouchMoveID()!=null &&
                                        this.moveButton.getTouchMoveID()==pointerID
                                ){
                            this.moveButton.setTouchMoveID(null);
                        }
                    }
                    vector2 = new Vector2(
                            this.attackButton.getCenter().x - (int)touchX,
                            this.attackButton.getCenter().y - (int)touchY
                    );
                    if(this.attackButton.getTouchMoveID()!=null && this.attackButton.getTouchMoveID()==pointerID){
                        if(vector2.length()<this.attackButton.getRadiusArea()){
                            this.attackButton.setHover(true);

                        }else{
                            this.attackButton.setHover(false);
                        }
                    }else{

                    }

                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(
                        this.moveButton.getTouchMoveID()!=null &&
                                this.moveButton.getTouchMoveID()==pointerID
                        ){
                    this.moveButton.setTouchMoveID(null);
                }
                if(this.attackButton.getTouchMoveID()!=null && this.attackButton.getTouchMoveID()==pointerID){
                    this.attackButton.setHover(false);
                    this.attackButton.setTouchMoveID(null);
                }
                break;
            case MotionEvent.ACTION_OUTSIDE:
                if(
                        this.moveButton.getTouchMoveID()!=null &&
                                this.moveButton.getTouchMoveID()==pointerID
                        ){
                    this.moveButton.setTouchMoveID(null);
                }
                if(this.attackButton.getTouchMoveID()!=null && this.attackButton.getTouchMoveID()==pointerID){
                    this.attackButton.setHover(false);
                    this.attackButton.setTouchMoveID(null);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if(
                        this.moveButton.getTouchMoveID()!=null &&
                                this.moveButton.getTouchMoveID()==pointerID
                        ){
                    this.moveButton.setTouchMoveID(null);
                }
                if(this.attackButton.getTouchMoveID()!=null && this.attackButton.getTouchMoveID()==pointerID){
                    this.attackButton.setHover(false);
                    this.attackButton.setTouchMoveID(null);
                }
                break;
            default:
        }
        /*
        circlePoints.add(new Point(Math.round(touchX), Math.round(touchY)));
        */
        // indicate view should be redrawn
        //this.moveButton.getCenter().set(Math.round(touchX), Math.round(touchY));
        this.moveButton.updateProperties();
        this.postInvalidate();
        return true;
    }
}