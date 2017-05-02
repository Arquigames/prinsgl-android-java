package com.arquigames.prinsgl.geometries;

import com.arquigames.prinsgl.GeometryUtils;
import com.arquigames.prinsgl.geometries.buffers.CircleBufferGeometry;

/**
 * Created by usuario on 12/08/2016.
 */
public class CircleGeometry extends Geometry{
    protected float radius;
    protected int segments;
    protected float thetaStart;
    protected float thetaLength;


    public CircleGeometry(float radius,int segments, float thetaStart,float thetaLength){
        super();
        this.radius = radius;
        this.segments = segments;
        this.thetaLength = thetaLength;
        this.thetaStart = thetaStart;
        CircleBufferGeometry bufferGeometry = new CircleBufferGeometry(this.radius,this.segments,this.thetaStart,this.thetaLength);
        GeometryUtils.buildGeometryFromBufferGeometry(this,bufferGeometry);

    }
    public CircleGeometry(){
        this(0,0,0,0);
    }
    @Override
    public Geometry copy(Geometry geometry){
        super.copy(geometry);
        if(geometry instanceof PlaneGeometry){
            PlaneGeometry source = (PlaneGeometry)geometry;
        }
        return this;
    }
    public PlaneGeometry clone(){
        PlaneGeometry geometry = new PlaneGeometry();
        geometry.copy(this);
        return geometry;
    }
}