package com.arquigames.prinsgl;

import android.content.Context;
import android.util.Log;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by usuario on 12/07/2016.
 */
public class Util {
    private static String TAG = "Util";
    public static String  print(float[] elements){
        String str = "[";
        for(int i = 0; i<elements.length;i++){
            str +=elements[i];
            if(i<elements.length-1){
                str +=",";
            }
        }
        str+="]";
        return str;
    }
    public static String  print(short[] elements){
        String str = "[";
        for(int i = 0; i<elements.length;i++){
            str +=elements[i];
            if(i<elements.length-1){
                str +=",";
            }
        }
        str+="]";
        return str;
    }
    public static float[] copyFloatValues(float[] elements){
        float[] newElements = new float[elements.length];
        for(int i=0;i<elements.length;i++){
            newElements[i] = elements[i];
        }
        return newElements;
    }
    public static int[] copyIntValues(int[] elements){
        int[] newElements = new int[elements.length];
        for(int i=0;i<elements.length;i++){
            newElements[i] = elements[i];
        }
        return newElements;
    }

    public static short[] copyShortValues(short[] shortArray) {
        short[] newElements = new short[shortArray.length];
        for(int i=0;i<shortArray.length;i++){
            newElements[i] = shortArray[i];
        }
        return newElements;
    }
    public static String exceptionToString(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return "Msg Exception -> "+sw.toString();
    }
    public static String readFileFromAssets(String path, String filename,Context context){
        return Util.readFileFromAssets(path,filename,"UTF-8",context);

    }
    public static String readFileFromAssets(String path, String filename,String fileFormat,Context context){
        return Util.readFileFromAssets2(path+filename,fileFormat,context);
    }
    public static String readFileFromAssets2(String filePath,Context context){
        return Util.readFileFromAssets2(filePath,"UTF-8",context);
    }
    public static String readFileFromAssets2(String filePath,String fileFormat,Context context){

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(filePath),fileFormat));

            // do reading, usually loop until end of file reading
            StringBuilder str = new StringBuilder();
            String mLine;
            boolean checked = false;
            while ((mLine = reader.readLine()) != null) {
                str.append(mLine+"\n");
                if(!checked)checked =true;
            }
            if(checked){
                Log.e(TAG,"readFileFromAssets() ->  - filePath = "+filePath+" loaded");
                //if(OpenGLRenderer.DEBUG)Log.e("Utils","GLSL - fileContent=  "+str.toString());
            }else{
                Log.e(TAG,"readFileFromAssets() ->  - couldnt load file : "+filePath);
            }

            return str.toString();
        } catch (Exception e) {
            Log.e(TAG,"readFileFromAssets() -> "+ Util.exceptionToString(e));

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Log.e(TAG,"Exception ocurred readFileFromAssets -> "+ Util.exceptionToString(e));
                }
            }
        }
        return null;

    }
}
