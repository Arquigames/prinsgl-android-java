package com.arquigames.prinsgl.planes;

import com.arquigames.prinsgl.boxes.Box3;
import com.arquigames.prinsgl.lines.Line3;
import com.arquigames.prinsgl.maths.matrix.Matrix3;
import com.arquigames.prinsgl.maths.matrix.Matrix4;
import com.arquigames.prinsgl.spheres.Sphere;
import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 26/06/2016.
 */
public class Plane {
    private Vector3 normal = new Vector3();
    private float constant = 0;
    public Plane(){

    }
    public Plane(Vector3 normal, float constant){
        this.normal.copy(normal);
        this.constant = constant;
    }
    public Plane set(Vector3 normal, float constant){
        this.normal.copy(normal);
        this.constant = constant;
        return this;
    }
    public void setNormal(Vector3 normal) {
        this.normal.copy(normal);
    }
    public Vector3 getNormal() {
        return normal;
    }
    public void setConstant(float constant) {
        this.constant = constant;
    }
    public float getConstant() {
        return constant;
    }
    public Plane setComponents(float x, float y, float z, float w){
        this.normal.set( x, y, z );
        this.constant = w;

        return this;
    }
    public Plane setFromNormalAndCoplanarPoint(Vector3 normal, Vector3 point){
        this.normal.copy( normal );
        this.constant = - point.dot( this.normal );	// must be this.normal, not normal, as this.normal is normalized

        return this;
    }
    public Plane setFromCoplanarPoints(Vector3 a, Vector3 b, Vector3 c){
        Vector3 v1 = new Vector3();
        Vector3 v2 = new Vector3();
        Vector3 normal = v1.subVectors( c, b ).cross( v2.subVectors( a, b ),null ).normalize();

        // Q: should an error be thrown if normal is zero (e.g. degenerate plane)?

        this.setFromNormalAndCoplanarPoint( normal, a );

        return this;
    }
    public Plane clone(){
        Plane p = new Plane(this.getNormal(),this.constant);
        return p;
    }
    public Plane copy(Plane p){
        this.normal.copy(p.getNormal());
        this.constant = p.getConstant();
        return this;
    }
    public Plane normalize(){
        // Note: will lead to a divide by zero if the plane is invalid.

        float inverseNormalLength = 1.0f / this.normal.length();
        this.normal.multiplyScalar( inverseNormalLength );
        this.constant *= inverseNormalLength;

        return this;
    }
    public Plane negate(){
        this.constant *= - 1;
        this.normal.negate();

        return this;
    }
    public float distanceToPoint(Vector3 point){
        return this.normal.dot( point ) + this.constant;
    }
    public float distanceToSphere(Sphere sphere){
        return this.distanceToPoint( sphere.getCenter() ) - sphere.getRadius();
    }
    public Vector3 projectPoint(Vector3 point, Vector3 optionalTarget ){
        return this.orthoPoint( point, optionalTarget ).sub( point,null ).negate();
    }
    public Vector3 orthoPoint(Vector3 point, Vector3 optionalTarget ){
        float perpendicularMagnitude = this.distanceToPoint( point );

        Vector3 result = optionalTarget==null ?  new Vector3():optionalTarget;
        return result.copy( this.normal ).multiplyScalar( perpendicularMagnitude );
    }
    public Vector3 intersectLine(Line3 line, Vector3 optionalTarget ){
        Vector3 v1 = new Vector3();
        Vector3 result = optionalTarget==null? new Vector3():optionalTarget;
        Vector3 direction = line.delta( v1 );
        float denominator = this.normal.dot( direction );
        if ( denominator == 0 ) {
            // line is coplanar, return origin
            if ( this.distanceToPoint( line.getStart() ) == 0 ) {
                return result.copy( line.getStart() );
            }
            // Unsure if this is the correct method to handle this case.
            return null;
        }
        float t = - ( line.getStart().dot( this.normal ) + this.constant ) / denominator;

        if ( t < 0 || t > 1 ) {
            return null;
        }
        return result.copy( direction ).multiplyScalar( t ).add( line.getStart(),null );
    }
    public boolean intersectsLine(Line3 line){
        // Note: this tests if a line intersects the plane, not whether it (or its end-points) are coplanar with it.

        float startSign = this.distanceToPoint( line.getStart() );
        float endSign = this.distanceToPoint( line.getEnd() );

        return ( startSign < 0 && endSign > 0 ) || ( endSign < 0 && startSign > 0 );
    }
    public boolean intersectsBox(Box3 box){
        return box.intersectsPlane( this );
    }
    public boolean intersectsSphere(Sphere sphere){
        return sphere.intersectsPlane( this );
    }
    public Vector3 coplanarPoint(Vector3 optionalTarget){
        Vector3 result = optionalTarget==null ?  new Vector3():optionalTarget;
        return result.copy( this.normal ).multiplyScalar( - this.constant );
    }
    public Plane applyMatrix4(Matrix4 matrix, Matrix3 optionalNormalMatrix ) throws Exception{

        Vector3 v1 = new Vector3();
        Vector3 v2 = new Vector3();
        Matrix3 m1 = new Matrix3();

        // compute new normal based on theory here:
        // http://www.songho.ca/opengl/gl_normaltransform.html
        Matrix3 normalMatrix = optionalNormalMatrix==null ? m1.getNormalMatrix( matrix ) : optionalNormalMatrix;
        Vector3 newNormal = v1.copy( this.normal ).applyMatrix3( normalMatrix );

        Vector3 newCoplanarPoint = this.coplanarPoint( v2 );
        newCoplanarPoint.applyMatrix4( matrix );

        this.setFromNormalAndCoplanarPoint( newNormal, newCoplanarPoint );

        return this;
    }
    public Plane translate(Vector3 offset){
        this.constant = this.constant - offset.dot( this.normal );

        return this;
    }
    public boolean equals(Plane plane){
        return plane.getNormal().equals( this.normal ) && ( plane.getConstant() == this.constant );

    }
}
