package com.arquigames.prinsgl.gl.attributes;

/**
 * Created by usuario on 12/07/2016.
 */
public class GLAttribute {
    private String name = "";
    private String type = "";
    private int glLocation = -1;
    private int glType = 0;
    private int glSize = 0;
    private boolean needsUpdate = false;
    public GLAttribute(String type,String name){
        this.setName(name);
        this.setType(type);
    }
    public GLAttribute(String name){
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGlLocation() {
        return glLocation;
    }

    public void setGlLocation(int glLocation) {
        this.glLocation = glLocation;
    }

    public int getGlType() {
        return glType;
    }

    public void setGlType(int glType) {
        this.glType = glType;
    }

    public int getGlSize() {
        return glSize;
    }

    public void setGlSize(int glSize) {
        this.glSize = glSize;
    }



    public boolean isNeedsUpdate() {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }
}
