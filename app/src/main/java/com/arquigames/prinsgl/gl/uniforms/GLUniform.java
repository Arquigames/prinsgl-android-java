package com.arquigames.prinsgl.gl.uniforms;

/**
 * Created by usuario on 12/07/2016.
 */
public class GLUniform implements IUniform {

    private String name = "";
    private String type = "";
    private int glLocation = -1;
    private int glType = 0;
    private int glSize = 0;
    private Object value = null;
    private boolean needsUpdate = false;
    public GLUniform(String type, Object value,String name){
        this.setName(name);
        this.setValue(value);
        this.setType(type);
    }
    public GLUniform(int glLocation, String type, int glType, int glSize,Object value,String name){
        this.setGlLocation(glLocation);
        this.setType(type);
        this.setGlType(glType);
        this.setGlSize(glSize);
        this.setValue(value);
        this.setName(name);
    }
    public GLUniform(int location, String type, int glType, int glSize,Object value){
        this(location,type,glType,glSize,value,"");
    }
    public GLUniform(int location, String type, int glType, int glSize){
        this(location,type,glType,glSize,null);
    }
    public GLUniform(int location, String type, int glType){
        this(location,type,glType,0);
    }
    public GLUniform(int location, String type){
        this(location,type,0);
    }
    public GLUniform(int location){
        this(location,"");
    }
    public GLUniform(){
        this(-1);
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

    public Object getValue() {
        this.setNeedsUpdate(false);
        return value;
    }

    public void setValue(Object value) {
        this.setNeedsUpdate(true);
        this.value = value;
    }

    public boolean isNeedsUpdate() {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void dispose() {
        //TODO
        this.value = null;
    }

    @Override
    public GLUniform clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("cannot clone GLUniform");
    }
}