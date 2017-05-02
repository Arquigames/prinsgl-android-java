package com.arquigames.prinsgl.maths.vectors;

import com.arquigames.prinsgl.maths.Quaternion;
import com.arquigames.prinsgl.maths.matrix.Matrix4;

/**
 * Created by usuario on 26/06/2016.
 */
public class Vector4 {
    private float x = 0;
    private float y = 0;
    private float z = 0;
    private float w = 1;
    public Vector4(){

    }
    public Vector4(float constant){
        this.setX(constant);
        this.setY(constant);
        this.setZ(constant);
        this.setW(constant);
    }
    public Vector4(float constant,float w){
        this.setX(constant);
        this.setY(constant);
        this.setZ(constant);
        this.setW(w);
    }
    public Vector4(float x, float y, float z, float w){
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW(w);
    }
    public Vector4 set(float x, float y, float z, float w){
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW(w);
        return this;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getX() {
        return x;
    }
    public void setY(float y) {
        this.y = y;
    }
    public float getY() {
        return y;
    }
    public void setZ(float z) {
        this.z = z;
    }
    public float getZ() {
        return z;
    }
    public void setW(float w) {
        this.w = w;
    }
    public float getW() {
        return w;
    }
    public Vector4 clone(){
        return new Vector4(this.x,this.y,this.z,this.w);
    }
    public Vector4 copy(Vector4 v){
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
        this.w = v.getW();
        return this;
    }
    public Vector4 copyX(Vector4 v){
        this.x = v.getX();
        return this;
    }
    public Vector4 copyY(Vector4 v){
        this.y = v.getY();
        return this;
    }
    public Vector4 copyZ(Vector4 v){
        this.z = v.getZ();
        return this;
    }
    public Vector4 copyW(Vector4 v){
        this.w = v.getW();
        return this;
    }
    public Vector4 add(Vector4 v, Vector4 w ) {
        if ( w != null ) {
            return this.addVectors( v, w );
        }
        this.x += v.getX();
        this.y += v.getY();
        this.z += v.getZ();
        this.w += v.getW();
        return this;
    }
    public Vector4 add(Vector4 v) {
        return this.add(v,null);
    }
    public Vector4 addVectors(Vector4 v, Vector4 w ){
        this.x = v.getX() + w.getX();
        this.y = v.getY() + w.getY();
        this.z = v.getZ() + w.getZ();
        this.w = v.getW() + w.getW();
        return this;
    }
    public Vector4 addScalar(float s){
        this.x +=s;
        this.y +=s;
        this.z +=s;
        this.w +=s;
        return this;
    }
    public Vector4 addScaledVector(Vector4 v, float s ) {

        this.x += v.getX() * s;
        this.y += v.getY() * s;
        this.z += v.getZ() * s;
        this.w += v.getW() * s;

        return this;

    }
    public Vector4 sub(Vector4 v, Vector4 w){
        if(w!=null){
            return this.subVectors(v,w);
        }
        this.x -=v.getX();
        this.y -=v.getY();
        this.z -=v.getZ();
        this.w -=v.getW();
        return this;
    }
    public Vector4 sub(Vector4 v){
        return this.sub(v,null);
    }
    public Vector4 subVectors(Vector4 v, Vector4 w ) {

        this.x = v.getX() -w.getX();
        this.y = v.getY() -w.getY();
        this.z = v.getZ() -w.getZ();
        this.w = v.getW() -w.getW();

        return this;
    }
    public Vector4 subScalar(float s) {

        this.x -= s;
        this.y -= s;
        this.z -= s;
        this.w -= s;

        return this;
    }
    public Vector4 multiplyScalar(float s) {
        if(Float.isInfinite(s)){
            this.x =0;
            this.y =0;
            this.z =0;
            this.w =0;
        }else{
            this.x *= s;
            this.y *= s;
            this.z *= s;
            this.w *= s;
        }
        return this;
    }
    public Vector4 applyMatrix4(Matrix4 m){
        float x = this.x, y = this.y, z = this.z, w = this.w;

        float[] e = m.getElements();

        this.x = e[ 0 ] * x + e[ 4 ] * y + e[  8 ] * z + e[ 12 ]  * w;
        this.y = e[ 1 ] * x + e[ 5 ] * y + e[  9 ] * z + e[ 13 ]  * w;
        this.z = e[ 2 ] * x + e[ 6 ] * y + e[ 10 ] * z + e[ 14 ]  * w;
        this.w = e[ 3 ] * x + e[ 7 ] * y + e[ 11 ] * z + e[ 15 ]  * w;

        return this;
    }
    public Vector4 divideScalar(float scalar){
        return this.multiplyScalar((1 / scalar) );
    }
    public Vector4 setAxisAngleFromQuaternion(Quaternion q){
        // http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle/index.htm

        // q is assumed to be normalized

        this.w = (float)(2 * Math.acos( q.getW() ));

        float s = (float) Math.sqrt( 1 - q.getW() * q.getW() );

        if ( s < 0.0001 ) {

            this.x = 1;
            this.y = 0;
            this.z = 0;

        } else {

            this.x = q.getX() / s;
            this.y = q.getY() / s;
            this.z = q.getZ() / s;

        }

        return this;
    }
    public Vector4 setAxisAngleFromRotationMatrix(Matrix4 m){
        // http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToAngle/index.htm

        // assumes the upper 3x3 of m is a pure rotation matrix (i.e, unscaled)

        float angle, x, y, z,		// variables for result
                epsilon = 0.01f,		// margin to allow for rounding errors
                epsilon2 = 0.1f;		// margin to distinguish between 0 and 180 degrees

        float[]	te = m.getElements();

        float
                m11 = te[ 0 ], m12 = te[ 4 ], m13 = te[ 8 ],
                m21 = te[ 1 ], m22 = te[ 5 ], m23 = te[ 9 ],
                m31 = te[ 2 ], m32 = te[ 6 ], m33 = te[ 10 ];

        if ( ( Math.abs( m12 - m21 ) < epsilon )
                && ( Math.abs( m13 - m31 ) < epsilon )
                && ( Math.abs( m23 - m32 ) < epsilon ) ) {

            // singularity found
            // first check for identity matrix which must have +1 for all terms
            // in leading diagonal and zero in other terms

            if ( ( Math.abs( m12 + m21 ) < epsilon2 )
                    && ( Math.abs( m13 + m31 ) < epsilon2 )
                    && ( Math.abs( m23 + m32 ) < epsilon2 )
                    && ( Math.abs( m11 + m22 + m33 - 3 ) < epsilon2 ) ) {

                // this singularity is identity matrix so angle = 0

                this.set( 1.f, 0.f, 0.f, 0.f );

                return this; // zero angle, arbitrary axis

            }

            // otherwise this singularity is angle = 180

            angle = (float) Math.PI;

            float xx = ( m11 + 1 ) / 2;
            float yy = ( m22 + 1 ) / 2;
            float zz = ( m33 + 1 ) / 2;
            float xy = ( m12 + m21 ) / 4;
            float xz = ( m13 + m31 ) / 4;
            float yz = ( m23 + m32 ) / 4;

            if ( ( xx > yy ) && ( xx > zz ) ) {

                // m11 is the largest diagonal term

                if ( xx < epsilon ) {

                    x = 0;
                    y = 0.707106781f;
                    z = 0.707106781f;

                } else {

                    x = (float) Math.sqrt( xx );
                    y = xy / x;
                    z = xz / x;

                }

            } else if ( yy > zz ) {

                // m22 is the largest diagonal term

                if ( yy < epsilon ) {

                    x = 0.707106781f;
                    y = 0.f;
                    z = 0.707106781f;

                } else {

                    y = (float) Math.sqrt( yy );
                    x = xy / y;
                    z = yz / y;

                }

            } else {

                // m33 is the largest diagonal term so base result on this

                if ( zz < epsilon ) {

                    x = 0.707106781f;
                    y = 0.707106781f;
                    z = 0;

                } else {

                    z = (float) Math.sqrt( zz );
                    x = xz / z;
                    y = yz / z;

                }

            }
            this.set( x, y, z, angle );
            return this; // return 180 deg rotation
        }
        // as we have reached here there are no singularities so we can handle normally

        float s = (float) Math.sqrt( ( m32 - m23 ) * ( m32 - m23 )
                + ( m13 - m31 ) * ( m13 - m31 )
                + ( m21 - m12 ) * ( m21 - m12 ) ); // used to normalize

        if ( Math.abs( s ) < 0.001 ) s = 1;

        // prevent divide by zero, should not happen if matrix is orthogonal and should be
        // caught by singularity test above, but I've left it in just in case

        this.x = ( m32 - m23 ) / s;
        this.y = ( m13 - m31 ) / s;
        this.z = ( m21 - m12 ) / s;
        this.w = (float) Math.acos( ( m11 + m22 + m33 - 1 ) / 2 );

        return this;
    }
    public Vector4 min(Vector4 v){
        this.x = Math.min(this.x,v.getX());
        this.y = Math.min(this.y,v.getY());
        this.z = Math.min(this.z,v.getZ());
        this.w = Math.min(this.w,v.getW());
        return this;
    }
    public Vector4 max(Vector4 v){
        this.x = Math.max(this.x,v.getX());
        this.y = Math.max(this.y,v.getY());
        this.z = Math.max(this.z,v.getZ());
        this.w = Math.max(this.w,v.getW());
        return this;
    }
    public Vector4 clamp(Vector4 min, Vector4 max){

        this.x = Math.max( min.getX(), Math.min( max.getX(), this.x ) );
        this.y = Math.max( min.getY(), Math.min( max.getY(), this.y ) );
        this.z = Math.max( min.getZ(), Math.min( max.getZ(), this.z ) );
        this.w = Math.max( min.getW(), Math.min( max.getW(), this.z ) );

        return this;
    }
    public Vector4 clampScalar(float minVal, float maxVal){
        Vector4 min = new Vector4();
        Vector4 max = new Vector4();

        min.set( minVal, minVal, minVal, minVal );
        max.set( maxVal, maxVal, maxVal, maxVal );

        return this.clamp( min, max );
    }
    public Vector4 floor(){
        this.x = (float) Math.floor( this.x );
        this.y = (float) Math.floor( this.y );
        this.z = (float) Math.floor( this.z );
        this.w = (float) Math.floor( this.w );

        return this;
    }
    public Vector4 ceil(){
        this.x = (float) Math.ceil( this.x );
        this.y = (float) Math.ceil( this.y );
        this.z = (float) Math.ceil( this.z );
        this.w = (float) Math.ceil( this.w );

        return this;
    }
    public Vector4 round(){
        this.x = Math.round( this.x );
        this.y = Math.round( this.y );
        this.z = Math.round( this.z );
        this.w = Math.round( this.w );

        return this;
    }
    public Vector4 roundToZero(){
        this.x = ( this.x < 0 ) ? (float) Math.ceil( this.x ) : (float) Math.floor( this.x );
        this.y = ( this.y < 0 ) ? (float) Math.ceil( this.y ) : (float) Math.floor( this.y );
        this.z = ( this.z < 0 ) ? (float) Math.ceil( this.z ) : (float) Math.floor( this.z );
        this.w = ( this.w < 0 ) ? (float) Math.ceil( this.w ) : (float) Math.floor( this.w );

        return this;
    }
    public Vector4 negate(){
        this.x = - this.x;
        this.y = - this.y;
        this.z = - this.z;
        this.w = - this.w;

        return this;
    }
    public Vector4 negateX(){
        this.x = -this.x;
        return this;
    }
    public Vector4 negateY(){
        this.y = -this.y;
        return this;
    }
    public Vector4 negateZ(){
        this.z = -this.z;
        return this;
    }
    public Vector4 negateW(){
        this.w = - this.w;
        return this;
    }
    public float dot( Vector4 v ) {

        return (this.x * v.getX() + this.y * v.getY() + this.z * v.getZ() + this.w * v.getW());

    }
    public float lengthSq(){
        return (this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
    }
    public float length(){
        return (float) Math.sqrt( this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w );
    }
    public Vector4 normalize(){
        return this.divideScalar( this.length() );
    }
    public Vector4 setLength(float length){
        return this.multiplyScalar( length / this.length() );
    }
    public Vector4 lerp(Vector4 v, float alpha){
        this.x += ( v.getX() - this.x ) * alpha;
        this.y += ( v.getY() - this.y ) * alpha;
        this.z += ( v.getZ() - this.z ) * alpha;
        this.w += ( v.getW() - this.w ) * alpha;

        return this;
    }
    public Vector4 lerpVectors(Vector4 v1, Vector4 v2, float alpha){
        this.subVectors( v2, v1 ).multiplyScalar( alpha ).add( v1,null );

        return this;
    }
    public boolean equals(Vector4 v){
        return ( ( v.getX() == this.x ) && ( v.getY() == this.y ) && ( v.getZ() == this.z ) && ( v.getW() == this.w ) );
    }
    public String toString(){
        return "Vector4[x:"+this.x+",y:"+this.y+",z:"+this.z+",w="+this.w+"]";
    }

}
