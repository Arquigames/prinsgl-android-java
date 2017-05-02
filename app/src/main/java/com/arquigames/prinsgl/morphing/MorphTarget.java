package com.arquigames.prinsgl.morphing;

import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 16/08/2016.
 */
public class MorphTarget {
    private String name;
    private java.util.LinkedList<Vector3> vertices;
    public MorphTarget(){
        this.setName("");
        this.setVertices(new java.util.LinkedList<Vector3>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.LinkedList<Vector3> getVertices() {
        return vertices;
    }

    public void setVertices(java.util.LinkedList<Vector3> vertices) {
        this.vertices = vertices;
    }
}
