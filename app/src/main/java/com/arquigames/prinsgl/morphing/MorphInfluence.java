package com.arquigames.prinsgl.morphing;

/**
 * Created by usuario on 16/08/2016.
 */
public class MorphInfluence implements Comparable<MorphInfluence> {

    private float influence;
    private int index;

    public MorphInfluence(float influence, int index){
        this.setInfluence(influence);
        this.setIndex(index);
    }

    @Override
    public int compareTo(MorphInfluence morphInfluence) {
        float diff = this.influence - morphInfluence.getInfluence();
        //return diff>0 ? 1 : (diff<0 ? -1 : 0);
        return diff>0 ? -1 : (diff<0 ? 1 : 0);
    }

    public float getInfluence() {
        return influence;
    }

    public void setInfluence(float influence) {
        this.influence = influence;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
