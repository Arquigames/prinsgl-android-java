package com.arquigames.prinsgl.maths;

import com.arquigames.prinsgl.Object3D;
import com.arquigames.prinsgl.maths.matrix.Matrix4;
import com.arquigames.prinsgl.maths.vectors.Vector3;
import com.arquigames.prinsgl.maths.vectors.Vector4;

/**
 * Created by usuario on 26/06/2016.
 */
public class Quaternion {
    private float x;
    private float y;
    private float z;
    private float w;

    private Object3D container =null;//parent container

    public Quaternion(){
        this.setX(0);
        this.setY(0);
        this.setZ(0);
        this.setW(1);
    }
    public Quaternion(Object3D container){
        this();
        this.setContainer(container);
    }
    public Quaternion(float x, float y, float z, float w){
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW( w != 0 ? w:1);
    }
    public void setX(float x) {
        this.x = x;
        this.onChangeCallback();
    }
    public float getX() {
        return x;
    }
    public void setY(float y) {
        this.y = y;
        this.onChangeCallback();
    }
    public float getY() {
        return y;
    }
    public void setZ(float z) {
        this.z = z;
        this.onChangeCallback();
    }
    public float getZ() {
        return z;
    }
    public void setW(float w) {
        this.w = w;
        this.onChangeCallback();
    }
    public float getW() {
        return w;
    }
    public void setContainer(Object3D container) {
        this.container = container;
    }
    public Object3D getContainer() {
        return this.container;
    }
    private void onChangeCallback(){
        if(this.container !=null){
            this.container.onQuaternionChange();
        }
    }
    public void set(float x, float y, float z, float w){
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW(w);
    }
    public Quaternion clone(){
        return new Quaternion(this.x,this.y,this.z,this.w);
    }
    public Quaternion copy(Quaternion q){
        this.x = q.getX();
        this.y = q.getY();
        this.z = q.getZ();
        this.w = q.getW();

        this.onChangeCallback();
        return this;
    }
    public Quaternion setFromEuler(Euler euler, boolean update){
        // http://www.mathworks.com/matlabcentral/fileexchange/
        // 	20696-function-to-convert-between-dcm-euler-angles-quaternions-and-euler-vectors/
        //	content/SpinCalc.m

        float c1 = (float)java.lang.Math.cos( euler.getX() / 2 );
        float c2 = (float)java.lang.Math.cos( euler.getY() / 2 );
        float c3 = (float)java.lang.Math.cos( euler.getZ() / 2 );
        float s1 = (float)java.lang.Math.sin( euler.getX() / 2 );
        float s2 = (float)java.lang.Math.sin( euler.getY() / 2 );
        float s3 = (float)java.lang.Math.sin( euler.getZ() / 2 );

        String order = euler.getOrder();

        if ( order == "XYZ" ) {

            this.x = s1 * c2 * c3 + c1 * s2 * s3;
            this.y = c1 * s2 * c3 - s1 * c2 * s3;
            this.z = c1 * c2 * s3 + s1 * s2 * c3;
            this.w = c1 * c2 * c3 - s1 * s2 * s3;

        } else if ( order == "YXZ" ) {

            this.x = s1 * c2 * c3 + c1 * s2 * s3;
            this.y = c1 * s2 * c3 - s1 * c2 * s3;
            this.z = c1 * c2 * s3 - s1 * s2 * c3;
            this.w = c1 * c2 * c3 + s1 * s2 * s3;

        } else if ( order == "ZXY" ) {

            this.x = s1 * c2 * c3 - c1 * s2 * s3;
            this.y = c1 * s2 * c3 + s1 * c2 * s3;
            this.z = c1 * c2 * s3 + s1 * s2 * c3;
            this.w = c1 * c2 * c3 - s1 * s2 * s3;

        } else if ( order == "ZYX" ) {

            this.x = s1 * c2 * c3 - c1 * s2 * s3;
            this.y = c1 * s2 * c3 + s1 * c2 * s3;
            this.z = c1 * c2 * s3 - s1 * s2 * c3;
            this.w = c1 * c2 * c3 + s1 * s2 * s3;

        } else if ( order == "YZX" ) {

            this.x = s1 * c2 * c3 + c1 * s2 * s3;
            this.y = c1 * s2 * c3 + s1 * c2 * s3;
            this.z = c1 * c2 * s3 - s1 * s2 * c3;
            this.w = c1 * c2 * c3 - s1 * s2 * s3;

        } else if ( order == "XZY" ) {

            this.x = s1 * c2 * c3 - c1 * s2 * s3;
            this.y = c1 * s2 * c3 - s1 * c2 * s3;
            this.z = c1 * c2 * s3 + s1 * s2 * c3;
            this.w = c1 * c2 * c3 + s1 * s2 * s3;

        }

        if ( update ) this.onChangeCallback();

        return this;

    }
    public Quaternion setFromAxisAngle(Vector3 axis, float angle){
        // http://www.euclideanspace.com/maths/geometry/rotations/conversions/angleToQuaternion/index.htm

        // assumes axis is normalized

        float halfAngle = angle / 2, s = (float)java.lang.Math.sin( halfAngle );

        this.x = axis.getX() * s;
        this.y = axis.getY() * s;
        this.z = axis.getZ() * s;
        this.w = (float)java.lang.Math.cos( halfAngle );

        this.onChangeCallback();

        return this;
    }
    public Quaternion setFromRotationMatrix(Matrix4 m){
        // http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToQuaternion/index.htm

        // assumes the upper 3x3 of m is a pure rotation matrix (i.e, unscaled)

        float[] te = m.getElements();

        float   m11 = te[ 0 ], m12 = te[ 4 ], m13 = te[ 8 ],
                m21 = te[ 1 ], m22 = te[ 5 ], m23 = te[ 9 ],
                m31 = te[ 2 ], m32 = te[ 6 ], m33 = te[ 10 ],

                trace = m11 + m22 + m33,
                s;

        if ( trace > 0 ) {

            s = 0.5f / (float)java.lang.Math.sqrt( trace + 1.0 );

            this.w = 0.25f / s;
            this.x = ( m32 - m23 ) * s;
            this.y = ( m13 - m31 ) * s;
            this.z = ( m21 - m12 ) * s;

        } else if ( m11 > m22 && m11 > m33 ) {

            s = 2.0f * (float)java.lang.Math.sqrt( 1.0 + m11 - m22 - m33 );

            this.w = ( m32 - m23 ) / s;
            this.x = 0.25f* s;
            this.y = ( m12 + m21 ) / s;
            this.z = ( m13 + m31 ) / s;

        } else if ( m22 > m33 ) {

            s = 2.0f * (float)java.lang.Math.sqrt( 1.0 + m22 - m11 - m33 );

            this.w = ( m13 - m31 ) / s;
            this.x = ( m12 + m21 ) / s;
            this.y = 0.25f * s;
            this.z = ( m23 + m32 ) / s;

        } else {

            s = 2.0f * (float)java.lang.Math.sqrt( 1.0 + m33 - m11 - m22 );

            this.w = ( m21 - m12 ) / s;
            this.x = ( m13 + m31 ) / s;
            this.y = ( m23 + m32 ) / s;
            this.z = 0.25f * s;

        }

        this.onChangeCallback();

        return this;
    }
    public Quaternion setFromUnitVectors(Vector3 vFrom, Vector3 vTo ){
        // http://lolengine.net/blog/2014/02/24/quaternion-from-two-vectors-final

        // assumes direction vectors vFrom and vTo are normalized

        float  r;

        float EPS = 0.000001f;

        Vector3 v1 = new Vector3();

        r = vFrom.dot( vTo ) + 1;

        if ( r < EPS ) {

            r = 0;

            if ( java.lang.Math.abs( vFrom.getX() ) > java.lang.Math.abs( vFrom.getZ() ) ) {

                v1.set( - vFrom.getY(), vFrom.getX(), 0 );

            } else {

                v1.set( 0, - vFrom.getZ(), vFrom.getY() );

            }

        } else {

            v1.crossVectors( vFrom, vTo );

        }

        this.x = v1.getX();
        this.y = v1.getY();
        this.z = v1.getZ();
        this.w = r;

        this.normalize();

        return this;
    }
    public Quaternion inverse(){
        this.conjugate().normalize();

        return this;
    }
    public Quaternion conjugate(){
        this.x *= - 1;
        this.y *= - 1;
        this.z *= - 1;
        this.onChangeCallback();
        return this;
    }
    public float dot(Vector4 v){
        return this.x * v.getX() + this.y * v.getY() + this.z * v.getZ() + this.w * v.getW();
    }
    public float lengthSq(){
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }
    public float length(){
        return (float)java.lang.Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
    }
    public Quaternion normalize(){
        float l = this.length();

        if ( l == 0 ) {

            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.w = 1;

        } else {

            l = 1f / l;

            this.x = this.x * l;
            this.y = this.y * l;
            this.z = this.z * l;
            this.w = this.w * l;

        }

        this.onChangeCallback();

        return this;
    }
    public Quaternion multiply(Quaternion q, Quaternion p){
        if ( p !=null) {
            return this.multiplyQuaternions( q, p );
        }
        return this.multiplyQuaternions( this, q );
    }
    public Quaternion multiply(Quaternion q){
        return this.multiply(q,null);
    }
    public Quaternion multiplyQuaternions(Quaternion a, Quaternion b){
        // from http://www.euclideanspace.com/maths/algebra/realNormedAlgebra/quaternions/code/index.htm

        float qax = a.getX(), qay = a.getY(), qaz = a.getZ(), qaw = a.getW();
        float qbx = b.getX(), qby = b.getY(), qbz = b.getZ(), qbw = b.getW();

        this.x = qax * qbw + qaw * qbx + qay * qbz - qaz * qby;
        this.y = qay * qbw + qaw * qby + qaz * qbx - qax * qbz;
        this.z = qaz * qbw + qaw * qbz + qax * qby - qay * qbx;
        this.w = qaw * qbw - qax * qbx - qay * qby - qaz * qbz;

        this.onChangeCallback();

        return this;
    }
    public Quaternion slerp(Quaternion qb, float t){
        if ( t == 0f ) return this;
        if ( t == 1f ) return this.copy( qb );

        float x = this.x, y = this.y, z = this.z, w = this.w;

        // http://www.euclideanspace.com/maths/algebra/realNormedAlgebra/quaternions/slerp/

        float cosHalfTheta = w * qb.getW() + x * qb.getX() + y * qb.getY() + z * qb.getZ();

        if ( cosHalfTheta < 0 ) {

            this.w = - qb.getW();
            this.x = - qb.getX();
            this.y = - qb.getY();
            this.z = - qb.getZ();

            cosHalfTheta = - cosHalfTheta;

        } else {

            this.copy( qb );

        }

        if ( cosHalfTheta >= 1.0 ) {

            this.w = w;
            this.x = x;
            this.y = y;
            this.z = z;

            return this;

        }

        float sinHalfTheta = (float)java.lang.Math.sqrt( 1.0f - cosHalfTheta * cosHalfTheta );

        if ( (float)java.lang.Math.abs( sinHalfTheta ) < 0.001 ) {

            this.w = 0.5f * ( w + this.w );
            this.x = 0.5f * ( x + this.x );
            this.y = 0.5f * ( y + this.y );
            this.z = 0.5f * ( z + this.z );

            return this;

        }

        float halfTheta = (float)java.lang.Math.atan2( sinHalfTheta, cosHalfTheta );
        float ratioA = (float)java.lang.Math.sin( ( 1 - t ) * halfTheta ) / sinHalfTheta,
                ratioB = (float)java.lang.Math.sin( t * halfTheta ) / sinHalfTheta;

        this.w = ( w * ratioA + this.w * ratioB );
        this.x = ( x * ratioA + this.x * ratioB );
        this.y = ( y * ratioA + this.y * ratioB );
        this.z = ( z * ratioA + this.z * ratioB );

        this.onChangeCallback();

        return this;
    }
    public boolean equals(Quaternion quaternion){
        return ( quaternion.getX() == this.x ) && ( quaternion.getY() == this.y ) && ( quaternion.getZ() == this.z ) && ( quaternion.getW() == this.w );
    }
    public Quaternion fromArray(float[] array, int offset){

        this.x = array[ offset ];
        this.y = array[ offset + 1 ];
        this.z = array[ offset + 2 ];
        this.w = array[ offset + 3 ];

        this.onChangeCallback();

        return this;
    }
    public float[] toArray(float[] array, int offset){

        array[ offset ] = this.x;
        array[ offset + 1 ] = this.y;
        array[ offset + 2 ] = this.z;
        array[ offset + 3 ] = this.w;

        return array;

    }
    public static Quaternion slerp(Quaternion qa, Quaternion qb, Quaternion qm, float t ){
        return qm.copy( qa ).slerp( qb, t );
    }
    public static void slerpFlat(
            float[] dst,
            int dstOffset,
            float[] src0,
            int srcOffset0,
            float[] src1,
            int srcOffset1,
            float t
    ){
        // fuzz-free, array-based Quaternion SLERP operation

        float
                x0 = src0[ srcOffset0 + 0 ],
                y0 = src0[ srcOffset0 + 1 ],
                z0 = src0[ srcOffset0 + 2 ],
                w0 = src0[ srcOffset0 + 3 ],

                x1 = src1[ srcOffset1 + 0 ],
                y1 = src1[ srcOffset1 + 1 ],
                z1 = src1[ srcOffset1 + 2 ],
                w1 = src1[ srcOffset1 + 3 ];

        if ( w0 != w1 || x0 != x1 || y0 != y1 || z0 != z1 ) {

            float s = 1 - t,

                    cos = x0 * x1 + y0 * y1 + z0 * z1 + w0 * w1,

                    dir = ( cos >= 0 ? 1 : - 1 ),
                    sqrSin = 1 - cos * cos;

            // Skip the Slerp for tiny steps to avoid numeric problems:
            if ( sqrSin > 2.220446049250313e-16f ) {

                float sin = (float)java.lang.Math.sqrt( sqrSin ),
                        len = (float)java.lang.Math.atan2( sin, cos * dir );

                s = (float)java.lang.Math.sin( s * len ) / sin;
                t = (float)java.lang.Math.sin( t * len ) / sin;

            }

            float tDir = t * dir;

            x0 = x0 * s + x1 * tDir;
            y0 = y0 * s + y1 * tDir;
            z0 = z0 * s + z1 * tDir;
            w0 = w0 * s + w1 * tDir;

            // Normalize in case we just did a lerp:
            if ( s == 1 - t ) {

                float f = 1f / (float)java.lang.Math.sqrt( x0 * x0 + y0 * y0 + z0 * z0 + w0 * w0 );

                x0 *= f;
                y0 *= f;
                z0 *= f;
                w0 *= f;

            }

        }

        dst[ dstOffset ] = x0;
        dst[ dstOffset + 1 ] = y0;
        dst[ dstOffset + 2 ] = z0;
        dst[ dstOffset + 3 ] = w0;
    }
    public void addX(float value) {
        this.x +=value;
        this.onChangeCallback();
    }
    public void addY(float value) {
        this.y +=value;
        this.onChangeCallback();
    }
    public void addZ(float value) {
        this.z +=value;
        this.onChangeCallback();
    }
    public void addW(float value) {
        this.w +=value;
        this.onChangeCallback();
    }
    public void subtractX(float value){
        this.x -=value;
        this.onChangeCallback();
    }
    public void subtractY(float value){
        this.y -=value;
        this.onChangeCallback();
    }
    public void subtractZ(float value){
        this.z -=value;
        this.onChangeCallback();
    }
    public void subtractW(float value){
        this.w -=value;
        this.onChangeCallback();
    }
    public String toString(){
        return "quaternion[,"+this.x+","+this.y+","+this.z+","+this.w+"]";
    }
}
