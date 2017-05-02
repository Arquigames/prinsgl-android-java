package com.arquigames.prinsgl.geometries;

import com.arquigames.prinsgl.GeometryUtils;
import com.arquigames.prinsgl.geometries.buffers.BoxBufferGeometry;

/**
 * Created by usuario on 12/08/2016.
 */
public class BoxGeometry extends Geometry{
    protected float width;
    protected float height;
    protected float depth;
    protected int widthSegments;
    protected int heightSegments;
    protected int depthSegments;


    public BoxGeometry(float width,float height,float depth, int widthSegments,int heightSegments,int depthSegments){
        super();
        this.setWidth(width<1 ? 1:width);
        this.setHeight(height<1 ? 1:width);
        this.setDepth(depth<1 ? 1:depth);
        this.setWidthSegments(widthSegments<1 ? 1:widthSegments);
        this.setHeightSegments(heightSegments<1 ? 1:heightSegments);
        this.setDepthSegments(depthSegments<1 ? 1:depthSegments);

        BoxBufferGeometry bufferGeometry = new BoxBufferGeometry(
                this.getWidth(),
                this.getHeight(),
                this.getDepth(),
                this.getWidthSegments(),
                this.getHeightSegments(),
                this.getDepthSegments()
        );
        GeometryUtils.buildGeometryFromBufferGeometry(this,bufferGeometry);
        if(bufferGeometry.getGroups().size()>0){
            this.groupsNeedsUpdate = false;
            this.setGroups(bufferGeometry.getGroups());
        }

    }
    public BoxGeometry(){
        this(0,0,0,0,0,0);
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

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
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

    public int getDepthSegments() {
        return depthSegments;
    }

    public void setDepthSegments(int depthSegments) {
        this.depthSegments = depthSegments;
    }
}
