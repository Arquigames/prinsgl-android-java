package com.arquigames.prinsgl.geometries.buffers;

import android.util.Log;

import com.arquigames.prinsgl.gl.attributes.BufferAttribute;

/**
 * Created by usuario on 26/06/2016.
 */
public class CircleBufferGeometry extends BufferGeometry {

    private static String TAG = "CircleBufferGeometry";

    protected float radius;
    protected int segments;
    protected float thetaStart;
    protected float thetaLength;

    public CircleBufferGeometry(float radius,int segments,float thetaStart,float thetaLength ){
        super();

        radius = radius<=0 ? 1:radius;

        segments = segments <3 ? 3: segments;
        thetaStart = thetaStart <=0 ? 0: thetaStart;

        thetaLength = thetaLength <=0 ? (float) Math.PI * 2 : thetaLength;


        this.setRadius(radius);
        this.setSegments(segments);
        this.setThetaStart(thetaStart);
        this.setThetaLength(thetaLength);

        int vertices = segments + 2;

        this.vertices = new float[vertices * 3];
        this.normals = new float[vertices * 3];
        this.uvs = new float[vertices * 2];

        // center data is already zero, but need to set a few extras
        this.normals[ 2 ] = 1.0f;
        this.uvs[ 0 ] = 0.5f;
        this.uvs[ 1 ] = 0.5f;

        for ( int s = 0, i = 3, ii = 2 ; s <= segments; s ++, i += 3, ii += 2 ) {

            float segment = thetaStart + ((1f*s) / segments) * thetaLength;

            this.vertices[ i ] = radius * (float) Math.cos( segment );
            this.vertices[ i + 1 ] = radius * (float) Math.sin( segment );

            this.normals[ i + 2 ] = 1; // normal z

            this.uvs[ ii ] = ( this.vertices[ i ] / radius + 1 ) / 2;
            this.uvs[ ii + 1 ] = ( this.vertices[ i + 1 ] / radius + 1 ) / 2;

            Log.e(TAG,"build -> vertices[i="+i+"]="+this.vertices[i]);
            Log.e(TAG,"build -> vertices[i="+(i+1)+"]="+this.vertices[i+1]);
            Log.e(TAG,"build -> vertices[i="+(i+2)+"]="+this.vertices[i+2]);

        }

        java.util.LinkedList<Short> indices = new java.util.LinkedList<Short>();

        for ( short i = 1; i <= segments; i ++ ) {

            indices.add(i);
            indices.add((short)(i + 1));
            indices.add((short)0);

        }

        if(indices.size()>0){
            this.indices = new short[indices.size()];
            int _indicesCount = 0;
            java.util.Iterator<Short> _it = indices.iterator();
            while(_it.hasNext()){
                this.indices[_indicesCount++] = _it.next();
                Log.e(TAG,"build -> indices[i="+(_indicesCount-1)+"]="+this.indices[_indicesCount-1]);
            }
        }


        if(this.indices!=null)this.setIndex( new BufferAttribute(this.indices, 1) );
        this.addAttribute( "position", new BufferAttribute( this.vertices, 3 ) );
        this.addAttribute( "normal", new BufferAttribute( this.normals, 3 ) );
        this.addAttribute( "uv", new BufferAttribute( this.uvs, 2 ) );


    }
    public CircleBufferGeometry(){
        this(0,0,0,0);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getSegments() {
        return segments;
    }

    public void setSegments(int segments) {
        this.segments = segments;
    }

    public float getThetaStart() {
        return thetaStart;
    }

    public void setThetaStart(float thetaStart) {
        this.thetaStart = thetaStart;
    }

    public float getThetaLength() {
        return thetaLength;
    }

    public void setThetaLength(float thetaLength) {
        this.thetaLength = thetaLength;
    }

}
