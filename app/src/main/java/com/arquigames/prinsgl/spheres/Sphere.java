package com.arquigames.prinsgl.spheres;

import com.arquigames.prinsgl.boxes.Box3;
import com.arquigames.prinsgl.maths.matrix.Matrix4;
import com.arquigames.prinsgl.planes.Plane;
import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 26/06/2016.
 */
public class Sphere implements Cloneable{
    private Vector3 center;
    private float radius;
    public Sphere(){
        this.center = new Vector3();
        this.radius = 0f;
    }
    public Sphere(Vector3 center, float radius){
        this.center = center.clone();
        this.radius = radius;
    }
    public Sphere set(Vector3 center, float radius){
        this.center.copy(center);
        this.radius = radius;
        return this;
    }
    public void setCenter(Vector3 center) {
        this.center.copy(center);
    }
    public Vector3 getCenter() {
        return center;
    }
    public void setRadius(float radius) {
        this.radius = radius;
    }
    public float getRadius() {
        return radius;
    }
    public Sphere setFromPoints(Object[] points){
        return this.setFromPoints(points,null);
    }
    public Sphere setFromPoints(Object[] points, Vector3 optionalCenter ){
        Vector3 center = this.center;
        Box3 box = new Box3();
        if ( optionalCenter != null ){
            center.copy( optionalCenter );
        }else{
            box.setFromPoints( points ).center( center );
        }
        float maxRadiusSq = 0f;
        for ( int i = 0, il = points.length; i < il; i ++ ) {
            maxRadiusSq = (float) Math.max( maxRadiusSq, center.distanceToSquared( (Vector3)points[ i ] ) );
        }
        this.radius = (float) Math.sqrt( maxRadiusSq );

        return this;
    }
    @Override
    public Sphere clone(){
        return new Sphere(this.getCenter(),this.getRadius());
    }
    public Sphere copy(Sphere sphere){
        this.center.copy( sphere.getCenter() );
        this.radius = sphere.getRadius();

        return this;
    }
    public boolean empty(){
        return ( this.radius <= 0f );
    }
    public boolean containsPoint(Vector3 point){
        return ( point.distanceToSquared( this.center ) <= ( this.radius * this.radius ) );
    }
    public float distanceToPoint(Vector3 point){
        return ( point.distanceTo( this.center ) - this.radius );
    }
    public boolean distanceToPoint(Sphere sphere){
        float radiusSum = this.radius + sphere.radius;

        return sphere.center.distanceToSquared( this.center ) <= ( radiusSum * radiusSum );
    }
    public boolean intersectsBox(Box3 box){
        return box.intersectsSphere( this );
    }
    public boolean intersectsPlane(Plane plane){
        // We use the following equation to compute the signed distance from
        // the center of the sphere to the plane.
        //
        // distance = q * n - d
        //
        // If this distance is greater than the radius of the sphere,
        // then there is no intersection.

        return Math.abs( this.center.dot( plane.getNormal() ) - plane.getConstant() ) <= this.radius;
    }
    public Vector3 clampPoint(Vector3 point, Vector3 optionalTarget ){
        float deltaLengthSq = this.center.distanceToSquared( point );

        Vector3 result = optionalTarget==null ?new Vector3():optionalTarget;

        result.copy( point );

        if ( deltaLengthSq > ( this.radius * this.radius ) ) {

            result.sub( this.center,null ).normalize();
            result.multiplyScalar( this.radius ).add( this.center,null );

        }

        return result;
    }
    public Box3 getBoundingBox(Box3 optionalTarget){
        Box3 box = optionalTarget==null ?  new Box3(): optionalTarget;

        box.set( this.center, this.center );
        box.expandByScalar( this.radius );

        return box;
    }
    public Sphere applyMatrix4(Matrix4 matrix){
        this.center.applyMatrix4( matrix );
        this.radius = this.radius * matrix.getMaxScaleOnAxis();

        return this;
    }
    public Sphere translate(Vector3 offset){
        this.center.add( offset,null );

        return this;
    }
    public boolean equals(Sphere sphere){
        return sphere.getCenter().equals( this.center ) && ( sphere.getRadius() == this.radius );
    }
}
