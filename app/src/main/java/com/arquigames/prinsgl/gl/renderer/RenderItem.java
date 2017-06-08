package com.arquigames.prinsgl.gl.renderer;

import com.arquigames.prinsgl.Object3D;
import com.arquigames.prinsgl.materials.Material;

/**
 * Created by usuario on 08/08/2016.
 */
public class RenderItem implements Comparable<RenderItem>,Cloneable{



    private float z = 0;
    private Object3D object3D;
    private RenderGroup renderGroup;
    private Material material;
    public RenderItem(Object3D object3D, Material material,float z,RenderGroup renderGroup){

        this.setObject3D(object3D);
        this.setMaterial(material);
        this.setZ(z);
        this.setRenderGroup(renderGroup);
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Object3D getObject3D() {
        return object3D;
    }

    public void setObject3D(Object3D object3D) {
        this.object3D = object3D;
    }

    public RenderGroup getRenderGroup() {
        return renderGroup;
    }

    public void setRenderGroup(RenderGroup renderGroup) {
        this.renderGroup = renderGroup;
    }


    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }


    @Override
    public int compareTo(RenderItem renderItem) {
        if(this.z<renderItem.getZ())return -1;
        return this.z==renderItem.getZ() ? 0:1;
    }
    @Override
    public RenderItem clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException("cannot clone RenderItem");
    }
}
