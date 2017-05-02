package com.arquigames.prinsgl.geometries;

import com.arquigames.prinsgl.GeometryUtils;
import com.arquigames.prinsgl.geometries.buffers.SphereBufferGeometry;

/**
 * Created by usuario on 12/08/2016.
 */
public class SphereGeometry extends Geometry{

    protected float radius;
    protected int widthSegments;
    protected int heightSegments;
    protected float phiStart;
    protected float phiLength;
    protected float thetaStart;
    protected float thetaLength;

    public SphereGeometry(float radius,int widthSegments,int heightSegments,float phiStart,float phiLength,float thetaStart,float thetaLength ){
        super();

        this.radius = radius;
        this.widthSegments = widthSegments;
        this.heightSegments = heightSegments;
        this.phiStart = phiStart;
        this.phiLength = phiLength;
        this.thetaStart = thetaStart;
        this.thetaLength = thetaLength;

        SphereBufferGeometry bufferGeometry = new SphereBufferGeometry(
                this.radius,
                this.widthSegments,
                this.heightSegments,
                this.phiStart,
                this.phiLength,
                this.thetaStart,
                this.thetaLength
        );
        GeometryUtils.buildGeometryFromBufferGeometry(this,bufferGeometry);

    }
    public SphereGeometry(){
        this(0,0,0,0,0,0,0);
    }
}