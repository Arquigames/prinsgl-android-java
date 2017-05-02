package com.arquigames.prinsgl.lights;


import com.arquigames.prinsgl.maths.Color;

/**
 * Created by usuario on 26/06/2016.
 */
public class AmbientLight extends Light {
    public AmbientLight(){
        super();
    }
    public AmbientLight(int color, float intensity){
        super(color,intensity);
        this.ambientColor.setHex(color);
    }
    public AmbientLight(int color){
        super(color,1f);
        this.ambientColor.setHex(color);
    }
    public AmbientLight(Color color){
        super(color,1f);
        this.ambientColor.copy(color);
    }
    public AmbientLight(Color color, float intensity){
        super(color,intensity);
        this.ambientColor.copy(color);
    }

    @Override
    public Light clone() {
        AmbientLight ambientLight = new AmbientLight();
        try {
            ambientLight.copy(this);
            return ambientLight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Light copy(Light source) throws Exception{
        super.copy(source);
        return this;
    }
}
