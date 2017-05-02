package com.arquigames.prinsgl;


import com.arquigames.prinsgl.lights.Light;

/**
 * Created by usuario on 27/06/2016.
 */
public class Scene extends Object3D {
    public boolean autoUpdate = true;
    private Fog fog = null;

    private java.util.TreeSet<Light>  lights;
    private boolean lightsNeedsUpdate = false;

    public Scene(){
        super();
        this.fog = null;
        this.setLights(new java.util.TreeSet<Light>());
    }

    public Fog getFog() {
        return fog;
    }

    public void setFog(Fog fog) {
        this.fog = fog;
    }

    @Override
    public Object3D add(Object3D object3D) throws Exception{
        boolean only =
                object3D instanceof Light ;
        if(only){
            this.addLight((Light)object3D);
        }else{
            return super.add(object3D);
        }
        return this;
    }
    @Override
    public boolean remove(Object3D object3D) throws Exception{
        boolean only =
                object3D instanceof Light ;
        if(only){
            return this.removeLight((Light)object3D);
        }else{
            return super.remove(object3D);
        }
    }

    public boolean addLight(Light light) {
        boolean added = this.lights.add(light);
        if(added){
            this.setLightsNeedsUpdate(true);
        }
        return added;
    }
    public boolean removeLight(Light light){
        boolean removed = this.lights.remove(light);
        if(removed){
            this.setLightsNeedsUpdate(true);
        }
        return removed;
    }

    public java.util.TreeSet<Light> getLights() {
        this.setLightsNeedsUpdate(false);
        return lights;
    }

    private void setLights(java.util.TreeSet<Light> lights) {
        this.lights = lights;
    }

    public boolean isLightsNeedsUpdate() {
        return lightsNeedsUpdate;
    }

    private void setLightsNeedsUpdate(boolean lightsNeedsUpdate) {
        this.lightsNeedsUpdate = lightsNeedsUpdate;
    }
}


