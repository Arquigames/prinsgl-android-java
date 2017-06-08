package com.arquigames.prinsgl;


/**
 * Created by usuario on 09/07/2016.
 */
public class Records implements Cloneable{
    private java.util.HashMap<String,String> plainTexts;
    public Records(){
        this.plainTexts = new java.util.HashMap<String,String>();
    }
    public void addPlainText(String key, String contents){
        this.plainTexts.put(key,contents);
    }
    public String getPlainText(String key){
        return this.plainTexts.get(key);
    }
    @Override
    public Records clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException("cannot clone Records");
    }
}
