package com.arquigames.prinsgl.materials;

import com.arquigames.prinsgl.gl.uniforms.GLUniform;

/**
 * Created by usuario on 14/08/2016.
 */
public class MeshBasicMaterial extends Material{
    private static String TAG = "MeshBasicMaterial";
    public MeshBasicMaterial(){
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
        if(this.map!=null){

            glUniform = new GLUniform();
            glUniform.setName("matMap");
            glUniform.setType(GLUniform.U_T);
            glUniform.setValue(this.map);
            this.uniforms.put("matMap",glUniform);
        }
        if(this.opacity>=0){

            glUniform = new GLUniform();
            glUniform.setName("matOpacity");
            glUniform.setType(GLUniform.U_F);
            glUniform.setValue(this.opacity);
            this.uniforms.put("matOpacity",glUniform);
        }
    }

}
