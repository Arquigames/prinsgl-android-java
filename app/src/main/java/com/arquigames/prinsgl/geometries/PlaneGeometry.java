package com.arquigames.prinsgl.geometries;

import com.arquigames.prinsgl.GeometryUtils;
import com.arquigames.prinsgl.geometries.buffers.PlaneBufferGeometry;

/**
 * Created by usuario on 10/08/2016.
 */
public class PlaneGeometry extends Geometry{
    protected float width;
    protected float height;
    protected int widthSegments;
    protected int heightSegments;


    public PlaneGeometry(float width,float height, int widthSegments,int heightSegments){
        super();
        this.setWidth(width<=0 ? 2:width);
        this.setHeight(height<=0 ? 2:width);
        this.setWidthSegments(widthSegments<=0 ? 2:widthSegments);
        this.setHeightSegments(heightSegments<=0 ? 2:heightSegments);

        PlaneBufferGeometry bufferGeometry = new PlaneBufferGeometry(this.getWidth(),this.getHeight(),this.getWidthSegments(),this.getHeightSegments());
        GeometryUtils.buildGeometryFromBufferGeometry(this,bufferGeometry);

    }
    public PlaneGeometry(){
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
    @Override
    public Geometry copy(Geometry geometry){
        super.copy(geometry);
        if(geometry instanceof PlaneGeometry){
            PlaneGeometry source = (PlaneGeometry)geometry;
            this.setWidth(source.getWidth());
            this.setHeight(source.getHeight());
            this.setWidthSegments(source.getWidthSegments());
            this.setHeightSegments(source.getHeightSegments());
        }
        return this;
    }
    @Override
    public PlaneGeometry clone(){
        PlaneGeometry geometry = new PlaneGeometry();
        geometry.copy(this);
        return geometry;
    }
}
