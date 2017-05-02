package com.arquigames.prinsgl;

import android.opengl.GLES20;
import android.util.Log;

import com.arquigames.prinsgl.cameras.Camera;
import com.arquigames.prinsgl.gl.GLProgram;
import com.arquigames.prinsgl.gl.renderer.GLRenderer;
import com.arquigames.prinsgl.gl.attributes.GLAttribute;
import com.arquigames.prinsgl.gl.uniforms.GLStructArrayUniform;
import com.arquigames.prinsgl.gl.uniforms.GLStructUniform;
import com.arquigames.prinsgl.gl.uniforms.GLUniform;
import com.arquigames.prinsgl.gl.uniforms.IUniform;
import com.arquigames.prinsgl.lights.AmbientLight;
import com.arquigames.prinsgl.lights.DirectionalLight;
import com.arquigames.prinsgl.lights.Light;
import com.arquigames.prinsgl.lights.PointLight;
import com.arquigames.prinsgl.lights.SpotLight;
import com.arquigames.prinsgl.materials.Material;
import com.arquigames.prinsgl.materials.MeshBasicMaterial;
import com.arquigames.prinsgl.materials.MeshPhongMaterial;
import com.arquigames.prinsgl.materials.MeshShaderMaterial;
import com.arquigames.prinsgl.maths.Color;
import com.arquigames.prinsgl.maths.matrix.Matrix4;
import com.arquigames.prinsgl.maths.vectors.Vector2;
import com.arquigames.prinsgl.maths.vectors.Vector3;
import com.arquigames.prinsgl.maths.vectors.Vector4;
import com.arquigames.prinsgl.textures.Texture;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by usuario on 26/06/2016.
 */
public class ProgramUtils {

    public static String TAG = "ProgramUtils";
    public static boolean DEBUG = false;

    public static boolean programNeedsRecreate(GLProgram program){
        if(program.getProgramLocation()>0 && !GLES20.glIsProgram(program.getProgramLocation())){
            if(GLRenderer.DEBUG)Log.e(TAG,"programNeedsRecreate(GLProgram)-> GLES20.glIsProgram() return false, id="+program.getProgramLocation());
            return true;
        }
        if(GLRenderer.DEBUG)Log.e(TAG,"programNeedsRecreate(GLProgram)-> is program");
        return false;
    }

    public static GLUniform getGLUniformFromStructArray(String structArrayName, String structIndex, String uniformName, GLProgram glProgram){
        if(structArrayName==null || structArrayName.trim().equals(""))return null;
        if(uniformName==null || uniformName.trim().equals(""))return null;
        if(structIndex==null || structIndex.trim().equals(""))return null;
        java.util.HashMap<String,IUniform> uniformsProgram = glProgram.getUniforms();
        IUniform iUniform;
        GLUniform glUniform;
        GLStructUniform glStructUniform;
        GLStructArrayUniform glStructArrayUniform;

        iUniform = uniformsProgram.get(structArrayName);
        if(iUniform!=null){
            if(iUniform instanceof GLStructArrayUniform){
                glStructArrayUniform = (GLStructArrayUniform)iUniform;
                glStructUniform = glStructArrayUniform.get(structIndex);
                if(glStructUniform!=null){
                    glUniform = glStructUniform.get(uniformName);
                    if(glUniform!=null){
                        return glUniform;
                    }else{
                        if(GLRenderer.DEBUG){
                            Log.e(TAG,"get uniform  from struct uniform from struct array : "+uniformName+" is null");
                        }
                    }
                }else{
                    if(GLRenderer.DEBUG){
                        Log.e(TAG,"get uniform struct from struct array : "+structIndex);
                    }
                }
            }else{
                if(GLRenderer.DEBUG){
                    Log.e(TAG,"iUniform : "+structArrayName+", isnt instance of GLStructArrayUniform");
                }
            }
        }else{
            if(GLRenderer.DEBUG){
                Log.e(TAG,"cannot get struct array uniform from = "+structArrayName);
            }
        }
        return null;

    }

    public static GLUniform getGLUniformFromStruct(String structName, String uniformName, GLProgram glProgram){
        if(structName==null || structName.trim().equals(""))return null;
        if(uniformName==null || uniformName.trim().equals(""))return null;
        java.util.HashMap<String,IUniform> uniformsProgram = glProgram.getUniforms();
        IUniform iUniform;
        GLUniform glUniform;
        GLStructUniform glStructUniform;

        iUniform = uniformsProgram.get(structName);
        if(iUniform!=null){
            if(iUniform instanceof GLStructUniform){
                glStructUniform = (GLStructUniform)iUniform;
                glUniform = glStructUniform.get(uniformName);
                if(glUniform!=null){
                    return glUniform;
                }else{
                    if(GLRenderer.DEBUG){
                        Log.e(TAG,"cannot get  unifrom = "+uniformName);
                    }
                }
            }
        }else{
            if(GLRenderer.DEBUG){
                Log.e(TAG,"cannot get struct unifrom = "+structName);
            }
        }
        return null;

    }
    public static void glUniformMatrix2fv(Object value, Integer uniformLocation){
        FloatBuffer floatBuffer = null;
        if(value instanceof FloatBuffer){
            if(uniformLocation!=null && uniformLocation>0){
                floatBuffer = (FloatBuffer)value;
                GLES20.glUniformMatrix2fv(
                        uniformLocation,
                        1,
                        false,
                        floatBuffer
                );
            }else{
                if(GLRenderer.DEBUG) Log.e(TAG,"cannot resolve uniformLocation for struct array of type m2 ");
            }
        }
    }
    public static void glUniformMatrix3fv(Object value, Integer uniformLocation){
        if(value instanceof FloatBuffer){
            if(uniformLocation!=null && uniformLocation>0){
                FloatBuffer floatBuffer = (FloatBuffer)value;
                GLES20.glUniformMatrix3fv(
                        uniformLocation,
                        1,
                        false,
                        floatBuffer
                );
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for struct array of type m3 ");
            }
        }
    }
    public static void glUniformMatrix4fv(Object value, Integer uniformLocation){
        if(value instanceof FloatBuffer){
            if(uniformLocation!=null && uniformLocation>0){
                FloatBuffer floatBuffer = (FloatBuffer)value;
                GLES20.glUniformMatrix4fv(
                        uniformLocation,
                        1,
                        false,
                        floatBuffer
                );
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for struct array of type m4 ");
            }
        }
    }
    public static void glUniform4f(Object value, Integer uniformLocation){
        if(value instanceof Color){
            /**/
            if(uniformLocation!=null && uniformLocation>=0 ){
                Color c = (Color)value;
                GLES20.glUniform4f(
                        uniformLocation,
                        c.getR(),
                        c.getG(),
                        c.getB(),
                        1.0f
                );
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for uniform of type '4f' ");
            }
        }else if(value instanceof Vector4){
            if(uniformLocation!=null && uniformLocation>0){
                Vector4 v4 = (Vector4)value;
                GLES20.glUniform4f(
                        uniformLocation,
                        v4.getX(),
                        v4.getY(),
                        v4.getZ(),
                        v4.getW()
                );
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for struct array of type v4 ");
            }
        }else{
            if(value!=null && GLRenderer.DEBUG)Log.e(TAG,"cannot resolve object of className = "+value.getClass().getName()+", type v4 ");
        }
    }
    public static void glUniform4fv(Object value, Integer uniformLocation){
        if(value instanceof FloatBuffer){
            if(uniformLocation!=null && uniformLocation>0){
                FloatBuffer floatBuffer = (FloatBuffer)value;
                GLES20.glUniform4fv(
                        uniformLocation,
                        floatBuffer.array().length / 4,
                        floatBuffer
                );
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for struct array of type 4fv ");
            }
        }
    }
    public static void glUniform3fv(Object value, Integer uniformLocation){
        if(value instanceof FloatBuffer){
            if(uniformLocation!=null && uniformLocation>0){
                FloatBuffer floatBuffer = (FloatBuffer)value;
                GLES20.glUniform3fv(
                        uniformLocation,
                        floatBuffer.array().length / 3,
                        floatBuffer
                );
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for struct array of type 3fv ");
            }
        }
    }
    public static void glUniform2fv(Object value, Integer uniformLocation){
        if(value instanceof FloatBuffer){
            if(uniformLocation!=null && uniformLocation>0){
                FloatBuffer floatBuffer = (FloatBuffer)value;
                GLES20.glUniform2fv(
                        uniformLocation,
                        floatBuffer.array().length / 2,
                        floatBuffer
                );
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for struct array of type 2fv ");
            }
        }
    }
    public static void glUniform1fv(Object value, Integer uniformLocation){
        if(value instanceof FloatBuffer){
            if(uniformLocation!=null && uniformLocation>0){
                FloatBuffer floatBuffer = (FloatBuffer)value;
                GLES20.glUniform1fv(
                        uniformLocation,
                        floatBuffer.array().length,
                        floatBuffer
                );
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for struct array of type 1fv ");
            }
        }
    }
    public static void glUniform3iv(Object value, Integer uniformLocation){
        if(value instanceof IntBuffer){
            if(uniformLocation!=null && uniformLocation>0){
                IntBuffer intBuffer = (IntBuffer)value;
                GLES20.glUniform3iv(
                        uniformLocation,
                        intBuffer.array().length / 3,
                        intBuffer
                );
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for struct array of type 3iv ");
            }
        }
    }
    public static void glUniform2iv(Object value, Integer uniformLocation){
        if(value instanceof IntBuffer){
            if(uniformLocation!=null && uniformLocation>0){
                IntBuffer intBuffer = (IntBuffer)value;
                GLES20.glUniform2iv(
                        uniformLocation,
                        intBuffer.array().length / 2,
                        intBuffer
                );
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for struct array of type 2iv ");
            }
        }
    }
    public static void glUniform1iv(Object value, Integer uniformLocation){
        if(value instanceof IntBuffer){
            if(uniformLocation!=null && uniformLocation>0){
                IntBuffer intBuffer = (IntBuffer)value;
                GLES20.glUniform1iv(
                        uniformLocation,
                        intBuffer.array().length,
                        intBuffer
                );
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for struct array of type 1iv ");
            }
        }
    }
    public static void glUniform3f(Object value, Integer uniformLocation){
        if(value instanceof Vector3){
            if(uniformLocation!=null && uniformLocation>0){
                Vector3 v3 = (Vector3)value;
                GLES20.glUniform3f(
                        uniformLocation,
                        v3.getX(),
                        v3.getY(),
                        v3.getZ()
                );
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for struct array of type 3f ");
            }
        }else if(value instanceof Color){
            /**/
            if(uniformLocation!=null && uniformLocation>=0 ){
                Color c = (Color)value;
                GLES20.glUniform3f(
                        uniformLocation,
                        c.getR(),
                        c.getG(),
                        c.getB()
                );
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for uniform of type 'c' ");
            }
        }
    }
    public static void glUniform2f(Object value, Integer uniformLocation){
        if(value instanceof Vector2){
            if(uniformLocation!=null && uniformLocation>0){
                Vector2 v2 = (Vector2)value;
                GLES20.glUniform2f(
                        uniformLocation,
                        v2.getX(),
                        v2.getY()
                );
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for struct array of type 3f ");
            }
        }
    }
    public static void glUniform1f(Object value, Integer uniformLocation){
            if(uniformLocation!=null && uniformLocation>0){
                if(value instanceof Float){
                    GLES20.glUniform1f(uniformLocation,((Float) value));
                }else if(value instanceof Integer){
                    float _val = (Integer) value + 0f;
                    GLES20.glUniform1f(uniformLocation,_val);
                }else{
                    //TODO
                }
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for struct array of type f, 1f ");
            }
    }
    public static void glUniform1i(Object value, Integer uniformLocation,GLRenderer glRenderer){
        if(value instanceof Integer){
            if(uniformLocation!=null && uniformLocation>0){
                GLES20.glUniform1i(uniformLocation, ((Integer) value));
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"cannot resolve uniformLocation for struct array of type i, 1i ");
            }
        }else if(value instanceof Texture){
            Texture texture = (Texture)value;
            if(texture.getImage()==null){
                if(texture.getResourceID()>0){
                    TextureUtils.loadImageOnTextureFromResourceID(texture,glRenderer);
                }else if(!texture.getSourceFile().equals("")){
                    TextureUtils.loadImageOnTextureFromAssetsPath(texture,glRenderer);
                }
            }
            if(texture.getImage()!=null){
                int textureUnit = TextureUtils.getTextureUnit(glRenderer);
                if(textureUnit>=0){
                    glRenderer.getGlState().setUsedTextureUnits(
                            glRenderer.getGlState().getUsedTextureUnits()+1
                    );
                    boolean settedd = glRenderer.getGlState().setTexture(texture, textureUnit,glRenderer);
                    if(settedd){
                        GLES20.glUniform1i(uniformLocation, textureUnit);
                        if(GLRenderer.DEBUG)Log.e(TAG, "current textureInt -> " + textureUnit);
                    }else{
                        if(GLRenderer.DEBUG)Log.e(TAG, "could not setup texture unit -> " + textureUnit);
                    }


                }else{
                    if(GLRenderer.DEBUG)Log.e(TAG,"cannot set textureUnit to ->"+textureUnit);
                }
            }
        }else{
            if(value!=null && GLRenderer.DEBUG)Log.e(TAG,"cannot set for className = "+value.getClass().getName());
        }
    }

    public static int loadShader(int type, String shaderCode) {

        // Create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        checkGlError("glCreateShader type=" + type);

        // Add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.e(TAG, "Could not compile shader " + type + ":");
            Log.e(TAG, " " + GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            shader = 0;
        }

        return shader;
    }
    public static void checkGlError(String glOperation) {
        int error = GLES20.glGetError();
        if (error != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    public static void build(Material material, GLRenderer glRenderer){
        String vertexShaderCode;
        String fragmentShaderCode;
        String vertexPath;
        String fragmentPath;

        if(material instanceof MeshShaderMaterial){
            vertexPath      = ((MeshShaderMaterial)material).getVertexPath();
            fragmentPath    = ((MeshShaderMaterial)material).getFragmentPath();
        }else if(material instanceof MeshPhongMaterial){
            vertexPath      = Constants.MATERIAL_PHONG_VERTEX_PATH;
            fragmentPath    = Constants.MATERIAL_PHONG_FRAGMENT_PATH;
        }else if(material instanceof MeshBasicMaterial){
            vertexPath      = Constants.MATERIAL_BASIC_VERTEX_PATH;
            fragmentPath    = Constants.MATERIAL_BASIC_FRAGMENT_PATH;
        }else{
            return;
        }
        material.buildUniforms();
        java.util.HashMap<String,Integer> lightsDefines = null;
        if(glRenderer.isUniformsLightsNeedsUpdate()){
            lightsDefines = ProgramUtils.buildUniformsLight(material.getUniforms(),glRenderer.getSceneLights());
        }

        String extra = "";
        if(material.getMap()!=null){
            extra += "#define USE_MAP\n";
        }
        if(lightsDefines!=null && lightsDefines.size()>0){
            java.util.Iterator<String> keysDefines = lightsDefines.keySet().iterator();
            String keyDefine;
            Integer value ;
            while(keysDefines.hasNext()){
                keyDefine = keysDefines.next();
                value = lightsDefines.get(keyDefine);
                extra += "#define "+keyDefine+" "+value+"\n";
            }
        }


        String precision = glRenderer.getGlCapabilities().getPrecision();
        precision = "precision "+precision+" float;\nprecision "+precision+" int;\n";

        vertexShaderCode = glRenderer.getRecords().getPlainText(vertexPath);

        if(vertexShaderCode==null){
            vertexShaderCode = Util.readFileFromAssets2(
                    vertexPath,
                    glRenderer.getContext()
            );
            glRenderer.getRecords().addPlainText(vertexPath,vertexShaderCode);
        }
        vertexShaderCode = precision + extra + vertexShaderCode;
        vertexShaderCode = ProgramUtils.parseIncludes(null,vertexShaderCode,glRenderer);
        //-----------------------------------------------------------------------
        fragmentShaderCode = glRenderer.getRecords().getPlainText(fragmentPath);
        if(fragmentShaderCode==null){
            fragmentShaderCode = Util.readFileFromAssets2(
                    fragmentPath,
                    glRenderer.getContext()
            );
            glRenderer.getRecords().addPlainText(fragmentPath,fragmentShaderCode);
        }
        fragmentShaderCode = precision + extra + fragmentShaderCode;
        fragmentShaderCode = ProgramUtils.parseIncludes(null,fragmentShaderCode,glRenderer);

        int vertexShader = ProgramUtils.loadShader(
                GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        if (vertexShader == 0) {
            Log.e(TAG, "Vertex shader failed");
            return;
        }
        int fragmentShader = ProgramUtils.loadShader(
                GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);
        if (fragmentShader == 0) {
            Log.e(TAG, "Fragment shader failed");
            return;
        }
        int programLocation = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(programLocation, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(programLocation, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(programLocation);                  // create OpenGL program executables

        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programLocation, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GLES20.GL_TRUE) {
            if(GLRenderer.DEBUG)Log.e(TAG, "Could not link program: ");
            if(GLRenderer.DEBUG)Log.e(TAG, GLES20.glGetProgramInfoLog(programLocation));
            GLES20.glDeleteProgram(programLocation);
            return;
        }
        GLES20.glDeleteShader(vertexShader);
        GLES20.glDeleteShader(fragmentShader);

        GLProgram program = new GLProgram();
        program.setProgramLocation(programLocation);
        ProgramUtils.fetchUniformLocations(program);
        ProgramUtils.fetchAttributeLocations(program);

        material.setProgram(program);

    }

    public static String parseIncludes(String pattern,String str,GLRenderer glRenderer) {
        if(pattern==null){
            pattern = "#include *<([\\w\\d_\\/]+)>";
        }
        if(str!=null && !str.isEmpty()){
            Pattern pp = Pattern.compile(pattern);
            Matcher matcher = pp.matcher(str);
            String filePath;
            String matchText;
            String contents;
            while(matcher.find()){
                matchText = filePath = matcher.group(1);
                filePath += ".glsl";
                contents = glRenderer.getRecords().getPlainText(filePath);
                if(contents==null){
                    contents = Util.readFileFromAssets2(filePath,glRenderer.getContext());
                    if(contents == null || contents.isEmpty()){
                        continue;
                    }
                    glRenderer.getRecords().addPlainText(filePath,contents);
                }
                str = str.replaceAll("#include *<"+matchText+">","\n"+contents+"\n");
                matcher = pp.matcher(str);
            }
        }
        return str;
    }

    private static java.util.HashMap<String,Integer> buildUniformsLight(ObjectJSON uniforms, TreeSet<Light> sceneLights) {

        if(sceneLights!=null && uniforms!=null && sceneLights.size()>0){
            AmbientLight ambientLight;
            DirectionalLight directionalLight;
            PointLight pointLight;
            SpotLight spotLight;
            java.util.HashMap<String,Integer> defines = new java.util.HashMap<String,Integer>();
            GLStructArrayUniform glStructArrayUniform_ambientLights     = new GLStructArrayUniform("ambientLights");
            GLStructArrayUniform glStructArrayUniform_directionalLights = new GLStructArrayUniform("directionalLights");
            GLStructArrayUniform glStructArrayUniform_pointLights       = new GLStructArrayUniform("pointLights");
            GLStructArrayUniform glStructArrayUniform_spotLights       = new GLStructArrayUniform("spotLights");
            int indexAmbientLight       = 0;
            int indexDirectionalLight   = 0;
            int indexPointLight         = 0;
            int indexSpotLight         = 0;
            GLStructUniform glStructUniform = null;
            GLUniform glUniform;


            java.util.Iterator<Light> lightIterator = sceneLights.iterator();
            Light light;
            while(lightIterator.hasNext()){
                light = lightIterator.next();
                glStructUniform = null;
                if(light instanceof AmbientLight){
                    ambientLight = (AmbientLight)light;
                    //--------------------------------------------------
                    glStructUniform = new GLStructUniform(indexAmbientLight);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("ambient");
                    glUniform.setValue(ambientLight.getAmbientColor());
                    glUniform.setType(GLUniform.U_C);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("diffuse");
                    glUniform.setValue(ambientLight.getDiffuseColor());
                    glUniform.setType(GLUniform.U_C);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("specular");
                    glUniform.setValue(ambientLight.getSpecularColor());
                    glUniform.setType(GLUniform.U_C);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("intensity");
                    glUniform.setValue(ambientLight.getIntensity());
                    glUniform.setType(GLUniform.U_F);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glStructArrayUniform_ambientLights.put(indexAmbientLight,glStructUniform);
                    indexAmbientLight++;
                }else if(light instanceof DirectionalLight){
                    directionalLight = (DirectionalLight)light;
                    //--------------------------------------------------
                    glStructUniform = new GLStructUniform(indexDirectionalLight);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("ambient");
                    glUniform.setValue(directionalLight.getAmbientColor());
                    glUniform.setType(GLUniform.U_C);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("diffuse");
                    glUniform.setValue(directionalLight.getDiffuseColor());
                    glUniform.setType(GLUniform.U_C);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("specular");
                    glUniform.setValue(directionalLight.getSpecularColor());
                    glUniform.setType(GLUniform.U_C);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("intensity");
                    glUniform.setValue(directionalLight.getIntensity());
                    glUniform.setType(GLUniform.U_F);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("direction");
                    glUniform.setValue(directionalLight.getPosition());
                    glUniform.setType(GLUniform.U_V3);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glStructArrayUniform_directionalLights.put(indexDirectionalLight,glStructUniform);
                    indexDirectionalLight++;
                }else if(light instanceof PointLight){

                    pointLight = (PointLight) light;
                    //--------------------------------------------------
                    glStructUniform = new GLStructUniform(indexPointLight);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("ambient");
                    glUniform.setValue(pointLight.getAmbientColor());
                    glUniform.setType(GLUniform.U_C);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("diffuse");
                    glUniform.setValue(pointLight.getDiffuseColor());
                    glUniform.setType(GLUniform.U_C);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("specular");
                    glUniform.setValue(pointLight.getSpecularColor());
                    glUniform.setType(GLUniform.U_C);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("intensity");
                    glUniform.setValue(pointLight.getIntensity());
                    glUniform.setType(GLUniform.U_F);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("position");
                    glUniform.setValue(pointLight.getPosition());
                    glUniform.setType(GLUniform.U_V3);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("distance");
                    glUniform.setValue(pointLight.getDistance());
                    glUniform.setType(GLUniform.U_F);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("decay");
                    glUniform.setValue(pointLight.getDecay());
                    glUniform.setType(GLUniform.U_F);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glStructArrayUniform_pointLights.put(indexPointLight,glStructUniform);
                    indexPointLight++;

                }else if(light instanceof SpotLight){

                    spotLight = (SpotLight) light;
                    //--------------------------------------------------
                    glStructUniform = new GLStructUniform(indexSpotLight);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("ambient");
                    glUniform.setValue(spotLight.getAmbientColor());
                    glUniform.setType(GLUniform.U_C);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("diffuse");
                    glUniform.setValue(spotLight.getDiffuseColor());
                    glUniform.setType(GLUniform.U_C);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("specular");
                    glUniform.setValue(spotLight.getSpecularColor());
                    glUniform.setType(GLUniform.U_C);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("intensity");
                    glUniform.setValue(spotLight.getIntensity());
                    glUniform.setType(GLUniform.U_F);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("position");
                    glUniform.setValue(spotLight.getPosition());
                    glUniform.setType(GLUniform.U_V3);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("distance");
                    glUniform.setValue(spotLight.getDistance());
                    glUniform.setType(GLUniform.U_F);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("penumbra");
                    glUniform.setValue(spotLight.getPenumbra());
                    glUniform.setType(GLUniform.U_F);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("spotCutOff");
                    glUniform.setValue(spotLight.getSpotCutOff());
                    glUniform.setType(GLUniform.U_F);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("spotCosCutOff");
                    glUniform.setValue(spotLight.getSpotCosCutOff());
                    glUniform.setType(GLUniform.U_F);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("spotExponent");
                    glUniform.setValue(spotLight.getSpotExponent());
                    glUniform.setType(GLUniform.U_F);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glUniform = new GLUniform();
                    glUniform.setName("spotDirection");
                    glUniform.setValue(spotLight.getSpotDirection());
                    glUniform.setType(GLUniform.U_V3);
                    glStructUniform.getUniforms().add(glUniform);
                    //--------------------------------------------------
                    glStructArrayUniform_spotLights.put(indexSpotLight,glStructUniform);
                    indexSpotLight++;
                }else{
                    //TODO
                }
                //----------------------------------------------------------
                if(glStructUniform!=null)light.setUniform(glStructUniform);
                //----------------------------------------------------------
            }
            if(indexAmbientLight>0){
                defines.put("AMBIENT_LIGHTS",indexAmbientLight);
                uniforms.put(glStructArrayUniform_ambientLights.getName(),glStructArrayUniform_ambientLights);
            }
            if(indexDirectionalLight>0){
                defines.put("DIRECTIONAL_LIGHTS",indexDirectionalLight);
                uniforms.put(glStructArrayUniform_directionalLights.getName(),glStructArrayUniform_directionalLights);
            }
            if(indexPointLight>0){
                defines.put("POINT_LIGHTS",indexPointLight);
                uniforms.put(glStructArrayUniform_pointLights.getName(),glStructArrayUniform_pointLights);
            }
            if(indexSpotLight>0){
                defines.put("SPOT_LIGHTS",indexSpotLight);
                uniforms.put(glStructArrayUniform_spotLights.getName(),glStructArrayUniform_spotLights);
            }
            return defines;
        }
        return null;
    }

    public static void fetchUniformLocations(GLProgram program){
        if(program.getProgramLocation()<=0){
            if(GLRenderer.DEBUG)Log.e(TAG,"fetchUniformLocations()-> cannot fetch uniforms location with program = "+program.getProgramLocation());
            return;
        }

        int programLocation = program.getProgramLocation();
        java.util.HashMap<String,IUniform> uniforms = program.getUniforms();

        //--------------------
        //current sdk <17
        int bufferSize = 150;
        byte[] buffer_name = new byte[bufferSize];
        int[] length = new int[1];
        //------------------------------------

        int[] size = new int[1];

        int[] uniformSize = new int[1];
        int[] uniformType = new int[1];
        String uniformStr = "";
        int uniformLocation = -1;


        String structRe = "^([\\w\\d_]+)\\.([\\w\\d_]+)$";
        String arrayStructRe = "^([\\w\\d_]+)\\[(\\d+)\\]\\.([\\w\\d_]+)$";
        String arrayRe = "^([\\w\\d_]+)\\[0\\]$";

        Pattern pStructRe = Pattern.compile(structRe);
        Pattern pArrayStructRe = Pattern.compile(arrayStructRe);
        Pattern pArrayRe = Pattern.compile(arrayRe);

        String name = "";
        String index = "";
        String property = "";

        GLUniform glUniform= null;
        GLStructUniform glStructUniform = null;
        GLStructArrayUniform glStructArrayUniform = null;

        Matcher matcher = null;
        if(GLRenderer.DEBUG)Log.e(TAG,"fetchUniformLocations()-> programLocation="+programLocation+"");
        GLES20.glGetProgramiv(programLocation,GLES20.GL_ACTIVE_UNIFORMS,size,0);
        Log.e("Sizeof Uniforms --> ", size[0] + "");
        if(size[0]>0){
            size = new int[size[0]];
            for(int i=0;i<size.length;i++){
                GLES20.glGetActiveUniform(programLocation, i, bufferSize, length, 0, uniformSize, 0, uniformType, 0, buffer_name, 0);
                if(length[0]==0)continue;
                uniformStr = "";
                for(int _i=0;_i<length[0];_i++){
                    uniformStr += (char)buffer_name[_i];
                }
                //}


                uniformLocation = GLES20.glGetUniformLocation(programLocation, uniformStr);
                if(GLRenderer.DEBUG)Log.e(TAG,"getUniformLocations()-> parsing active uniform = " + uniformStr+",size="+uniformSize[0]+",type="+uniformType[0]+",location="+uniformLocation);
                matcher = pStructRe.matcher(uniformStr);
                if(matcher.find()){
                    name      = matcher.group(1);
                    property  = matcher.group(2);
                    if(GLRenderer.DEBUG)Log.e(TAG,"getUniformLocations()-> match StructRe = "+name+"."+property);
                    glStructUniform = (GLStructUniform)uniforms.get(name);
                    if(glStructUniform==null){
                        glStructUniform = new GLStructUniform(name);
                        uniforms.put(name,glStructUniform);
                    }
                    glUniform = new GLUniform();
                    glUniform.setGlLocation(uniformLocation);
                    glUniform.setGlType(uniformType[0]);
                    glUniform.setGlSize(uniformSize[0]);
                    glUniform.setName(property);
                    glStructUniform.getUniforms().add(glUniform);
                    continue;
                }

                matcher = pArrayStructRe.matcher(uniformStr);
                if(matcher.find()){
                    name      = matcher.group(1);
                    index     = matcher.group(2);
                    property  = matcher.group(3);
                    if(GLRenderer.DEBUG)Log.e(TAG,"getUniformLocations()-> match ArrayStructRe = "+name+"["+index+"]"+"."+property);
                    //Util.printMatcherGroups(matcher);
                    glStructArrayUniform = (GLStructArrayUniform)uniforms.get(name);
                    if(glStructArrayUniform ==null){
                        glStructArrayUniform = new GLStructArrayUniform(name);
                        uniforms.put(name, glStructArrayUniform);
                    }
                    glStructUniform = glStructArrayUniform.get(index);
                    if(glStructUniform==null){
                        glStructUniform = new GLStructUniform(index);
                        glStructArrayUniform.put(index,glStructUniform);
                    }
                    glUniform = new GLUniform();
                    glUniform.setGlLocation(uniformLocation);
                    glUniform.setGlType(uniformType[0]);
                    glUniform.setGlSize(uniformSize[0]);
                    glUniform.setName(property);
                    glStructUniform.getUniforms().add(glUniform);
                    continue;
                }
                matcher = pArrayRe.matcher(uniformStr);
                if(matcher.find()){
                    name  = matcher.group(1);
                    if(GLRenderer.DEBUG)Log.e(TAG,"getUniformLocations()-> match ArrayRe = "+name);
                    //Util.printMatcherGroups(matcher);
                    glUniform = new GLUniform();
                    glUniform.setGlLocation(uniformLocation);
                    glUniform.setGlType(uniformType[0]);
                    glUniform.setGlSize(uniformSize[0]);
                    glUniform.setName(name);
                    uniforms.put(name,glUniform);
                    continue;
                }
                if(GLRenderer.DEBUG)Log.e(TAG,"getUniformLocations()-> match Re = "+uniformStr);
                glUniform = new GLUniform();
                glUniform.setGlLocation(uniformLocation);
                glUniform.setGlType(uniformType[0]);
                glUniform.setGlSize(uniformSize[0]);
                glUniform.setName(uniformStr);
                uniforms.put(uniformStr,glUniform);
            }
        }
    }

    public static void fetchAttributeLocations(GLProgram program){
        if(program.getProgramLocation()<=0){
            if(GLRenderer.DEBUG)Log.e(TAG,"fetchUniformLocations()-> cannot fetch attributes location with program = "+program.getProgramLocation());
            return;
        }
        int programLocation = program.getProgramLocation();
        java.util.HashMap<String,GLAttribute> attributes = program.getAttributes();

        //--------------------
        //current sdk <17
        int bufferSize = 150;
        byte[] name = new byte[bufferSize];
        int[] length = new int[1];
        //------------------------------------

        int[] size_active_attributes = new int[1];

        int[] attributeSize = new int[1];
        int[] attributeType = new int[1];

        String attributeStr = "";
        int attributeLocation = -1;


        GLES20.glGetProgramiv(programLocation,GLES20.GL_ACTIVE_ATTRIBUTES,size_active_attributes,0);

        if(GLRenderer.DEBUG)Log.e(TAG,"fetchAttributeLocations()-> total active attributes = "+ size_active_attributes[0] + "");
        if(size_active_attributes[0]>0) {

            GLAttribute tmpAttribute;

            size_active_attributes = new int[size_active_attributes[0]];
            for (int i = 0; i < size_active_attributes.length; i++) {
                GLES20.glGetActiveAttrib(programLocation, i, bufferSize, length, 0, attributeSize, 0, attributeType, 0,name,0);
                if(length[0]==0)continue;
                attributeStr = "";
                for(int _i=0;_i<length[0];_i++){
                    attributeStr += (char)name[_i];
                }
                attributeLocation = GLES20.glGetAttribLocation(programLocation, attributeStr);

                if(GLRenderer.DEBUG)Log.e(TAG,"fetchAttributeLocations()-> parsing active attribute = "+ attributeStr+",size="+attributeSize[0]+",type="+attributeType[0]+",location="+attributeLocation);//this.attributes.put(attributeStr,new Integer(attributeLocation));
                //------------------------------------------------------
                tmpAttribute = new GLAttribute(attributeStr);

                tmpAttribute.setGlLocation(attributeLocation);
                tmpAttribute.setGlType(attributeType[0]);
                tmpAttribute.setGlSize(attributeSize[0]);
                attributes.put(attributeStr, tmpAttribute);
                //------------------------------------------------------
            }
        }
    }

    public static void loadDefaultUniforms(Material material,Object3D object3D,Camera camera){

        GLProgram program = material.getProgram();

        GLUniform uCameraMatrix        = (GLUniform)program.getUniforms().get("cameraMatrix");
        GLUniform uCameraPosition      = (GLUniform)program.getUniforms().get("cameraPosition");
        GLUniform uNormalMatrix        = (GLUniform)program.getUniforms().get("normalMatrix");
        GLUniform uViewMatrix          = (GLUniform)program.getUniforms().get("viewMatrix");
        GLUniform uModelViewMatrix     = (GLUniform)program.getUniforms().get("modelViewMatrix");
        GLUniform uProjectionMatrix    = (GLUniform)program.getUniforms().get("projectionMatrix");
        GLUniform uMVPMatrix           = (GLUniform)program.getUniforms().get("uMVPMatrix");

        int uniformLocation;

        if(uCameraMatrix!=null){
            uniformLocation = uCameraMatrix.getGlLocation();
            if(uniformLocation>=0){
                if(GLRenderer.DEBUG)Log.e(TAG,"loadDefaultUniforms()-> uCameraMatrix");
                GLES20.glUniformMatrix4fv(uniformLocation, 1, false, camera.getMatrixWorld().getElements(), 0);
            }
        }
        if(uCameraPosition!=null){
            uniformLocation = uCameraPosition.getGlLocation();
            if(uniformLocation>=0){
                if(GLRenderer.DEBUG)Log.e(TAG,"loadDefaultUniforms()-> uCameraPosition");
                GLES20.glUniform3f(uniformLocation,camera.getPosition().getX(),camera.getPosition().getY(),camera.getPosition().getZ());
            }
        }
        if(uViewMatrix!=null){

            uniformLocation = uViewMatrix.getGlLocation();
            if(uniformLocation>=0){
                if(GLRenderer.DEBUG)Log.e(TAG,"loadDefaultUniforms()-> uViewMatrix");
                GLES20.glUniformMatrix4fv(uniformLocation, 1, false, camera.getMatrixWorldInverse().getElements(), 0);
            }

        }
        if(uNormalMatrix!=null){

            uniformLocation = uNormalMatrix.getGlLocation();
            if(uniformLocation>=0){
                if(GLRenderer.DEBUG)Log.e(TAG,"loadDefaultUniforms()-> uNormalMatrix");
                GLES20.glUniformMatrix3fv(uniformLocation, 1, false, object3D.getNormalMatrix().getElements(), 0);
            }

        }
        if(uModelViewMatrix!=null){

            uniformLocation = uModelViewMatrix.getGlLocation();
            if(uniformLocation>=0){
                if(GLRenderer.DEBUG)Log.e(TAG,"loadDefaultUniforms()-> uModelViewMatrix");
                GLES20.glUniformMatrix4fv(uniformLocation, 1, false, object3D.getModelViewMatrix().getElements(), 0);
            }

        }
        if(uProjectionMatrix!=null){
            uniformLocation = uProjectionMatrix.getGlLocation();
            if(uniformLocation>=0){
                if(GLRenderer.DEBUG)Log.e(TAG,"loadDefaultUniforms()-> uProjectionMatrix");
                GLES20.glUniformMatrix4fv(uniformLocation, 1, false, camera.getProjectionMatrix().getElements(), 0);
            }

        }
        if(uMVPMatrix!=null){
            uniformLocation = uMVPMatrix.getGlLocation();
            if(uniformLocation>=0){
                if(GLRenderer.DEBUG)Log.e(TAG,"loadDefaultUniforms()-> uMVPMatrix");
                Matrix4 matrix = new Matrix4();
                matrix.multiplyMatrices(camera.getProjectionMatrix(), object3D.getModelViewMatrix());
                GLES20.glUniformMatrix4fv(uniformLocation, 1, false,matrix.getElements(), 0);
            }
        }
    }

    public static void loadUniforms( Material material,GLRenderer glRenderer){

        GLProgram glProgram = material.getProgram();

        ObjectJSON uniforms = null;
        if(material instanceof MeshShaderMaterial){
            uniforms = ((MeshShaderMaterial)material).uniforms;
        }else if(material instanceof MeshPhongMaterial){
            uniforms = material.getUniforms();
        }else if(material instanceof MeshBasicMaterial){
            uniforms = material.getUniforms();
        }
        else{
            return;
        }

        java.util.Iterator<String> keys = uniforms.getKeysIterator();
        String key;
        GLUniform glUniformMaterial;
        GLUniform glUniformProgram;
        IUniform iUniform;
        while(keys.hasNext()){
            key = keys.next();

            iUniform = (IUniform) uniforms.get(key);
            if(iUniform instanceof GLUniform){
                glUniformMaterial = (GLUniform) iUniform;
                glUniformProgram = ProgramUtils.getGLUniform(key,glProgram);
                if(glUniformProgram!=null && glUniformProgram.getGlLocation()>=0){
                    if(GLRenderer.DEBUG)Log.e(
                            TAG,
                            "loadUniforms()-> bindIUniform: "+
                                    "name="+glUniformMaterial.getName()+
                                    ",value="+glUniformMaterial.getValue().toString()+
                                    ",type="+glUniformMaterial.getType()+
                                    ",location="+glUniformProgram.getGlLocation()
                    );
                    ProgramUtils.bindIUniform(
                            glUniformMaterial.getValue(),
                            glUniformMaterial.getType(),
                            glUniformProgram.getGlLocation(),
                            glRenderer,
                            glProgram
                    );
                }
            }else{
                //TODO
                if(iUniform instanceof GLStructUniform){
                    ProgramUtils.bindIUniform(
                            iUniform,
                            GLUniform.U_S,
                            null,
                            glRenderer,
                            glProgram
                    );
                }else{
                    if(iUniform instanceof GLStructArrayUniform){
                        ProgramUtils.bindIUniform(
                                iUniform,
                                GLUniform.U_SA,
                                null,
                                glRenderer,
                                glProgram
                        );
                    }else{
                        //TODO
                    }
                }
            }


            /*
            glUniformMaterial = (GLUniform) uniforms.get(key);
            if(glUniformMaterial!=null){

                glUniformProgram = ProgramUtils.getGLUniform(key,glProgram);
                if(glUniformProgram!=null && glUniformProgram.getGlLocation()>=0){
                    if(GLRenderer.DEBUG)Log.e(
                                TAG,
                                "loadUniforms()-> bindIUniform: "+
                                        "value="+glUniformMaterial.getValue().toString()+
                                        ",type="+glUniformMaterial.getType()+
                                        ",location="+glUniformProgram.getGlLocation()
                        );
                    ProgramUtils.bindIUniform(
                            glUniformMaterial.getValue(),
                            glUniformMaterial.getType(),
                            glUniformProgram.getGlLocation(),
                            glRenderer,
                            glProgram
                    );
                }
            }
            */

        }


    }

    public static GLUniform getGLUniform(String uniformName, GLProgram glProgram){
        if(uniformName==null || uniformName.trim().equals(""))return null;
        java.util.HashMap<String,IUniform> uniformsProgram = glProgram.getUniforms();
        IUniform iUniform;
        GLUniform glUniform;
        iUniform = uniformsProgram.get(uniformName);
        if(iUniform!=null){
            if(iUniform instanceof GLUniform){
                glUniform = (GLUniform)iUniform;
                return glUniform;
            }
        }
        return null;
    }
    public static void bindIUniform(Object value, String type, Integer location, GLRenderer glRenderer, GLProgram glProgram) {
        if (type == null || type == "") {
            if(GLRenderer.DEBUG)Log.e(TAG, "type string is empty -> bindIUniform(method)");
            return;
        }
        if (value == null) {
            if(GLRenderer.DEBUG)Log.e(TAG, "value is null -> bindIUniform(method)");
            return;
        }
        if (location == null && !type.equals(GLUniform.U_SA) && !type.equals(GLUniform.U_S)) {
            if(GLRenderer.DEBUG)Log.e(TAG, "location is null -> bindIUniform(method)");
            return;
        }

        GLUniform glUniform;
        GLStructUniform glStructUniform;
        GLStructArrayUniform glStructArrayUniform;

        GLUniform programGLUniform;
        GLStructUniform programGLStructUniform;
        GLStructArrayUniform programGLStructArrayUniform;

        switch (type) {
            case GLUniform.U_1I:
            case GLUniform.U_I:
            case GLUniform.U_T:
                ProgramUtils.glUniform1i(value, location,glRenderer);
                break;
            case GLUniform.U_1F:
            case GLUniform.U_F:
                ProgramUtils.glUniform1f(value, location);
                break;
            case GLUniform.U_2F:
            case GLUniform.U_V2:
                ProgramUtils.glUniform2f(value, location);
                break;
            case GLUniform.U_3F:
            case GLUniform.U_V3:
            case GLUniform.U_C:
                ProgramUtils.glUniform3f(value, location);
                break;
            case GLUniform.U_4F:
            case GLUniform.U_V4:
                ProgramUtils.glUniform4f(value, location);
                break;
            case GLUniform.U_1IV:
                ProgramUtils.glUniform1iv(value, location);
                break;
            case GLUniform.U_2IV:
                ProgramUtils.glUniform2iv(value, location);
                break;
            case GLUniform.U_3IV:
                ProgramUtils.glUniform3iv(value, location);
                break;
            case GLUniform.U_1FV:
                ProgramUtils.glUniform1fv(value, location);
                break;
            case GLUniform.U_2FV:
                ProgramUtils.glUniform2fv(value, location);
                break;
            case GLUniform.U_3FV:
                ProgramUtils.glUniform3fv(value, location);
                break;
            case GLUniform.U_4FV:
                ProgramUtils.glUniform4fv(value, location);
                break;
            case GLUniform.U_M2FV:
            case GLUniform.U_M2:
                ProgramUtils.glUniformMatrix2fv(value, location);
                break;
            case GLUniform.U_M3FV:
            case GLUniform.U_M3:
                ProgramUtils.glUniformMatrix3fv(value, location);
                break;
            case GLUniform.U_M4FV:
            case GLUniform.U_M4:
                ProgramUtils.glUniformMatrix4fv(value, location);
                break;
            case GLUniform.U_S:
                if(GLRenderer.DEBUG)Log.e(TAG,"binding uniform struct");

                if(value instanceof GLStructUniform){
                    glStructUniform = (GLStructUniform)value;
                    ProgramUtils.bindGLStructUniform(glStructUniform,glRenderer,glProgram);
                }
                break;
            case GLUniform.U_SA:
                if(GLRenderer.DEBUG)Log.e(TAG,"binding uniform struct array");

                if(value instanceof GLStructArrayUniform){
                    glStructArrayUniform = (GLStructArrayUniform)value;
                    ProgramUtils.bindGLStructArrayUniform(glStructArrayUniform,glRenderer,glProgram);
                }
                break;
        }
    }

    public static void bindGLStructArrayUniform(
            GLStructArrayUniform glStructArrayUniform,
            GLRenderer glRenderer,
            GLProgram glProgram) {

        java.util.Set<String> keys = glStructArrayUniform.getStructUniforms().keySet();
        java.util.Iterator<String> iteratorKeys = keys.iterator();
        java.util.Iterator<GLUniform> glUniformIterator;
        String key;
        GLStructUniform glStructUniform;
        GLUniform programGLUniform;
        GLUniform glUniform;
        String structArrayName = glStructArrayUniform.getName();
        while(iteratorKeys.hasNext()){
            key = iteratorKeys.next();
            glStructUniform = glStructArrayUniform.getStructUniforms().get(key);
            if(glStructUniform!=null){
                glUniformIterator = glStructUniform.getUniforms().iterator();
                while(glUniformIterator.hasNext()){
                    glUniform = glUniformIterator.next();
                    if(GLRenderer.DEBUG || ProgramUtils.DEBUG){
                        Log.e(
                                TAG,
                                "bindUniform()-> "+
                                        "getGLUniformFromStructArray = "+structArrayName+
                                        ", glStructUniformIndex = "+key+
                                        ", glUniformName = "+glUniform.getName()
                        );
                    }
                    programGLUniform = ProgramUtils.getGLUniformFromStructArray(
                            structArrayName,
                            key,
                            glUniform.getName(),
                            glProgram
                    );
                    if(programGLUniform!=null){
                        if(GLRenderer.DEBUG || ProgramUtils.DEBUG){
                            Log.e(
                                    "ProgramUtils",
                                    "bindUniform()-> from struct array: "+
                                            "value="+glUniform.getValue().toString()+
                                            ",type="+glUniform.getType()+
                                            ",location="+programGLUniform.getGlLocation()
                            );
                        }
                        ProgramUtils.bindIUniform(
                                glUniform.getValue(),
                                glUniform.getType(),
                                programGLUniform.getGlLocation(),
                                glRenderer,
                                glProgram
                        );
                    }
                }
            }
        }
    }

    public static void bindGLStructUniform(GLStructUniform glStructUniform,GLRenderer glRenderer,GLProgram glProgram) {
        GLUniform glUniform;
        GLUniform programGLUniform;
        java.util.Iterator<GLUniform> uniformsIterator = glStructUniform.getUniforms().iterator();
        while(uniformsIterator.hasNext()){
            glUniform = uniformsIterator.next();
            programGLUniform = ProgramUtils.getGLUniformFromStruct(
                    glStructUniform.getName(),
                    glUniform.getName(),
                    glProgram
            );
            if(     programGLUniform!=null &&
                    programGLUniform.getGlLocation()>=0 &&
                    glUniform.getType()!= GLUniform.U_S &&
                    glUniform.getType()!= GLUniform.U_SA

                    ){

                //PREVENT RECURSIVE
                if(GLRenderer.DEBUG){
                    Log.e(TAG,"bind uniform from struct (value="+
                            glUniform.getValue().toString()+
                            ",type="+glUniform.getType()+
                            ",glLocation = "+programGLUniform.getGlLocation()+")");
                }
                ProgramUtils.bindIUniform(
                        glUniform.getValue(),
                        glUniform.getType(),
                        programGLUniform.getGlLocation(),
                        glRenderer,
                        glProgram
                );
            }
        }

    }


}
