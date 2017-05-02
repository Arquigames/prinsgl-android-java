package com.arquigames.prinsgl.materials;

import com.arquigames.prinsgl.maths.MathUtils;

/**
 * Created by usuario on 14/07/2016.
 */

public class MultiMaterial implements IMaterial{
    private int id = 0;
    private String uuid = "";
    private java.util.LinkedList<Material> materials;

    private boolean visible = true;

    public MultiMaterial(){
        this.setUuid(MathUtils.generateUUID());
        this.setMaterials(new java.util.LinkedList<Material>());
    }
    public MultiMaterial(java.util.LinkedList<Material> materials){
        this();
        this.setMaterials(materials);
    }
    public MultiMaterial(Material[] materials){
        this();
        for(int i=0,l=materials.length;i<l;i++){
            this.add(materials[i]);
        }
    }
    public MultiMaterial(Object[] materials){
        this();
        for(int i=0,l=materials.length;i<l;i++){
            this.add((Material)materials[i]);
        }
    }
    public boolean add(Material material){
        java.util.Iterator<Material> iterator = this.materials.iterator();
        Material mat;
        boolean find = false;
        while(iterator.hasNext()){
            mat = iterator.next();
            if(mat.getId()==material.getId()){
                find = true;
            }
        }
        if(!find){
            this.materials.add(material);
        }
        return !find;
    }
    public boolean remove(Material material){
        return this.materials.remove(material);
    }
    @Override
    public int getId(){
        return this.id;
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public void setVisible(boolean value) {
        this.visible = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public java.util.LinkedList<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(java.util.LinkedList<Material> materials) {
        this.materials = materials;
    }
}
