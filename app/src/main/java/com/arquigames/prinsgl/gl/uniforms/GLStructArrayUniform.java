package com.arquigames.prinsgl.gl.uniforms;

/**
 * Created by usuario on 12/07/2016.
 */
public class GLStructArrayUniform implements IUniform {
    private String name = "";
    private java.util.HashMap<String,GLStructUniform> structUniforms;
    public GLStructArrayUniform(String name){
        this.setName(name);
        this.setStructUniforms(new java.util.HashMap<String,GLStructUniform>());
    }
    public void put(String key,GLStructUniform structUniform){
        this.getStructUniforms().put(key,structUniform);
    }
    public void put(Integer key,GLStructUniform structUniform){
        String _key = key+"";
        this.put(_key,structUniform);
    }
    public GLStructUniform get(String key){
        return this.getStructUniforms().get(key);
    }
    public void clear(){
        this.getStructUniforms().clear();
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    public java.util.HashMap<String, GLStructUniform> getStructUniforms() {
        return structUniforms;
    }

    public void setStructUniforms(java.util.HashMap<String, GLStructUniform> structUniforms) {
        this.structUniforms = structUniforms;
    }
    @Override
    public void dispose() {
        //TODO
        if(this.structUniforms!=null){
            java.util.Iterator<GLStructUniform> it = this.structUniforms.values().iterator();
            GLStructUniform glStructUniform;
            while(it.hasNext()){
                glStructUniform = it.next();
                glStructUniform.dispose();
            }
            this.structUniforms.clear();
            this.structUniforms = null;
        }
    }
}
