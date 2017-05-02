package com.arquigames.prinsgl.lights;

import android.util.Log;

import com.arquigames.prinsgl.Util;
import com.arquigames.prinsgl.gl.uniforms.GLUniform;
import com.arquigames.prinsgl.maths.Color;
import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 25/09/2016.
 */

public class PointLight extends Light {

    protected float distance;
    protected float decay;
    public PointLight(){
        super();
        this.setDistance(0);
        this.setDecay(1);
        this.position.set( 0f, 1f, 0f );
        this.updateMatrix();
    }
    public PointLight(Vector3 position){
        super();
        this.position.copy(position);
        this.updateMatrix();
    }
    public PointLight(Vector3 position,float distance,float decay){
        this(position,distance);
        this.setDecay(decay);
    }
    public PointLight(Vector3 position,float distance){
        this(position);
        this.setDistance(distance);
    }
    public PointLight(int color, float intensity){
        super(color,intensity);
    }
    public PointLight(Vector3 position,Color color, float intensity){
        super(color,intensity);
        this.position.copy(position);
        this.updateMatrix();
    }
    public PointLight(Vector3 position,Color color){
        super(color,1f);
        this.position.copy(position);
        this.updateMatrix();
    }
    public PointLight(Vector3 position,Color color,float distance, float decay){
        super(color,1f);
        this.position.copy(position);
        this.distance =distance;
        this.decay = decay;
        this.updateMatrix();
    }
    public PointLight(Color color, float intensity){
        super(color,intensity);
    }

    @Override
    public Light clone() {
        try{
            PointLight pointLight = new PointLight();
            pointLight.copy(this);
            return pointLight;
        }catch(Exception e){
            Log.e("PointLight","[CLONE] = "+ Util.exceptionToString(e));
            return null;
        }
    }
    @Override
    public Light copy(Light source) throws Exception{
        super.copy(source);
        if(source instanceof PointLight){
            PointLight pointLight = (PointLight)source;
            this.setDistance(pointLight.getDistance());
            this.setDecay(pointLight.getDecay());
        }
        return this;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
        if(this.uniform!=null){
            GLUniform glUniform = this.uniform.get("distance");
            if(glUniform!=null)glUniform.setValue(this.distance);
        }
    }

    public float getDecay() {
        return decay;
    }

    public void setDecay(float decay) {
        this.decay = decay;
        if(this.uniform!=null){
            GLUniform glUniform = this.uniform.get("decay");
            if(glUniform!=null)glUniform.setValue(this.decay);
        }
    }
}
