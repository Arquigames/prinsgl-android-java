package com.arquigames.prinsgl.loaders.files;

import com.arquigames.prinsgl.ObjectJSON;

/**
 * Created by usuario on 14/08/2016.
 */
public class File implements Cloneable{
    private String path;
    private int id;
    private String fileName;

    private float percent = -1f;

    private ObjectJSON json = null;

    private boolean processed = false;

    public File(String path, String fileName, int id){
        this.setPath(path);
        this.setId(id);
        this.setFile(fileName);
    }
    @Override
    public File clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException("cannot clone File");
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile() {
        return fileName;
    }

    public void setFile(String file) {
        this.fileName = file;
    }

    public ObjectJSON getJson() {
        return json;
    }

    public void setJson(ObjectJSON json) {
        this.json = json;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
