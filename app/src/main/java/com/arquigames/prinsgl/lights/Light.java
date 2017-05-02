package com.arquigames.prinsgl.lights;

import android.util.Log;

import com.arquigames.prinsgl.gl.renderer.GLRenderer;
import com.arquigames.prinsgl.gl.uniforms.GLStructUniform;
import com.arquigames.prinsgl.gl.uniforms.GLUniform;
import com.arquigames.prinsgl.maths.Color;
import com.arquigames.prinsgl.Object3D;
import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 26/06/2016.
 */
public abstract class Light extends Object3D {
    protected String TAG = "Light";
    protected Color ambientColor = new Color(1f,1f,1f);
    protected Color specularColor = new Color(1f,1f,1f);
    protected Color diffuseColor = new Color(1f,1f,1f);
    protected float intensity = 1f;

    protected Vector3 spotDirection = new Vector3();
    protected float spotExponent = 0.1f;
    protected float spotCutOff = (float) Math.PI;
    protected float spotCosCutOff = 0f;

    protected float constantAtennuation = 1f;
    protected float linearAtennuation = 0f;
    protected float quadraticAtennuation = 0f;
    protected GLStructUniform uniform = null;

    @Override
    public void dispose(){
        if(this.uniform!=null){
            this.uniform.dispose();
        }
    }

    public Light(){
        super();
    }
    public Light(int color, float intensity){
        this();
        this.getDiffuseColor().setHex(color);
        this.setIntensity(intensity);
    }
    public Light(Color color, float intensity){
        this();
        this.setDiffuseColor(color.clone());
        this.setIntensity(intensity);
    }
    public Light copy(Light source) throws Exception{
        super.copy(source, false);
        this.ambientColor.copy( source.getAmbientColor() );
        this.diffuseColor.copy(source.getDiffuseColor());
        this.specularColor.copy(source.getSpecularColor());
        this.intensity = source.getIntensity();
        this.spotDirection.copy(source.getSpotDirection());
        this.spotExponent = source.getSpotExponent();
        this.spotCutOff = source.getSpotCutOff();
        this.setSpotCosCutOff((float) Math.cos(this.spotCutOff));
        this.constantAtennuation = source.getConstantAtennuation();
        this.linearAtennuation = source.getLinearAtennuation();
        this.quadraticAtennuation = source.getQuadraticAtennuation();

        return this;
    }
    @Override
    public abstract Light clone();


    @Override
    public Object3D add(Object3D object3D) throws Exception{
        if(GLRenderer.DEBUG){
            Log.e(TAG,"cannot add objects to Light");
        }
        return this;
    }

    public Color getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Color ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Color getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Color specularColor) {
        this.specularColor = specularColor;
    }

    public Color getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Color diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
        if(this.uniform!=null){
            GLUniform glUniform = this.uniform.get("intensity");
            if(glUniform!=null)glUniform.setValue(this.intensity);
        }
    }

    public Vector3 getSpotDirection() {
        return spotDirection;
    }

    public void setSpotDirection(Vector3 spotDirection) {
        this.spotDirection = spotDirection;
    }

    public float getSpotExponent() {
        return spotExponent;
    }

    public void setSpotExponent(float spotExponent) {
        this.spotExponent = spotExponent;
        if(this.uniform!=null){
            GLUniform glUniform = this.uniform.get("spotExponent");
            if(glUniform!=null)glUniform.setValue(this.spotExponent);
        }
    }

    public float getSpotCutOff() {
        return spotCutOff;
    }
    public float getSpotCutOff(boolean val) {
        if(val){
            return (float)(this.getSpotCutOff() * 180f / Math.PI);
        }
        return this.getSpotCutOff();
    }
    public void setSpotCutOff(float spotCutOff){
        this.setSpotCutOff(spotCutOff,false);
    }

    public void setSpotCutOff(float spotCutOff,boolean val) {
        this.spotCutOff = val ? (float)(spotCutOff*Math.PI/180f) : spotCutOff;
        this.setSpotCosCutOff((float) Math.cos(this.spotCutOff));
        if(this.uniform!=null){
            GLUniform glUniform = this.uniform.get("spotCutOff");
            if(glUniform!=null)glUniform.setValue(this.spotCutOff);
            glUniform = this.uniform.get("spotCosCutOff");
            if(glUniform!=null)glUniform.setValue(this.getSpotCosCutOff());
        }
    }

    public float getConstantAtennuation() {
        return constantAtennuation;
    }

    public void setConstantAtennuation(float constantAtennuation) {
        this.constantAtennuation = constantAtennuation;
        if(this.uniform!=null){
            GLUniform glUniform = this.uniform.get("constantAtennuation");
            if(glUniform!=null)glUniform.setValue(this.constantAtennuation);
        }
    }

    public float getLinearAtennuation() {
        return linearAtennuation;
    }

    public void setLinearAtennuation(float linearAtennuation) {
        this.linearAtennuation = linearAtennuation;
        if(this.uniform!=null){
            GLUniform glUniform = this.uniform.get("linearAtennuation");
            if(glUniform!=null)glUniform.setValue(this.linearAtennuation);
        }
    }

    public float getQuadraticAtennuation() {
        return quadraticAtennuation;
    }

    public void setQuadraticAtennuation(float quadraticAtennuation) {
        this.quadraticAtennuation = quadraticAtennuation;
        if(this.uniform!=null){
            GLUniform glUniform = this.uniform.get("quadraticAtennuation");
            if(glUniform!=null)glUniform.setValue(this.quadraticAtennuation);
        }
    }

    public void setUniform(GLStructUniform uniform) {
        this.uniform = uniform;
    }

    public float getSpotCosCutOff() {
        return spotCosCutOff;
    }

    public void setSpotCosCutOff(float spotCosCutOff) {
        this.spotCosCutOff = spotCosCutOff;
    }
}
