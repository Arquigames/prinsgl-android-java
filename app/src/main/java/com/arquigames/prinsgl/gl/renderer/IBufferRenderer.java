package com.arquigames.prinsgl.gl.renderer;

/**
 * Created by usuario on 02/07/2016.
 */
public interface IBufferRenderer {
    public void render(int start, int count);
    public void setMode(int mode);
    public int getMode();
}
