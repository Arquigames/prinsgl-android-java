package com.arquigames.prinsgl.geometries.buffers;

import android.util.Log;

import com.arquigames.prinsgl.gl.attributes.BufferAttribute;

/**
 * Created by usuario on 10/08/2016.
 */
public class PlaneBufferGeometry extends BufferGeometry {

    private static String TAG = "PlaneBufferGeometry";

    protected float width;
    protected float height;
    protected int widthSegments;
    protected int heightSegments;

    protected float width_half;
    protected float height_half;

    protected int gridX;
    protected int gridY;

    protected int gridX1;
    protected int gridY1;

    protected float segment_width;
    protected float segment_height;


    protected int offset;
    protected int offset2;

    public PlaneBufferGeometry(float width,float height, int widthSegments,int heightSegments){
        super();
        this.setWidth(width<=0 ? 2:width);
        this.setHeight(height<=0 ? 2:height);
        this.setWidthSegments(widthSegments<=0 ? 2:widthSegments);
        this.setHeightSegments(heightSegments<=0 ? 2: heightSegments);

        this.setWidth_half(this.getWidth() /2);
        this.setHeight_half(this.getHeight() /2);

        this.setGridX(this.getWidthSegments());
        this.setGridY(this.getHeightSegments());

        this.setGridX1(this.getGridX() +1);
        this.setGridY1(this.getGridY() +1);

        this.setSegment_width(this.getWidth() / this.getGridX());
        this.setSegment_height(this.getHeight() / this.getGridY());

        this.vertices = new float[this.getGridX1() * this.getGridY1() * 3];
        this.normals = new float[this.getGridX1() * this.getGridY1() * 3];
        this.uvs = new float[this.getGridX1() * this.getGridY1() * 2];

        int cc=0;
        //-------------------------------------------------------------
        for (int iy = 0; iy < this.getGridY1(); iy ++ ) {

            float y = iy * this.getSegment_height() - this.getHeight_half();

            for (int ix = 0; ix < this.getGridX1(); ix ++ ) {

                float x = ix * this.getSegment_width() - this.getWidth_half();

                this.vertices[this.getOffset()] = x;
                this.vertices[ this.getOffset() + 1 ] = - y;

                this.normals[ this.getOffset() + 2 ] = 1;

                this.uvs[this.getOffset2()] = ix*1f / this.getGridX();
                this.uvs[ this.getOffset2() + 1 ] = 1 - ( iy*1f / this.getGridY());

                String str_vertex = this.vertices[this.getOffset()]+","+this.vertices[ this.getOffset() + 1 ]+",0";
                String str_uv = this.uvs[this.getOffset2()]+","+this.uvs[ this.getOffset2() + 1 ];
                Log.e(TAG,"VERTEX - UV->"+cc+", ["+str_vertex+"], uv["+str_uv+"]");

                this.setOffset(this.getOffset() + 3);
                this.setOffset2(this.getOffset2() + 2);
                cc++;

            }

        }

        this.setOffset(0);

        this.indices = new short[this.getGridX() * this.getGridY() * 6];

        for (int iy = 0; iy < this.getGridY(); iy ++ ) {

            for (int ix = 0; ix < this.getGridX(); ix ++ ) {

                int a = ix + this.getGridX1() * iy;
                int b = ix + this.getGridX1() * ( iy + 1 );
                int c = ( ix + 1 ) + this.getGridX1() * ( iy + 1 );
                int d = ( ix + 1 ) + this.getGridX1() * iy;

                Log.e(TAG,"indices(a="+a+",b="+b+",d="+d+")");
                Log.e(TAG,"indices(b="+b+",c="+c+",d="+d+")");

                this.indices[this.getOffset()] = (short)a;
                this.indices[ this.getOffset() + 1 ] = (short)b;
                this.indices[ this.getOffset() + 2 ] = (short)d;

                this.indices[ this.getOffset() + 3 ] = (short)b;
                this.indices[ this.getOffset() + 4 ] = (short)c;
                this.indices[ this.getOffset() + 5 ] = (short)d;

                this.setOffset(this.getOffset() + 6);

            }

        }

        this.setIndex(new BufferAttribute(this.indices, 1) );
        this.addAttribute( "position", new BufferAttribute( this.vertices, 3 ) );
        this.addAttribute( "normal", new BufferAttribute( this.normals, 3 ) );
        this.addAttribute( "uv", new BufferAttribute( this.uvs, 2 ) );

    }
    public PlaneBufferGeometry(){
        this(0,0,0,0);
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
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

    public float getWidth_half() {
        return width_half;
    }

    public void setWidth_half(float width_half) {
        this.width_half = width_half;
    }

    public float getHeight_half() {
        return height_half;
    }

    public void setHeight_half(float height_half) {
        this.height_half = height_half;
    }

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public int getGridX1() {
        return gridX1;
    }

    public void setGridX1(int gridX1) {
        this.gridX1 = gridX1;
    }

    public int getGridY1() {
        return gridY1;
    }

    public void setGridY1(int gridY1) {
        this.gridY1 = gridY1;
    }

    public float getSegment_width() {
        return segment_width;
    }

    public void setSegment_width(float segment_width) {
        this.segment_width = segment_width;
    }

    public float getSegment_height() {
        return segment_height;
    }

    public void setSegment_height(float segment_height) {
        this.segment_height = segment_height;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset2() {
        return offset2;
    }

    public void setOffset2(int offset2) {
        this.offset2 = offset2;
    }
}
