package com.arquigames.prinsgl.loaders.threejs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import com.arquigames.prinsgl.Constants;
import com.arquigames.prinsgl.ObjectJSON;
import com.arquigames.prinsgl.TextureUtils;
import com.arquigames.prinsgl.Util;
import com.arquigames.prinsgl.geometries.Geometry;
import com.arquigames.prinsgl.gl.renderer.GLRenderer;
import com.arquigames.prinsgl.materials.Material;
import com.arquigames.prinsgl.materials.MeshBasicMaterial;
import com.arquigames.prinsgl.materials.MeshPhongMaterial;
import com.arquigames.prinsgl.maths.Color;
import com.arquigames.prinsgl.maths.Face3;
import com.arquigames.prinsgl.maths.MathUtils;
import com.arquigames.prinsgl.maths.vectors.Vector2;
import com.arquigames.prinsgl.maths.vectors.Vector3;
import com.arquigames.prinsgl.morphing.MorphTarget;
import com.arquigames.prinsgl.textures.Texture;
import com.arquigames.extlib.json.JSONArray;
import com.arquigames.extlib.json.JSONException;
import com.arquigames.extlib.json.JSONObject;

import java.util.LinkedList;


/**
 * Created by usuario on 13/08/2016.
 */
public class ThreeJsonLoader {
    private static String TAG= "ThreeJsonLoader";
    public static boolean DEBUG = false;
    private Context context;
    private ObjectJSON textures;
    public ThreeJsonLoader(){
        this.textures = new ObjectJSON();
    }
    public ThreeJsonLoader(Context context){
        this();
        this.context = context;
    }
    public ObjectJSON load(String path){
        this.textures.clear();
        String contents = Util.readFileFromAssets2(path,this.context);
        if(contents!=null && !contents.isEmpty()){
            return this.parseModel(contents,path);
        }
        return null;
    }

    private ObjectJSON parseModel(String contents,String path) {
        float scale=1f;
        Double value;
        Geometry geometry = new Geometry();
        try {
            JSONObject json = new JSONObject(contents);
            value = json.getDouble("scale");
            if(value!=null && value!=0){
                scale = (float)(1f/value);
            }else{
                scale = 1f;
            }
            this.parseModel(json,geometry,scale);
            this.parseMorphing(json,geometry,scale);
            geometry.setNormalsNeedsUpdate(false);
            geometry.computeFaceNormals();
            geometry.computeBoundingSphere();

            java.util.LinkedList<Material> materials = null;
            JSONArray jsonArray = json.getJSONArray("materials");
            if(jsonArray!=null && jsonArray.length()>0){
                if(ThreeJsonLoader.DEBUG)Log.e(TAG,"parsing materials... wait, total = "+jsonArray.length());
                materials = this.initMaterials(jsonArray,path);
            }else{
                if(ThreeJsonLoader.DEBUG)Log.e(TAG,"materials = 0");
                materials = null;
            }


            ObjectJSON result = new ObjectJSON();
            result.put("geometry", geometry);
            if(materials!=null && materials.size()>0){
                result.put("materials",materials);
            }
            return result;

        } catch (JSONException e) {
            Log.e(TAG,Util.exceptionToString(e));
        }
        return null;
    }

    private void parseMorphing(JSONObject json, Geometry geometry, float scale) {
        MorphTarget morphTarget;
        JSONArray jsonArray = json.getJSONArray("morphTargets");
        JSONArray jsonArray1;
        java.util.Iterator<Object> iterator;
        JSONObject jsonObject;
        Object obj;
        Vector3 vertex;
        if(jsonArray!=null && jsonArray.length()>0){
             iterator = jsonArray.iterator();
            while(iterator.hasNext()){
                obj = iterator.next();
                if(obj instanceof JSONObject){
                    jsonObject = (JSONObject)obj;
                    jsonArray1 = jsonObject.getJSONArray("vertices");
                    //-----------------------------------
                    morphTarget = new MorphTarget();
                    //-----------------------------------
                    morphTarget.setName(jsonObject.getString("name"));
                    if(jsonArray1!=null && jsonArray1.length()>0){
                        for(int i=0,size = jsonArray1.length();i<size;i+=3){
                            vertex = new Vector3();
                            vertex.setX((float)jsonArray1.getDouble(i) * scale);
                            vertex.setY((float)jsonArray1.getDouble(i+1) * scale);
                            vertex.setZ((float)jsonArray1.getDouble(i+2) * scale);

                            morphTarget.getVertices().add(vertex);
                        }
                    }
                    geometry.getMorphTargets().add(morphTarget);
                }
            }
        }

    }

    private LinkedList<Material> initMaterials(JSONArray materials,String path) {
        Object obj = null;
        JSONObject jsonObject = null;
        java.util.LinkedList<Material> elements = new java.util.LinkedList<Material>();
        if(materials!=null && materials.length()>0){
            for(int i=0;i<materials.length();i++){
                jsonObject = materials.getJSONObject(i);
                Material mat = this.createMaterial(jsonObject,path);
                elements.add(mat);
            }
        }

        return elements;
    }

    private Material createMaterial(JSONObject jsonObject,String path) {

        Material mat = null;
        Color color = new Color();
        java.util.Iterator<String> iterator = jsonObject.keys();
        JSONObject json = new JSONObject();
        JSONArray jsonArray=null ;
        float[] array=null;

        json.put("uuid", MathUtils.generateUUID());
        json.put("type","MeshLambertMaterial");

        while(iterator.hasNext()){
            String key = iterator.next();
            switch ( key ) {
                case "DbgColor":
                case "DbgIndex":
                case "opticalDensity":
                case "illumination":
                    break;
                case "DbgName":
                    json.put("name",jsonObject.getString(key));
                    break;
                case "blending":
                    try{
                        json.put("blending", Constants.get(jsonObject.getString(key)));
                    }catch(Exception e){
                        if(ThreeJsonLoader.DEBUG)Log.e(TAG,"request value = "+jsonObject.getString(key));
                        if(ThreeJsonLoader.DEBUG)Log.e(TAG,Util.exceptionToString(e));
                    }
                    //json.blending = THREE[ value ];
                    break;
                case "colorAmbient":
                case "mapAmbient":
                    //console.warn( "THREE.Loader.createMaterial:", name, "is no longer supported." );
                    break;
                case "colorDiffuse":
                    jsonArray = jsonObject.getJSONArray(key);
                    if(jsonArray!=null && jsonArray.length()>0){
                        array = new float[3];
                        array[0] = (float)jsonArray.getDouble(0);
                        array[1] = (float)jsonArray.getDouble(1);
                        array[2] = (float)jsonArray.getDouble(2);
                        json.put("diffuseColor",new Color(array[0],array[1],array[2]));
                    }
                    break;
                case "colorSpecular":
                    jsonArray = jsonObject.getJSONArray(key);
                    if(jsonArray!=null && jsonArray.length()>0){
                        array = new float[3];
                        array[0] = (float)jsonArray.getDouble(0);
                        array[1] = (float)jsonArray.getDouble(1);
                        array[2] = (float)jsonArray.getDouble(2);
                        json.put("specularColor",new Color(array[0],array[1],array[2]));
                    }
                    break;
                case "colorEmissive":
                    jsonArray = jsonObject.getJSONArray(key);
                    if(jsonArray!=null && jsonArray.length()>0){
                        array = new float[3];
                        array[0] = (float)jsonArray.getDouble(0);
                        array[1] = (float)jsonArray.getDouble(1);
                        array[2] = (float)jsonArray.getDouble(2);
                        json.put("emissiveColor",new Color(array[0],array[1],array[2]));
                    }
                    break;
                case "specularCoef":
                    json.put("shininess",Float.valueOf(jsonObject.getDouble(key).floatValue()));
                    break;
                case "shading":
                    String value = jsonObject.getString(key);
                    if ( value.toLowerCase().equals("basic") ) json.put("type","MeshBasicMaterial") ;
                    if ( value.toLowerCase().equals("phong") ) json.put("type","MeshPhongMaterial") ;
                    break;
                case "mapDiffuse":
                    json.put("map",
                            this.loadTexture(
                                    jsonObject.getString(key),
                                    path,
                                    jsonObject.getJSONArray("mapDiffuseRepeat"),
                                    jsonObject.getJSONArray("mapDiffuseOffset"),
                                    jsonObject.getJSONArray("mapDiffuseWrap"),
                                    jsonObject.getDouble("mapDiffuseAnisotropy")
                            )
                    );
                    break;
                case "mapDiffuseRepeat":
                case "mapDiffuseOffset":
                case "mapDiffuseWrap":
                case "mapDiffuseAnisotropy":
                    break;
                case "mapLight":
                    json.put("lightMap",
                            this.loadTexture(
                                    jsonObject.getString(key),
                                    path,
                                    jsonObject.getJSONArray("mapLightRepeat"),
                                    jsonObject.getJSONArray("mapLightOffset"),
                                    jsonObject.getJSONArray("mapLightWrap"),
                                    jsonObject.getDouble("mapLightAnisotropy")
                            )
                    );
                    break;
                case "mapLightRepeat":
                case "mapLightOffset":
                case "mapLightWrap":
                case "mapLightAnisotropy":
                    break;
                case "mapAO":
                    json.put("aoMap",
                            this.loadTexture(
                                    jsonObject.getString(key),
                                    path,
                                    jsonObject.getJSONArray("mapAORepeat"),
                                    jsonObject.getJSONArray("mapAOOffset"),
                                    jsonObject.getJSONArray("mapAOWrap"),
                                    jsonObject.getDouble("mapAOAnisotropy")
                            )
                    );
                    break;
                case "mapAORepeat":
                case "mapAOOffset":
                case "mapAOWrap":
                case "mapAOAnisotropy":
                    break;
                case "mapBump":
                    json.put("bumpMap",
                            this.loadTexture(
                                    jsonObject.getString(key),
                                    path,
                                    jsonObject.getJSONArray("mapBumpRepeat"),
                                    jsonObject.getJSONArray("mapBumpOffset"),
                                    jsonObject.getJSONArray("mapBumpWrap"),
                                    jsonObject.getDouble("mapBumpAnisotropy")
                            )
                    );
                    break;
                case "mapBumpScale":
                    json.put("bumpScale",jsonObject.getDouble(key).floatValue());
                    break;
                case "mapBumpRepeat":
                case "mapBumpOffset":
                case "mapBumpWrap":
                case "mapBumpAnisotropy":
                    break;
                case "mapNormal":
                    json.put("normalMap",
                            this.loadTexture(
                                    jsonObject.getString(key),
                                    path,
                                    jsonObject.getJSONArray("mapNormalRepeat"),
                                    jsonObject.getJSONArray("mapNormalOffset"),
                                    jsonObject.getJSONArray("mapNormalWrap"),
                                    jsonObject.getDouble("mapNormalAnisotropy")
                            )
                    );
                    break;
                case "mapNormalFactor":
                    JSONArray arr = new JSONArray();
                    arr.put(jsonObject.getDouble(key).floatValue());
                    arr.put(jsonObject.getDouble(key).floatValue());
                    json.put("normalScale",arr);
                    break;
                case "mapNormalRepeat":
                case "mapNormalOffset":
                case "mapNormalWrap":
                case "mapNormalAnisotropy":
                    break;
                case "mapSpecular":
                    json.put("specularMap",
                            this.loadTexture(
                                    jsonObject.getString(key),
                                    path,
                                    jsonObject.getJSONArray("mapSpecularRepeat"),
                                    jsonObject.getJSONArray("mapSpecularOffset"),
                                    jsonObject.getJSONArray("mapSpecularWrap"),
                                    jsonObject.getDouble("mapSpecularAnisotropy")
                            )
                    );
                    break;
                case "mapSpecularRepeat":
                case "mapSpecularOffset":
                case "mapSpecularWrap":
                case "mapSpecularAnisotropy":
                    break;
                case "mapAlpha":
                    json.put("alphaMap",
                            this.loadTexture(
                                    jsonObject.getString(key),
                                    path,
                                    jsonObject.getJSONArray("mapAlphaRepeat"),
                                    jsonObject.getJSONArray("mapAlphaOffset"),
                                    jsonObject.getJSONArray("mapAlphaWrap"),
                                    jsonObject.getDouble("mapAlphaAnisotropy")
                            )
                    );
                    break;
                case "mapAlphaRepeat":
                case "mapAlphaOffset":
                case "mapAlphaWrap":
                case "mapAlphaAnisotropy":
                    break;
                case "flipSided":
                    try{
                        json.put("side",Constants.FACE_BACK_SIDE);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                case "doubleSided":
                    try{
                        json.put("side",Constants.FACE_DOUBLE_SIDE);
                    }catch(Exception e){
                        if(ThreeJsonLoader.DEBUG)Log.e(TAG, Util.exceptionToString(e));
                    }
                    break;
                case "opacity":
                    json.put("opacity",jsonObject.getDouble(key));
                    break;
                case "depthTest":
                case "depthWrite":
                case "colorWrite":
                case "reflectivity":
                case "transparent":
                case "visible":
                case "wireframe":
                    json.put(key,jsonObject.getBoolean(key));
                    break;
                case "vertexColors":
                    Boolean valB = jsonObject.getBoolean(key);
                    if(valB!=null){
                        try{
                            json.put("vertexColors",valB);
                        }catch(Exception e){
                            if(ThreeJsonLoader.DEBUG) Log.e(TAG, Util.exceptionToString(e));
                        }

                    }else{
                        String valS = jsonObject.getString(key);
                        if(valS!=null){
                            try{
                                json.put("vertexColors",Constants.get("FaceColors"));
                            }catch(Exception e){
                                if(ThreeJsonLoader.DEBUG) Log.e(TAG, Util.exceptionToString(e));
                            }

                        }
                    }
                    break;
                default:
                    //TODO
                    break;
            }
        }

        String type = json.getString("type");
        if(type!=null){
            if(type=="MeshBasicMaterial"){
                //json.remove("emissive");
            }
            if(type=="MeshPhongMaterial"){
                //json.remove("specular");
            }
        }
        Double opacity = json.getDouble("opacity");
        if(opacity!=null){
            if(opacity.floatValue()<1){
                json.put("transparent",true);
            }
        }
        try{
            mat = this.parseJsonMaterial(json);
        }catch(Exception e){
            if(ThreeJsonLoader.DEBUG) Log.e(TAG, Util.exceptionToString(e));
        }


        return mat;


    }

    private Material parseJsonMaterial(JSONObject json) {

        String type = json.getString("type");

        Material material;

        switch(type){
            case "MeshPhongMaterial":
                material = new MeshPhongMaterial();
                break;
            case "MeshBasicMaterial":
                material = new MeshBasicMaterial();
                break;
            default:
                //TODO
                return null;
        }
        Object obj;

        String uuid = json.getString("uuid");
        if(uuid!=null){
            material.setUuid(uuid);
        }
        String name = json.getString("name");
        if(name!=null){
            material.setName(name);
        }
        Color diffuseColor = (Color)json.get("diffuseColor");
        if(diffuseColor!=null){
            material.setDiffuseColor(diffuseColor);
        }
        Color emissiveColor = (Color)json.get("emissiveColor");
        if(emissiveColor!=null){
            material.setEmissiveColor(emissiveColor);
        }
        Color specularColor = (Color)json.get("specularColor");
        if(specularColor!=null){
            material.setSpecularColor(specularColor);
        }
        Integer shininess = json.getInt("shininess");
        if(shininess!=null){
            if(material instanceof MeshPhongMaterial){
                ( (MeshPhongMaterial)material ).setShininess(shininess);
            }
        }
        Integer blending = json.getInt("blending");
        if(blending!=null){
            material.setBlending(blending);
        }
        Integer side = json.getInt("side");
        if(side!=null){
            material.setSide(side);
        }
        Double opacity = (Double)json.get("opacity");
        if(opacity!=null){
            material.setOpacity(opacity.floatValue());
        }
        Boolean transparent = json.getBoolean("transparent");
        if(transparent!=null){
            material.setTransparent(transparent);
        }
        Boolean depthWrite = json.getBoolean("depthWrite");
        if(depthWrite!=null){
            material.setDepthWrite(depthWrite);
        }
        Boolean colorWrite = json.getBoolean("colorWrite");
        if(colorWrite!=null){
            material.setColorWrite(colorWrite);
        }
        Boolean wireframe = json.getBoolean("wireframe");
        if(wireframe!=null)material.setWireframe(wireframe);

        Double wireframeLinewidth = json.getDouble("wireframeLinewidth");
        if(wireframeLinewidth!=null)material.setLineWidth( wireframeLinewidth.floatValue() );

        String map = json.getString("map");
        if(map!=null){
            if(ThreeJsonLoader.DEBUG)Log.e(TAG,"parsing map");
            obj = this.textures.get(map);
            if(obj!=null && obj instanceof Texture){
                material.setMap((Texture)obj);
            }else{
                if(ThreeJsonLoader.DEBUG)Log.e(TAG,"parsing map is not Texture");
            }
        }else{
            if(ThreeJsonLoader.DEBUG)Log.e(TAG,"parsing map is null");
        }

        return material;
    }

    public int isBitSet(int value, int position){
        return value & ( 1 << position );
    }

    private void parseModel(JSONObject json, Geometry geometry, float scale) {
        int i, j, fi,

                offset, zLength,

                colorIndex, normalIndex, uvIndex, materialIndex,

                type;

        int isQuad,
                hasMaterial,
                hasFaceVertexUv,
                hasFaceNormal, hasFaceVertexNormal,
                hasFaceColor, hasFaceVertexColor;

        Vector3 vertex;
        Face3 face ,faceA, faceB;
        int hex;
        Vector3 normal;

        JSONArray uvLayer;
        Vector2 uv;
        float u, v,x,y,z;

        JSONArray faces 	= json.getJSONArray("faces");
        JSONArray vertices 	= json.getJSONArray("vertices");
        JSONArray normals 	= json.getJSONArray("normals");
        JSONArray colors 	= json.getJSONArray("colors");
        JSONArray uvs 		= json.getJSONArray("uvs");

        if(uvs!=null && uvs.length()>0){
            uvs = uvs.getJSONArray(0);
        }

        JSONArray jsonArray = null;


        int nUvLayers = 0;

        Object obj;
        Double dtemp;

        offset = 0;
        zLength = vertices!=null ? vertices.length() : 0;
        if(ThreeJsonLoader.DEBUG)Log.e(TAG,"total vertices = "+zLength);
        while ( offset < zLength ) {
            //-----------------------------------------
            dtemp = vertices.getDouble(offset++);
            x = dtemp.floatValue() * scale;
            //-----------------------------------------
            dtemp = vertices.getDouble(offset++);
            y = dtemp.floatValue() * scale;
            //-----------------------------------------
            dtemp = vertices.getDouble(offset++);
            z = dtemp.floatValue() * scale;
            //-----------------------------------------
            if(ThreeJsonLoader.DEBUG)Log.e(TAG,"addVertex(x="+x+",y="+y+",z="+z+")");
            geometry.addVertex(x,y,z);
            //-----------------------------------------
        }
        int currentIndex = 0;
        offset = 0;
        zLength = faces!=null ? faces.length() : 0;
        if(ThreeJsonLoader.DEBUG)Log.e(TAG,"total faces = "+zLength);
        while ( offset < zLength ) {
            currentIndex = offset;
            type = faces.getInt(offset++);


            isQuad              = this.isBitSet(type, 0);
            hasMaterial         = this.isBitSet(type, 1);
            hasFaceVertexUv     = this.isBitSet(type, 3);
            hasFaceNormal       = this.isBitSet(type, 4);
            hasFaceVertexNormal = this.isBitSet(type, 5);
            hasFaceColor        = this.isBitSet(type, 6);
            hasFaceVertexColor  = this.isBitSet(type, 7);


            if ( isQuad>0 ) {
                if(ThreeJsonLoader.DEBUG){
                    Log.e(TAG, "faces[" + currentIndex + "]=" + type + " is Quad");
                }

                faceA = new Face3();

                faceA.setIndexA((short)faces.getInt(offset));
                faceA.setIndexB((short)faces.getInt(offset + 1));
                faceA.setIndexC((short)faces.getInt(offset + 3));

                faceB = new Face3();

                faceB.setIndexA((short)faces.getInt(offset + 1));
                faceB.setIndexB((short)faces.getInt(offset + 2));
                faceB.setIndexC((short)faces.getInt(offset + 3));

                offset += 4;

                if ( hasMaterial>0 ) {
                    if(ThreeJsonLoader.DEBUG)Log.e(TAG,"faces["+currentIndex+"]="+type+" has material");
                    materialIndex = faces.getInt(offset ++);
                    faceA.setMaterialIndex(materialIndex);
                    faceB.setMaterialIndex(materialIndex);

                    if(hasFaceVertexUv>0){
                        for ( j = 0; j < 4; j ++ ) {

                            uvIndex = faces.getInt(offset ++);

                            u = (float)uvs.getDouble(uvIndex * 2);
                            v = (float)uvs.getDouble(uvIndex * 2 + 1);

                            uv = new Vector2( u, v );
                            //------------------------------------
                            if(j==0)faceA.setUvAtIndexA(uv.clone());
                            if(j==1)faceA.setUvAtIndexB(uv.clone());
                            if(j==3)faceA.setUvAtIndexC(uv.clone());
                            //------------------------------------
                            if(j==1)faceB.setUvAtIndexA(uv.clone());
                            if(j==2)faceB.setUvAtIndexB(uv.clone());
                            if(j==3)faceB.setUvAtIndexC(uv.clone());
                            //------------------------------------

                        }
                    }
                    if(hasFaceNormal>0){
                        if(ThreeJsonLoader.DEBUG)Log.e(TAG,"faces["+currentIndex+"]="+type+" has face normal");
                        normalIndex = faces.getInt(offset ++) * 3;

                        faceA.setNormal(
                                new Vector3(
                                        (float)normals.getDouble(normalIndex ++),
                                        (float)normals.getDouble(normalIndex ++),
                                        (float)normals.getDouble(normalIndex)
                                )
                        );

                        faceB.setNormal(faceA.getNormal().clone());
                    }
                    if(hasFaceVertexNormal>0){
                        if(GLRenderer.DEBUG)Log.e(TAG,"faces["+currentIndex+"]="+type+" has face vertex normal");
                        for ( i = 0; i < 4; i ++ ) {

                            normalIndex = faces.getInt(offset ++) * 3;

                            normal = new Vector3(
                                    (float)normals.getDouble(normalIndex ++),
                                    (float)normals.getDouble(normalIndex ++),
                                    (float)normals.getDouble(normalIndex)
                            );
                            //-----------------------------------------
                            if(i==0)faceA.setNormalA(normal.clone());
                            if(i==1)faceA.setNormalB(normal.clone());
                            if(i==3)faceA.setNormalC(normal.clone());
                            //-----------------------------------------
                            if(i==1)faceB.setNormalA(normal.clone());
                            if(i==2)faceB.setNormalB(normal.clone());
                            if(i==3)faceB.setNormalC(normal.clone());
                            //-----------------------------------------
                        }
                    }
                    if(hasFaceColor>0){
                        if(ThreeJsonLoader.DEBUG)Log.e(TAG,"faces["+currentIndex+"]="+type+" has face color");
                        colorIndex = faces.getInt(offset ++);
                        hex = colors.getInt(colorIndex);

                        Color _c = new Color().setHex( hex );
                        faceA.setColor(_c);
                        _c = new Color().setHex( hex );
                        faceB.setColor(_c);
                    }
                    if ( hasFaceVertexColor>0 ) {
                        if(ThreeJsonLoader.DEBUG)Log.e(TAG,"faces["+currentIndex+"]="+type+" has face vertex color");
                        for ( i = 0; i < 4; i ++ ) {

                            colorIndex = faces.getInt(offset ++);
                            hex = colors.getInt(colorIndex);

                            Color tmpColor = new Color( hex );

                            //--------------------------------------------------
                            if(i==0)faceA.setColorAtIndexA(tmpColor.clone());
                            if(i==1)faceA.setColorAtIndexB(tmpColor.clone());
                            if(i==3)faceA.setColorAtIndexC(tmpColor.clone());
                            //--------------------------------------------------
                            if(i==1)faceB.setColorAtIndexA(tmpColor.clone());
                            if(i==2)faceB.setColorAtIndexB(tmpColor.clone());
                            if(i==3)faceB.setColorAtIndexC(tmpColor.clone());
                            //--------------------------------------------------

                        }

                    }
                    if(ThreeJsonLoader.DEBUG)Log.e(TAG,"add faceA = "+faceA.toString());
                    geometry.addFace3(faceA);
                    if(ThreeJsonLoader.DEBUG)Log.e(TAG,"add faceB = "+faceB.toString());
                    geometry.addFace3(faceB);

                }
            }else{
                //IS ONLY FACE(NO QUAD)
                if(ThreeJsonLoader.DEBUG)Log.e(TAG,"faces["+currentIndex+"]="+type+" has no Quad");
                face = new Face3();
                face.setIndexA((short)faces.getInt(offset ++));
                face.setIndexB((short)faces.getInt(offset ++));
                face.setIndexC((short)faces.getInt(offset ++));

                if ( hasMaterial>0 ) {
                    if(ThreeJsonLoader.DEBUG)Log.e(TAG,"faces["+currentIndex+"]="+type+" has material");
                    materialIndex = faces.getInt(offset ++);
                    face.setMaterialIndex(materialIndex);

                }
                if(hasFaceVertexUv>0){
                    for ( j = 0; j < 3; j ++ ) {

                        uvIndex = faces.getInt(offset ++);

                        u = (float)uvs.getDouble(uvIndex * 2);
                        v = (float)uvs.getDouble(uvIndex * 2 + 1);

                        uv = new Vector2( u, v );
                        if(j==0)face.setUvAtIndexA(uv);
                        if(j==1)face.setUvAtIndexA(uv);
                        if(j==2)face.setUvAtIndexA(uv);

                    }
                }
                if ( hasFaceNormal>0 ) {
                    if(ThreeJsonLoader.DEBUG)Log.e(TAG,"faces["+currentIndex+"]="+type+" has face normal");
                    normalIndex = faces.getInt(offset ++) * 3;

                    face.setNormal(
                            new Vector3(
                                    (float)normals.getDouble(normalIndex ++),
                                    (float)normals.getDouble(normalIndex ++),
                                    (float)normals.getDouble(normalIndex )
                            )
                    );

                }
                if ( hasFaceVertexNormal>0 ) {
                    if(ThreeJsonLoader.DEBUG)Log.e(TAG,"faces["+currentIndex+"]="+type+" has face vertex normal");
                    for ( i = 0; i < 3; i ++ ) {

                        normalIndex = faces.getInt(offset ++) * 3;

                        normal = new Vector3(
                                (float)normals.getDouble(normalIndex ++),
                                (float)normals.getDouble(normalIndex ++),
                                (float)normals.getDouble(normalIndex )
                        );
                        if(i==0)face.setNormalA(normal);
                        if(i==1)face.setNormalB(normal);
                        if(i==2)face.setNormalC(normal);

                    }

                }
                if ( hasFaceColor>0 ) {
                    if(ThreeJsonLoader.DEBUG)Log.e(TAG,"faces["+currentIndex+"]="+type+" has face color");
                    colorIndex = faces.getInt(offset ++);
                    face.setColor(new Color(colors.getInt(colorIndex)));

                }
                if ( hasFaceVertexColor>0 ) {
                    if(ThreeJsonLoader.DEBUG)Log.e(TAG,"faces["+currentIndex+"]="+type+" has face vertex color");
                    for ( i = 0; i < 3; i ++ ) {

                        colorIndex = faces.getInt(offset ++);
                        if(i==0)face.setColor("a",new Color( colors.getInt(colorIndex) ));
                        if(i==1)face.setColor("b",new Color( colors.getInt(colorIndex) ));
                        if(i==2)face.setColor("c",new Color( colors.getInt(colorIndex) ));

                    }

                }
                if(ThreeJsonLoader.DEBUG)Log.e(TAG,"add face = "+face.toString());
                geometry.addFace3(face);


            }

        }

    }
    public String loadTexture(
            String filePath,
            String path,
            JSONArray repeat,
            JSONArray offset,
            JSONArray wrap,
            Double anisotropy
    ){
        Texture texture = new Texture();

        Bitmap bitmap = null;
        try{
            if(ThreeJsonLoader.DEBUG)Log.e(TAG,"setting bitmap from = "+filePath);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888;
            if(ThreeJsonLoader.DEBUG)Log.e(TAG,"Config -> "+opts.inPreferredConfig);

            bitmap = TextureUtils.getBitmapFromAssetPath(this.context,filePath);
            texture.setImage(bitmap);
            if(ThreeJsonLoader.DEBUG)Log.e(TAG, "Config_1 -> " + bitmap.getConfig());
            if(repeat!=null){
                texture.getRepeat().setX((float) repeat.getDouble(0));
                texture.getRepeat().setY((float)repeat.getDouble(1));

                if(repeat.getDouble(0)!=1){
                    texture.setWrapS(Constants.REPEAT_WRAPPING);
                }
                if(repeat.getDouble(1)!=1){
                    texture.setWrapT(Constants.REPEAT_WRAPPING);
                }

            }
            if(offset!=null){
                texture.getOffset().setX((float) offset.getDouble(0));
                texture.getOffset().setY((float)offset.getDouble(1));
            }
            if(wrap!=null){
                String str0 = wrap.getString(0);
                String str1 = wrap.getString(1);

                if(str0.equals("repeat"))texture.setWrapS( Constants.REPEAT_WRAPPING);
                if(str0.equals("mirror"))texture.setWrapS(Constants.MIRRORED_REPEAT_WRAPPING);

                if(str1.equals("repeat"))texture.setWrapT(Constants.REPEAT_WRAPPING);
                if(str1.equals("mirror"))texture.setWrapT(Constants.MIRRORED_REPEAT_WRAPPING);

            }
            if(anisotropy!=null){
                texture.setAnisotropy(anisotropy.floatValue());
            }

            String uuid = MathUtils.generateUUID();
            texture.setUuid(uuid);
            this.textures.put(uuid, texture);
            if(ThreeJsonLoader.DEBUG)Log.e(TAG,"uuid from loadTexture = "+uuid);
            return uuid;

        }catch(Exception e){
            if(ThreeJsonLoader.DEBUG) Log.e(TAG, Util.exceptionToString(e));
        }

        return null;
    }
}
