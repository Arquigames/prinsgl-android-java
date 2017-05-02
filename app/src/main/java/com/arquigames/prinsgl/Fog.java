package com.arquigames.prinsgl;


import com.arquigames.prinsgl.maths.Color;

/**
 * Created by usuario on 27/06/2016.
 */
public class Fog {
    private Color color = null;
    private float near = 1f;
    private float far = 1000.0f;
    public Fog(Color color, float near, float far){
        this.setColor(color);
        this.setNear(near);
        this.setFar(far);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getNear() {
        return near;
    }

    public void setNear(float near) {
        this.near = near;
    }

    public float getFar() {
        return far;
    }

    public void setFar(float far) {
        this.far = far;
    }
}
