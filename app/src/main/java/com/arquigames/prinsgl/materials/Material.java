package com.arquigames.prinsgl.materials;

import com.arquigames.prinsgl.Constants;
import com.arquigames.prinsgl.ObjectJSON;
import com.arquigames.prinsgl.gl.GLProgram;
import com.arquigames.prinsgl.gl.uniforms.GLUniform;
import com.arquigames.prinsgl.maths.Color;
import com.arquigames.prinsgl.maths.MathUtils;
import com.arquigames.prinsgl.meshes.Mesh;
import com.arquigames.prinsgl.textures.Texture;

public abstract class Material implements IMaterial{
	protected int id=0;
	public static int countMaterials = 0;
	protected String uuid = "";
	protected String name = "";
	protected boolean visible = true;
	protected boolean transparent = false;
	protected float opacity = 1f;

	protected boolean wireframe = false;
	protected float lineWidth = 1f;

	protected GLProgram glProgram = null;

	protected int blending = Constants.NORMAL_BLENDING;
	protected int side = Constants.FACE_FRONT_SIDE;
	protected int shading = Constants.SMOOTH_SHADING; // THREE.FlatShading, THREE.SmoothShading
	protected int vertexColors = Constants.NO_COLORS; // THREE.NoColors, THREE.VertexColors, THREE.FaceColors

	protected int blendSrc = Constants.SrcAlphaFactor;
	protected int blendDst = Constants.OneMinusSrcAlphaFactor;
	protected int blendEquation = Constants.ADD_EQUATION;
	protected int blendSrcAlpha = 0;
	protected int blendDstAlpha = 0;
	protected int blendEquationAlpha = 0;

	protected int depthFunc = Constants.LESS_EQUAL_DEPTH;
	protected boolean depthTest = true;
	protected boolean depthWrite = true;

	protected boolean clippingPlanes = false;
	protected boolean clipShadows = false;

	protected boolean colorWrite = true;

	protected boolean polygonOffset = false;
	protected int polygonOffsetFactor = 0;
	protected int polygonOffsetUnits = 0;

	protected int alphaTest = 0;
	protected boolean premultipliedAlpha = false;

	protected Color diffuseColor;
	protected Color emissiveColor;
	protected Color specularColor;
	protected Color ambientColor;


	protected ObjectJSON uniforms;

	protected Texture map;

	protected java.util.TreeSet<String> meshesUUID;

	protected boolean morphTargets = false;

	public Material(){
		this.setId(Material.countMaterials++);
		this.setUuid(MathUtils.generateUUID());
		this.setDiffuseColor(new Color(0f,0f,0f));
		this.setEmissiveColor(new Color(0.06f,0.06f,0.06f));
		this.setSpecularColor(new Color(1f,1f,1f));
		this.setAmbientColor(new Color(1f,1f,1f));
		this.setMap(null);
		this.setUniforms(new ObjectJSON());
		this.meshesUUID = new java.util.TreeSet<String>();
	}
	public abstract void buildUniforms();
	@Override
	public int getId(){
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isTransparent() {
		return this.transparent;
	}

	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
		if(this.uniforms!=null){
			GLUniform glUniform = (GLUniform)this.uniforms.get("matOpacity");
			if(glUniform!=null)glUniform.setValue(this.opacity);
		}
	}

	public GLProgram getProgram() {
		return glProgram;
	}

	public void setProgram(GLProgram glProgram) {
		this.glProgram = glProgram;
	}

	public GLProgram getGlProgram() {
		return glProgram;
	}

	public void setGlProgram(GLProgram glProgram) {
		this.glProgram = glProgram;
	}

	public boolean isWireframe() {
		return wireframe;
	}

	public void setWireframe(boolean wireframe) {
		this.wireframe = wireframe;
	}

	public float getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}

	public int getSide() {
		return side;
	}

	public int getBlending() {
		return blending;
	}

	public void setBlending(int blending) {
		this.blending = blending;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public int getShading() {
		return shading;
	}

	public void setShading(int shading) {
		this.shading = shading;
	}

	public int getVertexColors() {
		return vertexColors;
	}

	public void setVertexColors(int vertexColors) {
		this.vertexColors = vertexColors;
	}

	public int getBlendSrc() {
		return blendSrc;
	}

	public void setBlendSrc(int blendSrc) {
		this.blendSrc = blendSrc;
	}

	public int getBlendDst() {
		return blendDst;
	}

	public void setBlendDst(int blendDst) {
		this.blendDst = blendDst;
	}

	public int getBlendEquation() {
		return blendEquation;
	}

	public void setBlendEquation(int blendEquation) {
		this.blendEquation = blendEquation;
	}

	public int getBlendSrcAlpha() {
		return blendSrcAlpha;
	}

	public void setBlendSrcAlpha(int blendSrcAlpha) {
		this.blendSrcAlpha = blendSrcAlpha;
	}

	public int getBlendEquationAlpha() {
		return blendEquationAlpha;
	}

	public void setBlendEquationAlpha(int blendEquationAlpha) {
		this.blendEquationAlpha = blendEquationAlpha;
	}

	public int getDepthFunc() {
		return depthFunc;
	}

	public void setDepthFunc(int depthFunc) {
		this.depthFunc = depthFunc;
	}

	public boolean isDepthTest() {
		return depthTest;
	}

	public void setDepthTest(boolean depthTest) {
		this.depthTest = depthTest;
	}

	public boolean isDepthWrite() {
		return depthWrite;
	}

	public void setDepthWrite(boolean depthWrite) {
		this.depthWrite = depthWrite;
	}

	public boolean isClippingPlanes() {
		return clippingPlanes;
	}

	public void setClippingPlanes(boolean clippingPlanes) {
		this.clippingPlanes = clippingPlanes;
	}

	public boolean isClipShadows() {
		return clipShadows;
	}

	public void setClipShadows(boolean clipShadows) {
		this.clipShadows = clipShadows;
	}

	public boolean isColorWrite() {
		return colorWrite;
	}

	public void setColorWrite(boolean colorWrite) {
		this.colorWrite = colorWrite;
	}

	public boolean isPolygonOffset() {
		return polygonOffset;
	}

	public void setPolygonOffset(boolean polygonOffset) {
		this.polygonOffset = polygonOffset;
	}

	public int getPolygonOffsetFactor() {
		return polygonOffsetFactor;
	}

	public void setPolygonOffsetFactor(int polygonOffsetFactor) {
		this.polygonOffsetFactor = polygonOffsetFactor;
	}

	public int getPolygonOffsetUnits() {
		return polygonOffsetUnits;
	}

	public void setPolygonOffsetUnits(int polygonOffsetUnits) {
		this.polygonOffsetUnits = polygonOffsetUnits;
	}

	public int getAlphaTest() {
		return alphaTest;
	}

	public void setAlphaTest(int alphaTest) {
		this.alphaTest = alphaTest;
	}

	public boolean isPremultipliedAlpha() {
		return premultipliedAlpha;
	}

	public void setPremultipliedAlpha(boolean premultipliedAlpha) {
		this.premultipliedAlpha = premultipliedAlpha;
	}

	public int getBlendDstAlpha() {
		return blendDstAlpha;
	}

	public void setBlendDstAlpha(int blendDstAlpha) {
		this.blendDstAlpha = blendDstAlpha;
	}

	public Color getDiffuseColor() {
		return diffuseColor;
	}

	public void setDiffuseColor(Color diffuseColor) {
		this.diffuseColor = diffuseColor;
	}

	public Color getEmissiveColor() {
		return emissiveColor;
	}

	public void setEmissiveColor(Color emissiveColor) {
		this.emissiveColor = emissiveColor;
	}

	public Color getSpecularColor() {
		return specularColor;
	}

	public void setSpecularColor(Color specularColor) {
		this.specularColor = specularColor;
	}

	public Color getAmbientColor() {
		return ambientColor;
	}

	public void setAmbientColor(Color ambientColor) {
		this.ambientColor = ambientColor;
	}

	public Texture getMap() {
		return map;
	}

	public void setMap(Texture map) {
		this.map = map;
		if(this.map!=null)this.map.attachMaterialUUID(this);
	}

	public ObjectJSON getUniforms() {
		return this.uniforms;
	}

	public void setUniforms(ObjectJSON uniforms) {
		this.uniforms = uniforms;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}



	public String toString(){
		return "diffuseColor="+this.diffuseColor.toString()+
				",emissiveColor="+this.emissiveColor.toString()+
				",specularColor="+this.specularColor.toString()+
				",ambientColor="+this.ambientColor.toString()+
				",wireframe="+(this.wireframe ? "true":"false")+
				",lineWidth="+this.lineWidth;
	}

	public void attachMeshID(Mesh mesh){
		this.meshesUUID.add(mesh.getUuid());
	}
	public void removeMeshID(Mesh mesh){
		this.meshesUUID.remove(mesh.getUuid());
	}
	public int totalMeshesUUIDAttached(){
		return this.meshesUUID.size();
	}
	public void dispose(){
		if(this.totalMeshesUUIDAttached()==0){
			if(this.glProgram!=null){
				this.glProgram.dispose();
			}
			this.uniforms.clear();
			this.map.removeMaterialUUID(this);
			this.map.dispose();
		}
	}

	public boolean isMorphTargets() {
		return morphTargets;
	}

	public void setMorphTargets(boolean morphTargets) {
		this.morphTargets = morphTargets;
	}
}