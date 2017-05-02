package com.arquigames.test_app.application.comp;

import android.util.Log;

import com.arquigames.test_app.application.Components;
import com.arquigames.test_app.application.comp.touches.TouchControlView;
import com.arquigames.test_app.application.utils.GameScreen;
import com.arquigames.test_app.application.utils.GameTimer;
import com.arquigames.prinsgl.ObjectJSON;
import com.arquigames.prinsgl.Scene;
import com.arquigames.prinsgl.Util;
import com.arquigames.prinsgl.geometries.Geometry;
import com.arquigames.prinsgl.loaders.threejs.ThreeJsonLoader;
import com.arquigames.prinsgl.materials.Material;
import com.arquigames.prinsgl.materials.MultiMaterial;
import com.arquigames.prinsgl.maths.vectors.Vector3;
import com.arquigames.prinsgl.meshes.Mesh;

/**
 * Created by usuario on 28/09/2016.
 */

public class Ship {
    private Mesh mesh;
    private Scene scene;
    private Components components;
    private static Ship obj;
    private static String TAG = "Ship";
    private Vector3 tmp_position = new Vector3();
    public Ship(Scene scene,Components components){
        this.scene = scene;
        this.components = components;
        /*
        MeshPhongMaterial meshPhongMaterial = new MeshPhongMaterial();
        meshPhongMaterial.getDiffuseColor().set(0f,1f,0f);
        meshPhongMaterial.setShininess(5);

        Geometry geometry = new SphereGeometry(5,8,8,0,0,0,0);
        this.mesh = new Mesh(geometry,meshPhongMaterial);
        */
        ThreeJsonLoader loader = new ThreeJsonLoader(this.components.getParent().getParent().getContext());
        ObjectJSON objectJSON = loader.load("json/ship.json");
        if(objectJSON.get("geometry")!=null){
            Geometry geometry = (Geometry)objectJSON.get("geometry");
            java.util.LinkedList<Material> materials = (java.util.LinkedList<Material>)objectJSON.get("materials");
            if(materials!=null){
                this.mesh = new Mesh(geometry,materials.size()==1 ? materials.getFirst():new MultiMaterial(materials.toArray()));
                this.mesh.getPosition().addX(-3f);
                try {
                    this.scene.add(this.mesh);
                } catch (Exception e) {
                    Log.e(TAG, Util.exceptionToString(e));
                }
            }
        }

    }
    public static Ship getInstance(Scene scene, Components components){
        if(obj==null){
            obj = new Ship(scene,components);
        }
        return obj;
    }
    public Mesh getMesh(){
        return this.mesh;
    }
    public boolean isVisible(){
        if(this.mesh!=null){
            return this.mesh.visible;
        }
        return false;
    }
    public void setVisible(boolean value){
        if(this.mesh!=null){
            this.mesh.visible = value;
        }
    }
    public Vector3 getPosition(){
        if(this.mesh!=null){
            return this.mesh.getPosition();
        }
        return null;
    }
    public void render(GameTimer gameTimer, TouchControlView touchControlView, GameScreen gameScreen){
        if(touchControlView!=null){
            Vector3 vector3 = touchControlView.getVectorMove();
            if(vector3!=null && this.mesh!=null){
                vector3.negateY();
                this.tmp_position.copy(this.mesh.getPosition());
                this.mesh.getPosition().addScaledVector(vector3,0.001f);
                if(gameScreen.isPositionXOut(this.mesh.getPosition())){
                    this.mesh.getPosition().copyX(this.tmp_position);
                }
                if(gameScreen.isPositionYOut(this.mesh.getPosition())){
                    this.mesh.getPosition().copyY(this.tmp_position);
                }
            }
        }
        this.mesh.getRotation().addX(0.005f);
    }
}
