package com.arquigames.prinsgl.loaders.events;

/**
 * Created by usuario on 14/08/2016.
 */
public class EventLoader {

    public static int ACTION_LOADED = 0x01;
    public static int ACTION_PERFORMED = 0x02;

    private int what;
    private Object target;
    private int action;
    public EventLoader(Object target,int what,int action){
        this.setTarget(target);
        this.setWhat(what);
        this.setAction(action);
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
