package com.arquigames.prinsgl.gl.attributes;

import android.opengl.GLES20;

import com.arquigames.prinsgl.maths.Face3;
import com.arquigames.prinsgl.maths.vectors.Vector2;
import com.arquigames.prinsgl.maths.vectors.Vector3;
import com.arquigames.prinsgl.maths.vectors.Vector4;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by usuario on 07/08/2016.
 */
public class BufferAttribute implements Cloneable{

    protected String name = "";

    protected float[] floatArray;
    protected short[] shortArray;
    protected int[] intArray;
    protected int glType=0;

    protected int coordsPerData=0;

    protected int stride=0;

    protected Buffer buffer;

    protected int mode = GLES20.GL_TRIANGLES;

    protected boolean isNormalized = false;//ONLY FOR VECTOR2 OR VECTOR3 ATTRIBUTESs

    public BufferAttribute(){

    }
    public BufferAttribute(int numValues,int coordsPerData, int glType){
        this.coordsPerData = coordsPerData;
        this.glType = glType;
        switch (glType){
            case GLES20.GL_FLOAT:
                this.floatArray = new float[numValues];
                this.stride = coordsPerData * 4;
                break;
            case GLES20.GL_UNSIGNED_SHORT:
                this.shortArray = new short[numValues];
                this.stride = coordsPerData * 2;
                break;
            case GLES20.GL_UNSIGNED_INT:
                this.intArray = new int[numValues];
                this.stride = coordsPerData * 2;
                break;
        }
    }
    public BufferAttribute(float [] values,int coordsPerData, int glType){
        this.floatArray = values;
        this.coordsPerData = coordsPerData;
        this.glType = glType;
        this.stride = coordsPerData * 4;
    }
    public BufferAttribute(short [] values,int coordsPerData, int glType){
        this.shortArray = values;
        this.coordsPerData = coordsPerData;
        this.glType = glType;
        this.stride = coordsPerData * 2;
    }
    public BufferAttribute(int [] values,int coordsPerData, int glType){
        this.intArray = values;
        this.coordsPerData = coordsPerData;
        this.glType = glType;
        this.stride = coordsPerData * 2;
    }
    public float[] getFloatArray(){
        return this.floatArray;
    }
    public short[] getShortArray(){
        return this.shortArray;
    }
    public int[] getIntArray(){
        return this.intArray;
    }
    public int getSize(){
        int size= 0;
        switch (this.glType){
            case GLES20.GL_FLOAT:
                size = this.floatArray.length;
                break;
            case GLES20.GL_UNSIGNED_SHORT:
            case GLES20.GL_SHORT:
                size = this.shortArray.length;
                break;
            case GLES20.GL_UNSIGNED_INT:
            case GLES20.GL_INT:
                size = this.intArray.length;
                break;
        }
        return size;
    }
    public void setV2(java.util.LinkedList<Vector2> vertices){
        java.util.Iterator<Vector2> iterator = vertices.iterator();
        Vector2 vertex;
        int size = vertices.size()*2;
        this.floatArray = new float[size];
        int count=0;
        while(iterator.hasNext()){
            vertex = iterator.next();
            this.floatArray[count++]=vertex.getX();
            this.floatArray[count++]=vertex.getY();
        }
        this.setGlType(GLES20.GL_FLOAT);
        this.setCoordsPerData(2);
        this.setStride(8);
    }
    public void setV3(java.util.LinkedList<Vector3> vertices){
        java.util.Iterator<Vector3> iterator = vertices.iterator();
        Vector3 vertex;
        int size = vertices.size()*3;
        this.floatArray = new float[size];
        int count=0;
        while(iterator.hasNext()){
            vertex = iterator.next();
            this.floatArray[count++]=vertex.getX();
            this.floatArray[count++]=vertex.getY();
            this.floatArray[count++]=vertex.getZ();
        }
        this.setGlType(GLES20.GL_FLOAT);
        this.setCoordsPerData(3);
        this.setStride(12);
    }
    public void setV4(java.util.LinkedList<Vector4> vertices){
        java.util.Iterator<Vector4> iterator = vertices.iterator();
        Vector4 vertex;
        int size = vertices.size()*4;
        this.floatArray = new float[size];
        int count=0;
        while(iterator.hasNext()){
            vertex = iterator.next();
            this.floatArray[count++]=vertex.getX();
            this.floatArray[count++]=vertex.getY();
            this.floatArray[count++]=vertex.getZ();
            this.floatArray[count++]=vertex.getW();
        }
        this.setGlType(GLES20.GL_FLOAT);
        this.setCoordsPerData(4);
        this.setStride(16);
    }
    public void setIndicesFace3(java.util.LinkedList<Face3> faces){
        java.util.Iterator<Face3> iterator = faces.iterator();
        Face3 face;
        int size = faces.size()*3;
        if(size>32767){
            this.setGlType(GLES20.GL_UNSIGNED_INT);
            this.intArray = new int[size];
            this.setStride(4);//4 bytes
        }else{
            this.setGlType(GLES20.GL_UNSIGNED_SHORT);
            this.shortArray = new short[size];
            this.setStride(2);//2 bytes
        }


        int count=0;
        while(iterator.hasNext()){
            face = iterator.next();
            if(this.glType==GLES20.GL_UNSIGNED_SHORT){
                this.shortArray[count++]=(short)face.getIndexA();
                this.shortArray[count++]=(short)face.getIndexB();
                this.shortArray[count++]=(short)face.getIndexC();
            }else{
                this.intArray[count++]=face.getIndexA();
                this.intArray[count++]=face.getIndexB();
                this.intArray[count++]=face.getIndexC();
            }
        }
        this.setCoordsPerData(1);

    }
    public BufferAttribute(float[] values,int coordsPerData){
        this.floatArray = values;
        this.setGlType(GLES20.GL_FLOAT);
        this.setCoordsPerData(coordsPerData);
        this.setStride(coordsPerData * 4);
    }
    public BufferAttribute(short[] values,int coordsPerData){
        this.shortArray = values;
        this.setGlType(GLES20.GL_UNSIGNED_SHORT);
        this.setCoordsPerData(coordsPerData);
        this.setStride(coordsPerData * 2);
    }
    public BufferAttribute(int[] values,int coordsPerData){
        this.intArray = values;
        this.setGlType(GLES20.GL_UNSIGNED_INT);
        this.setCoordsPerData(coordsPerData);
        this.setStride(coordsPerData * 4);
    }
    public Buffer getBuffer(){
        if(this.buffer==null){
            ByteBuffer bb;
            switch (this.glType){
                case GLES20.GL_FLOAT:
                    bb = ByteBuffer.allocateDirect(
                            this.floatArray.length * 4);
                    bb.order(ByteOrder.nativeOrder());
                    this.buffer = bb.asFloatBuffer().put(this.floatArray).position(0);
                    break;
                case GLES20.GL_UNSIGNED_SHORT:
                case GLES20.GL_SHORT:
                    bb = ByteBuffer.allocateDirect(
                            this.shortArray.length * 2);
                    bb.order(ByteOrder.nativeOrder());
                    this.buffer = bb.asShortBuffer().put(this.shortArray).position(0);
                    break;
                case GLES20.GL_UNSIGNED_INT:
                case GLES20.GL_INT:
                    bb = ByteBuffer.allocateDirect(
                            this.intArray.length * 4);
                    bb.order(ByteOrder.nativeOrder());
                    this.buffer = bb.asIntBuffer().put(this.intArray).position(0);
                    break;
            }
        }
        return this.buffer;
    }
    public BufferAttribute setXYZ(int index, float x, float y, float z ) {
        index *= this.getCoordsPerData();
        this.floatArray[ index + 0 ] = x;
        this.floatArray[ index + 1 ] = y;
        this.floatArray[ index + 2 ] = z;
        return this;
    }
    public BufferAttribute setXY(int index, float x, float y ) {

        index *= this.getCoordsPerData();
        this.floatArray[ index + 0 ] = x;
        this.floatArray[ index + 1 ] = y;
        return this;
    }
    public String toString(){
        return "BufferAttribute[glType="+this.glType+",coordsPerData="+this.coordsPerData+",mode="+this.mode+",stride="+this.stride+",size="+this.getSize()+"]";
    }


    public boolean isShortType(){
        return this.shortArray!=null && this.shortArray.length>0;
    }
    public boolean isIntType(){
        return this.intArray!=null && this.intArray.length>0;
    }
    public boolean isFloatType(){
        return this.floatArray!=null && this.floatArray.length>0;
    }

    @Override
    public BufferAttribute clone(){
        /*no clona/copia ni floatBuffer ni shortBuffer*/
        BufferAttribute bufferAttribute = new BufferAttribute();
        bufferAttribute.copy(this);
        return bufferAttribute;
    }
    public BufferAttribute copy(BufferAttribute bufferAttribute){
        float[] floatArray  = bufferAttribute.getFloatArray();
        short[] shortArray  = bufferAttribute.getShortArray();
        int[] intArray      = bufferAttribute.getIntArray();
        int i=0,j=0;
        int length=0;
        if(floatArray!=null && floatArray.length>0){
            length = floatArray.length;
            this.floatArray = new float[length];
            for(i=0;i<length;i++){
                this.floatArray[i] = floatArray[i];
            }
        }
        if(shortArray!=null && shortArray.length>0){
            length = shortArray.length;
            this.shortArray = new short[length];
            for(i=0;i<length;i++){
                this.shortArray[i] = shortArray[i];
            }
        }
        if(intArray!=null && intArray.length>0){
            length = intArray.length;
            this.intArray = new int[length];
            for(i=0;i<length;i++){
                this.intArray[i] = intArray[i];
            }
        }
        this.glType         = bufferAttribute.getGlType();
        this.coordsPerData  = bufferAttribute.getCoordsPerData();
        this.stride         = bufferAttribute.getCoordsPerData();
        this.mode           = bufferAttribute.getMode();
        this.isNormalized   = bufferAttribute.isNormalized();
        return this;
    }


    public int getGlType() {
        return glType;
    }

    public void setGlType(int glType) {
        this.glType = glType;
    }

    public int getCoordsPerData() {
        return coordsPerData;
    }

    public void setCoordsPerData(int coordsPerData) {
        this.coordsPerData = coordsPerData;
    }

    public int getStride() {
        return stride;
    }

    public void setStride(int stride) {
        this.stride = stride;
    }

    public boolean isNormalized() {
        return isNormalized;
    }

    public void setNormalized(boolean normalized) {
        isNormalized = normalized;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int count() {

        int _count = 0;
        if(this.coordsPerData>0){
            switch(this.glType){
                case GLES20.GL_FLOAT:
                    _count = this.floatArray.length/this.coordsPerData;
                    break;
                case GLES20.GL_SHORT:
                case GLES20.GL_UNSIGNED_SHORT:
                    _count = this.shortArray.length/this.coordsPerData;
                    break;
                case GLES20.GL_INT:
                case GLES20.GL_UNSIGNED_INT:
                    _count = this.intArray.length/this.coordsPerData;
                    break;
            }
        }
        return _count;

    }

    public void clear() {
        if(this.buffer!=null){
            this.buffer.clear();
        }
        this.floatArray = null;
        this.shortArray = null;
        this.intArray = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
