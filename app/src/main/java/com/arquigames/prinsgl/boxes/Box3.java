package com.arquigames.prinsgl.boxes;


import com.arquigames.prinsgl.maths.matrix.Matrix4;
import com.arquigames.prinsgl.planes.Plane;
import com.arquigames.prinsgl.spheres.Sphere;
import com.arquigames.prinsgl.maths.vectors.Vector3;

import java.util.LinkedList;

/**
 * Created by usuario on 26/06/2016.
 */
public class Box3 {

    public static float MAX_VALUE = +99999999999f;
    public static float MIN_VALUE = -99999999999f;

    private Vector3 min = new Vector3();
    private Vector3 max = new Vector3();
    public Box3(){
        this.setMin(new Vector3(Box3.MAX_VALUE, Box3.MAX_VALUE, Box3.MAX_VALUE));
        this.setMax(new Vector3(Box3.MIN_VALUE,Box3.MIN_VALUE,Box3.MIN_VALUE));
    }
    public Box3 set(Vector3 min, Vector3 max){
        this.min.copy(min);
        this.max.copy(max);
        return this;
    }
    public void setMin(Vector3 min) {
        this.min.copy(min);
    }
    public Vector3 getMin() {
        return min;
    }
    public void setMax(Vector3 max) {
        this.max.copy(max);
    }
    public Vector3 getMax() {
        return max;
    }
    public Box3 setFromPoints(Object[] points){
        this.makeEmpty();
        for ( int i = 0, il = points.length; i < il; i ++ ) {
            this.expandByPoint( (Vector3)points[ i ] );
        }
        return this;
    }
    public Box3 setFromCenterAndSize(Vector3 center, Vector3 size){
        Vector3 v1 = new Vector3();
        Vector3 halfSize = v1.copy( size ).multiplyScalar( 0.5f );

        this.min.copy( center ).sub( halfSize,null );
        this.max.copy( center ).add( halfSize,null );

        return this;
    }
    public Box3 clone(){
        Box3 temp = new Box3();
        temp.copy(this);
        return temp;
    }
    public Box3 copy(Box3 box){
        this.min.copy(box.getMin());
        this.max.copy(box.getMax());
        return this;
    }
    public Box3 makeEmpty(){
        float max = +999999999;
        float min = -999999999;

        this.min.set(max,max,max);
        this.max.set(min,min,min);

        return this;
    }
    public boolean isEmpty(){
        return ( this.max.getX() < this.min.getX() ) || ( this.max.getY() < this.min.getY() ) || ( this.max.getZ() < this.min.getZ() );
    }
    public Vector3 center(){
        return this.center(null);
    }
    public Vector3 center(Vector3 optionalTarget){
        Vector3 result = optionalTarget==null ? new Vector3(): optionalTarget;
        return result.addVectors( this.min, this.max ).multiplyScalar( 0.5f );
    }
    public Vector3 size(Vector3 optionalTarget){
        Vector3 result = optionalTarget==null ? new Vector3(): optionalTarget;
        return result.subVectors( this.max, this.min );
    }
    public Box3 expandByPoint(Vector3 point){
        this.min.min( point );
        this.max.max( point );
        return this;
    }
    public Box3 expandByVector(Vector3 vector){
        this.min.sub( vector,null );
        this.max.add( vector,null );
        return this;
    }
    public Box3 expandByScalar(float scalar){
        this.min.addScalar( - scalar );
        this.max.addScalar( scalar );

        return this;
    }
    public boolean containsPoint(Vector3 point){
        if (
                point.getX() < this.min.getX() || point.getX() > this.max.getX() ||
                        point.getY() < this.min.getY() || point.getY() > this.max.getY() ||
                        point.getZ() < this.min.getZ() || point.getZ() > this.max.getZ()
                ){
            return false;
        }
        return true;
    }
    public boolean containsBox(Box3 box){
        if (
                ( this.min.getX() <= box.min.getX() ) && ( box.max.getX() <= this.max.getX() ) &&
                        ( this.min.getY() <= box.min.getY() ) && ( box.max.getY() <= this.max.getY() ) &&
                        ( this.min.getZ() <= box.min.getZ() ) && ( box.max.getZ() <= this.max.getZ() )
                ){
            return true;
        }
        return false;
    }
    public Vector3 getParamenter(Vector3 point, Vector3 optionalTarget ){
        // This can potentially have a divide by zero if the box
        // has a size dimension of 0.

        Vector3 result = optionalTarget==null ? new Vector3(): optionalTarget;

        return result.set(
                ( point.getX() - this.min.getX() ) / ( this.max.getX() - this.min.getX() ),
                ( point.getY() - this.min.getY() ) / ( this.max.getY() - this.min.getY() ),
                ( point.getZ() - this.min.getZ() ) / ( this.max.getZ() - this.min.getZ() )
        );
    }
    public boolean intersectsSphere(Sphere sphere){
        Vector3 closestPoint = new Vector3();

        // Find the point on the AABB closest to the sphere center.
        this.clampPoint( sphere.getCenter(), closestPoint );

        // If that point is inside the sphere, the AABB and sphere intersect.
        return closestPoint.distanceToSquared( sphere.getCenter() ) <= sphere.getRadius()*sphere.getRadius();
    }
    public boolean intersectsPlane(Plane plane){
        float min, max;

        if ( plane.getNormal().getX() > 0 ) {

            min = plane.getNormal().getX() * this.min.getX();
            max = plane.getNormal().getX() * this.max.getX();

        } else {

            min = plane.getNormal().getX() * this.max.getX();
            max = plane.getNormal().getX() * this.min.getX();

        }

        if ( plane.getNormal().getY() > 0 ) {

            min += plane.getNormal().getY() * this.min.getY();
            max += plane.getNormal().getY() * this.max.getY();

        } else {

            min += plane.getNormal().getY() * this.max.getY();
            max += plane.getNormal().getY() * this.min.getY();

        }

        if ( plane.getNormal().getZ() > 0 ) {

            min += plane.getNormal().getZ() * this.min.getZ();
            max += plane.getNormal().getZ() * this.max.getZ();

        } else {

            min += plane.getNormal().getZ() * this.max.getZ();
            max += plane.getNormal().getZ() * this.min.getZ();

        }

        return ( min <= plane.getConstant() && max >= plane.getConstant() );
    }
    public Vector3 clampPoint(Vector3 point, Vector3 optionalTarget ){
        Vector3 result = optionalTarget==null ? new Vector3() : optionalTarget;
        return result.copy( point ).clamp( this.min, this.max );
    }
    public float distanceToPoint(Vector3 point){
        Vector3 v1 = new Vector3();
        Vector3 clampedPoint = v1.copy( point ).clamp( this.min, this.max );
        return clampedPoint.sub( point,null ).length();
    }
    public Sphere getBoundingSphere(Sphere optionalTarget){
        Vector3 v1 = new Vector3();
        Sphere result = optionalTarget==null ? new Sphere() : optionalTarget;

        result.setCenter(this.center(null));
        result.setRadius(this.size( v1 ).length() * 0.5f);

        return result;
    }
    public Box3 intersect(Box3 box){
        this.min.max( box.getMin() );
        this.max.min( box.getMax() );

        return this;
    }
    public Box3 union(Box3 box){
        this.min.min( box.getMin() );
        this.max.max( box.getMax() );

        return this;
    }
    public Box3 applyMatrix(Matrix4 matrix){

        Vector3[] points = new Vector3[]{
                new Vector3(),
                new Vector3(),
                new Vector3(),
                new Vector3(),
                new Vector3(),
                new Vector3(),
                new Vector3(),
                new Vector3()
        };

        // NOTE: I am using a binary pattern to specify all 2^3 combinations below
        points[ 0 ].set( this.min.getX(), this.min.getY(), this.min.getZ() ).applyMatrix4( matrix ); // 000
        points[ 1 ].set( this.min.getX(), this.min.getY(), this.max.getZ() ).applyMatrix4( matrix ); // 001
        points[ 2 ].set( this.min.getX(), this.max.getY(), this.min.getZ() ).applyMatrix4( matrix ); // 010
        points[ 3 ].set( this.min.getX(), this.max.getY(), this.max.getZ() ).applyMatrix4( matrix ); // 011
        points[ 4 ].set( this.max.getX(), this.min.getY(), this.min.getZ() ).applyMatrix4( matrix ); // 100
        points[ 5 ].set( this.max.getX(), this.min.getY(), this.max.getZ() ).applyMatrix4( matrix ); // 101
        points[ 6 ].set( this.max.getX(), this.max.getY(), this.min.getZ() ).applyMatrix4( matrix ); // 110
        points[ 7 ].set( this.max.getX(), this.max.getY(), this.max.getZ() ).applyMatrix4( matrix );	// 111

        this.makeEmpty();
        this.setFromPoints( points );

        return this;
    }
    public Box3 translate(Vector3 offset){
        this.min.add( offset,null );
        this.max.add( offset,null );

        return this;
    }
    public boolean equals(Box3 box){
        return box.getMin().equals( this.min ) && box.getMax().equals( this.max );
    }

    public void setFromArray(float[] array){
        this.makeEmpty();

        float minX = Box3.MAX_VALUE;
        float minY = Box3.MAX_VALUE;
        float minZ = Box3.MAX_VALUE;

        float maxX = Box3.MIN_VALUE;
        float maxY = Box3.MIN_VALUE;
        float maxZ = Box3.MIN_VALUE;

        for ( int i = 0, il = array.length; i < il; i += 3 ) {

            float x = array[ i ];
            float y = array[ i + 1 ];
            float z = array[ i + 2 ];

            if ( x < minX ) minX = x;
            if ( y < minY ) minY = y;
            if ( z < minZ ) minZ = z;

            if ( x > maxX ) maxX = x;
            if ( y > maxY ) maxY = y;
            if ( z > maxZ ) maxZ = z;

        }

        this.min.set( minX, minY, minZ );
        this.max.set( maxX, maxY, maxZ );
    }
    public void setFromArray(LinkedList<Vector3> vertices) {
        this.makeEmpty();

        float minX = Box3.MAX_VALUE;
        float minY = Box3.MAX_VALUE;
        float minZ = Box3.MAX_VALUE;

        float maxX = Box3.MIN_VALUE;
        float maxY = Box3.MIN_VALUE;
        float maxZ = Box3.MIN_VALUE;

        Object[] _vertices = vertices.toArray();
        float[] _array = new float[_vertices.length*3];
        int count = 0;
        for(int _i=0,length = _vertices.length;_i<length;_i++){
            _array[count++] = ((Vector3)_vertices[_i]).getX();
            _array[count++] = ((Vector3)_vertices[_i]).getY();
            _array[count++] = ((Vector3)_vertices[_i]).getZ();
        }

        this.setFromArray(_array);

    }
}
