package com.arquigames.prinsgl.maths.vectors;

/**
 * Created by usuario on 12/08/2016.
 */
public class Vector2I {
    private int x;
    private int y;
    public Vector2I(int x, int y){
        this.setX(x);
        this.setY(y);
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
    public String toString(){
        return "Vector2I(x="+this.x+",y="+this.y+")";
    }
}
