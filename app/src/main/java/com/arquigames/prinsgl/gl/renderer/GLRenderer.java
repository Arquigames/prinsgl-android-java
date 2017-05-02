package com.arquigames.prinsgl.gl.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.arquigames.prinsgl.gl.attributes.BufferAttribute;
import com.arquigames.prinsgl.Constants;
import com.arquigames.prinsgl.gl.GLCapabilities;
import com.arquigames.prinsgl.gl.GLState;
import com.arquigames.prinsgl.maths.Frustum;
import com.arquigames.prinsgl.GeometryUtils;
import com.arquigames.prinsgl.Object3D;
import com.arquigames.prinsgl.ProgramUtils;
import com.arquigames.prinsgl.Records;
import com.arquigames.prinsgl.Scene;
import com.arquigames.prinsgl.Util;
import com.arquigames.prinsgl.cameras.Camera;
import com.arquigames.prinsgl.geometries.Geometry;
import com.arquigames.prinsgl.gl.attributes.GLAttribute;
import com.arquigames.prinsgl.lights.Light;
import com.arquigames.prinsgl.materials.IMaterial;
import com.arquigames.prinsgl.materials.Material;
import com.arquigames.prinsgl.materials.MultiMaterial;
import com.arquigames.prinsgl.maths.matrix.Matrix4;
import com.arquigames.prinsgl.meshes.Mesh;
import com.arquigames.prinsgl.maths.vectors.Vector3;
import com.arquigames.prinsgl.morphing.MorphInfluence;

import java.util.Collections;


/**
 * Created by usuario on 07/08/2016.
 */
public class GLRenderer {
    public static boolean DEBUG = false;
    private Frustum frustum;
    private Matrix4 projectionScreenMatrix;
    private boolean sortObjects = true;
    private Vector3 zIndex;
    private Context context;

    private java.util.LinkedList<RenderItem> transparentItems ;
    private java.util.LinkedList<RenderItem> opaqueItems ;

    private RenderItemComparator renderItemComparator;
    private java.util.LinkedList<Integer> enabledAttributes;

    private Records records;

    private IBufferRenderer iBufferRenderer;
    private GLBufferRenderer glBufferRenderer;
    private GLIndexedBufferRenderer glIndexedBufferRenderer;

    private float pixelRatio = 1f;

    private GLState glState;
    public static String TAG;

    private boolean uniformsLightsNeedsUpdate = false;
    private java.util.TreeSet<Light> sceneLights = null;

    private GLCapabilities glCapabilities ;

    public GLRenderer(Context context){
        this.context= context;
        this.setFrustum(new Frustum());
        this.setProjectionScreenMatrix(new Matrix4());
        this.setzIndex(new Vector3());

        this.renderItemComparator = new RenderItemComparator();

        this.transparentItems = new java.util.LinkedList<RenderItem>();
        this.opaqueItems = new java.util.LinkedList<RenderItem>();

        this.enabledAttributes = new java.util.LinkedList<Integer>();

        this.setRecords(new Records());

        this.glBufferRenderer = new GLBufferRenderer();
        this.glIndexedBufferRenderer = new GLIndexedBufferRenderer();

        this.glState = new GLState();

        this.setGlCapabilities(new GLCapabilities());

    }
    public void loadCapabilities(){
        this.glCapabilities.init();
    }
    public void loadDefaultState(){
        this.glState.setupDefaultState();
    }
    public Context getContext(){
        return this.context;
    }
    public void render(Scene scene, Camera camera) throws Exception{
        if (GLRenderer.DEBUG) Log.e("GLRenderer", "BEGIN OF RENDERER FUNCTION");
        if (scene.autoUpdate) scene.updateMatrixWorld(true);

        //-----------------------------------------------------------
        this.setUniformsLightsNeedsUpdate(scene.isLightsNeedsUpdate());
        if(this.isUniformsLightsNeedsUpdate()){
            this.setSceneLights(scene.getLights());
        }
        //-----------------------------------------------------------
        if (camera != null) {
            camera.updateMatrixWorld(true);
            camera.getMatrixWorldInverse().getInverse(camera.getMatrixWorld(), true);
            this.getProjectionScreenMatrix().multiplyMatrices(
                    camera.getProjectionMatrix(),
                    camera.getMatrixWorldInverse()
            );
            if (GLRenderer.DEBUG)
                Log.e("OpenGLRenderer", "render(scene,camera)-> setup frustum matrix");
            this.getFrustum().setFromMatrix(this.getProjectionScreenMatrix());
        }
        this.projectObjects(scene,camera);
        //-----------------------------------------------------------
        //Collections.sort(this.transparentItems,this.renderItemComparator);
        //Collections.sort(this.opaqueItems,this.renderItemComparator);
        //-----------------------------------------------------------
        Collections.sort(this.transparentItems);
        Collections.sort(this.opaqueItems);
        //-----------------------------------------------------------
        if(GLRenderer.DEBUG)Log.e(TAG,"transparentItems = "+this.transparentItems.size());
        if(GLRenderer.DEBUG)Log.e(TAG,"opaqueItems = "+this.opaqueItems.size());
        this.renderObjects(this.transparentItems,camera);
        this.renderObjects(this.opaqueItems,camera);
        this.transparentItems.clear();
        this.opaqueItems.clear();
        //-----------------------------------------------------------
        this.setUniformsLightsNeedsUpdate(false);
        //-----------------------------------------------------------
        if (GLRenderer.DEBUG) Log.e("GLRenderer", "END OF RENDERER FUNCTION");
    }

    private void renderObjects(java.util.LinkedList<RenderItem> items, Camera camera) {

        java.util.Iterator<RenderItem> iteratorItems = items.iterator();
        RenderItem renderItem;
        Material mat;
        Mesh mesh;

        while(iteratorItems.hasNext()){
            renderItem = iteratorItems.next();
            mesh = (Mesh)renderItem.getObject3D();
            mat = renderItem.getMaterial();
            //----------------------------------------------
            this.setMaterialProperties(mat);
            //----------------------------------------------
            if(mat.getProgram()==null){
                ProgramUtils.build(mat,this);
            }else{
                if(this.isUniformsLightsNeedsUpdate()){
                    mat.getProgram().dispose();
                    ProgramUtils.build(mat,this);
                }
            }
            if(mat.getProgram()==null)continue;
            this.glState.resetUsedTextureUnit();
            //USE PROGRAM
            GLES20.glUseProgram(mat.getProgram().getProgramLocation());

            //---------------------------------------------------------------
            //UPDATE MORPH TARGETS
            this.updateMorphs(mesh,mat);
            //---------------------------------------------------------------


            //SETUP VERTEX ATTRIBUTES
            this.setupVertexAttributes(mat,mesh.getGeometry());

            //LOAD UNIFORMS
            ProgramUtils.loadDefaultUniforms(mat,mesh,camera);
            ProgramUtils.loadUniforms(mat,this);


            // DRAW THE SHAPE
            BufferAttribute indices = (BufferAttribute)mesh.getGeometry().getBuffers().get("indices");
            BufferAttribute position = (BufferAttribute)mesh.getGeometry().getBuffers().get("position");

            if(mat.isWireframe()){
                GeometryUtils.makeWireframeAttribute(mesh.getGeometry());
                indices = (BufferAttribute) mesh.getGeometry().getBuffers().get("wireframe");
                if(indices!=null)this.glState.setLineWidth(this.getPixelRatio() * mat.getLineWidth());
            }else{
                //TODO
                //indices=null;
            }

            int dataCount = 0;
            int dataStart = 0;
            if(indices!=null){
                dataCount = indices.getSize();
                this.glIndexedBufferRenderer.setRequireBufferAttribute(true);
                this.glIndexedBufferRenderer.setIndex(indices);
                this.iBufferRenderer = this.glIndexedBufferRenderer;
            }else{
                dataCount = position.count();
                this.iBufferRenderer = this.glBufferRenderer;
            }
            if(mat.isWireframe()){
                this.iBufferRenderer.setMode(GLES20.GL_LINES);
            }else{
                int _mode = mesh.getDrawMode();
                switch(_mode){
                    case Constants.TRIANGLES_DRAW_MODE:
                        this.iBufferRenderer.setMode(GLES20.GL_TRIANGLES);
                        break;
                    case Constants.TRIANGLE_STRIP_DRAW_MODE:
                        this.iBufferRenderer.setMode(GLES20.GL_TRIANGLE_STRIP);
                        break;
                    case Constants.TRIANGLE_FAN_DRAW_MODE:
                        this.iBufferRenderer.setMode(GLES20.GL_TRIANGLE_FAN);
                        break;
                }
            }

            int groupStart = renderItem.getRenderGroup()!=null ? renderItem.getRenderGroup().getStart() : 0;
            int groupCount = renderItem.getRenderGroup()!=null ? renderItem.getRenderGroup().getCount() : Integer.MAX_VALUE;

            int drawStart = Math.max(dataStart,groupStart);
            int drawEnd   = Math.min(dataCount,groupCount);

            this.iBufferRenderer.render(drawStart,drawEnd);

            //DISABLING ENABLED ATTRIBUTES
            this.disableEnabledAttributes();
            this.enabledAttributes.clear();


        }


    }

    private void updateMorphs(Mesh mesh, Material mat) {

        java.util.LinkedList<Float> morphTargetInfluences = mesh.getMorphTargetInfluences();
        if(morphTargetInfluences.size()>0){
            java.util.Iterator<Float> iterator = morphTargetInfluences.iterator();
            float influence;
            java.util.LinkedList<MorphInfluence> activeInfluences = new java.util.LinkedList<MorphInfluence>();
            int index = 0;
            while(iterator.hasNext()){
                influence = iterator.next();
                activeInfluences.add(new MorphInfluence(influence,index));
                index++;
            }
            Collections.sort(activeInfluences);
            int diff = activeInfluences.size()-8;
            while(diff>0){
                activeInfluences.removeLast();
                diff--;
            }
            mesh.getGeometry().getMorphTargets();
        }
    }

    private void setMaterialProperties(Material material) {
        if (material.getSide() != Constants.FACE_DOUBLE_SIDE){
            this.glState.enable(GLES20.GL_CULL_FACE);
        }else{
            this.glState.disable(GLES20.GL_CULL_FACE);
        }
        this.glState.setFlipSided( material.getSide() == Constants.FACE_BACK_SIDE);

        if ( material.isTransparent() ) {
            if(GLRenderer.DEBUG)Log.e(TAG,"material is Transparent");
            this.glState.setBlending(
                    material.getBlending(),
                    material.getBlendEquation(),
                    material.getBlendSrc(),
                    material.getBlendDst(),
                    material.getBlendEquationAlpha(),
                    material.getBlendSrcAlpha(),
                    material.getBlendDstAlpha(),
                    material.isPremultipliedAlpha()
            );

        } else {
            if(GLRenderer.DEBUG)Log.e(TAG,"material is NOT Transparent");
            this.glState.setBlending(
                    Constants.NO_BLENDING,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    false
            );

        }

        this.glState.setDepthFunc(material.getDepthFunc());
        this.glState.setDepthTest(material.isDepthTest());
        this.glState.setDepthMask(material.isDepthWrite());
        this.glState.setColorMask(material.isColorWrite());
        this.glState.setPolygonOffset(material.isPolygonOffset(), material.getPolygonOffsetFactor(), material.getPolygonOffsetUnits());
    }

    public void clearBufferState(){
        this.clearBufferState(true,true,true);
    }
    public void clearBufferState(boolean color,boolean depth, boolean stencil){
        this.glState.clear(color,depth,stencil);
    }
    public void clearColor(){
        this.clearColor(0f,0f,0f,0f);
    }
    public void clearColor(float r,float g,float b, float a){
        this.glState.clearColor(r,g,b,a);
    }

    private void disableEnabledAttributes() {

        java.util.Iterator<Integer> iterator = this.enabledAttributes.iterator();
        int attrib;
        while(iterator.hasNext()){
            attrib = iterator.next();
            // Disable vertex array
            GLES20.glDisableVertexAttribArray(attrib);
        }
    }

    private void setupVertexAttributes(Material mat, Geometry geometry) {

        java.util.Set<String> keys = geometry.getBuffers().getKeys();
        java.util.Iterator<String> iteratorKeys = keys.iterator();
        String key;
        GLAttribute glAttributeProgram;
        BufferAttribute bufferAttribute;
        while(iteratorKeys.hasNext()){
            key = iteratorKeys.next();
            if(key.equals("indices"))continue;
            bufferAttribute = (BufferAttribute) geometry.getBuffers().get(key);
            if(bufferAttribute!=null){
                glAttributeProgram = mat.getGlProgram().getAttribute(key);
                if(glAttributeProgram!=null && glAttributeProgram.getGlLocation()>=0){
                    if(GLRenderer.DEBUG){
                        Log.e(TAG,"Bind Attribute ["+key+"]");
                        Log.e(TAG,"Vertices(size="+bufferAttribute.getFloatArray().length+") = "+ Util.print(bufferAttribute.getFloatArray()));
                    }
                    GLES20.glEnableVertexAttribArray(glAttributeProgram.getGlLocation());
                    GLES20.glVertexAttribPointer(
                            glAttributeProgram.getGlLocation(),
                            bufferAttribute.getCoordsPerData(),
                            bufferAttribute.getGlType(),
                            bufferAttribute.isNormalized(),
                            bufferAttribute.getStride(),
                            bufferAttribute.getBuffer()
                    );
                    this.enabledAttributes.add(glAttributeProgram.getGlLocation());
                }
            }
        }
    }

    private void projectObjects(Object3D object, Camera camera) throws  Exception{
        IMaterial material = null;
        Material mat;
        Mesh mesh = null;
        if (!object.visible) return;
        if (object instanceof Mesh) {
            mesh = (Mesh) object;
            mesh.getGeometry().buildAttributes();
            if(GLRenderer.DEBUG)Log.e(TAG,"Project Mesh");
            mesh.getModelViewMatrix().multiplyMatrices(camera.getMatrixWorldInverse(), mesh.getMatrixWorld());
            mesh.getNormalMatrix().getNormalMatrix(mesh.getModelViewMatrix());

            if (this.getFrustum().intersectsObject(object)) {
                if(GLRenderer.DEBUG)Log.e(TAG,"Frustum intersect Mesh");
                material = mesh.getMaterial();
                if (material.isVisible()) {
                    if(GLRenderer.DEBUG)Log.e(TAG,"material is visible");
                    if (this.isSortObjects()) {
                        if(GLRenderer.DEBUG)Log.e(TAG,"Render is Sortable");
                        this.getzIndex().setFromMatrixPosition(object.getMatrixWorld());
                        this.getzIndex().applyProjection(this.getProjectionScreenMatrix());
                    }

                    if ( !(material instanceof MultiMaterial) ){
                        if(GLRenderer.DEBUG)Log.e(TAG,"material NO is MultiMaterial");
                        this.addRenderItem(
                                mesh,
                                (Material)material,
                                this.getzIndex().getZ(),
                                null
                        );


                    }else{
                        //TODO
                        if(GLRenderer.DEBUG)Log.e(TAG,"material IS MultiMaterial");
                        MultiMaterial multiMaterial = (MultiMaterial)material;
                        java.util.LinkedList<RenderGroup> groups = mesh.getGeometry().getGroups();
                        java.util.Iterator<RenderGroup> iterator = groups.iterator();
                        RenderGroup renderGroup;
                        while(iterator.hasNext()){
                            renderGroup = iterator.next();
                            if(GLRenderer.DEBUG)Log.e(TAG,"get from "+renderGroup.toString() +", from materials(size="+multiMaterial.getMaterials().size()+")");
                            this.addRenderItem(
                                    mesh,
                                    multiMaterial.getMaterials().get(renderGroup.getMaterialIndex()),
                                    this.getzIndex().getZ(),
                                    renderGroup
                            );
                        }
                    }

                }else{
                    if(GLRenderer.DEBUG)Log.e(TAG,"material NO is visible");
                }
            }else{
                if(GLRenderer.DEBUG)Log.e(TAG,"Frustum NO intersect Mesh");
            }
        }else if(object instanceof Light){
            //TODO
        }else{

        }


        java.util.Iterator<Object3D> iterator = object.getChildren().iterator();
        Object3D obj;
        while (iterator.hasNext()) {
            obj = iterator.next();
            if(obj.isMarkedtoRemove()){
                object.remove(obj);
            }else{
                this.projectObjects(obj,camera);
            }
        }
    }

    private void addRenderItem(Mesh mesh, Material material, float z,RenderGroup group) {
        if(material.isTransparent()){
            this.transparentItems.add(new RenderItem(mesh,material,z,group));
        }else{
            this.opaqueItems.add(new RenderItem(mesh,material,z,group));
        }
    }

    public Matrix4 getProjectionScreenMatrix() {
        return projectionScreenMatrix;
    }

    public void setProjectionScreenMatrix(Matrix4 projectionScreenMatrix) {
        this.projectionScreenMatrix = projectionScreenMatrix;
    }

    public Frustum getFrustum() {
        return frustum;
    }

    public void setFrustum(Frustum frustum) {
        this.frustum = frustum;
    }

    public boolean isSortObjects() {
        return sortObjects;
    }

    public void setSortObjects(boolean sortObjects) {
        this.sortObjects = sortObjects;
    }

    public Vector3 getzIndex() {
        return zIndex;
    }

    public void setzIndex(Vector3 zIndex) {
        this.zIndex = zIndex;
    }

    public Records getRecords() {
        return records;
    }

    public void setRecords(Records records) {
        this.records = records;
    }

    public float getPixelRatio() {
        return pixelRatio;
    }

    public void setPixelRatio(float pixelRatio) {
        this.pixelRatio = pixelRatio;
    }

    public void setViewport(int startW, int startH, int width, int height) {
        this.glState.setViewport(startW,startH,width,height);
    }

    public GLCapabilities getGlCapabilities() {
        return glCapabilities;
    }

    public void setGlCapabilities(GLCapabilities glCapabilities) {
        this.glCapabilities = glCapabilities;
    }
    public GLState getGlState(){
        return this.glState;
    }

    public java.util.TreeSet<Light> getSceneLights() {
        return sceneLights;
    }

    public void setSceneLights(java.util.TreeSet<Light> sceneLights) {
        this.sceneLights = sceneLights;
    }

    public boolean isUniformsLightsNeedsUpdate() {
        return uniformsLightsNeedsUpdate;
    }

    public void setUniformsLightsNeedsUpdate(boolean uniformsLightsNeedsUpdate) {
        this.uniformsLightsNeedsUpdate = uniformsLightsNeedsUpdate;
    }
}
