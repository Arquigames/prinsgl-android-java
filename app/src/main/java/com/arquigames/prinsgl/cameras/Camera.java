package com.arquigames.prinsgl.cameras;


import com.arquigames.prinsgl.Object3D;
import com.arquigames.prinsgl.maths.matrix.Matrix4;

/**
 * Created by usuario on 12/07/2016.
 */
public abstract class Camera extends Object3D {
    protected Matrix4 matrixWorldInverse = new Matrix4();
    protected Matrix4 projectionMatrix = new Matrix4();
    public Camera(){
        super();
    }
    public void setMatrixWorldInverse(Matrix4 matrixWorldInverse) {
        this.matrixWorldInverse = matrixWorldInverse;
    }
    public Matrix4 getMatrixWorldInverse() {
        return matrixWorldInverse;
    }
    public void setProjectionMatrix(Matrix4 projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }
    public Matrix4 getProjectionMatrix() {
        return projectionMatrix;
    }
    @Override
    public abstract Camera clone();
    public abstract void updateProjectionMatrix();

    public Camera copy(Camera source) throws Exception{
        super.copy(source,true);
        this.matrixWorldInverse.copy( source.getMatrixWorldInverse() );
        this.projectionMatrix.copy( source.getProjectionMatrix() );
        return this;
    }
    @Override
    public Object3D add(Object3D object) throws Exception{
        throw new Exception("this version cannot add Objects3D to Camera");
    }
    @Override
    public boolean remove(Object3D object) throws Exception{
        return false;
    }
}
