package com.arquigames.prinsgl.materials;

import android.util.Log;

import com.arquigames.prinsgl.gl.uniforms.GLUniform;

/**
 * Created by usuario on 14/08/2016.
 */
public class MeshPhongMaterial extends Material{
    private static String TAG = "MeshPhongMaterial";
    private float shininess = 30;

    public MeshPhongMaterial(){
        super();
    }

    @Override
    public void buildUniforms() {
        GLUniform glUniform;
        if(this.diffuseColor!=null){

            glUniform = new GLUniform();
            glUniform.setName("matDiffuseColor");
            glUniform.setType(GLUniform.U_C);
            glUniform.setValue(this.diffuseColor);
            this.uniforms.put("matDiffuseColor",glUniform);
        }
        if(this.specularColor!=null){

            glUniform = new GLUniform();
            glUniform.setName("matSpecularColor");
            glUniform.setType(GLUniform.U_C);
            glUniform.setValue(this.specularColor);
            this.uniforms.put("matSpecularColor",glUniform);
        }
        if(this.emissiveColor!=null){

            glUniform = new GLUniform();
            glUniform.setName("matEmissiveColor");
            glUniform.setType(GLUniform.U_C);
            glUniform.setValue(this.emissiveColor);
            this.uniforms.put("matEmissiveColor",glUniform);
        }
        if(this.getShininess() >0){

            glUniform = new GLUniform();
            glUniform.setName("matShininess");
            glUniform.setType(GLUniform.U_F);
            glUniform.setValue(this.getShininess());
            this.uniforms.put("matShininess",glUniform);
        }
        if(this.map!=null){

            glUniform = new GLUniform();
            glUniform.setName("matMap");
            glUniform.setType(GLUniform.U_T);
            glUniform.setValue(this.map);
            this.uniforms.put("matMap",glUniform);
            Log.e(TAG,"setting matMap texture.");
        }
        if(this.opacity>=0){

            glUniform = new GLUniform();
            glUniform.setName("matOpacity");
            glUniform.setType(GLUniform.U_F);
            glUniform.setValue(this.opacity);
            this.uniforms.put("matOpacity",glUniform);
        }
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }
}
