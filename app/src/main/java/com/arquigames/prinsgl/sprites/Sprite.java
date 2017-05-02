package com.arquigames.prinsgl.sprites;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.arquigames.prinsgl.Scene;
import com.arquigames.prinsgl.TextureUtils;
import com.arquigames.prinsgl.Util;
import com.arquigames.prinsgl.geometries.Geometry;
import com.arquigames.prinsgl.geometries.PlaneGeometry;
import com.arquigames.prinsgl.materials.Material;
import com.arquigames.prinsgl.materials.MeshBasicMaterial;
import com.arquigames.prinsgl.maths.vectors.Vector3;
import com.arquigames.prinsgl.meshes.Mesh;
import com.arquigames.prinsgl.textures.Texture;

/**
 * Created by usuario on 30/09/2016.
 */

public class Sprite{
    private static String TAG = "Sprite";
    private Mesh mesh;
    private MeshBasicMaterial meshBasicMaterial;
    private Geometry geometry;
    public Sprite(String filePath, Context context, Scene scene){
        Bitmap bitmap = TextureUtils.getBitmapFromAssetPath(context,filePath);
        Texture texture = new Texture();
        texture.setImage(bitmap);
        this.build(scene,texture);

    }
    public Sprite(int resourceID, Context context, Scene scene){
        Texture texture = TextureUtils.getTextureFromResource(context.getResources(),resourceID);
        this.build(scene,texture);
    }
    private void build(Scene scene,Texture texture){
        MeshBasicMaterial meshBasicMaterial = new MeshBasicMaterial();
        meshBasicMaterial.setMap(texture);
        meshBasicMaterial.setTransparent(true);
        //meshBasicMaterial.setPremultipliedAlpha(false);
        PlaneGeometry planeGeometry = new PlaneGeometry(2,2,1,1);
        this.mesh = new Mesh(planeGeometry,meshBasicMaterial);
        try {
            scene.add(this.mesh);
        } catch (Exception e) {
            Log.e(TAG, Util.exceptionToString(e));
        }
    }
    public Material getMaterial(){
        return this.meshBasicMaterial;
    }
    public Geometry getGeometry(){
        return this.geometry;
    }
    public Mesh getMesh(){
        return this.mesh;
    }
    public void dispose(){
        this.mesh.markToRemove();
        this.mesh  = null;
        this.geometry = null;
        this.meshBasicMaterial = null;
    }

    public Vector3 getScale() {
        if(this.mesh!=null){
            return this.mesh.getScale();
        }else{
            return null;
        }

    }
}
