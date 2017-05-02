package com.arquigames.prinsgl.lights;

import android.util.Log;

import com.arquigames.prinsgl.maths.Color;
import com.arquigames.prinsgl.Util;


/**
 * Created by usuario on 26/06/2016.
 */
public class DirectionalLight extends Light {

    public DirectionalLight(){
        super();
        this.position.set( 0f, 1f, 0f );
        this.updateMatrix();
    }
    public DirectionalLight(int color, float intensity){
        super(color,intensity);
    }
    public DirectionalLight(Color color, float intensity){
        super(color,intensity);
    }

    @Override
    public Light clone() {
        try{
            DirectionalLight directionalLight = new DirectionalLight();
            directionalLight.copy(this);
            return directionalLight;
        }catch(Exception e){
            Log.e("DirectionalLight","[CLONE] = "+ Util.exceptionToString(e));
            return null;
        }
    }
    @Override
    public Light copy(Light source) throws Exception{
        super.copy(source);
        return this;
    }
}
