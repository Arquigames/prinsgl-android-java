package com.arquigames.prinsgl;


import android.support.annotation.NonNull;
import android.util.Log;

import com.arquigames.prinsgl.geometries.Geometry;
import com.arquigames.prinsgl.geometries.buffers.BufferGeometry;
import com.arquigames.prinsgl.gl.attributes.BufferAttribute;
import com.arquigames.prinsgl.gl.renderer.GLRenderer;
import com.arquigames.prinsgl.maths.Color;
import com.arquigames.prinsgl.maths.Face3;
import com.arquigames.prinsgl.maths.vectors.Vector2;
import com.arquigames.prinsgl.maths.vectors.Vector3;
import com.arquigames.prinsgl.morphing.MorphTarget;

import java.util.LinkedList;

/**
 * Created by usuario on 06/08/2016.
 */
public class GeometryUtils {
    private static String TAG = "GeometryUtils";
    public static boolean ECHO_STEP = false;
    public static void makeBuffersFromFace3(Geometry geometry) {
        java.util.LinkedList<Vector3> vertices = geometry.getVertices();
        java.util.LinkedList<Face3> faces = geometry.getFaces();

        java.util.Iterator<Face3> iteratorFaces = faces.iterator();
        Face3 face3;
        Vector3 vertex;
        Vector3 normal;
        Vector2 vector2;
        Color color;

        int _verticesCount      = 0;
        int _uvsCount           = 0;
        int _colorsCount        = 0;
        int _normalsCount       = 0;

        float[] _vertices   = null;
        float[] _normals    = null;
        float[] _uvs        = null;
        float[] _colors     = null;
        if(vertices.size()>0){
            _vertices   = new float[faces.size() * 3 * 3];
            _uvs        = new float[faces.size() * 3 * 2];
            _colors     = new float[faces.size() * 3 * 3];
            _normals    = new float[faces.size() * 3 * 3];
        }

        while(iteratorFaces.hasNext()){
            face3 = iteratorFaces.next();
            vertex = vertices.get(face3.getIndexA());
            _vertices[_verticesCount++] = vertex.getX();
            _vertices[_verticesCount++] = vertex.getY();
            _vertices[_verticesCount++] = vertex.getZ();
            //-----------------------------------------------
            vertex = vertices.get(face3.getIndexB());
            _vertices[_verticesCount++] = vertex.getX();
            _vertices[_verticesCount++] = vertex.getY();
            _vertices[_verticesCount++] = vertex.getZ();
            //-----------------------------------------------
            vertex = vertices.get(face3.getIndexC());
            _vertices[_verticesCount++] = vertex.getX();
            _vertices[_verticesCount++] = vertex.getY();
            _vertices[_verticesCount++] = vertex.getZ();
            //-----------------------------------------------
            vector2 = face3.getUvA();
            if(vector2!=null) {
                _uvs[_uvsCount++] = vector2.getX();
                _uvs[_uvsCount++] = vector2.getY();
            }else{
                _uvs[_uvsCount++] = 0f;
                _uvs[_uvsCount++] = 0f;
            }
            //-----------------------------------------------
            vector2 = face3.getUvB();
            if(vector2!=null){
                _uvs[_uvsCount++] = vector2.getX();
                _uvs[_uvsCount++] = vector2.getY();
            }else{
                _uvs[_uvsCount++] = 0f;
                _uvs[_uvsCount++] = 0f;
            }
            //-----------------------------------------------
            vector2 = face3.getUvC();
            if(vector2!=null){
                _uvs[_uvsCount++] = vector2.getX();
                _uvs[_uvsCount++] = vector2.getY();
            }else{
                _uvs[_uvsCount++] = 0f;
                _uvs[_uvsCount++] = 0f;
            }
            color = face3.getColorA();
            if(color!=null){
                _colors[_colorsCount++] = color.getR();
                _colors[_colorsCount++] = color.getG();
                _colors[_colorsCount++] = color.getB();
            }else{
                _colors[_colorsCount++] = 0f;
                _colors[_colorsCount++] = 0f;
                _colors[_colorsCount++] = 0f;
            }
            //-----------------------------------------------
            color = face3.getColorB();
            if(color!=null){
                _colors[_colorsCount++] = color.getR();
                _colors[_colorsCount++] = color.getG();
                _colors[_colorsCount++] = color.getB();
            }else{
                _colors[_colorsCount++] = 0f;
                _colors[_colorsCount++] = 0f;
                _colors[_colorsCount++] = 0f;
            }

            //-----------------------------------------------
            color = face3.getColorC();
            if(color!=null){
                _colors[_colorsCount++] = color.getR();
                _colors[_colorsCount++] = color.getG();
                _colors[_colorsCount++] = color.getB();
            }else{
                _colors[_colorsCount++] = 0f;
                _colors[_colorsCount++] = 0f;
                _colors[_colorsCount++] = 0f;
            }

            normal = face3.getNormalA();
            if(normal!=null){
                _normals[_normalsCount++] = normal.getX();
                _normals[_normalsCount++] = normal.getY();
                _normals[_normalsCount++] = normal.getZ();
            }else{
                _normals[_normalsCount++] = 0f;
                _normals[_normalsCount++] = 0f;
                _normals[_normalsCount++] = 0f;
            }
            normal = face3.getNormalB();
            if(normal!=null){
                _normals[_normalsCount++] = normal.getX();
                _normals[_normalsCount++] = normal.getY();
                _normals[_normalsCount++] = normal.getZ();
            }else{
                _normals[_normalsCount++] = 0f;
                _normals[_normalsCount++] = 0f;
                _normals[_normalsCount++] = 0f;
            }
            normal = face3.getNormalC();
            if(normal!=null){
                _normals[_normalsCount++] = normal.getX();
                _normals[_normalsCount++] = normal.getY();
                _normals[_normalsCount++] = normal.getZ();
            }else{
                _normals[_normalsCount++] = 0f;
                _normals[_normalsCount++] = 0f;
                _normals[_normalsCount++] = 0f;
            }

        }
        BufferAttribute bufferAttribute;
        if(_vertices!=null && _vertices.length>0){
            if(GeometryUtils.ECHO_STEP)Log.e(TAG,"_vertices.length = "+_vertices.length);
            bufferAttribute = new BufferAttribute(_vertices,3);
            if(GeometryUtils.ECHO_STEP)Log.e(TAG,"Make Vertices = "+Util.print(bufferAttribute.getFloatArray()));
            geometry.getBuffers().put("position",bufferAttribute);
        }else{
            geometry.getBuffers().remove("position");
        }
        if(_uvs!=null && _uvs.length>0){
            bufferAttribute = new BufferAttribute(_uvs,2);
            geometry.getBuffers().put("uv",bufferAttribute);
        }else{
            geometry.getBuffers().remove("uv");
        }
        if(_colors!=null && _colors.length>0){
            bufferAttribute = new BufferAttribute(_colors,3);
            geometry.getBuffers().put("color",bufferAttribute);
        }else{
            geometry.getBuffers().remove("color");
        }
        if(_normals!=null && _normals.length>0){
            bufferAttribute = new BufferAttribute(_normals,3);
            geometry.getBuffers().put("normal",bufferAttribute);
        }else{
            geometry.getBuffers().remove("normal");
        }
        //------------------------------------------------------------------------
        java.util.Iterator<MorphTarget> morphTargetIterator = geometry.getMorphTargets().iterator();
        MorphTarget morphTarget;
        java.util.LinkedList<BufferAttribute> bufferAttributes = new java.util.LinkedList<BufferAttribute>();
        while(morphTargetIterator.hasNext()){
            morphTarget = morphTargetIterator.next();
            //-------------------------------------------------
            bufferAttribute = new BufferAttribute();
            bufferAttribute.setV3(morphTarget.getVertices());
            bufferAttribute.setName(morphTarget.getName());
            //-------------------------------------------------
            bufferAttributes.add(bufferAttribute);
        }
        geometry.setMorphAttributes(bufferAttributes);
        //------------------------------------------------------------------------

    }
    public static void makeWireframeAttribute(Geometry geometry){
        BufferAttribute wireframe = null;
        wireframe = (BufferAttribute) geometry.getBuffers().get("wireframe");
        if(wireframe==null){

            BufferAttribute indices = (BufferAttribute)geometry.getBuffers().get("indices");
            if(indices==null)return;

            if(indices.isIntType()){
                int[] _wireframe = null;
                int _wireframeCount = 0;
                java.util.LinkedList<Integer> newIndices = null;
                if(indices.getShortArray().length>0){
                    int[] _indices = indices.getIntArray();
                    int indexA,indexB,indexC;

                    newIndices = new java.util.LinkedList<>();

                    android.util.SparseArray<java.util.LinkedList<Integer>> edges =
                            new android.util.SparseArray<>();

                    for(int i=0,length = _indices.length;i<length;i +=3){
                        indexA = _indices[i];
                        indexB = _indices[i+1];
                        indexC = _indices[i+2];
                        if(GeometryUtils.checkEdgesFromIndices(edges,indexA,indexB)){
                            newIndices.add(indexA);
                            newIndices.add(indexB);
                        }
                        if(GeometryUtils.checkEdgesFromIndices(edges,indexB,indexC)){
                            newIndices.add(indexB);
                            newIndices.add(indexC);
                        }
                        if(GeometryUtils.checkEdgesFromIndices(edges,indexC,indexA)){
                            newIndices.add(indexC);
                            newIndices.add(indexA);
                        }
                    }
                }else{

                    BufferAttribute position = (BufferAttribute)geometry.getBuffers().get("position");
                    int b,c;
                    if(position!=null && position.getFloatArray().length>0){
                        newIndices = new java.util.LinkedList<>();
                        for ( int i = 0, l = ( position.getFloatArray().length / 3 - 1); i < l; i += 3 ) {

                            b = i + 1;
                            c = i + 2;

                            newIndices.add(i);//a
                            newIndices.add(b);
                            newIndices.add(b);
                            newIndices.add(c);
                            newIndices.add(c);
                            newIndices.add(i);//a

                        }
                    }
                }

                if(newIndices!=null && newIndices.size()>0){
                    _wireframe = new int[newIndices.size()];
                    for (int newIndex : newIndices) {
                        _wireframe[_wireframeCount++] = newIndex;
                    }
                    geometry.getBuffers().put("wireframe",new BufferAttribute(_wireframe,1));
                }
            }else if(indices.isShortType()){
                short[] _wireframe = null;
                int _wireframeCount = 0;
                java.util.LinkedList<Short> newIndices = null;
                if(indices.getShortArray().length>0){
                    short[] _indices = indices.getShortArray();
                    short indexA,indexB,indexC;

                    newIndices = new java.util.LinkedList<>();

                    android.util.SparseArray<java.util.LinkedList<Short>> edges =
                            new android.util.SparseArray<>();

                    for(int i=0,length = _indices.length;i<length;i +=3){
                        indexA = _indices[i];
                        indexB = _indices[i+1];
                        indexC = _indices[i+2];
                        if(GeometryUtils.checkEdgesFromIndices(edges,indexA,indexB)){
                            newIndices.add(indexA);
                            newIndices.add(indexB);
                        }
                        if(GeometryUtils.checkEdgesFromIndices(edges,indexB,indexC)){
                            newIndices.add(indexB);
                            newIndices.add(indexC);
                        }
                        if(GeometryUtils.checkEdgesFromIndices(edges,indexC,indexA)){
                            newIndices.add(indexC);
                            newIndices.add(indexA);
                        }
                    }
                }else{

                    BufferAttribute position = (BufferAttribute)geometry.getBuffers().get("position");
                    short b,c;
                    if(position!=null && position.getFloatArray().length>0){
                        newIndices = new java.util.LinkedList<Short>();
                        for ( short i = 0, l = (short)( position.getFloatArray().length / 3 - 1); i < l; i += 3 ) {

                            b = (short)(i + 1);
                            c = (short)(i + 2);

                            newIndices.add(i);//a
                            newIndices.add(b);
                            newIndices.add(b);
                            newIndices.add(c);
                            newIndices.add(c);
                            newIndices.add(i);//a

                        }
                    }
                }

                if(newIndices!=null && newIndices.size()>0){
                    _wireframe = new short[newIndices.size()];
                    for (short newIndex : newIndices) {
                        _wireframe[_wireframeCount++] = newIndex;
                    }
                    geometry.getBuffers().put("wireframe",new BufferAttribute(_wireframe,1));
                }
            }else{

            }



        }
    }
    public static boolean checkEdgesFromIndices(
            android.util.SparseArray<java.util.LinkedList<Short>> edges,
            short indexA,
            short indexB
    ){
        if ( indexA > indexB ) {
            short tmp = indexA;
            indexA = indexB;
            indexB = tmp;
        }
        boolean flag = false;
        java.util.LinkedList<Short> list = edges.get(indexA);
        if(list==null){
            list = new java.util.LinkedList<>();
            list.add(indexB);
            edges.put(indexA,list);
            flag = true;
        }else{
            if(list.indexOf(indexB)<0){
                list.add(indexB);
                flag = true;
            }
        }
        return flag;
    }
    public static boolean checkEdgesFromIndices(
            android.util.SparseArray<java.util.LinkedList<Integer>> edges,
            int indexA,
            int indexB
    ){
        if ( indexA > indexB ) {
            int tmp = indexA;
            indexA = indexB;
            indexB = tmp;
        }
        boolean flag = false;
        java.util.LinkedList<Integer> list = edges.get(indexA);
        if(list==null){
            list = new java.util.LinkedList<>();
            list.add(indexB);
            edges.put(indexA,list);
            flag = true;
        }else{
            if(list.indexOf(indexB)<0){
                list.add(indexB);
                flag = true;
            }
        }
        return flag;
    }

    public static BufferAttribute buildShortIndicesForLines(LinkedList<Short> indices) {
        //PROVIENE DE INDICES DE TRIANGULOS (GL_TRIANGLES)
        BufferAttribute bufferAttribute = null;
        if(indices!=null && indices.size()>0 && indices.size()%3==0){
            android.util.SparseArray<java.util.LinkedList<Short>> edges =
                    new android.util.SparseArray<>();

            java.util.LinkedList<Short> newIndices = new java.util.LinkedList<>();
            short a,b,c;
            for(int i=0,l=indices.size();i<l;i+=3){
                a = indices.get(i);
                b = indices.get(i+1);
                c = indices.get(i+2);

                if(GeometryUtils.checkEdgesFromIndices(edges,a,b)){
                    newIndices.add(a);
                    newIndices.add(b);
                }
                if(GeometryUtils.checkEdgesFromIndices(edges,b,c)){
                    newIndices.add(b);
                    newIndices.add(c);
                }
                if(GeometryUtils.checkEdgesFromIndices(edges,c,a)){
                    newIndices.add(c);
                    newIndices.add(a);
                }
            }
            if(newIndices.size()>0){
                short[] _wireframe = new short[newIndices.size()];
                int _wireframeCount = 0;
                for (Short newIndex : newIndices) {
                    _wireframe[_wireframeCount++] = newIndex;
                }
                bufferAttribute = new BufferAttribute(_wireframe,1);
            }
        }
        return bufferAttribute;
    }
    public static BufferAttribute buildIntIndicesForLines(LinkedList<Integer> indices) {
        //PROVIENE DE INDICES DE TRIANGULOS (GL_TRIANGLES)
        BufferAttribute bufferAttribute = null;
        if(indices!=null && indices.size()>0 && indices.size()%3==0){
            android.util.SparseArray<java.util.LinkedList<Integer>> edges =
                    new android.util.SparseArray<>();

            java.util.LinkedList<Integer> newIndices = new java.util.LinkedList<>();
            int a,b,c;
            for(int i=0,l=indices.size();i<l;i+=3){
                a = indices.get(i);
                b = indices.get(i+1);
                c = indices.get(i+2);

                if(GeometryUtils.checkEdgesFromIndices(edges,a,b)){
                    newIndices.add(a);
                    newIndices.add(b);
                }
                if(GeometryUtils.checkEdgesFromIndices(edges,b,c)){
                    newIndices.add(b);
                    newIndices.add(c);
                }
                if(GeometryUtils.checkEdgesFromIndices(edges,c,a)){
                    newIndices.add(c);
                    newIndices.add(a);
                }
            }
            if(newIndices.size()>0){
                int[] _wireframe = new int[newIndices.size()];
                int _wireframeCount = 0;
                for (Integer newIndex : newIndices) {
                    _wireframe[_wireframeCount++] = newIndex;
                }
                bufferAttribute = new BufferAttribute(_wireframe,1);
            }
        }
        return bufferAttribute;
    }

    public static void buildGeometryFromBufferGeometry(@NonNull Geometry geometry,@NonNull BufferGeometry bufferGeometry) {
        ObjectJSON attributes = bufferGeometry.getAttributes();
        float[] vertices    = attributes.get("position")!=null  ? ((BufferAttribute)attributes.get("position")).getFloatArray() : null;
        float[] normals     = attributes.get("normal")!=null    ? ((BufferAttribute)attributes.get("normal")).getFloatArray()   : null;
        float[] colors      = attributes.get("color")!=null     ? ((BufferAttribute)attributes.get("color")).getFloatArray()    : null;
        float[] uvs         = attributes.get("uv")!=null        ? ((BufferAttribute)attributes.get("uv")).getFloatArray()       : null;

        BufferAttribute indices = ((BufferAttribute)attributes.get("indices"));


        short[] shortIndices = null;
        int[] intIndices = null;

        if(indices.isShortType()){
            shortIndices = indices.getShortArray();
        }else if(indices.isIntType()){
            intIndices = indices.getIntArray();
        }else{
            //TODO
        }

        int i=0,j=0,length=0;
        Face3 face;
        java.util.LinkedList<Vector3> normal = null;
        java.util.LinkedList<Color> color = null;
        java.util.LinkedList<Vector2> uv = null;
        if(vertices!=null && vertices.length>0 && vertices.length%3==0){
            if(normals!=null && normals.length>0) {
                normal = new LinkedList<>();
            }
            if(colors!=null && colors.length>0) {
                color = new LinkedList<>();
            }
            if(uvs!=null && uvs.length>0) {
                uv = new LinkedList<>();
            }
            for(length=vertices.length;i<length;i+=3,j+=2){
                geometry.getVertices().add(new Vector3(vertices[i],vertices[i+1],vertices[i+2]));
                if(normal!=null && normals!=null) {
                    normal.add(new Vector3(normals[i], normals[i + 1], normals[i + 2]));
                }
                if(color!=null && colors!=null) {
                    color.add(new Color(colors[i], colors[i + 1], colors[i + 2]));
                }
                if(uv!=null && uvs!=null) {
                    uv.add(new Vector2(uvs[j], uvs[j + 1]));
                }
            }
        }

        //TRIANGLE
        if(shortIndices!=null && shortIndices.length>0 && shortIndices.length%3==0){
            i=0;
            length = shortIndices.length;
            for(;i<length;i+=3){
                face = new Face3(shortIndices[i],shortIndices[i+1],shortIndices[i+2]);
                if(normals!=null){
                    face.setNormalA(normal.get(shortIndices[i]));
                    face.setNormalB(normal.get(shortIndices[i+1]));
                    face.setNormalC(normal.get(shortIndices[i+2]));
                }
                if(colors!=null){
                    face.setColorAtIndexA(color.get(shortIndices[i]));
                    face.setColorAtIndexB(color.get(shortIndices[i+1]));
                    face.setColorAtIndexC(color.get(shortIndices[i+2]));
                }
                if(uvs!=null){
                    face.setUvAtIndexA(uv.get(shortIndices[i]));
                    face.setUvAtIndexB(uv.get(shortIndices[i+1]));
                    face.setUvAtIndexC(uv.get(shortIndices[i+2]));
                }
                geometry.addFace3(face);
            }
        }
        if(intIndices!=null && intIndices.length>0 && intIndices.length%3==0){
            i=0;
            length = intIndices.length;
            for(;i<length;i+=3){
                face = new Face3(shortIndices[i],intIndices[i+1],intIndices[i+2]);
                if(normals!=null){
                    face.setNormalA(normal.get(intIndices[i]));
                    face.setNormalB(normal.get(intIndices[i+1]));
                    face.setNormalC(normal.get(intIndices[i+2]));
                }
                if(colors!=null){
                    face.setColorAtIndexA(color.get(intIndices[i]));
                    face.setColorAtIndexB(color.get(intIndices[i+1]));
                    face.setColorAtIndexC(color.get(intIndices[i+2]));
                }
                if(uvs!=null){
                    face.setUvAtIndexA(uv.get(intIndices[i]));
                    face.setUvAtIndexB(uv.get(intIndices[i+1]));
                    face.setUvAtIndexC(uv.get(intIndices[i+2]));
                }
                geometry.addFace3(face);
            }
        }

    }
}
