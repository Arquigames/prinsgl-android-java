package com.arquigames.prinsgl.maths;

import android.graphics.Point;

/**
 * Created by usuario on 13/07/2016.
 */
public class MathUtils {
    public MathUtils(){

    }
    public static String generateUUID(){
        char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] uuid = new char[36];
        int rnd = 0, r;

        for ( int i = 0; i < 36; i ++ ) {
            if ( i == 8 || i == 13 || i == 18 || i == 23 ) {
                uuid[ i ] = '-';
            } else if ( i == 14 ) {
                uuid[ i ] = '4';
            } else {
                if ( rnd <= 0x02 ) rnd = 0x2000000 + (int)( java.lang.Math.random() * 0x1000000 );
                r = rnd & 0xf;
                rnd = rnd >> 4;
                uuid[ i ] = chars[ ( i == 19 ) ? ( r & 0x3 ) | 0x8 : r ];
            }
        }
        return String.valueOf(uuid);
    }
    public static double clamp(double value,double min, double max){
        return java.lang.Math.max( min, java.lang.Math.min( max, value ) );
    }
    public static float clamp(float value,float min, float max){
        return java.lang.Math.max( min, java.lang.Math.min( max, value ) );
    }
    public static int clamp(int value,int min, int max){
        return java.lang.Math.max( min, java.lang.Math.min( max, value ) );
    }
    public static float mapLinear(double x,double a1,double a2,double b1,double b2){
        return (float)(b1 + ( x - a1 ) * ( b2 - b1 ) / ( a2 - a1 ));
    }
    public static float degToRad(float degrees){
        return (float)(degrees * java.lang.Math.PI / 180f);
    }
    public static float radToDeg(float radians){
        return radians * (180f / (float)java.lang.Math.PI);
    }
    public static float euclideanModulo(float n, float m){
        return ( ( n % m ) + m ) % m;
    }
    public static float mapLinear(float x,float a1,float a2,float b1,float b2 ){
        return (b1 + ( x - a1 ) * ( b2 - b1 ) / ( a2 - a1 ));
    }
    public static float smoothstep(float x, float min, float max){
        if ( x <= min ) return 0f;
        if ( x >= max ) return 1f;

        x = ( x - min ) / ( max - min );

        return x * x * ( 3f - 2f * x );
    }
    public static float smootherstep(float x, float min, float max){
        if ( x <= min ) return 0f;
        if ( x >= max ) return 1f;

        x = ( x - min ) / ( max - min );

        return x * x * x * ( x * ( x * 6f - 15f ) + 10f );
    }
    public static int randInt(int low, int high){
        return (int)(low + java.lang.Math.floor( java.lang.Math.random() * ( high - low + 1 ) ));
    }
    public static float randFloat(float low, float high){
        return (float)(low + java.lang.Math.random() * ( high - low ));
    }
    public static float randFloatSpread(float range){
        return (float)(range * ( 0.5 - java.lang.Math.random() ));
    }
    public static boolean isPowerOfTwo(int val){
        int value = val;
        return ( value & ( value - 1 ) ) == 0 && value != 0;
    }
    public static int nearestPowerOfTwo(int val){
        double res = java.lang.Math.pow( 2, java.lang.Math.round( java.lang.Math.log( val ) / java.lang.Math.log(2) ) );
        return (int)java.lang.Math.round(res);
    }
    public static int nextPowerOfTwo(int val){
        int value = val;
        value --;
        value |= value >> 1;
        value |= value >> 2;
        value |= value >> 4;
        value |= value >> 8;
        value |= value >> 16;
        value ++;

        return value;
    }
    public static double distance(Point p1, Point p2){
        int dx = p1.x - p2.x;
        int dy = p1.y - p2.y;
        double distance = java.lang.Math.sqrt(java.lang.Math.pow(dx,2) + java.lang.Math.pow(dy,2));
        return distance;
    }
}
