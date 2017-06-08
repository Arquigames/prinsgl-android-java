package com.arquigames.prinsgl.maths.vectors;


import com.arquigames.prinsgl.maths.Euler;
import com.arquigames.prinsgl.maths.MathUtils;
import com.arquigames.prinsgl.maths.Quaternion;
import com.arquigames.prinsgl.cameras.Camera;
import com.arquigames.prinsgl.maths.matrix.Matrix3;
import com.arquigames.prinsgl.maths.matrix.Matrix4;

import java.util.LinkedList;

/**
 * Created by usuario on 26/06/2016.
 */
public class Vector3 implements  Cloneable {

    private float x = 0;
    private float y = 0;
    private float z = 0;

    private int index = -1;//temp index for this Vector when it is used as Vertex
    private Vector2 uv = null; // temp uv coordinate per this Vector

    public Vector3(){
        this.setX(0);
        this.setY(0);
        this.setZ(0);
    }
    public Vector3(float x, float y,float z){
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }
    public float[] getValues(){
        return new float[]{this.x,this.y,this.z};
    }
    public Vector3 setY(float y) {
        this.y = y;
        return this;
    }
    public float getY() {
        return y;
    }
    public Vector3 setZ(float z) {
        this.z = z;
        return this;
    }
    public float getZ() {
        return z;
    }
    public Vector3 setX(float x) {
        this.x = x;
        return this;
    }
    public float getX() {
        return x;
    }
    public Vector3 set(float x, float y, float z){
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        return this;
    }
    public void setComponent(int index,float value) throws Exception{
        switch ( index ) {

            case 0: this.x = value; break;
            case 1: this.y = value; break;
            case 2: this.z = value; break;
            default:throw new Exception("index out of ranged (0,1,2)");
        }
    }
    public float getComponent(int index ) throws Exception{

        switch ( index ) {
            case 0: return this.x;
            case 1: return this.y;
            case 2: return this.z;
            default: throw new Exception("index out of ranged (0,1,2)");
        }

    }
    @Override
    public Vector3 clone(){
        return new Vector3(this.x,this.y,this.z);
    }
    public Vector3 copy(Vector3 v){
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
        return this;
    }
    public Vector3 copyX(Vector3 v){
        this.x = v.getX();
        return this;
    }
    public Vector3 copyY(Vector3 v){
        this.y = v.getY();
        return this;
    }
    public Vector3 copyZ(Vector3 v){
        this.z = v.getZ();
        return this;
    }
    public Vector3 add(Vector3 v, Vector3 w ) {
        if ( w != null ) {
            return this.addVectors( v, w );
        }
        this.x += v.getX();
        this.y += v.getY();
        this.z += v.getZ();
        return this;
    }
    public Vector3 add(Vector3 v) {
        return this.add(v,null);
    }
    public Vector3 addVectors(Vector3 v, Vector3 w ){
        this.x = v.getX() + w.getX();
        this.y = v.getY() + w.getY();
        this.z = v.getZ() + w.getZ();
        return this;
    }
    public Vector3 addScalar(float s){
        this.x +=s;
        this.y +=s;
        this.z +=s;
        return this;
    }
    public Vector3 addScaledVector(Vector3 v, float s ) {

        this.x += v.getX() * s;
        this.y += v.getY() * s;
        this.z += v.getZ() * s;

        return this;

    }
    public Vector3 sub(Vector3 v, Vector3 w){
        if(w!=null){
            return this.subVectors(v,w);
        }
        this.x -=v.getX();
        this.y -=v.getY();
        this.z -=v.getZ();
        return this;
    }
    public Vector3 sub(Vector3 v){
        return this.sub(v,null);
    }
    public Vector3 subVectors(Vector3 v, Vector3 w ) {
        this.x = v.getX() -w.getX();
        this.y = v.getY() -w.getY();
        this.z = v.getZ() -w.getZ();
        return this;
    }
    public Vector3 subScalar(float s) {

        this.x -= s;
        this.y -= s;
        this.z -= s;

        return this;
    }
    public Vector3 multiply(Vector3 v, Vector3 w ) {
        if ( w != null ) {
            return this.multiplyVectors( v, w );
        }
        this.x *= v.getX();
        this.y *= v.getY();
        this.z *= v.getZ();

        return this;
    }
    public Vector3 multiplyVectors(Vector3 v, Vector3 w ) {

        this.x = v.getX()*w.getX();
        this.y = v.getY()*w.getY();
        this.z = v.getZ()*w.getZ();

        return this;
    }
    public Vector3 multiplyScalar(float s) {
        if(Float.isInfinite(s)){
            this.x =0;
            this.y =0;
            this.z =0;
        }else{
            this.x *= s;
            this.y *= s;
            this.z *= s;
        }
        return this;
    }
    public Vector3 applyEuler(Euler euler){
        Quaternion quaternion = new Quaternion();
        quaternion.setFromEuler(euler,false);
        this.applyQuaternion(quaternion);
        return this;
    }
    public Vector3 applyQuaternion(Quaternion q){
        float x = this.x;
        float y = this.y;
        float z = this.z;

        float qx = q.getX();
        float qy = q.getY();
        float qz = q.getZ();
        float qw = q.getW();

        // calculate quat * vector

        float ix =  qw * x + qy * z - qz * y;
        float iy =  qw * y + qz * x - qx * z;
        float iz =  qw * z + qx * y - qy * x;
        float iw = - qx * x - qy * y - qz * z;

        // calculate result * inverse quat

        this.x = ix * qw + iw * - qx + iy * - qz - iz * - qy;
        this.y = iy * qw + iw * - qy + iz * - qx - ix * - qz;
        this.z = iz * qw + iw * - qz + ix * - qy - iy * - qx;

        return this;
    }
    public Vector3 applyAxisAngle(Vector3 axis, float angle){
        Quaternion quaternion = new Quaternion();
        quaternion.setFromAxisAngle(axis,angle);
        this.applyQuaternion(quaternion);
        return this;
    }
    public Vector3 applyMatrix3(Matrix3 m){
        float x = this.x;
        float y = this.y;
        float z = this.z;

        float[] e = m.getElements();

        this.x = e[ 0 ] * x + e[ 3 ] * y + e[ 6 ] * z;
        this.y = e[ 1 ] * x + e[ 4 ] * y + e[ 7 ] * z;
        this.z = e[ 2 ] * x + e[ 5 ] * y + e[ 8 ] * z;

        return this;
    }
    public Vector3 applyMatrix4(Matrix4 m){
        float x = this.x, y = this.y, z = this.z;

        float[] e = m.getElements();

        this.x = e[ 0 ] * x + e[ 4 ] * y + e[ 8 ]  * z + e[ 12 ];
        this.y = e[ 1 ] * x + e[ 5 ] * y + e[ 9 ]  * z + e[ 13 ];
        this.z = e[ 2 ] * x + e[ 6 ] * y + e[ 10 ] * z + e[ 14 ];

        return this;
    }
    public Vector3 applyProjection(Matrix4 m){
        // input: THREE.Matrix4 projection matrix

        float x = this.x, y = this.y, z = this.z;

        float[] e = m.getElements();
        float d = (float)(1 / ( e[ 3 ] * x + e[ 7 ] * y + e[ 11 ] * z + e[ 15 ] )); // perspective divide

        this.x = ( e[ 0 ] * x + e[ 4 ] * y + e[ 8 ]  * z + e[ 12 ] ) * d;
        this.y = ( e[ 1 ] * x + e[ 5 ] * y + e[ 9 ]  * z + e[ 13 ] ) * d;
        this.z = ( e[ 2 ] * x + e[ 6 ] * y + e[ 10 ] * z + e[ 14 ] ) * d;

        return this;
    }
    public Vector3 project(Camera camera) throws Exception{
        Matrix4 matrix = new Matrix4();

        matrix.multiplyMatrices( camera.getProjectionMatrix(), matrix.getInverse( camera.getMatrixWorld(),false ) );
        return this.applyProjection( matrix );
    }
    public Vector3 unproject(Camera camera) throws Exception{
        Matrix4 matrix = new Matrix4();

        matrix.multiplyMatrices( camera.getMatrixWorld(), matrix.getInverse( camera.getProjectionMatrix(),false ) );
        return this.applyProjection( matrix );
    }
    public Vector3 transformDirection(Matrix4 m){
        float x = this.x, y = this.y, z = this.z;

        float[] e = m.getElements();

        this.x = e[ 0 ] * x + e[ 4 ] * y + e[ 8 ]  * z;
        this.y = e[ 1 ] * x + e[ 5 ] * y + e[ 9 ]  * z;
        this.z = e[ 2 ] * x + e[ 6 ] * y + e[ 10 ] * z;

        this.normalize();

        return this;
    }
    public Vector3 divide(Vector3 v){
        this.x /= v.getX();
        this.y /= v.getY();
        this.z /= v.getZ();

        return this;
    }
    public Vector3 min(Vector3 v){
        this.x = Math.min(this.x,v.getX());
        this.y = Math.min(this.y,v.getY());
        this.z = Math.min(this.z,v.getZ());
        return this;
    }
    public Vector3 max(Vector3 v){
        this.x = Math.max(this.x,v.getX());
        this.y = Math.max(this.y,v.getY());
        this.z = Math.max(this.z,v.getZ());
        return this;
    }
    public Vector3 clamp(Vector3 min, Vector3 max){

        this.x = Math.max( min.getX(), Math.min( max.getX(), this.x ) );
        this.y = Math.max( min.getY(), Math.min( max.getY(), this.y ) );
        this.z = Math.max( min.getZ(), Math.min( max.getZ(), this.z ) );

        return this;
    }
    public Vector3 clampScalar(float minVal, float maxVal){
        Vector3 min = new Vector3();
        Vector3 max = new Vector3();

        min.set( minVal, minVal, minVal );
        max.set( maxVal, maxVal, maxVal );

        return this.clamp( min, max );
    }
    public Vector3 clampLength(float min, float max){
        float length = this.length();

        this.multiplyScalar( Math.max( min, Math.min( max, length ) ) / length );

        return this;
    }
    public Vector3 floor(){
        this.x = (float) Math.floor( this.x );
        this.y = (float) Math.floor( this.y );
        this.z = (float) Math.floor( this.z );

        return this;
    }
    public Vector3 ceil(){
        this.x = (float) Math.ceil( this.x );
        this.y = (float) Math.ceil( this.y );
        this.z = (float) Math.ceil( this.z );

        return this;
    }
    public Vector3 roundToZero(){
        this.x = ( this.x < 0 ) ? (float) Math.ceil( this.x ) : (float) Math.floor( this.x );
        this.y = ( this.y < 0 ) ? (float) Math.ceil( this.y ) : (float) Math.floor( this.y );
        this.z = ( this.z < 0 ) ? (float) Math.ceil( this.z ) : (float) Math.floor( this.z );

        return this;
    }
    public Vector3 negate(){
        this.x = - this.x;
        this.y = - this.y;
        this.z = - this.z;

        return this;
    }
    public Vector3 negateX(){
        this.x = -this.x;
        return this;
    }
    public Vector3 negateY(){
        this.y = -this.y;
        return this;
    }
    public Vector3 negateZ(){
        this.z = -this.z;
        return this;
    }
    public float dot( Vector3 v ) {

        return (float)(this.x * v.getX() + this.y * v.getY() + this.z * v.getZ());

    }
    public float lengthSq(){
        return (float)(this.x * this.x + this.y * this.y + this.z * this.z);
    }
    public float length(){
        return (float) Math.sqrt( this.x * this.x + this.y * this.y + this.z * this.z );
    }
    public Vector3 setLength(float length){
        return this.multiplyScalar( length / this.length() );
    }
    public Vector3 lerp(Vector3 v, float alpha){
        this.x += ( v.getX() - this.x ) * alpha;
        this.y += ( v.getY() - this.y ) * alpha;
        this.z += ( v.getZ() - this.z ) * alpha;

        return this;
    }
    public Vector3 lerpVectors(Vector3 v1, Vector3 v2, float alpha){
        this.subVectors( v2, v1 ).multiplyScalar( alpha ).add( v1,null );

        return this;
    }
    public Vector3 cross(Vector3 v, Vector3 w){
        if ( w !=null ) {
            return this.crossVectors( v, w );
        }
        float x = this.x, y = this.y, z = this.z;
        this.x = y * v.z - z * v.y;
        this.y = z * v.x - x * v.z;
        this.z = x * v.y - y * v.x;
        return this;
    }
    public Vector3 cross(Vector3 v){
        return this.cross(v,null);
    }
    public Vector3 crossVectors(Vector3 a, Vector3 b){
        float ax = a.x, ay = a.y, az = a.z;
        float bx = b.x, by = b.y, bz = b.z;

        this.x = ay * bz - az * by;
        this.y = az * bx - ax * bz;
        this.z = ax * by - ay * bx;

        return this;
    }
    public Vector3 round(){
        this.x = Math.round( this.x );
        this.y = Math.round( this.y );
        this.z = Math.round( this.z );

        return this;
    }
    public Vector3 divideScalar(float scalar){
        return this.multiplyScalar( (1f / scalar) );
    }
    public Vector3 normalize(){
        return this.divideScalar( this.length() );
    }
    public Vector3 projectOnVector(Vector3 vector){
        Vector3 v1 = new Vector3();
        v1.copy( vector ).normalize();
        float dot = this.dot( v1 );
        return this.copy( v1 ).multiplyScalar( dot );
    }
    public Vector3 projectOnPlane(Vector3 planeNormal){
        Vector3 v1 = new Vector3();
        v1.copy( this ).projectOnVector( planeNormal );
        return this.sub(v1);
    }

    public Vector3 reflect(Vector3 normal){
        Vector3 v1 = new Vector3();
        v1.copy(normal);
        v1.multiplyScalar(2*this.dot(normal));
        return this.sub(v1,null);
    }
    public float angleTo(Vector3 vec){
        float theta = (float)(this.dot( vec ) / ( Math.sqrt( this.lengthSq() * vec.lengthSq() ) ));

        return (float) Math.acos( MathUtils.clamp( theta, - 1, 1 ) );
    }
    public float distanceTo(Vector3 vec){
        return (float) Math.sqrt( this.distanceToSquared( vec ) );
    }
    public float distanceToSquared(Vector3 vec){
        float dx = this.x - vec.getX();
        float dy = this.y - vec.getY();
        float dz = this.z - vec.getZ();

        return (dx * dx + dy * dy + dz * dz);
    }
    public Vector3 setFromMatrixPosition(Matrix4 m){
        float[] e = m.getElements();
        this.x = e[ 12 ];
        this.y = e[ 13 ];
        this.z = e[ 14 ];

        return this;
    }
    public Vector3 setFromMatrixScale(Matrix4 m){
        float[] e = m.getElements();
        float sx = this.set( e[ 0 ], e[ 1 ], e[ 2 ] ).length();
        float sy = this.set( e[ 4 ], e[ 5 ], e[ 6 ] ).length();
        float sz = this.set( e[ 8 ], e[ 9 ], e[ 10 ] ).length();

        this.x = sx;
        this.y = sy;
        this.z = sz;

        return this;
    }
    public Vector3 setFromMatrixColumn(int index, Matrix4 m){
        int offset = index * 4;

        float[] e = m.getElements();

        this.x = e[ offset ];
        this.y = e[ offset + 1 ];
        this.z = e[ offset + 2 ];

        return this;
    }
    public boolean equals(Vector3 v){
        return ( ( v.getX() == this.x ) && ( v.getY() == this.y ) && ( v.getZ() == this.z ) );
    }
    public Vector3 setAt(String e, float value){
        switch(e){
            case "x":
                this.setX(value);
                break;
            case "y":
                this.setY(value);
                break;
            case "z":
                this.setZ(value);
                break;
            default:
                //TODO
        }
        return this;
    }

    public float[] toArray( float[] array, int offset ) {
        if(offset<=0)offset=0;
        if(offset+2>=array.length)return null;
        array[ offset ] = this.x;
        array[ offset + 1 ] = this.y;
        array[ offset + 2 ] = this.z;
        return array;
    }
    @Override
    public String toString(){
        return "Vector3[x:"+this.x+",y:"+this.y+",z:"+this.z+"]";
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Vector2 getUv() {
        return uv;
    }

    public void setUv(Vector2 uv) {
        this.uv = uv;
    }


    public Vector3 fromArray(float[] array, int offset ) {
        if ( offset<=0 ) offset = 0;
        if(offset+2<array.length){
            this.x = array[ offset ];
            this.y = array[ offset + 1 ];
            this.z = array[ offset + 2 ];
        }
        return this;
    }
    public Vector3 fromArray(float[] array) {
        return this.fromArray(array,0);
    }
    public Vector3 fromArray(Float[] array, int offset ) {

        if ( offset<=0 ) offset = 0;
        if(offset+2<array.length){
            this.x = array[ offset ];
            this.y = array[ offset + 1 ];
            this.z = array[ offset + 2 ];
        }
        return this;
    }
    public Vector3 fromArray(Float[] array) {
        return this.fromArray(array,0);
    }
    public Vector3 fromArray(LinkedList<Vector3> vertices, int offset) {
        if ( offset<=0 ) offset = 0;
        this.copy(vertices.get(offset));
        return this;
    }
    public Vector3 set(String key,float value){
        switch(key){
            case "x":
                this.x = value;
                break;
            case "y":
                this.y = value;
                break;
            case "z":
                this.z = value;
                break;
        }
        return this;
    }

    public void addX(float value) {
        this.x += value;
    }
    public void addY(float value) {
        this.y += value;
    }
    public void addZ(float value) {
        this.z += value;
    }
    public void subtractX(float value) {
        this.x -= value;
    }
    public void subtractY(float value) {
        this.y -= value;
    }
    public void subtractZ(float value) {
        this.z -= value;
    }
}
