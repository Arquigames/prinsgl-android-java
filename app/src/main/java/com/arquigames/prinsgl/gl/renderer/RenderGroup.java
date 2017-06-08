package com.arquigames.prinsgl.gl.renderer;


/**
 * Created by usuario on 08/08/2016.
 */
public class RenderGroup implements Cloneable{

    private int start = 0;
    private int count = 0;
    private int materialIndex=0;

    public RenderGroup(){
    }
    public RenderGroup(int start,int count, int materialIndex){
        this.start = start;
        this.count = count;
        this.materialIndex = materialIndex;
    }
    public String toString(){
        return "RenderGroup[start="+this.start+",count="+this.count+",materialIndex="+this.materialIndex+"]";
    }
    @Override
    public RenderGroup clone(){
        return new RenderGroup(this.start,this.count,this.materialIndex);
    }
    public RenderGroup copy(RenderGroup renderGroup){
        this.start = renderGroup.getStart();
        this.count = renderGroup.getCount();
        this.materialIndex = renderGroup.getMaterialIndex();
        return this;
    }


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMaterialIndex() {
        return materialIndex;
    }

    public void setMaterialIndex(int materialIndex) {
        this.materialIndex = materialIndex;
    }


}
