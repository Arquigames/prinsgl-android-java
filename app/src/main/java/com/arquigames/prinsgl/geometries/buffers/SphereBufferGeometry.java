package com.arquigames.prinsgl.geometries.buffers;

import android.opengl.GLES20;

import com.arquigames.prinsgl.gl.attributes.BufferAttribute;
import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 26/06/2016.
 */
public class SphereBufferGeometry extends BufferGeometry {

    public static boolean DEBUG  = false;

    protected float radius;
    protected int widthSegments;
    protected int heightSegments;
    protected float phiStart;
    protected float phiLength;
    protected float thetaStart;
    protected float thetaLength;

    public SphereBufferGeometry(
            float radius,
            int widthSegments,
            int heightSegments,
            float phiStart,
            float phiLength,
            float thetaStart,
            float thetaLength
    ){
        super();

        this.setRadius(radius<=0 ? 5: radius);
        this.setWidthSegments(widthSegments>3 ? widthSegments : 8);
        this.setHeightSegments(heightSegments>2 ? heightSegments : 6);
        this.setPhiStart(phiStart);
        this.setPhiLength(phiLength !=0 ? phiLength : (float) Math.PI * 2);
        this.setThetaStart(thetaStart);
        this.setThetaLength(thetaLength !=0 ? thetaLength : (float) Math.PI);

        float thetaEnd = this.getThetaStart() + this.getThetaLength();

        int vertexCount = ( ( this.getWidthSegments() + 1 ) * ( this.getHeightSegments() + 1 ) );

        BufferAttribute positions   = new BufferAttribute(  vertexCount * 3 , 3, GLES20.GL_FLOAT );
        BufferAttribute normals     = new BufferAttribute(  vertexCount * 3 , 3, GLES20.GL_FLOAT );
        BufferAttribute uvs         = new BufferAttribute(  vertexCount * 2 , 2, GLES20.GL_FLOAT );

        int index = 0;
        java.util.LinkedList<java.util.LinkedList<Integer>> vertices = new java.util.LinkedList<java.util.LinkedList<Integer>>();
        java.util.LinkedList<Integer> verticesRow = null;
        Vector3 normal = new Vector3();

        for ( int y = 0; y <= this.getHeightSegments(); y ++ ) {

            verticesRow = new java.util.LinkedList<Integer>();

            float v = y*1f / this.getHeightSegments();

            for ( int x = 0; x <= this.getWidthSegments(); x ++ ) {

                float u = x*1f / this.getWidthSegments();

                float px = - this.getRadius() * (float) Math.cos( this.getPhiStart() + u * this.getPhiLength() ) * (float) Math.sin( this.getThetaStart() + v * this.getThetaLength());
                float py =   this.getRadius() * (float) Math.cos( this.getThetaStart() + v * this.getThetaLength() );
                float pz =   this.getRadius() * (float) Math.sin( this.getPhiStart() + u * this.getPhiLength() ) * (float) Math.sin( this.getThetaStart() + v * this.getThetaLength() );

                normal.set( px, py, pz ).normalize();

                positions.setXYZ( index, px, py, pz );
                normals.setXYZ( index, normal.getX(), normal.getY(), normal.getZ() );
                uvs.setXY( index, u, 1 - v );

                verticesRow.add( index );

                index ++;

            }

            vertices.add( verticesRow );

        }


        java.util.LinkedList<Short> indices = new java.util.LinkedList<Short>();

        java.util.LinkedList<Integer> temp_v = null;

        for ( int y = 0; y < this.getHeightSegments(); y ++ ) {

            for ( int x = 0; x < this.getWidthSegments(); x ++ ) {

                temp_v = vertices.get(y);
                int v1 = temp_v.get(x+1);
                int v2 = temp_v.get(x);

                temp_v = vertices.get(y+1);

                int v3 = temp_v.get(x);
                int v4 = temp_v.get(x+1);

                if ( y != 0 || this.getThetaStart() > 0 ){
                    indices.add( (short)v1);
                    indices.add( (short)v2);
                    indices.add( (short)v4);
                }
                if ( y != this.getHeightSegments() - 1 || thetaEnd < Math.PI ){
                    indices.add( (short)v2);
                    indices.add( (short)v3);
                    indices.add( (short)v4);
                }

            }

        }

        java.util.Iterator<Short> _it = indices.iterator();
        if(indices.size()>0){
            this.shortIndices = new short[indices.size()];
            int _indicesCount=0;
            while(_it.hasNext()){
                this.shortIndices[_indicesCount++] = _it.next();
            }
        }

        if(this.shortIndices !=null)this.setIndex(new BufferAttribute(this.shortIndices, 1));
        this.addAttribute( "position", positions );
        this.addAttribute( "normal", normals );
        this.addAttribute( "uv", uvs );
    }
    public SphereBufferGeometry(){
        this(0,0,0,0,0,0,0);
    }
    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getWidthSegments() {
        return widthSegments;
    }

    public void setWidthSegments(int widthSegments) {
        this.widthSegments = widthSegments;
    }

    public int getHeightSegments() {
        return heightSegments;
    }

    public void setHeightSegments(int heightSegments) {
        this.heightSegments = heightSegments;
    }

    public float getPhiStart() {
        return phiStart;
    }

    public void setPhiStart(float phiStart) {
        this.phiStart = phiStart;
    }

    public float getPhiLength() {
        return phiLength;
    }

    public void setPhiLength(float phiLength) {
        this.phiLength = phiLength;
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
