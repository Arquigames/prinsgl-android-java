package com.arquigames.prinsgl.gl.renderer;

/**
 * Created by usuario on 02/07/2016.
 */
public interface IBufferRenderer extends Cloneable{
    void render(int start, int count);
    void setMode(int mode);
    int getMode();
    IBufferRenderer clone() throws CloneNotSupportedException;
}
