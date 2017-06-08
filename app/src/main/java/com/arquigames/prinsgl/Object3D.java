package com.arquigames.prinsgl;


import android.support.annotation.NonNull;

import com.arquigames.prinsgl.cameras.Camera;
import com.arquigames.prinsgl.lights.Light;
import com.arquigames.prinsgl.maths.Euler;
import com.arquigames.prinsgl.maths.MathUtils;
import com.arquigames.prinsgl.maths.Quaternion;
import com.arquigames.prinsgl.maths.matrix.Matrix3;
import com.arquigames.prinsgl.maths.matrix.Matrix4;
import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 13/07/2016.
 */
public class Object3D implements Comparable<Object3D>,Cloneable{
    protected String uuid = "";

    public static int counterObjects3D = 1;
    protected String name = "";
    protected Object3D parent = null;

    protected java.util.LinkedList<Object3D> children = null;

    public static Vector3 DefaultUp = new Vector3(0f,1f,0f);

    protected int id = 0;
    protected Vector3 up 				= null;
    protected Vector3 position 		    = new Vector3();
    protected Euler rotation 			= new Euler(this);
    protected Quaternion quaternion 	= new Quaternion(this);
    protected Vector3 scale 			= new Vector3(1,1,1);

    protected Matrix4 modelViewMatrix = new Matrix4();
    protected Matrix3 normalMatrix = new Matrix3();

    protected Matrix4 matrix = new Matrix4();
    protected Matrix4 matrixWorld = new Matrix4();


    public boolean visible = true;

    private Vector3 orientationX = new Vector3(1,0,0);
    private Vector3 orientationY = new Vector3(0,1,0);
    private Vector3 orientationZ = new Vector3(0,0,1);
    protected boolean matrixAutoUpdate = true;
    protected boolean matrixWorldNeedsUpdate = true;
    protected boolean markedtoRemove = false;

    public Object3D(){
        this.setUuid(MathUtils.generateUUID());
        this.setId(Object3D.counterObjects3D++);
        this.setUp(Object3D.DefaultUp.clone());
        this.children = new java.util.LinkedList<Object3D>();
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setParent(Object3D parent) {
        this.parent = parent;
    }
    public Object3D getParent() {
        return parent;
    }
    protected void setChildren(java.util.LinkedList<Object3D> children) {
        this.children = children;
    }
    public java.util.LinkedList<Object3D> getChildren() {
        return children;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void setUp(Vector3 up) {
        this.up = up;
    }
    public Vector3 getUp() {
        return up;
    }
    public void setPosition(Vector3 position) {
        this.position = position;
    }
    public Vector3 getPosition() {
        return position;
    }
    public void setRotation(Euler rotation) {
        this.rotation = rotation;
    }
    public Euler getRotation() {
        return rotation;
    }
    public void setQuaternion(Quaternion quaternion) {
        this.quaternion = quaternion;
    }
    public Quaternion getQuaternion() {
        return quaternion;
    }
    public void setScale(Vector3 scale) {
        this.scale = scale;
    }
    public Vector3 getScale() {
        return scale;
    }
    public void setModelViewMatrix(Matrix4 modelViewMatrix) {
        this.modelViewMatrix = modelViewMatrix.clone();
    }
    public Matrix4 getModelViewMatrix() {
        return modelViewMatrix;
    }
    public void setNormalMatrix(Matrix3 normalMatrix) {
        this.normalMatrix = normalMatrix.clone();
    }
    public Matrix3 getNormalMatrix() {
        return normalMatrix;
    }
    public void setMatrix(Matrix4 matrix) {
        this.matrix = matrix.clone();
    }
    public Matrix4 getMatrix() {
        return this.matrix;
    }
    public void setMatrixWorld(Matrix4 matrixWorld) {
        this.matrixWorld = matrixWorld.clone();
    }
    public Matrix4 getMatrixWorld() {
        return this.matrixWorld;
    }
    public void onRotationChange(){
        this.getQuaternion().setFromEuler(this.getRotation(), false);
    }
    public void onQuaternionChange(){
        this.getRotation().setFromQuaternion(this.getQuaternion(), null, false);
    }
    public void applyMatrix(Matrix4 matrix){
        this.getMatrix().multiplyMatrices( matrix, this.getMatrix() );

        this.getMatrix().decompose(this.getPosition(), this.getQuaternion(), this.getScale());
    }
    public void setRotationFromAxisAngle(Vector3 axis, float angle){
        // assumes axis is normalized
        this.getQuaternion().setFromAxisAngle(axis, angle);
    }
    public void setRotationFromEuler(Euler euler){
        this.getQuaternion().setFromEuler(euler, true);
    }
    public void setRotationFromMatrix(Matrix4 m){
        // assumes the upper 3x3 of m is a pure rotation matrix (i.e, unscaled)
        this.getQuaternion().setFromRotationMatrix(m);
    }
    public void setRotationFromQuaternion(Quaternion q){
        // assumes q is normalized
        this.getQuaternion().copy(q);
    }
    public Object3D rotateOnAxis(Vector3 axis, float angle){
        // rotate object on axis in object space
        // axis is assumed to be normalized

        Quaternion q1 = new Quaternion();
        q1.setFromAxisAngle( axis, angle );
        this.getQuaternion().multiply(q1);
        return this;
    }
    public Object3D rotateX(float angle){
        return this.rotateOnAxis(this.getOrientationX(), angle );
    }
    public Object3D rotateY(float angle){
        return this.rotateOnAxis(this.getOrientationY(), angle );
    }
    public Object3D rotateZ(float angle){
        return this.rotateOnAxis(this.getOrientationZ(), angle );
    }
    public Object3D translateOnAxis(Vector3 axis,float distance ){
        Vector3 v1 = new Vector3();
        v1.copy( axis ).applyQuaternion( this.getQuaternion() );

        this.getPosition().add( v1.multiplyScalar( distance ),null );

        return this;
    }
    public Object3D translateX(float distance){
        return this.translateOnAxis( this.getOrientationX(), distance );
    }
    public Object3D translateY(float distance){
        return this.translateOnAxis( this.getOrientationY(), distance );
    }
    public Object3D translateZ(float distance){
        return this.translateOnAxis(this.getOrientationZ(), distance);
    }
    public Vector3 localToWorld(Vector3 vector){
        return vector.applyMatrix4( this.getMatrixWorld() );
    }
    public Vector3 worldToLocal(Vector3 vector) throws Exception{
        Matrix4 m1 = new Matrix4();
        return vector.applyMatrix4( m1.getInverse( this.getMatrixWorld()) );
    }
    public void lookAt(Vector3 vector){
        // This routine does not support objects with rotated and/or translated parent(s)

        Matrix4 m1 = new Matrix4();
        m1.lookAt( this.getPosition(), vector, this.getUp() );
        this.getQuaternion().setFromRotationMatrix( m1 );
    }
    public Object3D add(Object3D object) throws Exception{

        if ( !object.equalsTo(this)) {

            if ( object.getParent() != null ) {
                object.getParent().remove( object );
            }
            object.setParent(this);
            this.getChildren().add( object );
        }
        return this;
    }
    public boolean remove(Object3D object) throws Exception{
        boolean check = object instanceof Light || object instanceof Camera;
        if(!check){
            if(this.children.contains(object)){
                if(object.isMarkedtoRemove()){
                    object.dispose();
                }
                object.setParent(null);
                this.children.remove(object);
                check = true;
            }else{
                check = false;
            }
        }else{
            check = false;
        }
        return check;
    }
    public boolean equals(Object3D object3D){
        return this.id==object3D.getId();
    }
    public boolean equalsTo(Object3D object){
        return  this.id==object.getId();
    }
    public Object3D getObjectByID(int id){
        if ( this.id == id) return this;
        java.util.Iterator<Object3D> iterator = this.children.iterator();
        Object3D obj;
        Object3D _obj;
        while(iterator.hasNext()){
            obj = iterator.next();
            _obj = obj.getObjectByID(id);
            if ( _obj != null ) {
                return _obj;
            }
        }
        return null;
    }
    public Object3D getObjectByName(String name){
        if ( this.name.equals(name)) return this;
        java.util.Iterator<Object3D> iterator = this.children.iterator();
        Object3D obj;
        Object3D _obj;
        while(iterator.hasNext()){
            obj = iterator.next();
            _obj = obj.getObjectByName(name);
            if ( _obj != null ) {
                return _obj;
            }
        }
        return null;
    }
    public Vector3 getWorldPosition(Vector3 optionalTarget){
        Vector3 result = optionalTarget==null ? new Vector3() : optionalTarget;
        this.updateMatrixWorld( true );
        return result.setFromMatrixPosition( this.matrixWorld);
    }
    public Vector3 getWorldPosition(){
        return this.getWorldPosition(null);
    }
    public Quaternion getWorldQuaternion(Quaternion optionalTarget){
        Vector3 position = new Vector3();
        Vector3 scale = new Vector3();
        Quaternion result = optionalTarget ==null ?  new Quaternion():optionalTarget;
        this.updateMatrixWorld( true );
        this.matrixWorld.decompose( position, result, scale );
        return result;
    }
    public Quaternion getWorldQuaternion(){
        return this.getWorldQuaternion(null);
    }
    public Euler getWorldRotation(Euler optionalTarget){
        Quaternion quaternion = new Quaternion();
        Euler result = optionalTarget ==null ?  new Euler() : optionalTarget;
        this.getWorldQuaternion( quaternion );
        return result.setFromQuaternion( quaternion, this.rotation.getOrder(), false );
    }
    public Euler getWorldRotation(){
        return this.getWorldRotation(null);
    }
    public Vector3 getWorldScale(Vector3 optionalTarget){
        Vector3 position = new Vector3();
        Quaternion quaternion = new Quaternion();
        Vector3 result = optionalTarget==null ? new Vector3():optionalTarget;
        this.updateMatrixWorld( true );
        this.matrixWorld.decompose( position, quaternion, result );
        return result;
    }
    public Vector3 getWorldDirection(Vector3 optionalTarget){
        Quaternion quaternion = new Quaternion();
        Vector3 result = optionalTarget==null? new Vector3():optionalTarget;
        this.getWorldQuaternion( quaternion );
        return result.set( 0, 0, 1 ).applyQuaternion( quaternion );
    }
    public void updateMatrix(){
        this.matrix.compose(this.position, this.quaternion, this.scale);
        this.matrixWorldNeedsUpdate = true;
    }
    public void updateMatrixWorld(boolean force){
        if ( this.matrixAutoUpdate) this.updateMatrix();

        if ( this.matrixWorldNeedsUpdate || force  ) {
            if ( this.parent == null ) {
                this.matrixWorld.copy( this.matrix );
            } else {
                this.matrixWorld.multiplyMatrices(this.parent.getMatrixWorld(), this.matrix);
            }
            this.matrixWorldNeedsUpdate = false;
            force = true;
        }

        // update children

        java.util.Iterator<Object3D> iteratorObj = this.children.iterator();
        Object3D obj;
        while(iteratorObj.hasNext()){
            obj = iteratorObj.next();
            obj.updateMatrixWorld( force );

        }
    }
    public void updateMatrixWorld(){
        this.updateMatrixWorld(true);
    }
    @Override
    public Object3D clone(){
        Object3D object = new Object3D();
        try{
            object.copy(this,true);
            return object;
        }catch(Exception e){
            return null;
        }
    }
    public Object3D copy(Object3D source, boolean recursive) throws Exception{


        this.name = source.name;

        if(source.getUp()!=null)this.up.copy( source.getUp() );

        this.position.copy( source.getPosition() );
        this.rotation.copy(source.getRotation());
        this.quaternion.copy( source.getQuaternion() );
        this.scale.copy( source.getScale() );
        this.modelViewMatrix.copy(source.getModelViewMatrix());
        this.normalMatrix.copy(source.getNormalMatrix());
        this.matrix.copy( source.getMatrix() );
        this.matrixWorld.copy( source.getMatrixWorld() );

        this.visible = source.visible;


        if ( recursive ) {

            java.util.Iterator<Object3D> iterator = source.getChildren().iterator();
            Object3D objTemp;
            while(iterator.hasNext()){
                objTemp = iterator.next();
                this.add( objTemp.clone() );
            }
        }
        return this;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getUuid() {
        return uuid;
    }

    public Vector3 getOrientationX() {
        return orientationX;
    }

    public void setOrientationX(Vector3 orientationX) {
        this.orientationX = orientationX;
    }

    public Vector3 getOrientationY() {
        return orientationY;
    }

    public void setOrientationY(Vector3 orientationY) {
        this.orientationY = orientationY;
    }

    public Vector3 getOrientationZ() {
        return orientationZ;
    }

    public void setOrientationZ(Vector3 orientationZ) {
        this.orientationZ = orientationZ;
    }

    @Override
    public int compareTo(@NonNull Object3D object3D) {
        if(this.id<object3D.getId())return -1;
        return this.id == object3D.getId() ? 0:1;
    }
    public void dispose(){
        //TODO
    }

    public void markToRemove() {
        this.setMarkedtoRemove(true);
    }

    public boolean isMarkedtoRemove() {
        return this.markedtoRemove;
    }

    private void setMarkedtoRemove(boolean markedtoRemove) {
        this.markedtoRemove = markedtoRemove;
    }
}