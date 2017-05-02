package com.arquigames.prinsgl.maths;


import com.arquigames.prinsgl.Object3D;
import com.arquigames.prinsgl.maths.matrix.Matrix4;
import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 26/06/2016.
 */
public class Euler {
    private float x;
    private float y;
    private float z;
    private String order = "";
    private Object3D container =null;//parent container

    public static String DefaultOrder = "XYZ";
    public static String[] RotationOrders = new String[] { "XYZ", "YZX", "ZXY", "XZY", "YXZ", "ZYX" };

    public Euler(){
        this.setX(0);
        this.setY(0);
        this.setZ(0);

        this.setOrder(Euler.DefaultOrder);
    }
    public Euler(Object3D container){
        this();
        this.setContainer(container);
    }
    public Euler(float x,float y, float z, String order){
        this.setX(x);
        this.setY(y);
        this.setZ(z);

        this.setOrder(order!=null ? order : Euler.DefaultOrder);
    }
    public Euler(float x,float y, float z){
        this(x,y,z,null);
    }
    public void setX(float x) {
        this.x = x;
        this.onChangeCallback();
    }
    public float getX() {
        return x;
    }
    public void setY(float y) {
        this.y = y;
        this.onChangeCallback();
    }
    public float getY() {
        return y;
    }
    public void setZ(float z) {
        this.z = z;
        this.onChangeCallback();
    }
    public float getZ() {
        return z;
    }
    public void setOrder(String order) {
        this.order = order;
        this.onChangeCallback();
    }
    public String getOrder() {
        return order;
    }
    public void setContainer(Object3D container) {
        this.container = container;
    }
    public Object3D getContainer() {
        return this.container;
    }
    private void onChangeCallback(){
        if(this.container !=null){
            this.container.onRotationChange();
        }
    }
    public Euler set (float x, float y, float z, String order){
        this.setX(x);
        this.setY(y);
        this.setZ(z);

        this.setOrder(order!=null ? order : Euler.DefaultOrder);
        this.onChangeCallback();
        return this;
    }
    public Euler set (float x, float y, float z){
        return this.set(x,y,z, null);
    }
    public Euler clone(){
        return new Euler(this.getX(),this.getY(),this.getZ(),this.getOrder());
    }
    public Euler copy(Euler e){
        this.setX(e.getX());
        this.setY(e.getY());
        this.setZ(e.getZ());
        this.setOrder(e.getOrder());
        this.onChangeCallback();

        return this;
    }
    public Euler setFromRotationMatrix(Matrix4 m, String order, boolean update){
        float[] te = m.getElements();

        float m11 = te[0], m12 = te[4], m13 = te[8];
        float m21 = te[1], m22 = te[5], m23 = te[9];
        float m31 = te[2], m32 = te[6], m33 = te[10];

        order = order!=null ? order : this.getOrder();
        if(order=="XYZ"){
            this.y = (float) Math.asin( MathUtils.clamp( m13, - 1, 1 ) );
            if (  Math.abs( m13 ) < 0.99999f ) {

                this.x = (float) Math.atan2( - m23, m33 );
                this.z = (float) Math.atan2( - m12, m11 );

            } else {

                this.x = (float) Math.atan2( m32, m22 );
                this.z = 0;

            }
        }else if ( order == "YXZ" ) {

            this.x = (float) Math.asin( - MathUtils.clamp( m23, - 1, 1 ) );

            if ( Math.abs( m23 ) < 0.99999 ) {

                this.y = (float) Math.atan2( m13, m33 );
                this.z = (float) Math.atan2( m21, m22 );

            } else {

                this.y = (float) Math.atan2( - m31, m11 );
                this.z = 0;

            }

        } else if ( order == "ZXY" ) {

            this.x = (float) Math.asin( MathUtils.clamp( m32, - 1, 1 ) );

            if ( Math.abs( m32 ) < 0.99999 ) {

                this.y = (float) Math.atan2( - m31, m33 );
                this.z = (float) Math.atan2( - m12, m22 );

            } else {

                this.y = 0;
                this.z = (float) Math.atan2( m21, m11 );

            }

        } else if ( order == "ZYX" ) {

            this.y = (float) Math.asin( - MathUtils.clamp( m31, - 1, 1 ) );

            if ( Math.abs( m31 ) < 0.99999 ) {

                this.x = (float) Math.atan2( m32, m33 );
                this.z = (float) Math.atan2( m21, m11 );

            } else {

                this.x = 0;
                this.z = (float) Math.atan2( - m12, m22 );

            }

        } else if ( order == "YZX" ) {

            this.z = (float) Math.asin( MathUtils.clamp( m21, - 1, 1 ) );

            if ( Math.abs( m21 ) < 0.99999 ) {

                this.x = (float) Math.atan2( - m23, m22 );
                this.y = (float) Math.atan2( - m31, m11 );

            } else {

                this.x = 0;
                this.y = (float) Math.atan2( m13, m33 );

            }

        } else if ( order == "XZY" ) {

            this.z = (float) Math.asin( - MathUtils.clamp( m12, - 1, 1 ) );

            if ( (float) Math.abs( m12 ) < 0.99999 ) {

                this.x = (float) Math.atan2( m32, m22 );
                this.y = (float) Math.atan2( m13, m11 );

            } else {

                this.x = (float) Math.atan2( - m23, m33 );
                this.y = 0;

            }

        } else {

            System.err.println("order undefined");

        }
        this.order = order;
        if(update)this.onChangeCallback();
        return this;
    }
    public Euler setFromRotationMatrix(Matrix4 m, String order){
        return this.setFromRotationMatrix(m,order,true);
    }
    public Euler setFromRotationMatrix(Matrix4 m){
        return this.setFromRotationMatrix(m, Euler.DefaultOrder);
    }
    public Euler setFromQuaternion(Quaternion q, String order, boolean update){
        Matrix4 matrix = new Matrix4();
        matrix.makeRotationFromQuaternion( q );
        this.setFromRotationMatrix( matrix, order, update );

        return this;
    }
    public Euler setFromQuaternion(Quaternion q, String order){
        return this.setFromQuaternion(q,order,true);
    }
    public Euler setFromQuaternion(Quaternion q){
        return this.setFromQuaternion(q, Euler.DefaultOrder);
    }
    public Euler setFromVector3(Vector3 v, String order){
        return this.set( v.getX(), v.getY(), v.getZ(), order!=null ? order : this.order );
    }
    public Euler setFromVector3(Vector3 v){
        return this.setFromVector3(v, Euler.DefaultOrder);
    }
    public void reorder(String newOrder){
        Quaternion q = new Quaternion();
        q.setFromEuler( this,true );
        this.setFromQuaternion( q, newOrder,true );
    }
    public boolean equals(Euler e){
        return
                this.x==e.getX() &&
                this.y==e.getY() &&
                this.z==e.getZ() &&
                this.order == e.getOrder();
    }
    public Vector3 toVector3(Vector3 vec){
        if(vec==null){
            vec = new Vector3();
        }
        return vec.set(this.x,this.y,this.z);
    }
    public String toString(){
        String str = "Euler["+this.x+","+this.y+","+this.z+","+this.order+"]";
        return str;
    }

    public void addX(float value) {
        this.x +=value;
        this.onChangeCallback();
    }
    public void addY(float value) {
        this.y +=value;
        this.onChangeCallback();
    }
    public void addZ(float value) {
        this.z +=value;
        this.onChangeCallback();
    }
    public void subtractX(float value){
        this.x -=value;
        this.onChangeCallback();
    }
    public void subtractY(float value){
        this.y -=value;
        this.onChangeCallback();
    }
    public void subtractZ(float value){
        this.z -=value;
        this.onChangeCallback();
    }
}
