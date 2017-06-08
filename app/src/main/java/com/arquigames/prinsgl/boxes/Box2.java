package com.arquigames.prinsgl.boxes;


import com.arquigames.prinsgl.maths.vectors.Vector2;

/**
 * Created by usuario on 26/06/2016.
 */
public class Box2 implements Cloneable{
    private Vector2 min;
    private Vector2 max;
    public Box2(){
        this.setMin(new Vector2(Float.MAX_VALUE, Float.MAX_VALUE));
        this.setMax(new Vector2(-Float.MAX_VALUE,-Float.MAX_VALUE));
    }
    public Box2(Vector2 min, Vector2 max){
        this.setMin(min);
        this.setMax(max);
    }
    public void setMin(Vector2 min) {
        this.min.copy(min);
    }
    public Vector2 getMin() {
        return min;
    }
    public void setMax(Vector2 max) {
        this.max.copy(max);
    }
    public Vector2 getMax() {
        return max;
    }
    public Box2 set(Vector2 min, Vector2 max){
        this.getMin().copy(min);
        this.getMax().copy(max);
        return this;
    }
    public Box2 setFromPoints(Vector2[] points){
        this.makeEmpty();

        for ( int i = 0, il = points.length; i < il; i ++ ) {

            this.expandByPoint( points[ i ] );

        }

        return this;
    }
    public Box2 setFromCenterAndSize(Vector2 center, Vector2 size){
        Vector2 v1 = new Vector2();
        Vector2 halfSize = v1.copy( size ).multiplyScalar( 0.5f );
        this.getMin().copy( center ).sub( halfSize);
        this.getMax().copy( center ).add( halfSize);

        return this;
    }
    @Override
    public Box2 clone(){
        Box2 b = new Box2();
        b.copy(this);
        return b;
    }
    public Box2 copy(Box2 box){
        this.getMin().copy( box.getMin() );
        this.getMax().copy( box.getMax() );

        return this;
    }
    public Box2 makeEmpty(){

        this.getMin().setX(Float.MAX_VALUE);
        this.getMin().setY(Float.MAX_VALUE);

        this.getMax().setX(-Float.MAX_VALUE);
        this.getMax().setY(-Float.MAX_VALUE);

        return this;
    }
    public boolean isEmpty(){
        return ( this.max.getX() < this.min.getX() ) || ( this.max.getY() < this.min.getY() );
    }
    public Vector2 center(Vector2 optionalTarget){
        if(optionalTarget==null){
            optionalTarget = new Vector2();
        }
        return optionalTarget.addVectors( this.min, this.max ).multiplyScalar( 0.5f );
    }
    public Box2 expandByPoint(Vector2 point){
        this.getMin().min( point );
        this.getMax().max( point );

        return this;
    }
    public Box2 expandByVector(Vector2 vector){
        this.min.sub( vector);
        this.max.add( vector);

        return this;
    }
    public Box2 expandByScalar(float scalar){
        this.min.addScalar( - scalar );
        this.max.addScalar( scalar );

        return this;
    }
    public boolean containsPoint(Vector2 point){
        if (
                point.getX() < this.min.getX() || point.getX() > this.max.getX() ||
                        point.getY() < this.min.getY() || point.getY() > this.max.getY()
                ){
            return false;
        }
        return true;
    }
    public boolean containsBox(Box2 box){
        if (
                ( this.min.getX() <= box.min.getX() ) && ( box.max.getX() <= this.max.getX() ) &&
                        ( this.min.getY() <= box.min.getY() ) && ( box.max.getY() <= this.max.getY() )
                ){
            return true;
        }
        return false;
    }
    public Vector2 getParameter(Vector2 point, Vector2 optionalTarget){
        Vector2 result = optionalTarget==null ? new Vector2(): optionalTarget;

        return result.set(
                ( point.getX() - this.min.getX() ) / ( this.max.getX() - this.min.getX() ),
                ( point.getY() - this.min.getY() ) / ( this.max.getY() - this.min.getY() )
        );

    }
    public boolean intersectsBox(Box2 box){
        if (
                box.getMax().getX() < this.getMin().getX() || box.getMin().getX() > this.getMax().getX() ||
                        box.getMax().getY() < this.getMin().getY() || box.getMin().getY() > this.getMax().getY()
                ) {

            return false;

        }

        return true;
    }
    public Vector2 clampPoint(Vector2 point, Vector2 optionalTarget){
        Vector2 result = optionalTarget==null ? new Vector2():optionalTarget;
        return result.copy( point ).clamp( this.min, this.max );
    }
    public float distanceToPoint(Vector2 point){
        Vector2 v1 = new Vector2();
        Vector2 clampedPoint = v1.copy( point ).clamp( this.min, this.max );
        return clampedPoint.sub( point).length();
    }
    public Box2 intersect(Box2 box){
        this.min.max( box.getMin() );
        this.max.min( box.getMax() );

        return this;
    }
    public Box2 union(Box2 box){
        this.min.min( box.getMin() );
        this.max.max( box.getMax() );

        return this;
    }
    public Box2 translate(Vector2 offset){
        this.min.add( offset);
        this.max.add( offset);

        return this;
    }
    public boolean equals(Box2 box){
        return box.getMin().equals( this.min ) && box.getMax().equals( this.max );
    }
}
