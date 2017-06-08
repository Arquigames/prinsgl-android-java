package com.arquigames.prinsgl.gl;

import android.opengl.GLES20;

import com.arquigames.prinsgl.gl.attributes.GLAttribute;
import com.arquigames.prinsgl.gl.uniforms.IUniform;

/**
 * Created by usuario on 08/08/2016.
 */
public class GLProgram implements Cloneable{

    private int programLocation = -1;

    private java.util.HashMap<String,IUniform> uniforms = null;
    private java.util.HashMap<String,GLAttribute> attributes = null;

    public GLProgram(){
        this.setUniforms(new java.util.HashMap<String,IUniform>());
        this.setAttributes(new java.util.HashMap<String,GLAttribute>());
    }
    public IUniform getUniform(String key){
        return this.uniforms.get(key);
    }
    public GLAttribute getAttribute(String key){
        return this.attributes.get(key);
    }
    public java.util.Set<String> getUniformsKeys(){
        return this.uniforms.keySet();
    }
    public java.util.Set<String> getAttributesKeys(){
        return this.attributes.keySet();
    }

    public int getProgramLocation() {
        return programLocation;
    }

    public void setProgramLocation(int programLocation) {
        this.programLocation = programLocation;
    }

    public java.util.HashMap<String, IUniform> getUniforms() {
        return uniforms;
    }

    public void setUniforms(java.util.HashMap<String, IUniform> uniforms) {
        this.uniforms = uniforms;
    }

    public java.util.HashMap<String, GLAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(java.util.HashMap<String, GLAttribute> attributes) {
        this.attributes = attributes;
    }

    public void dispose() {

        this.uniforms.clear();
        this.attributes.clear();
        if(this.programLocation>=0 && GLES20.glIsProgram(this.programLocation)){
            GLES20.glDeleteProgram(this.programLocation);
        }

    }
    @Override
    public GLProgram clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException("cannot clone GLProgram");
    }
}
