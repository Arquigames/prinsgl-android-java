package com.arquigames.prinsgl.maths;

/**
 * Created by usuario on 26/06/2016.
 */
public class Color {
    private float r;
    private float g;
    private float b;
    public Color(){

    }
    public Color(int value){
        this.setHex(value);
    }
    public Color(float r, float g, float b){
        this.set(r,g,b);
    }
    public void setR(float r) {
        this.r = r;
    }
    public float getR() {
        return r;
    }
    public void setG(float g) {
        this.g = g;
    }
    public float getG() {
        return g;
    }
    public void setB(float b) {
        this.b = b;
    }
    public float getB() {
        return b;
    }
    public Color set(float r, float g, float b){
        this.r = r;
        this.g = g;
        this.b = b;
        return this;
    }
    public Color set(int c){
        this.setHex(c);
        return this;
    }
    public Color set(String c){
        this.setStyle(c);
        return this;
    }
    public Color setScalar(float scalar){
        this.r =this.r*scalar;
        this.g =this.g*scalar;
        this.b =this.b*scalar;
        return this;
    }
    public Color setHex(int hex){
        hex = (int) Math.floor( hex );
        this.r = ( hex >> 16 & 255 ) / 255f;
        this.g = ( hex >> 8 & 255 ) / 255f;
        this.b = ( hex & 255 ) / 255f;

        return this;
    }
    public Color setRGB(float r, float g, float b){
        this.set(r,g,b);

        return this;
    }
    public float hue2rgb(float p, float q , float t){
        if ( t < 0 ) t += 1;
        if ( t > 1 ) t -= 1;
        if ( t < 1 / 6 ) return p + ( q - p ) * 6 * t;
        if ( t < 1 / 2 ) return q;
        if ( t < 2 / 3 ) return p + ( q - p ) * 6 * ( 2 / 3 - t );
        return p;
    }
    public Color setHSL(float h, float s, float l){
        // h,s,l ranges are in 0.0 - 1.0
        h = MathUtils.euclideanModulo( h, 1 );
        s = MathUtils.clamp( s, 0, 1 );
        l = MathUtils.clamp( l, 0, 1 );

        if ( s == 0 ) {

            this.r = this.g = this.b = l;

        } else {

            float p = l <= 0.5 ? l * ( 1 + s ) : l + s - ( l * s );
            float q = ( 2 * l ) - p;

            this.r = this.hue2rgb( q, p, h + 1 / 3 );
            this.g = this.hue2rgb( q, p, h );
            this.b = this.hue2rgb( q, p, h - 1 / 3 );

        }

        return this;
    }
    public Color setStyle(String style){
        return this;
    }
    public Color clone(){
        return new Color(this.r,this.g,this.b);
    }
    public Color copyGammaToLinear(Color color, float gammaFactor){
        this.r = (float) Math.pow( color.getR(), gammaFactor );
        this.g = (float) Math.pow( color.getG(), gammaFactor );
        this.b = (float) Math.pow( color.getB(), gammaFactor );

        return this;
    }
    public Color copyLinearToGamma(Color color, float gammaFactor){
        float safeInverse = ( gammaFactor > 0 ) ? ( 1.0f / gammaFactor ) : 1.0f;

        this.r = (float) Math.pow( color.getR(), safeInverse );
        this.g = (float) Math.pow( color.getG(), safeInverse );
        this.b = (float) Math.pow( color.getB(), safeInverse );

        return this;
    }
    public Color convertGammaToLinear(){
        float r = this.r, g = this.g, b = this.b;

        this.r = r * r;
        this.g = g * g;
        this.b = b * b;

        return this;
    }
    public Color convertLinearToGamma(){
        this.r = (float) Math.sqrt( this.r );
        this.g = (float) Math.sqrt( this.g );
        this.b = (float) Math.sqrt( this.b );

        return this;
    }
    public int getHex(){
        return ( (int)this.r * 255 ) << 16 ^ ( (int)this.g * 255 ) << 8 ^ ( (int)this.b * 255 ) << 0;
    }
    public Color add(Color color){
        this.r += color.getR();
        this.g += color.getG();
        this.b += color.getB();

        return this;
    }
    public Color addColors(Color color1, Color color2){
        this.r = color1.getR()+color2.getR();
        this.g = color1.getG()+color2.getG();
        this.b = color1.getB()+color2.getB();

        return this;
    }
    public Color addScalar(float s){
        this.r += s;
        this.g += s;
        this.b += s;

        return this;
    }
    public Color multiply(Color color){
        this.r *= color.getR();
        this.g *= color.getG();
        this.b *= color.getB();

        return this;
    }
    public Color multiplyScalar(float s){
        this.r *= s;
        this.g *= s;
        this.b *= s;

        return this;
    }
    public Color lerp(Color color, float alpha){
        this.r += ( color.getR() - this.r ) * alpha;
        this.g += ( color.getG() - this.g ) * alpha;
        this.b += ( color.getB() - this.b ) * alpha;

        return this;
    }
    public boolean equals(Color c){
        return ( c.getR() == this.r ) && ( c.getG() == this.g ) && ( c.getB() == this.b );
    }
    public Color fromArray(float[]array, int offset ){
        this.r = array[ offset ];
        this.g = array[ offset + 1 ];
        this.b = array[ offset + 2 ];

        return this;
    }
    public float[] toArray( float[]array, int offset ){
        array[ offset + 0 ] = this.r;
        array[ offset + 1 ] = this.g;
        array[ offset + 2 ] = this.b;

        return array;
    }
    public Color copy(Color source){
        this.setR(source.getR());
        this.setG(source.getG());
        this.setB(source.getB());
        return this;
    }
    public String toString(){
        return "Color["+this.r+","+this.g+","+this.b+"]";
    }
}
