package com.arquigames.prinsgl.gl.uniforms;

/**
 * Created by usuario on 12/07/2016.
 */
public class GLStructUniform  implements IUniform{
    private String name;
    private java.util.LinkedList<GLUniform> uniforms;
    public GLStructUniform(String name){
        this.setName(name);
        this.setUniforms(new java.util.LinkedList<GLUniform>());
    }
    public GLStructUniform(Integer name){
        this(name+"");
    }
    public GLUniform get(String name){
        java.util.Iterator<GLUniform> iterator = this.uniforms.iterator();
        GLUniform glUniform;
        while(iterator.hasNext()){
            glUniform = iterator.next();
            if(glUniform.getName().equals(name)){
                return glUniform;
            }
        }
        return null;
    }
    @Override
    public void dispose(){
        if(this.uniforms!=null){
            java.util.Iterator<GLUniform> it = this.uniforms.iterator();
            GLUniform glUniform;
            while(it.hasNext()){
                glUniform = it.next();
                glUniform.dispose();
            }
            this.uniforms.clear();
            this.uniforms = null;
        }
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    public java.util.LinkedList<GLUniform> getUniforms() {
        return uniforms;
    }

    public void setUniforms(java.util.LinkedList<GLUniform> uniforms) {
        this.uniforms = uniforms;
    }
}
