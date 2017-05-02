package com.arquigames.prinsgl.gl.renderer;

/**
 * Created by usuario on 08/08/2016.
 */
public class RenderItemComparator implements java.util.Comparator<RenderItem>{
    @Override
    public int compare(RenderItem t0, RenderItem t1) {
        if(t0.getZ()<t1.getZ()){
            return -1;
        }
        else if(t0.getZ()>t1.getZ()){
            return 1;
        }else{
            return 0;
        }
    }
}
