package com.arquigames.prinsgl;

import com.arquigames.prinsgl.gl.attributes.BufferAttribute;
import com.arquigames.prinsgl.gl.renderer.RenderGroup;
import com.arquigames.prinsgl.maths.vectors.Vector2;
import com.arquigames.prinsgl.maths.vectors.Vector3;
import com.arquigames.prinsgl.maths.vectors.Vector4;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by usuario on 12/07/2016.
 */
public class ObjectJSON implements Cloneable {
    private final Map<String,Object> values;
    public ObjectJSON(){
        this.values = new HashMap<String,Object>();
    }
    public int size(){
        return this.values.size();
    }
    @Override
    public ObjectJSON clone(){
        ObjectJSON objectJSON = new ObjectJSON();
        objectJSON.copy(this);
        return objectJSON;
    }
    public ObjectJSON copy(ObjectJSON objectJSON){
        java.util.Iterator<String> keys = objectJSON.getKeysIterator();
        String key;
        BufferAttribute bufferAttribute;
        int _integer;
        short _short;
        String _string;
        Vector2 _vector2;
        Vector3 _vector3;
        Vector4 _vector4;
        RenderGroup _renderGroup;

        Object obj ;
        while(keys.hasNext()){
            key = keys.next();
            obj = objectJSON.get(key);
            if(obj instanceof BufferAttribute){
                bufferAttribute = (BufferAttribute)obj;
                this.values.put(key,bufferAttribute.clone());
            }else if(obj instanceof Integer) {
                _integer = (Integer) obj;
                this.values.put(key, _integer);
            }else if(obj instanceof Vector2){
                _vector2 = (Vector2)obj;
                this.values.put(key,_vector2.clone());
            }else if(obj instanceof Vector3){
                _vector3 = (Vector3)obj;
                this.values.put(key,_vector3.clone());
            }else if(obj instanceof Vector4){
                _vector4 = (Vector4)obj;
                this.values.put(key,_vector4.clone());
            }else if(obj instanceof RenderGroup){
                _renderGroup = (RenderGroup)obj;
                this.values.put(key,_renderGroup.clone());
            }else if(obj instanceof Short){
                _short = (Short)obj;
                this.values.put(key,_short);
            }else if(obj instanceof String){
                _string = (String)obj;
                this.values.put(key,_string);
            }else{
                //TODO
            }
        }
        return this;
    }

    public void put( String key, Object value ){
        getValues().put( key, value );
    }
    public Object get( String key ){
        return getValues().get( key );
    }
    public Map<String,Object> getValues() {
        return values;
    }
    public java.util.Iterator<Object> getValuesIterator(){
        return this.values.values().iterator();
    }
    public java.util.Set<String> getKeys(){
        return this.values.keySet();
    }
    public java.util.Iterator<String> getKeysIterator(){
        return this.values.keySet().iterator();
    }
    public void remove(String key) {
        this.values.remove(key);

    }
    public void clear() {
        java.util.Iterator<Object> it = this.values.values().iterator();
        Object obj;
        BufferAttribute bufferAttribute;
        while(it.hasNext()){
            obj = it.next();
            if(obj instanceof BufferAttribute){
                bufferAttribute = (BufferAttribute)obj;
                bufferAttribute.clear();
            }
        }
        this.values.clear();

    }

    public Object[] toArrayValues() {
        return this.values.values().toArray();
    }
}

