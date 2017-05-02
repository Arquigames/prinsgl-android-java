package com.arquigames.prinsgl.lines;


import com.arquigames.prinsgl.maths.MathUtils;
import com.arquigames.prinsgl.maths.matrix.Matrix4;
import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 26/06/2016.
 */
public class Line3 {
    private Vector3 start = new Vector3();
    private Vector3 end = new Vector3();
    public Line3(){

    }
    public Line3(Vector3 start, Vector3 end){
        this.set(start,end);
    }
    public void setStart(Vector3 start){
        this.start.copy(start);
    }
    public void setEnd(Vector3 end){
        this.end.copy(end);
    }
    public Vector3 getStart(){
        return this.start;
    }
    public Vector3 getEnd(){
        return this.end;
    }
    public Line3 set(Vector3 start, Vector3 end){
        this.start.copy(start);
        this.end.copy(end);
        return this;
    }
    public Line3 clone(){
        return new Line3(this.start,this.end);
    }
    public Line3 copy(Line3 line){
        this.start.copy(line.getStart());
        return this;
    }
    public Vector3 center(Vector3 vec){
        if(vec==null){
            vec = new Vector3();
        }
        return vec.addVectors(this.start,this.end).multiplyScalar(0.5f);
    }
    public Vector3 delta(Vector3 vec){
        if(vec==null){
            vec = new Vector3();
        }
        return vec.subVectors(this.start,this.end);
    }
    public float distanceSq(){
        return this.start.distanceToSquared( this.end );
    }
    public float distance(){
        return this.start.distanceTo( this.end );
    }
    public Vector3 at(float t, Vector3 optionalTarget){
        if(optionalTarget==null){
            optionalTarget = new Vector3();
        }
        return this.delta( optionalTarget ).multiplyScalar( t ).add( this.start,null );
    }
    public float closestPointToPointParameter(Vector3 point, boolean clampToLine){

        Vector3 startP = new Vector3();
        Vector3 startEnd = new Vector3();

        startP.subVectors( point, this.start );
        startEnd.subVectors( this.end, this.start );

        float startEnd2 = startEnd.dot( startEnd );
        float startEnd_startP = startEnd.dot( startP );

        float t = startEnd_startP / startEnd2;

        if ( clampToLine ) {

            t = MathUtils.clamp( t, 0, 1 );

        }

        return t;
    }
    public Vector3 closestPointToPoint(Vector3 point, boolean clampToLine, Vector3 optionalTarget ){
        float t = this.closestPointToPointParameter( point, clampToLine );
        if(optionalTarget==null){
            optionalTarget = new Vector3();
        }
        return this.delta( optionalTarget ).multiplyScalar( t ).add( this.start,null );
    }
    public Line3 applyMatrix4(Matrix4 matrix){
        this.start.applyMatrix4( matrix );
        this.end.applyMatrix4( matrix );

        return this;
    }
    public boolean equals(Line3 line){
        return line.start.equals( this.start ) && line.end.equals( this.end );
    }
}
