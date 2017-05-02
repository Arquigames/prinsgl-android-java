package com.arquigames.prinsgl.maths.matrix;

import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 26/06/2016.
 */
public class Matrix3 {
    private float[] elements = new float[]{
            1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
    };
    public Matrix3(){

    }
    public Matrix3(

            float n11, float n12, float n13,
            float n21,float  n22, float n23,
            float n31, float n32, float n33

    ){
        this.set(
            n11,n12,n13,
            n21, n22,n23,
            n31,n32,n33
        );
    }
    public Matrix3(float[] elements){
        for(int i=0;i<elements.length;i++){
            this.elements[i] = elements[i];
        }
    }
    public float[] getElements(){
        return this.elements;
    }
    public Matrix3 set(
            float n11, float n12, float n13,
            float n21,float  n22, float n23,
            float n31, float n32, float n33
    ){
        float[] te = this.elements;

        te[ 0 ] = n11; te[ 3 ] = n12; te[ 6 ] = n13;
        te[ 1 ] = n21; te[ 4 ] = n22; te[ 7 ] = n23;
        te[ 2 ] = n31; te[ 5 ] = n32; te[ 8 ] = n33;

        return this;
    }
    public Matrix3 identity(){
        this.set(
                1, 0, 0,
                0, 1, 0,
                0, 0, 1
        );
        return this;
    }
    public Matrix3 clone(){
        return new Matrix3(this.elements);
    }
    public Matrix3 copy(Matrix3 m){
        float[] me = m.getElements();

        this.set(

                me[ 0 ], me[ 3 ], me[ 6 ],
                me[ 1 ], me[ 4 ], me[ 7 ],
                me[ 2 ], me[ 5 ], me[ 8 ]

        );

        return this;
    }
    public Vector3 multiplyVector3(Vector3 vector){
        return vector.applyMatrix3(this);
    }
    public Matrix3 multiplyScalar(float s){
        float[] te = this.elements;
        te[ 0 ] *= s; te[ 3 ] *= s; te[ 6 ] *= s;
        te[ 1 ] *= s; te[ 4 ] *= s; te[ 7 ] *= s;
        te[ 2 ] *= s; te[ 5 ] *= s; te[ 8 ] *= s;
        return this;
    }
    public float determinant(){
        float[] te = this.elements;
        float
                a = te[ 0 ], b = te[ 1 ], c = te[ 2 ],
                d = te[ 3 ], e = te[ 4 ], f = te[ 5 ],
                g = te[ 6 ], h = te[ 7 ], i = te[ 8 ];

        return a * e * i - a * f * h - b * d * i + b * f * g + c * d * h - c * e * g;
    }
    public Matrix3 getInverse(Matrix4 matrix, boolean throwable) throws Exception{
        // ( based on http://code.google.com/p/webgl-mjs/ )

        float[] me = matrix.getElements();
        float[] te = this.elements;

        te[ 0 ] =   me[ 10 ] * me[ 5 ] - me[ 6 ] * me[ 9 ];
        te[ 1 ] = - me[ 10 ] * me[ 1 ] + me[ 2 ] * me[ 9 ];
        te[ 2 ] =   me[ 6  ] * me[ 1 ] - me[ 2 ] * me[ 5 ];
        te[ 3 ] = - me[ 10 ] * me[ 4 ] + me[ 6 ] * me[ 8 ];
        te[ 4 ] =   me[ 10 ] * me[ 0 ] - me[ 2 ] * me[ 8 ];
        te[ 5 ] = - me[ 6  ] * me[ 0 ] + me[ 2 ] * me[ 4 ];
        te[ 6 ] =   me[ 9  ] * me[ 4 ] - me[ 5 ] * me[ 8 ];
        te[ 7 ] = - me[ 9  ] * me[ 0 ] + me[ 1 ] * me[ 8 ];
        te[ 8 ] =   me[ 5  ] * me[ 0 ] - me[ 1 ] * me[ 4 ];

        float det = me[ 0 ] * te[ 0 ] + me[ 1 ] * te[ 3 ] + me[ 2 ] * te[ 6 ];

        // no inverse

        if ( det == 0 ) {
            if(throwable){
                throw new Exception("det is zero");
            }else{
                this.identity();
            }
            return this;
        }
        this.multiplyScalar( 1.0f / det );
        return this;
    }
    public Matrix3 transpose(){
        float tmp;float[] m = this.elements;

        tmp = m[ 1 ]; m[ 1 ] = m[ 3 ]; m[ 3 ] = tmp;
        tmp = m[ 2 ]; m[ 2 ] = m[ 6 ]; m[ 6 ] = tmp;
        tmp = m[ 5 ]; m[ 5 ] = m[ 7 ]; m[ 7 ] = tmp;

        return this;
    }
    public float[] flattenToArrayOffset(float[] array, int offset){
        float[] te = this.elements;

        if(offset+8>=array.length)return null;

        array[ offset ] 	= te[ 0 ];
        array[ offset + 1 ] = te[ 1 ];
        array[ offset + 2 ] = te[ 2 ];

        array[ offset + 3 ] = te[ 3 ];
        array[ offset + 4 ] = te[ 4 ];
        array[ offset + 5 ] = te[ 5 ];

        array[ offset + 6 ] = te[ 6 ];
        array[ offset + 7 ] = te[ 7 ];
        array[ offset + 8 ] = te[ 8 ];

        return array;
    }
    public Matrix3 getNormalMatrix(Matrix4 m) throws Exception{
        // input: Matrix4
        this.getInverse( m,true ).transpose();
        return this;
    }
    public Matrix3 transposeIntoArray(float[] r){
        float[] m = this.elements;

        r[ 0 ] = m[ 0 ];
        r[ 1 ] = m[ 3 ];
        r[ 2 ] = m[ 6 ];
        r[ 3 ] = m[ 1 ];
        r[ 4 ] = m[ 4 ];
        r[ 5 ] = m[ 7 ];
        r[ 6 ] = m[ 2 ];
        r[ 7 ] = m[ 5 ];
        r[ 8 ] = m[ 8 ];

        return this;
    }
    public Matrix3 fromArray(float[] array){
        for(int i=0;i<9;i++){
            if(i<array.length){
                this.elements[i] = array[i];
            }
        }
        return this;
    }
    public float[] toArray(){
        return this.elements;
    }
    public float[] applyToVector3Array(float[] array,int offset,int length){

        /*
        * apply this matrix to each 3-floats values array, from offset width length of 'length',
        * length must be multiple of 3, and array should also be.
        * */
        Vector3 v1 = new Vector3();
        if ( length <0 ) length = array.length;
        for ( int i = 0, j = offset; i < length; i += 3, j += 3 ) {
            v1.fromArray( array, j );
            v1.applyMatrix3( this );
            v1.toArray( array, j );
        }
        return array;
    }
}
