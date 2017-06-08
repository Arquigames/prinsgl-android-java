package com.arquigames.prinsgl.geometries.buffers;

import com.arquigames.prinsgl.gl.attributes.BufferAttribute;
import com.arquigames.prinsgl.ObjectJSON;
import com.arquigames.prinsgl.gl.renderer.RenderGroup;

/**
 * Created by usuario on 10/08/2016.
 */
@SuppressWarnings("CloneDoesntCallSuperClone")
public class BufferGeometry implements  Cloneable{

    protected ObjectJSON attributes = null;//BufferAttribute

    protected float[] vertices = null;
    protected short[] shortIndices = null;
    protected int[] intIndices = null;
    protected float[] normals = null;
    protected float[] uvs = null;

    protected java.util.LinkedList<RenderGroup> groups;

    public BufferGeometry(){
        this.setAttributes(new ObjectJSON());
        this.setGroups(new java.util.LinkedList<RenderGroup>());
    }
    protected void addAttribute(String key, BufferAttribute bufferAttribute){
        this.getAttributes().put(key,bufferAttribute);
    }
    protected void setIndex(BufferAttribute bufferAttribute){
        this.addAttribute("indices",bufferAttribute);
    }

    public ObjectJSON getAttributes() {
        return attributes;
    }

    public void setAttributes(ObjectJSON attributes) {
        this.attributes = attributes;
    }

    public java.util.LinkedList<RenderGroup> getGroups() {
        return groups;
    }

    public void setGroups(java.util.LinkedList<RenderGroup> groups) {
        this.groups = groups;
    }
    @Override
    public BufferGeometry clone(){
        BufferGeometry bufferGeometry = new BufferGeometry();
        bufferGeometry.copy(this);
        return bufferGeometry;
    }
    public BufferGeometry copy(BufferGeometry bufferGeometry){
        if(bufferGeometry.getGroups().size()>0){
            this.groups.clear();
            java.util.Iterator<RenderGroup> iterator = bufferGeometry.getGroups().iterator();
            while(iterator.hasNext()){
                this.groups.add(iterator.next().clone());
            }
        }
        this.attributes = bufferGeometry.getAttributes().clone();
        return this;
    }
}
