package com.arquigames.test_app.application;

import android.content.Context;
import android.util.Log;

import com.arquigames.test_app.MainActivity;
import com.arquigames.test_app.MyGLRenderer;
import com.arquigames.test_app.R;
import com.arquigames.test_app.application.comp.Ship;
import com.arquigames.test_app.application.comp.touches.TouchControlView;
import com.arquigames.test_app.application.utils.GameScreen;
import com.arquigames.prinsgl.Scene;
import com.arquigames.prinsgl.Util;
import com.arquigames.prinsgl.cameras.Camera;
import com.arquigames.prinsgl.gl.renderer.GLRenderer;
import com.arquigames.prinsgl.lights.AmbientLight;
import com.arquigames.prinsgl.lights.DirectionalLight;
import com.arquigames.prinsgl.sprites.Sprite;

/**
 * Created by usuario on 26/09/2016.
 */

public class Components {
    public static String TAG = "Components";
    MyGLRenderer myGLRenderer;
    private int width;
    private int height;
    private Scene scene;
    private Camera camera;
    private GLRenderer glRenderer;
    private TouchControlView touchControlView;
    //------------------------------------
    private Ship ship;
    private GameScreen gameScreen;
    private Sprite sprite;
    private int time = -500;
    //------------------------------------
    public Components(MyGLRenderer myGLRenderer){
        this.myGLRenderer = myGLRenderer;
    }
    public MyGLRenderer getParent(){
        return this.myGLRenderer;
    }
    public void load(Scene scene, Camera camera, GLRenderer glRenderer){
        this.scene = scene;
        this.camera = camera;
        this.glRenderer = glRenderer;
        //----------------------------------
        this.gameScreen = GameScreen.getInstance(null);
        this.gameScreen.compute();
        //----------------------------------
        this.ship = new Ship(this.scene,this);
        //----------------------------------
        this.getTouchControlView();
        //----------------------------------
        sprite = new Sprite(R.mipmap.ic_launcher,this.getParent().getParent().getContext(),this.scene);
        sprite.getScale().set(8,8,8);
        //----------------------------------

        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.getPosition().set(1,0,1);
        directionalLight.getDiffuseColor().set(1f,1f,1f);
        directionalLight.setIntensity(0.5f);

        AmbientLight ambientLight = new AmbientLight();
        ambientLight.getAmbientColor().set(1f,1f,1f);

        try {
            this.scene.add(ambientLight);
            this.scene.add(directionalLight);
        } catch (Exception e) {
            Log.e(TAG, Util.exceptionToString(e));
        }

        /*
        MeshPhongMaterial meshPhongMaterial = new MeshPhongMaterial();
        meshPhongMaterial.getDiffuseColor().set(0f,0f,1f);
        meshPhongMaterial.setShininess(5);

        Geometry geometry = new SphereGeometry(5,16,16,0,0,0,0);
        Mesh mesh = new Mesh(geometry,meshPhongMaterial);


        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.getPosition().set(1,0,1);
        directionalLight.getDiffuseColor().set(1f,1f,1f);
        directionalLight.setIntensity(0.5f);



        MeshPhongMaterial meshPhongMaterial2 = new MeshPhongMaterial();
        meshPhongMaterial2.getDiffuseColor().set(0f,1f,0f);
        meshPhongMaterial2.setShininess(5);

        Geometry geometry2 = new SphereGeometry(5,16,16,0,0,0,0);
        Mesh mesh2 = new Mesh(geometry,meshPhongMaterial2);
        mesh2.getPosition().set(-10f,0f,-5f);

        PointLight pointLight = new PointLight(
                new Vector3(-30f,30f,30f),
                new Color(1f,1f,1f),
                150f,
                1f
        );
        */

    }
    public void render(){
        //TODO

        if(this.time==0){
            if(this.sprite!=null){
                this.sprite.dispose();
                this.sprite = null;
            }

        }else{
            this.time++;
        }
        this.ship.render(null,this.touchControlView,this.gameScreen);
    }
    public void onSurfaceChanged(int width,int height){
        this.width = width;
        this.height = height;
        if(!this.gameScreen.isComputedSquare3DPositions()){
            this.gameScreen.computeSquare3DPositions(this.camera);
        }
        Log.e("GameScreen","onSurfaceChanged :: "+this.gameScreen.toString());
    }

    public TouchControlView getTouchControlView() {
        if(this.touchControlView==null){
            Context context = this.getParent().getParent().getContext();
            if(context instanceof MainActivity){
                MainActivity mainActivity = (MainActivity)context;
                this.touchControlView = (TouchControlView) mainActivity.findViewById(R.id.touch_control_view);
            }
        }
        return this.touchControlView;
    }
}
