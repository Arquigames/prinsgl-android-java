package com.arquigames.prinsgl.maths.vectors;

/**
 * Created by usuario on 10/08/2016.
 */
public class Vector4I {
    private int x;
    private int y;
    private int z;
    private int w;
    public Vector4I(int x,int y,int z,int w){
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW(w);
    }
    public Vector4I(){
        this(0,0,0,0);
    }
    public Vector4I clone(){
        return new Vector4I(this.x,this.y,this.z,this.w);
    }
    public boolean equals(Vector4I vector4I){
        return
                        this.x==vector4I.getX() &&
                        this.y == vector4I.getY() &&
                        this.z == vector4I.getZ() &&
                        this.w == vector4I.getW();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }
}
