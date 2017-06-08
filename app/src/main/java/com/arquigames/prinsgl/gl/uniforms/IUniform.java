package com.arquigames.prinsgl.gl.uniforms;

/**
 * Created by usuario on 12/07/2016.
 */
public interface IUniform extends Cloneable {

    String U_1I = "1i";
    String U_F = "1f";
    String U_I = "i";
    String U_1F = "f";
    String U_2F = "2f";
    String U_3F = "3f";
    String U_4F = "4f";
    String U_1IV = "1iv";
    String U_2IV = "2iv";
    String U_3IV = "3iv";
    String U_1FV = "1fv";
    String U_2FV = "2fv";
    String U_3FV = "3fv";
    String U_4FV = "4fv";
    String U_M2FV = "Matrix2fv";
    String U_M3FV = "Matrix3fv";
    String U_M4FV = "Matrix4fv";
    String U_V2 = "v2";
    String U_V3 = "v3";
    String U_V4 = "v4";
    String U_C = "c";
    String U_M2 = "m2";
    String U_M3 = "m3";
    String U_M4 = "m4";
    String U_T = "t";
    String U_SA = "sa";
    String U_S = "s";

    String getName();
    void setName(String name);
    void dispose();
    IUniform clone() throws CloneNotSupportedException;
}

