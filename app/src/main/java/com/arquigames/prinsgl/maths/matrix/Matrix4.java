package com.arquigames.prinsgl.maths.matrix;

import com.arquigames.prinsgl.maths.Euler;
import com.arquigames.prinsgl.maths.MathUtils;
import com.arquigames.prinsgl.maths.Quaternion;
import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 26/06/2016.
 */
public class Matrix4 {

    private float[] elements = new float[]{
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f
    };
    public Matrix4(){

    }
    public Matrix4(
            float n11, float n12, float n13, float n14,
            float n21, float n22, float n23, float n24,
            float n31, float n32, float n33, float n34,
            float n41, float n42, float n43, float n44
    ){
        this.set(
            n11, n12, n13, n14,
            n21, n22, n23, n24,
            n31, n32, n33, n34,
            n41, n42, n43, n44
        );
    }
    public Matrix4(float[] elements){
        for(int i=0; i<elements.length;i++){
            this.elements[i] = elements[i];
        }
    }
    public void setElements(float[] elements) {
        this.set(elements);
    }
    public float[] getElements() {
        return elements;
    }
    public Matrix4 set(float[] e){
        for( int i=0; i<16;i++){
            this.elements[i] = e[i];
        }
        return this;
    }
    public Matrix4 set(
            float n11, float n12, float n13, float n14,
            float n21, float n22, float n23, float n24,
            float n31, float n32, float n33, float n34,
            float n41, float n42, float n43, float n44
    ){
        float[] te = this.elements;

        te[ 0 ] = n11; te[ 4 ] = n12; te[ 8 ] = n13; te[ 12 ] = n14;
        te[ 1 ] = n21; te[ 5 ] = n22; te[ 9 ] = n23; te[ 13 ] = n24;
        te[ 2 ] = n31; te[ 6 ] = n32; te[ 10 ] = n33; te[ 14 ] = n34;
        te[ 3 ] = n41; te[ 7 ] = n42; te[ 11 ] = n43; te[ 15 ] = n44;

        return this;
    }
    public Matrix4 identity(){
        this.set(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        );
        return this;
    }
    public Matrix4 clone(){
        return new Matrix4(this.elements);
    }
    public Matrix4 copy(Matrix4 m){
        this.setElements(m.getElements());
        return this;
    }
    public Matrix4 copyPosition(Matrix4 m){
        float[] te = this.elements;
        float[] me = m.getElements();

        te[ 12 ] = me[ 12 ];
        te[ 13 ] = me[ 13 ];
        te[ 14 ] = me[ 14 ];

        return this;
    }
    public Matrix4 extractBasis(Vector3 xAxis, Vector3 yAxis, Vector3 zAxis){
        float[] te = this.elements;

        xAxis.set( te[ 0 ], te[ 1 ], te[ 2 ] );
        yAxis.set( te[ 4 ], te[ 5 ], te[ 6 ] );
        zAxis.set( te[ 8 ], te[ 9 ], te[ 10 ] );

        return this;
    }
    public Matrix4 makeBasis(Vector3 xAxis, Vector3 yAxis, Vector3 zAxis){
        this.set(
                xAxis.getX(), yAxis.getX(), zAxis.getX(), 0,
                xAxis.getY(), yAxis.getY(), zAxis.getY(), 0,
                xAxis.getZ(), yAxis.getZ(), zAxis.getZ(), 0,
                0, 0, 0, 1
        );

        return this;
    }
    public Matrix4 extractRotation(Matrix4 m){
        Vector3 v1 = new Vector3();

        float[] te = this.elements;
        float[] me = m.getElements();

        float scaleX = 1f / v1.set( me[ 0 ], me[ 1 ], me[ 2 ] ).length();
        float scaleY = 1f / v1.set( me[ 4 ], me[ 5 ], me[ 6 ] ).length();
        float scaleZ = 1f / v1.set( me[ 8 ], me[ 9 ], me[ 10 ] ).length();

        te[ 0 ] = me[ 0 ] * scaleX;
        te[ 1 ] = me[ 1 ] * scaleX;
        te[ 2 ] = me[ 2 ] * scaleX;

        te[ 4 ] = me[ 4 ] * scaleY;
        te[ 5 ] = me[ 5 ] * scaleY;
        te[ 6 ] = me[ 6 ] * scaleY;

        te[ 8 ] = me[ 8 ] * scaleZ;
        te[ 9 ] = me[ 9 ] * scaleZ;
        te[ 10 ] = me[ 10 ] * scaleZ;

        return this;
    }
    public Matrix4 makeRotationFromEuler(Euler euler){
        float[] te = this.elements;

        float x = euler.getX(), y = euler.getY(), z = euler.getZ();
        float a = (float) Math.cos( x ), b = (float) Math.sin( x );
        float c = (float) Math.cos( y ), d = (float) Math.sin( y );
        float e = (float) Math.cos( z ), f = (float) Math.sin( z );

        if ( euler.getOrder() == "XYZ" ) {

            float ae = a * e, af = a * f, be = b * e, bf = b * f;

            te[ 0 ] = c * e;
            te[ 4 ] = - c * f;
            te[ 8 ] = d;

            te[ 1 ] = af + be * d;
            te[ 5 ] = ae - bf * d;
            te[ 9 ] = - b * c;

            te[ 2 ] = bf - ae * d;
            te[ 6 ] = be + af * d;
            te[ 10 ] = a * c;

        } else if ( euler.getOrder() == "YXZ" ) {

            float ce = c * e, cf = c * f, de = d * e, df = d * f;

            te[ 0 ] = ce + df * b;
            te[ 4 ] = de * b - cf;
            te[ 8 ] = a * d;

            te[ 1 ] = a * f;
            te[ 5 ] = a * e;
            te[ 9 ] = - b;

            te[ 2 ] = cf * b - de;
            te[ 6 ] = df + ce * b;
            te[ 10 ] = a * c;

        } else if ( euler.getOrder() == "ZXY" ) {

            float ce = c * e, cf = c * f, de = d * e, df = d * f;

            te[ 0 ] = ce - df * b;
            te[ 4 ] = - a * f;
            te[ 8 ] = de + cf * b;

            te[ 1 ] = cf + de * b;
            te[ 5 ] = a * e;
            te[ 9 ] = df - ce * b;

            te[ 2 ] = - a * d;
            te[ 6 ] = b;
            te[ 10 ] = a * c;

        } else if ( euler.getOrder() == "ZYX" ) {

            float ae = a * e, af = a * f, be = b * e, bf = b * f;

            te[ 0 ] = c * e;
            te[ 4 ] = be * d - af;
            te[ 8 ] = ae * d + bf;

            te[ 1 ] = c * f;
            te[ 5 ] = bf * d + ae;
            te[ 9 ] = af * d - be;

            te[ 2 ] = - d;
            te[ 6 ] = b * c;
            te[ 10 ] = a * c;

        } else if ( euler.getOrder() == "YZX" ) {

            float ac = a * c, ad = a * d, bc = b * c, bd = b * d;

            te[ 0 ] = c * e;
            te[ 4 ] = bd - ac * f;
            te[ 8 ] = bc * f + ad;

            te[ 1 ] = f;
            te[ 5 ] = a * e;
            te[ 9 ] = - b * e;

            te[ 2 ] = - d * e;
            te[ 6 ] = ad * f + bc;
            te[ 10 ] = ac - bd * f;

        } else if ( euler.getOrder() == "XZY" ) {

            float ac = a * c, ad = a * d, bc = b * c, bd = b * d;

            te[ 0 ] = c * e;
            te[ 4 ] = - f;
            te[ 8 ] = d * e;

            te[ 1 ] = ac * f + bd;
            te[ 5 ] = a * e;
            te[ 9 ] = ad * f - bc;

            te[ 2 ] = bc * f - ad;
            te[ 6 ] = b * e;
            te[ 10 ] = bd * f + ac;

        }

        // last column
        te[ 3 ] = 0;
        te[ 7 ] = 0;
        te[ 11 ] = 0;

        // bottom row
        te[ 12 ] = 0;
        te[ 13 ] = 0;
        te[ 14 ] = 0;
        te[ 15 ] = 1;

        return this;
    }
    public Matrix4 makeRotationFromQuaternion(Quaternion q){
        float[] te = this.elements;

        float x = q.getX(), y = q.getY(), z = q.getZ(), w = q.getW();
        float x2 = x + x, y2 = y + y, z2 = z + z;
        float xx = x * x2, xy = x * y2, xz = x * z2;
        float yy = y * y2, yz = y * z2, zz = z * z2;
        float wx = w * x2, wy = w * y2, wz = w * z2;

        te[ 0 ] = 1 - ( yy + zz );
        te[ 4 ] = xy - wz;
        te[ 8 ] = xz + wy;

        te[ 1 ] = xy + wz;
        te[ 5 ] = 1 - ( xx + zz );
        te[ 9 ] = yz - wx;

        te[ 2 ] = xz - wy;
        te[ 6 ] = yz + wx;
        te[ 10 ] = 1 - ( xx + yy );

        // last column
        te[ 3 ] = 0;
        te[ 7 ] = 0;
        te[ 11 ] = 0;

        // bottom row
        te[ 12 ] = 0;
        te[ 13 ] = 0;
        te[ 14 ] = 0;
        te[ 15 ] = 1;

        return this;
    }
    public Matrix4 lookAt(Vector3 eye, Vector3 target, Vector3 up ){
        Vector3 x = new Vector3();
        Vector3 y = new Vector3();
        Vector3 z = new Vector3();

        float[] te = this.elements;

        z.subVectors( eye, target ).normalize();

        if ( z.lengthSq() == 0 ) {

            z.setZ(1f);

        }

        x.crossVectors( up, z ).normalize();

        if ( x.lengthSq() == 0 ) {
            z.setX(z.getX()+0.0001f);
            x.crossVectors( up, z ).normalize();

        }

        y.crossVectors( z, x );


        te[ 0 ] = x.getX(); te[ 4 ] = y.getX(); te[ 8 ] = z.getX();
        te[ 1 ] = x.getY(); te[ 5 ] = y.getY(); te[ 9 ] = z.getY();
        te[ 2 ] = x.getZ(); te[ 6 ] = y.getZ(); te[ 10 ] = z.getZ();
        return this;
    }
    public Matrix4 multiply(Matrix4 m, Matrix4 n){
        if ( n !=null ) {
            return this.multiplyMatrices( m, n );
        }
        return this.multiplyMatrices( this, m );
    }
    public Matrix4 multiply(Matrix4 m){
         return this.multiply(m,null);
    }
    public Matrix4 multiplyMatrices(Matrix4 a, Matrix4 b){
        float[] ae = a.getElements();
        float[] be = b.getElements();
        float[] te = this.elements;

        float a11 = ae[ 0 ], a12 = ae[ 4 ], a13 = ae[ 8 ], a14 = ae[ 12 ];
        float a21 = ae[ 1 ], a22 = ae[ 5 ], a23 = ae[ 9 ], a24 = ae[ 13 ];
        float a31 = ae[ 2 ], a32 = ae[ 6 ], a33 = ae[ 10 ], a34 = ae[ 14 ];
        float a41 = ae[ 3 ], a42 = ae[ 7 ], a43 = ae[ 11 ], a44 = ae[ 15 ];

        float b11 = be[ 0 ], b12 = be[ 4 ], b13 = be[ 8 ], b14 = be[ 12 ];
        float b21 = be[ 1 ], b22 = be[ 5 ], b23 = be[ 9 ], b24 = be[ 13 ];
        float b31 = be[ 2 ], b32 = be[ 6 ], b33 = be[ 10 ], b34 = be[ 14 ];
        float b41 = be[ 3 ], b42 = be[ 7 ], b43 = be[ 11 ], b44 = be[ 15 ];

        te[ 0 ] = a11 * b11 + a12 * b21 + a13 * b31 + a14 * b41;
        te[ 4 ] = a11 * b12 + a12 * b22 + a13 * b32 + a14 * b42;
        te[ 8 ] = a11 * b13 + a12 * b23 + a13 * b33 + a14 * b43;
        te[ 12 ] = a11 * b14 + a12 * b24 + a13 * b34 + a14 * b44;

        te[ 1 ] = a21 * b11 + a22 * b21 + a23 * b31 + a24 * b41;
        te[ 5 ] = a21 * b12 + a22 * b22 + a23 * b32 + a24 * b42;
        te[ 9 ] = a21 * b13 + a22 * b23 + a23 * b33 + a24 * b43;
        te[ 13 ] = a21 * b14 + a22 * b24 + a23 * b34 + a24 * b44;

        te[ 2 ] = a31 * b11 + a32 * b21 + a33 * b31 + a34 * b41;
        te[ 6 ] = a31 * b12 + a32 * b22 + a33 * b32 + a34 * b42;
        te[ 10 ] = a31 * b13 + a32 * b23 + a33 * b33 + a34 * b43;
        te[ 14 ] = a31 * b14 + a32 * b24 + a33 * b34 + a34 * b44;

        te[ 3 ] = a41 * b11 + a42 * b21 + a43 * b31 + a44 * b41;
        te[ 7 ] = a41 * b12 + a42 * b22 + a43 * b32 + a44 * b42;
        te[ 11 ] = a41 * b13 + a42 * b23 + a43 * b33 + a44 * b43;
        te[ 15 ] = a41 * b14 + a42 * b24 + a43 * b34 + a44 * b44;

        return this;
    }
    public Matrix4 multiplyToArray(Matrix4 a, Matrix4 b, float[] r){
        this.multiplyMatrices( a, b );
        float[] te = this.elements;

        r[ 0 ] = te[ 0 ]; r[ 1 ] = te[ 1 ]; r[ 2 ] = te[ 2 ]; r[ 3 ] = te[ 3 ];
        r[ 4 ] = te[ 4 ]; r[ 5 ] = te[ 5 ]; r[ 6 ] = te[ 6 ]; r[ 7 ] = te[ 7 ];
        r[ 8 ]  = te[ 8 ]; r[ 9 ]  = te[ 9 ]; r[ 10 ] = te[ 10 ]; r[ 11 ] = te[ 11 ];
        r[ 12 ] = te[ 12 ]; r[ 13 ] = te[ 13 ]; r[ 14 ] = te[ 14 ]; r[ 15 ] = te[ 15 ];

        return this;
    }
    public Matrix4 multiplyScalar(float s){
        float[] te = this.elements;

        te[ 0 ] *= s; te[ 4 ] *= s; te[ 8 ] *= s; te[ 12 ] *= s;
        te[ 1 ] *= s; te[ 5 ] *= s; te[ 9 ] *= s; te[ 13 ] *= s;
        te[ 2 ] *= s; te[ 6 ] *= s; te[ 10 ] *= s; te[ 14 ] *= s;
        te[ 3 ] *= s; te[ 7 ] *= s; te[ 11 ] *= s; te[ 15 ] *= s;

        return this;

    }
    /*
    public BufferAttribute applyToBuffer(BufferAttribute buffer, int offset, int length){
        Vector3 v1 = new Vector3();
        if ( length ==0 ) length = buffer.length() / buffer.getItemSize();

        for ( int i = 0, j = offset; i < length; i ++, j ++ ) {


            v1.setX(buffer.getX(j));
            v1.setY(buffer.getY(j));
            v1.setZ(buffer.getZ(j));

            v1.applyMatrix4( this );

            buffer.setXYZ( j,v1.getX(), v1.getY(), v1.getZ() );

        }

        return buffer;
    }
    */
    public float determinant(){
        float[] te = this.elements;

        float n11 = te[ 0 ], n12 = te[ 4 ], n13 = te[ 8 ], n14 = te[ 12 ];
        float n21 = te[ 1 ], n22 = te[ 5 ], n23 = te[ 9 ], n24 = te[ 13 ];
        float n31 = te[ 2 ], n32 = te[ 6 ], n33 = te[ 10 ], n34 = te[ 14 ];
        float n41 = te[ 3 ], n42 = te[ 7 ], n43 = te[ 11 ], n44 = te[ 15 ];

        //TODO: make this more efficient
        //( based on http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/fourD/index.htm )

        return (
                n41 * (
                        + n14 * n23 * n32
                                - n13 * n24 * n32
                                - n14 * n22 * n33
                                + n12 * n24 * n33
                                + n13 * n22 * n34
                                - n12 * n23 * n34
                ) +
                        n42 * (
                                + n11 * n23 * n34
                                        - n11 * n24 * n33
                                        + n14 * n21 * n33
                                        - n13 * n21 * n34
                                        + n13 * n24 * n31
                                        - n14 * n23 * n31
                        ) +
                        n43 * (
                                + n11 * n24 * n32
                                        - n11 * n22 * n34
                                        - n14 * n21 * n32
                                        + n12 * n21 * n34
                                        + n14 * n22 * n31
                                        - n12 * n24 * n31
                        ) +
                        n44 * (
                                - n13 * n22 * n31
                                        - n11 * n23 * n32
                                        + n11 * n22 * n33
                                        + n13 * n21 * n32
                                        - n12 * n21 * n33
                                        + n12 * n23 * n31
                        )

        );
    }
    public Matrix4 transpose(){
        float[] te = this.elements;
        float tmp;

        tmp = te[ 1 ]; te[ 1 ] = te[ 4 ]; te[ 4 ] = tmp;
        tmp = te[ 2 ]; te[ 2 ] = te[ 8 ]; te[ 8 ] = tmp;
        tmp = te[ 6 ]; te[ 6 ] = te[ 9 ]; te[ 9 ] = tmp;

        tmp = te[ 3 ]; te[ 3 ] = te[ 12 ]; te[ 12 ] = tmp;
        tmp = te[ 7 ]; te[ 7 ] = te[ 13 ]; te[ 13 ] = tmp;
        tmp = te[ 11 ]; te[ 11 ] = te[ 14 ]; te[ 14 ] = tmp;

        return this;
    }
    public float[] flattenToArrayOffset(float[] array,int offset){
        float[] te = this.elements;

        array[ offset ] = te[ 0 ];
        array[ offset + 1 ] = te[ 1 ];
        array[ offset + 2 ] = te[ 2 ];
        array[ offset + 3 ] = te[ 3 ];

        array[ offset + 4 ] = te[ 4 ];
        array[ offset + 5 ] = te[ 5 ];
        array[ offset + 6 ] = te[ 6 ];
        array[ offset + 7 ] = te[ 7 ];

        array[ offset + 8 ]  = te[ 8 ];
        array[ offset + 9 ]  = te[ 9 ];
        array[ offset + 10 ] = te[ 10 ];
        array[ offset + 11 ] = te[ 11 ];

        array[ offset + 12 ] = te[ 12 ];
        array[ offset + 13 ] = te[ 13 ];
        array[ offset + 14 ] = te[ 14 ];
        array[ offset + 15 ] = te[ 15 ];

        return array;
    }
    public Vector3 getPosition(){
        Vector3 v1 = new Vector3();
        float[] te = this.elements;
        return v1.set( te[ 12 ], te[ 13 ], te[ 14 ] );
    }
    public Matrix4 setPosition(Vector3 v){
        float[] te = this.elements;

        te[ 12 ] = v.getX();
        te[ 13 ] = v.getY();
        te[ 14 ] = v.getZ();

        return this;
    }
    public Matrix4 getInverse(Matrix4 m, boolean throwable) throws Exception{
        // based on http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/fourD/index.htm
        float[] te = this.elements;
        float[] me = m.getElements();

        float n11 = me[ 0 ], n12 = me[ 4 ], n13 = me[ 8 ], n14 = me[ 12 ];
        float n21 = me[ 1 ], n22 = me[ 5 ], n23 = me[ 9 ], n24 = me[ 13 ];
        float n31 = me[ 2 ], n32 = me[ 6 ], n33 = me[ 10 ], n34 = me[ 14 ];
        float n41 = me[ 3 ], n42 = me[ 7 ], n43 = me[ 11 ], n44 = me[ 15 ];

        te[ 0 ] = n23 * n34 * n42 - n24 * n33 * n42 + n24 * n32 * n43 - n22 * n34 * n43 - n23 * n32 * n44 + n22 * n33 * n44;
        te[ 4 ] = n14 * n33 * n42 - n13 * n34 * n42 - n14 * n32 * n43 + n12 * n34 * n43 + n13 * n32 * n44 - n12 * n33 * n44;
        te[ 8 ] = n13 * n24 * n42 - n14 * n23 * n42 + n14 * n22 * n43 - n12 * n24 * n43 - n13 * n22 * n44 + n12 * n23 * n44;
        te[ 12 ] = n14 * n23 * n32 - n13 * n24 * n32 - n14 * n22 * n33 + n12 * n24 * n33 + n13 * n22 * n34 - n12 * n23 * n34;
        te[ 1 ] = n24 * n33 * n41 - n23 * n34 * n41 - n24 * n31 * n43 + n21 * n34 * n43 + n23 * n31 * n44 - n21 * n33 * n44;
        te[ 5 ] = n13 * n34 * n41 - n14 * n33 * n41 + n14 * n31 * n43 - n11 * n34 * n43 - n13 * n31 * n44 + n11 * n33 * n44;
        te[ 9 ] = n14 * n23 * n41 - n13 * n24 * n41 - n14 * n21 * n43 + n11 * n24 * n43 + n13 * n21 * n44 - n11 * n23 * n44;
        te[ 13 ] = n13 * n24 * n31 - n14 * n23 * n31 + n14 * n21 * n33 - n11 * n24 * n33 - n13 * n21 * n34 + n11 * n23 * n34;
        te[ 2 ] = n22 * n34 * n41 - n24 * n32 * n41 + n24 * n31 * n42 - n21 * n34 * n42 - n22 * n31 * n44 + n21 * n32 * n44;
        te[ 6 ] = n14 * n32 * n41 - n12 * n34 * n41 - n14 * n31 * n42 + n11 * n34 * n42 + n12 * n31 * n44 - n11 * n32 * n44;
        te[ 10 ] = n12 * n24 * n41 - n14 * n22 * n41 + n14 * n21 * n42 - n11 * n24 * n42 - n12 * n21 * n44 + n11 * n22 * n44;
        te[ 14 ] = n14 * n22 * n31 - n12 * n24 * n31 - n14 * n21 * n32 + n11 * n24 * n32 + n12 * n21 * n34 - n11 * n22 * n34;
        te[ 3 ] = n23 * n32 * n41 - n22 * n33 * n41 - n23 * n31 * n42 + n21 * n33 * n42 + n22 * n31 * n43 - n21 * n32 * n43;
        te[ 7 ] = n12 * n33 * n41 - n13 * n32 * n41 + n13 * n31 * n42 - n11 * n33 * n42 - n12 * n31 * n43 + n11 * n32 * n43;
        te[ 11 ] = n13 * n22 * n41 - n12 * n23 * n41 - n13 * n21 * n42 + n11 * n23 * n42 + n12 * n21 * n43 - n11 * n22 * n43;
        te[ 15 ] = n12 * n23 * n31 - n13 * n22 * n31 + n13 * n21 * n32 - n11 * n23 * n32 - n12 * n21 * n33 + n11 * n22 * n33;

        float det = n11 * te[ 0 ] + n21 * te[ 4 ] + n31 * te[ 8 ] + n41 * te[ 12 ];

        if ( det == 0 ) {

            if(throwable){
                throw new Exception("det is zero");
            }else{
                this.identity();
            }
            return this;
        }
        this.multiplyScalar( 1 / det );

        return this;
    }
    public Matrix4 getInverse(Matrix4 m) throws Exception{
        return this.getInverse(m,true);
    }
    public Matrix4 scale(Vector3 v){
        float[] te = this.elements;
        float x = v.getX(), y = v.getY(), z = v.getZ();

        te[ 0 ] *= x; te[ 4 ] *= y; te[ 8 ] *= z;
        te[ 1 ] *= x; te[ 5 ] *= y; te[ 9 ] *= z;
        te[ 2 ] *= x; te[ 6 ] *= y; te[ 10 ] *= z;
        te[ 3 ] *= x; te[ 7 ] *= y; te[ 11 ] *= z;

        return this;
    }
    public float getMaxScaleOnAxis(){
        float[] te = this.elements;

        float scaleXSq = te[ 0 ] * te[ 0 ] + te[ 1 ] * te[ 1 ] + te[ 2 ] * te[ 2 ];
        float scaleYSq = te[ 4 ] * te[ 4 ] + te[ 5 ] * te[ 5 ] + te[ 6 ] * te[ 6 ];
        float scaleZSq = te[ 8 ] * te[ 8 ] + te[ 9 ] * te[ 9 ] + te[ 10 ] * te[ 10 ];

        return (float) Math.sqrt( Math.max( scaleXSq, Math.max(scaleYSq, scaleZSq) ) );
    }
    public Matrix4 makeTranslation(float x, float y, float z){
        this.set(
                1, 0, 0, x,
                0, 1, 0, y,
                0, 0, 1, z,
                0, 0, 0, 1
        );
        return this;
    }
    public Matrix4 makeRotationX(float theta){
        float c = (float) Math.cos( theta ), s = (float) Math.sin( theta );
        this.set(
                1, 0,  0, 0,
                0, c, - s, 0,
                0, s,  c, 0,
                0, 0,  0, 1
        );
        return this;
    }
    public Matrix4 makeRotationY(float theta){
        float c = (float) Math.cos( theta ), s = (float) Math.sin( theta );

        this.set(
                c, 0, s, 0,
                0, 1, 0, 0,
                - s, 0, c, 0,
                0, 0, 0, 1
        );
        return this;
    }
    public Matrix4 makeRotationZ(float theta){
        float c = (float) Math.cos( theta ), s = (float) Math.sin( theta );

        this.set(
                c, - s, 0, 0,
                s,  c, 0, 0,
                0,  0, 1, 0,
                0,  0, 0, 1
        );

        return this;
    }
    public Matrix4 makeRotationAxis(Vector3 axis, float angle){
        // Based on http://www.gamedev.net/reference/articles/article1199.asp

        float c = (float) Math.cos( angle );
        float s = (float) Math.sin( angle );
        float t = 1 - c;
        float x = axis.getX(), y = axis.getY(), z = axis.getZ();
        float tx = t * x, ty = t * y;

        this.set(

                tx * x + c, tx * y - s * z, tx * z + s * y, 0,
                tx * y + s * z, ty * y + c, ty * z - s * x, 0,
                tx * z - s * y, ty * z + s * x, t * z * z + c, 0,
                0, 0, 0, 1

        );

        return this;
    }
    public Matrix4 makeScale(float x, float y, float z){
        this.set(

                x, 0, 0, 0,
                0, y, 0, 0,
                0, 0, z, 0,
                0, 0, 0, 1

        );

        return this;
    }
    public Matrix4 compose(Vector3 position, Quaternion quaternion, Vector3 scale){
        this.makeRotationFromQuaternion( quaternion );
        this.scale( scale );
        this.setPosition(position);

        return this;
    }
    public Matrix4 decompose(Vector3 position, Quaternion quaternion, Vector3 scale){
        Vector3 vector = new Vector3();
        Matrix4 matrix = new Matrix4();

        float[] te = this.elements;
        if(position!=null){
            position.setX(te[ 12 ]);
            position.setY(te[ 13 ]);
            position.setZ(te[ 14 ]);
        }
        if(quaternion!=null || scale!=null){
            float sx = vector.set( te[ 0 ], te[ 1 ], te[ 2 ] ).length();
            float sy = vector.set( te[ 4 ], te[ 5 ], te[ 6 ] ).length();
            float sz = vector.set( te[ 8 ], te[ 9 ], te[ 10 ] ).length();
            // if determine is negative, we need to invert one scale
            float det = this.determinant();
            if ( det < 0 ) {
                sx = - sx;
            }
            // scale the rotation part
            matrix.set( this.elements ); // at this point matrix is incomplete so we can't use .copy()
            float invSX = 1 / sx;
            float invSY = 1 / sy;
            float invSZ = 1 / sz;
            float[] e = matrix.getElements();
            e[ 0 ] *= invSX;
            e[ 1 ] *= invSX;
            e[ 2 ] *= invSX;
            e[ 4 ] *= invSY;
            e[ 5 ] *= invSY;
            e[ 6 ] *= invSY;
            e[ 8 ] *= invSZ;
            e[ 9 ] *= invSZ;
            e[ 10 ] *= invSZ;
            matrix.setElements(e);
            if(quaternion!=null){
                quaternion.setFromRotationMatrix( matrix );
            }
            if(scale!=null){
                scale.setX(sx);
                scale.setY(sy);
                scale.setZ(sz);
            }
        }
        return this;
    }
    public Matrix4 makeFrustum(float left, float right, float bottom, float top, float near, float far ){
        float[] te = this.elements;
        float x = 2 * near / ( right - left );
        float y = 2 * near / ( top - bottom );

        float a = ( right + left ) / ( right - left );
        float b = ( top + bottom ) / ( top - bottom );
        float c = - ( far + near ) / ( far - near );
        float d = - 2 * far * near / ( far - near );

        te[ 0 ] = x;	te[ 4 ] = 0;	te[ 8 ] = a;	te[ 12 ] = 0;
        te[ 1 ] = 0;	te[ 5 ] = y;	te[ 9 ] = b;	te[ 13 ] = 0;
        te[ 2 ] = 0;	te[ 6 ] = 0;	te[ 10 ] = c;	te[ 14 ] = d;
        te[ 3 ] = 0;	te[ 7 ] = 0;	te[ 11 ] = - 1;	te[ 15 ] = 0;
        return this;
    }
    public Matrix4 makePerspective(float fov, float aspect, float near, float far ){
        float ymax = near * (float) Math.tan( MathUtils.degToRad( fov * 0.5f ) );
        float ymin = - ymax;
        float xmin = ymin * aspect;
        float xmax = ymax * aspect;
        return this.makeFrustum( xmin, xmax, ymin, ymax, near, far );
    }
    public Matrix4 makeOrthographic(float left, float right, float top, float bottom, float near, float far ){
        float[] te = this.elements;
        float w = right - left;
        float h = top - bottom;
        float p = far - near;

        float x = ( right + left ) / w;
        float y = ( top + bottom ) / h;
        float z = ( far + near ) / p;

        te[ 0 ] = 2 / w;	te[ 4 ] = 0;	te[ 8 ] = 0;	te[ 12 ] = - x;
        te[ 1 ] = 0;	te[ 5 ] = 2 / h;	te[ 9 ] = 0;	te[ 13 ] = - y;
        te[ 2 ] = 0;	te[ 6 ] = 0;	te[ 10 ] = - 2 / p;	te[ 14 ] = - z;
        te[ 3 ] = 0;	te[ 7 ] = 0;	te[ 11 ] = 0;	te[ 15 ] = 1;

        return this;
    }
    public boolean equals(Matrix4 matrix){
        float[] te = this.elements;
        float[] me = matrix.getElements();

        for ( int i = 0; i < 16; i ++ ) {

            if ( te[ i ] != me[ i ] ) return false;

        }

        return true;
    }
    public Matrix4 fromArray(float[] array){
        return this.set( array );
    }
    public float[] toArray() {
        float[] te = this.elements;
        return new float[]{
                te[ 0 ], te[ 1 ], te[ 2 ], te[ 3 ],
                te[ 4 ], te[ 5 ], te[ 6 ], te[ 7 ],
                te[ 8 ], te[ 9 ], te[ 10 ], te[ 11 ],
                te[ 12 ], te[ 13 ], te[ 14 ], te[ 15 ]
        };
    }
    public float[] applyToVector3Array(float[] array,int offset,int length){
        Vector3 v1 = new Vector3();
        if ( length <0 ) length = array.length;
        for ( int i = 0, j = offset; i < length; i += 3, j += 3 ) {
            v1.fromArray( array, j );
            v1.applyMatrix4( this );
            v1.toArray( array, j );
        }
        return array;
    }
    public String toString(){
        String str ="[";
        for(int i=0; i<this.elements.length;i++){
            str+=this.elements[i];
            if(i<this.elements.length-1){
                str+=",";
            }
        }
        str +="]";
        return str;
    }

}
