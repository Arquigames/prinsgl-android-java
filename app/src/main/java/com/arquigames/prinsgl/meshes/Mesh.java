package com.arquigames.prinsgl.meshes;

import com.arquigames.prinsgl.Constants;
import com.arquigames.prinsgl.Scene;
import com.arquigames.prinsgl.geometries.Geometry;
import com.arquigames.prinsgl.Object3D;
import com.arquigames.prinsgl.lights.Light;
import com.arquigames.prinsgl.materials.IMaterial;
import com.arquigames.prinsgl.materials.Material;
import com.arquigames.prinsgl.materials.MultiMaterial;
import com.arquigames.prinsgl.morphing.MorphTarget;

/**
 * Created by usuario on 16/07/2016.
 */
public class Mesh extends Object3D {
    protected Geometry geometry;
    protected IMaterial material;
    protected int drawMode = 0;

    protected java.util.LinkedList<Float> morphTargetInfluences;
    protected java.util.HashMap<String,Integer> morphTargetDictionary;

    public Mesh(){
        super();
        this.setDrawMode(Constants.TRIANGLES_DRAW_MODE);
        this.setMorphTargetInfluences(new java.util.LinkedList<Float>());
        this.setMorphTargetDictionary(new java.util.HashMap<String,Integer>());

    }

    public Mesh(Geometry geometry, IMaterial material) {
        this();
        this.setGeometry(geometry);
        this.setMaterial(material);
    }
    @Override
    public void dispose(){
        Material mat;
        MultiMaterial multiMaterial;
        if(this.material instanceof Material){
            mat = (Material)this.material;
            mat.removeMeshID(this);
            mat.dispose();
        }else{
            if(this.material instanceof MultiMaterial){
                multiMaterial = ((MultiMaterial)this.material);
                java.util.Iterator<Material> it = multiMaterial.getMaterials().iterator();
                while(it.hasNext()){
                    mat = it.next();
                    mat.removeMeshID(this);
                    mat.dispose();
                }
            }
        }
        this.geometry.dispose();
    }
    @Override
    public Object3D add(Object3D object3D) throws Exception{
        boolean prevent =
                object3D instanceof Light ||
                object3D instanceof Scene;
        if(!prevent){
            return super.add(object3D);
        }
        return this;
    }
    @Override
    public Object3D clone(){
        Mesh object = new Mesh();
        try{
            object.copy(this,true);
            return object;
        }catch(Exception e){
            return null;
        }
    }
    @Override
    public Object3D copy(Object3D source, boolean recursive) throws Exception{
        Mesh mesh=null;
        if(source instanceof Mesh){
            mesh = (Mesh)source;
            this.setDrawMode(mesh.getDrawMode());
            this.setGeometry(mesh.getGeometry().clone());
            this.setMaterial(mesh.getMaterial());
        }
        return super.copy(source,recursive);
    }

    public void updateMorphTargets(){
        if(this.getGeometry()==null)return;
        this.morphTargetInfluences.clear();
        this.morphTargetDictionary.clear();
        java.util.LinkedList<MorphTarget> morphTargets = this.getGeometry().getMorphTargets();
        if(morphTargets!=null && morphTargets.size()>0){
            java.util.Iterator<MorphTarget> morphTargetIterator = morphTargets.iterator();
            MorphTarget morphTarget;
            int index=0;
            while(morphTargetIterator.hasNext()){
                morphTarget = morphTargetIterator.next();
                this.morphTargetInfluences.add(0f);
                this.morphTargetDictionary.put(morphTarget.getName(),index);
                index++;
            }
        }

    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
        this.updateMorphTargets();
    }

    public IMaterial getMaterial() {
        return material;
    }

    public void setMaterial(IMaterial material) {
        this.material = material;
        if(material instanceof Material){
            ((Material)this.material).attachMeshID(this);
        }else{
            if(material instanceof MultiMaterial){
                MultiMaterial multiMaterial = ((MultiMaterial)material);
                java.util.Iterator<Material> it = multiMaterial.getMaterials().iterator();
                Material mat;
                while(it.hasNext()){
                    mat = it.next();
                    mat.attachMeshID(this);
                }
            }
        }
    }

    public void setDrawMode(int drawMode) {
        this.drawMode = drawMode;
    }

    public int getDrawMode() {
        return drawMode;
    }

    public java.util.LinkedList<Float> getMorphTargetInfluences() {
        return this.morphTargetInfluences;
    }

    public void setMorphTargetInfluences(java.util.LinkedList<Float> morphTargetInfluences) {
        this.morphTargetInfluences = morphTargetInfluences;
    }

    public java.util.HashMap<String, Integer> getMorphTargetDictionary() {
        return morphTargetDictionary;
    }

    public void setMorphTargetDictionary(java.util.HashMap<String, Integer> morphTargetDictionary) {
        this.morphTargetDictionary = morphTargetDictionary;
    }
}
