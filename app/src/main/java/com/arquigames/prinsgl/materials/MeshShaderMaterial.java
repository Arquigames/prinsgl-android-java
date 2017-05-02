package com.arquigames.prinsgl.materials;

import com.arquigames.prinsgl.ObjectJSON;

/**
 * Created by usuario on 07/08/2016.
 */
public class MeshShaderMaterial extends Material {


    public ObjectJSON uniforms = new ObjectJSON();

    private String vertexPath;
    private String fragmentPath;

    public MeshShaderMaterial(String vertexPath,String fragmentPath){
        super();
        this.setVertexPath(vertexPath);
        this.setFragmentPath(fragmentPath);
    }


    public String getVertexPath() {
        return vertexPath;
    }

    public void setVertexPath(String vertexPath) {
        this.vertexPath = vertexPath;
    }

    public String getFragmentPath() {
        return fragmentPath;
    }

    public void setFragmentPath(String fragmentPath) {
        this.fragmentPath = fragmentPath;
    }

    @Override
    public void buildUniforms() {
        //TODO
    }
}
