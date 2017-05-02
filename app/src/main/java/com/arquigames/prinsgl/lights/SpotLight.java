package com.arquigames.prinsgl.lights;

import android.util.Log;

import com.arquigames.prinsgl.Util;
import com.arquigames.prinsgl.gl.uniforms.GLUniform;
import com.arquigames.prinsgl.maths.Color;
import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 27/09/2016.
 */

public class SpotLight extends Light {
    private float distance;
    private float penumbra;
    public SpotLight(){
        super();
        this.position.set( 0f, 1f, 0f );
        this.updateMatrix();
    }
    public SpotLight(Vector3 position, Color color, Vector3 direction, float distance, float intensity, float spotCutOff/*angle*/, float penumbra){
        super(color,1f);
        this.getPosition().copy(position);
        this.setDistance(distance);
        this.setIntensity(intensity);
        this.setSpotCutOff((float)(spotCutOff * Math.PI / 180f));
        this.setPenumbra(penumbra);
        this.getSpotDirection().copy(direction);
        this.setSpotExponent(1f);
        this.updateMatrix();
    }
    @Override
    public Light clone() {
        try{
            PointLight pointLight = new PointLight();
            pointLight.copy(this);
            return pointLight;
        }catch(Exception e){
            Log.e("SpotLight","[CLONE] = "+ Util.exceptionToString(e));
            return null;
        }
    }
    @Override
    public Light copy(Light source) throws Exception{
        super.copy(source);
        if(source instanceof SpotLight){
            SpotLight spotLight = (SpotLight)source;
            this.setDistance(spotLight.getDistance());
            this.setPenumbra(spotLight.getPenumbra());
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

    public float getPenumbra() {
        return penumbra;
    }

    public void setPenumbra(float penumbra) {
        this.penumbra = penumbra;
        if(this.uniform!=null){
            GLUniform glUniform = this.uniform.get("penumbra");
            if(glUniform!=null)glUniform.setValue(this.penumbra);
        }
    }
}
