package com.arquigames.prinsgl.testing.patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by usuario on 01/10/2016.
 */

public class Pattern1 {
     public static void Main(String ... values){
         String str = "#define ABC\n" +
                 "#include<ab/df/dfds>\n"+
                 "#include<va/cc/aa>\n"+
                 "vec2 asd;";
         String pattern = "#include +<([\\w\\d_\\/]+)>";
         Pattern pp = Pattern.compile(pattern);
         Matcher matcher = pp.matcher(str);
         String filePath;
         String contents = null;
         while(matcher.find()){
             filePath = matcher.group(1);
             filePath += ".glsl";
             str.replaceAll(pattern,filePath);
         }
     }
}
