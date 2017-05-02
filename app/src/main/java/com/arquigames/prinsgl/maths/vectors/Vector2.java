package com.arquigames.prinsgl.maths.vectors;

/**
 * Created by usuario on 26/06/2016.
 */
public class Vector2 {
    private float x = 0;
    private float y = 0;
    public Vector2(){
        this.setX(0);
        this.setY(0);
    }
    public Vector2(float x, float y ){
        this.setX(x);
        this.setY(y);
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
    public Vector2 set(float x, float y ){
        this.setX(x);
        this.setY(y);
        return this;
    }
    public Vector2 clone(){
        return new Vector2(this.x,this.y );
    }
    public Vector2 copy(Vector2 v){
        this.x = v.getX();
        this.y = v.getY();
        return this;
    }
    public Vector2 copyX(Vector2 v){
        this.x = v.getX();
        return this;
    }
    public Vector2 copyY(Vector2 v){
        this.y = v.getY();
        return this;
    }
    public Vector2 add(Vector2 v, Vector2 w ) {
        if ( w != null ) {
            return this.addVectors( v, w );
        }
        this.x += v.getX();
        this.y += v.getY();
        return this;
    }
    public Vector2 add(Vector2 v) {
        return this.add(v,null);
    }
    public Vector2 addVectors(Vector2 v, Vector2 w ){
        this.x = v.getX() + w.getX();
        this.y = v.getY() + w.getY();
        return this;
    }
    public Vector2 addScalar(float s){
        this.x +=s;
        this.y +=s;
        return this;
    }
    public Vector2 addScaledVector(Vector2 v, float s ) {
        this.x += v.getX() * s;
        this.y += v.getY() * s;
        return this;
    }
    public Vector2 sub(Vector2 v, Vector2 w){
        if(w!=null){
            return this.subVectors(v,w);
        }
        this.x -=v.getX();
        this.y -=v.getY();
        return this;
    }
    public Vector2 sub(Vector2 v){
        return this.sub(v,null);
    }
    public Vector2 subVectors(Vector2 v, Vector2 w ) {
        this.x = v.getX() -w.getX();
        this.y = v.getY() -w.getY();
        return this;
    }
    public Vector2 subScalar(float s) {
        this.x -= s;
        this.y -= s;
        return this;
    }
    //multiply element by element
    public Vector2 multiply(Vector2 v, Vector2 w ) {
        if ( w != null ) {
            return this.multiplyVectors( v, w );
        }
        this.x *= v.getX();
        this.y *= v.getY();
        return this;
    }
    //multiply element by element
    public Vector2 multiply(Vector2 v) {
        return this.multiply(v,null);
    }
    //multiply element by element
    public Vector2 multiplyVectors(Vector2 v, Vector2 w ) {
        this.x = v.getX()*w.getX();
        this.y = v.getY()*w.getY();
        return this;
    }
    public Vector2 multiplyScalar(float s) {
        if(Float.isInfinite(s)){
            this.x =0;
            this.y =0;
        }else{
            this.x *= s;
            this.y *= s;
        }
        return this;
    }
    public Vector2 divide(Vector2 v){
        this.x /= v.getX();
        this.y /= v.getY();

        return this;
    }
    public Vector2 divideScalar(float scalar){
        return this.multiplyScalar( (float)(1 / scalar) );
    }
    public Vector2 min(Vector2 v){
        this.x = Math.min(this.x,v.getX());
        this.y = Math.min(this.y,v.getY());
        return this;
    }
    public Vector2 max(Vector2 v){
        this.x = Math.max(this.x,v.getX());
        this.y = Math.max(this.y,v.getY());
        return this;
    }
    public Vector2 clamp(Vector2 min, Vector2 max){

        this.x = Math.max( min.getX(), Math.min( max.getX(), this.x ) );
        this.y = Math.max( min.getY(), Math.min( max.getY(), this.y ) );

        return this;
    }
    public Vector2 clampScalar(float minVal, float maxVal){
        Vector2 min = new Vector2();
        Vector2 max = new Vector2();

        min.set( minVal, minVal );
        max.set( maxVal, maxVal );

        return this.clamp( min, max );
    }
    public Vector2 clampLength(float min, float max){
        float length = this.length();
        this.multiplyScalar( Math.max( min, Math.min( max, length ) ) / length );
        return this;
    }
    public Vector2 floor(){
        this.x = (float) Math.floor( this.x );
        this.y = (float) Math.floor( this.y );

        return this;
    }
    public Vector2 ceil(){
        this.x = (float) Math.ceil( this.x );
        this.y = (float) Math.ceil( this.y );

        return this;
    }
    public Vector2 round(){
        this.x = Math.round( this.x );
        this.y = Math.round( this.y );

        return this;
    }
    public Vector2 roundToZero(){
        this.x = ( this.x < 0 ) ? (float) Math.ceil( this.x ) : (float) Math.floor( this.x );
        this.y = ( this.y < 0 ) ? (float) Math.ceil( this.y ) : (float) Math.floor( this.y );

        return this;
    }
    public Vector2 negate(){
        this.x = - this.x;
        this.y = - this.y;
        return this;
    }
    public Vector2 negateX(){
        this.x = -this.x;
        return this;
    }
    public Vector2 negateY(){
        this.y = -this.y;
        return this;
    }
    public float dot( Vector2 v ) {
        return (this.x * v.getX() + this.y * v.getY() );
    }
    public float length(){
        return (float) Math.sqrt( this.x * this.x + this.y * this.y );
    }
    public float lengthSq(){
        return (float)(this.x * this.x + this.y * this.y );
    }
    public Vector2 normalize(){
        return this.divideScalar( this.length() );
    }
    public float distanceTo(Vector2 vec){
        return (float) Math.sqrt( this.distanceToSquared( vec ) );
    }
    public float distanceToSquared(Vector2 vec){
        float dx = this.x - vec.getX();
        float dy = this.y - vec.getY();

        return (float)(dx * dx + dy * dy );
    }
    public Vector2 setLength(float length){
        return this.multiplyScalar( length / this.length() );
    }
    public Vector2 lerp(Vector2 v, float alpha){
        this.x += ( v.getX() - this.x ) * alpha;
        this.y += ( v.getY() - this.y ) * alpha;

        return this;
    }
    public Vector2 lerpVectors(Vector2 v1, Vector2 v2, float alpha){
        this.subVectors( v2, v1 ).multiplyScalar( alpha ).add( v1,null );

        return this;
    }
    public boolean equals(Vector2 v){
        return ( ( v.getX() == this.x ) && ( v.getY() == this.y ) );
    }
    public Vector2 fromArray(float[] array, int offset ) {
        if ( offset<=0 ) offset = 0;
        if(offset+1<array.length){
            this.x = array[ offset ];
            this.y = array[ offset + 1 ];
        }
        return this;
    }
    public Vector2 fromArray(float[] array) {
        return this.fromArray(array,0);
    }

    public float[] toArray( float[] array, int offset ) {
        if(offset<0)offset=0;
        if(offset+1>=array.length)return null;
        array[ offset ] = this.x;
        array[ offset + 1 ] = this.y;
        return array;
    }
    public float[] toArray( float[] array) {
        return this.toArray(array,0);
    }
    public Vector2 rotateAround(Vector2 center, float angle){
        float c = (float) Math.cos( angle ), s = (float) Math.sin( angle );

        float x = this.x - center.x;
        float y = this.y - center.y;

        this.x = x * c - y * s + center.x;
        this.y = x * s + y * c + center.y;

        return this;
    }
    public String toString(){
        return "Vector2["+this.x+","+this.y+"]";
    }

}
