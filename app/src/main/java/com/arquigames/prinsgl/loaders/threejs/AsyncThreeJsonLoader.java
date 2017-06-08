package com.arquigames.prinsgl.loaders.threejs;

import android.os.AsyncTask;

import com.arquigames.prinsgl.ObjectJSON;
import com.arquigames.prinsgl.loaders.events.EventLoader;
import com.arquigames.prinsgl.loaders.files.File;

/**
 * Created by usuario on 14/08/2016.
 */
public class AsyncThreeJsonLoader extends AsyncTask<File,File,Integer> implements Cloneable {
    private ThreeJsonLoader jsonLoader;
    private ThreeJsonLoaderListener threeJsonLoaderListener;
    public AsyncThreeJsonLoader(ThreeJsonLoader jsonLoader, ThreeJsonLoaderListener threeJsonLoaderListener){
        this.jsonLoader = jsonLoader;
        this.threeJsonLoaderListener = threeJsonLoaderListener;
    }
    public void load(File[] params){
        this.execute(params);
    }
    @Override
    public AsyncThreeJsonLoader clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException("cannot clone AsyncThreeJsonLoader");
    }
    @Override
    protected Integer doInBackground(File[] params) {

        File obj;
        for(int i=0;i<params.length;i++){
            obj = params[i];
            if(!this.isCancelled()){
                if(obj.getPercent()==-1f){
                    ObjectJSON json = this.jsonLoader.load(obj.getPath()+obj.getFile());
                    if(json!=null){
                        obj.setJson(json);
                        obj.setPercent( ((i+1f)/params.length) * 100.0f );
                    }
                    this.publishProgress(obj);
                }
            }else{
                break;
            }
        }
        return 0;
    }

    @Override
    protected void onProgressUpdate(File... values) {
        super.onProgressUpdate(values);
        this.threeJsonLoaderListener.onProgressLoaded(
                new EventLoader(
                        values[0],
                        -1,
                        values[0].getId()
                )
        );
    }

}

