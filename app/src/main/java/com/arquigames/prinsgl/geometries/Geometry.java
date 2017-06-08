package com.arquigames.prinsgl.geometries;

import android.util.Log;

import com.arquigames.prinsgl.gl.attributes.BufferAttribute;
import com.arquigames.prinsgl.maths.Color;
import com.arquigames.prinsgl.maths.Face3;
import com.arquigames.prinsgl.GeometryUtils;
import com.arquigames.prinsgl.ObjectJSON;
import com.arquigames.prinsgl.gl.renderer.RenderGroup;
import com.arquigames.prinsgl.boxes.Box3;
import com.arquigames.prinsgl.morphing.MorphTarget;
import com.arquigames.prinsgl.spheres.Sphere;
import com.arquigames.prinsgl.maths.vectors.Vector2;
import com.arquigames.prinsgl.maths.vectors.Vector3;


public class Geometry implements Cloneable{

	private static String TAG = "Geometry";

	public static int geometryCounter = 0;

	protected int id = 0;

	protected java.util.LinkedList<Face3> faces;
	protected java.util.LinkedList<Vector3> vertices;

	protected java.util.LinkedList<MorphTarget> morphTargets;

	protected java.util.LinkedList<BufferAttribute> morphAttributes;

	protected boolean verticesNeedsUpdate 	= false;
	protected boolean indicesNeedsUpdate 	= false;
	protected boolean uvsNeedsUpdate 		= false;
	protected boolean colorsNeedsUpdate 	= false;
	protected boolean groupsNeedsUpdate 	= false;
	protected boolean normalsNeedsUpdate	= false;
	protected boolean morphTargetsNeedsUpdate	= false;


	protected Sphere boundingSphere = null;
	protected java.util.LinkedList<RenderGroup> groups;
 

	protected ObjectJSON buffers;

	public Geometry(){
		this.faces = new java.util.LinkedList<Face3>();
		this.vertices = new java.util.LinkedList<Vector3>();
		this.setGroups(new java.util.LinkedList<RenderGroup>());
		this.setBuffers(new ObjectJSON());
		this.setMorphTargets(new java.util.LinkedList<MorphTarget>());
		this.setId(Geometry.geometryCounter++);
		this.setMorphAttributes(new java.util.LinkedList<BufferAttribute>());
	}
	public java.util.LinkedList<Vector3> getVertices(){
		return this.vertices;
	}
	public java.util.LinkedList<Face3> getFaces(){
		return this.faces;
	}
	public boolean hasIndices(){
		return this.faces.size()>0;
	}
	public void addFace3(short a,short b,short c, int materialIndex){
		this.faces.add(new Face3(a,b,c,materialIndex));
		this.indicesNeedsUpdate = true;
		this.groupsNeedsUpdate = true;
	}
	public void addFace3(short a,short b,short c){
		this.addFace3(a,b,c,0);
	}
	public void addFace3Uv(short a, short b, short c, Vector2 uvA, Vector2 uvB, Vector2 uvC, int materialIndex){
		this.faces.add(new Face3(a,b,c,materialIndex,uvA,uvB,uvC));
		this.indicesNeedsUpdate = true;
		if(uvA!=null && uvB!=null && uvC!=null)this.uvsNeedsUpdate = true;
		this.groupsNeedsUpdate = true;
	}
	public void addFace3Color(short a, short b, short c, Color cA, Color cB, Color cC, int materialIndex){
		this.faces.add(new Face3(a,b,c,materialIndex,cA,cB,cC));
		this.indicesNeedsUpdate = true;
		if(cA!=null && cB!=null && cC!=null)this.colorsNeedsUpdate = true;
		this.groupsNeedsUpdate = true;
	}
	public void addFace3Normals(short a,short b,short c,Vector3 nA,Vector3 nB,Vector3 nC, int materialIndex){
		this.faces.add(new Face3(a,b,c,materialIndex,nA,nB,nC));
		this.indicesNeedsUpdate = true; 
		this.groupsNeedsUpdate = true;
		if(nA!=null && nB!=null && nC!=null)this.normalsNeedsUpdate = true;
	}
	public void addFace3Normal(short a,short b,short c,Vector3 n, int materialIndex){
		this.faces.add(new Face3(a,b,c,materialIndex,n));
		this.indicesNeedsUpdate = true; 
		this.groupsNeedsUpdate = true;
		if(n!=null)this.normalsNeedsUpdate = true;
	}
	public void addFace3NormalsUvs(
		short a,
		short b,
		short c,
		Vector3 nA,
		Vector3 nB,
		Vector3 nC, 
		Vector2 uvA,
		Vector2 uvB,
		Vector2 uvC, 
		int materialIndex
	){
		this.faces.add(
			new Face3(
				a,
				b,
				c,
				materialIndex,
				nA,
				nB,
				nC,
				uvA,
				uvB,
				uvC
			)
		);
		this.indicesNeedsUpdate = true; 
		this.groupsNeedsUpdate = true;
		if(nA!=null && nB!=null && nC!=null)this.normalsNeedsUpdate = true;
		if(uvA!=null && uvB!=null && uvC!=null)this.uvsNeedsUpdate = true;
	}
	public void addFace3NormalUvs(
		short a,
		short b,
		short c,
		Vector3 n,
		Vector2 uvA,
		Vector2 uvB,
		Vector2 uvC, 
		int materialIndex
	){
		this.faces.add(
			new Face3(
				a,
				b,
				c,
				materialIndex,
				n, 
				uvA,
				uvB,
				uvC
			)
		);
		this.indicesNeedsUpdate = true; 
		this.groupsNeedsUpdate = true;
		if(n!=null)this.normalsNeedsUpdate = true;
		if(uvA!=null && uvB!=null && uvC!=null)this.uvsNeedsUpdate = true;
	}
	public void addFace3NormalsColors(
		short a,
		short b,
		short c,
		Vector3 nA,
		Vector3 nB,
		Vector3 nC, 
		Color cA,
		Color cB,
		Color cC, 
		int materialIndex
	){
		this.faces.add(
			new Face3(
				a,
				b,
				c,
				materialIndex,
				nA,
				nB,
				nC,
				cA,
				cB,
				cC
			)
		);
		this.indicesNeedsUpdate = true; 
		this.groupsNeedsUpdate = true;
		if(nA!=null && nB!=null && nC!=null)this.normalsNeedsUpdate = true;
		if(cA!=null && cB!=null && cC!=null)this.colorsNeedsUpdate = true;
	}
	public void addFace3NormalsUvsColors(
		short a,
		short b,
		short c,
		Vector3 nA,
		Vector3 nB,
		Vector3 nC, 
		Vector2 uvA,
		Vector2 uvB,
		Vector2 uvC,
		Color cA,
		Color cB,
		Color cC,  
		int materialIndex
	){
		this.faces.add(
			new Face3(
				a,
				b,
				c,
				materialIndex,
				nA,
				nB,
				nC,
				uvA,
				uvB,
				uvC,
				cA,
				cB,
				cC
			)
		);
		this.indicesNeedsUpdate = true; 
		this.groupsNeedsUpdate = true;
		if(nA	!=null && nB	!=null && nC	!=null)this.normalsNeedsUpdate 	= true;
		if(uvA	!=null && uvB	!=null && uvC	!=null)this.uvsNeedsUpdate 		= true;
		if(cA	!=null && cB	!=null && cC	!=null)this.colorsNeedsUpdate 	= true;
	}
	public void addFace3(Face3 face3){
		this.faces.add(face3);
		if(face3.hasUvs())this.uvsNeedsUpdate = true;
		if(face3.hasColors())this.colorsNeedsUpdate = true;
		if(face3.hasNormals())this.normalsNeedsUpdate= true;
		this.indicesNeedsUpdate = true;
		this.groupsNeedsUpdate = true;
	}
	public void addVertex(float x,float y,float z){
		this.vertices.add(new Vector3(x,y,z));
		this.verticesNeedsUpdate = true;
	}
	public void addVertex(Vector3 v){
		this.vertices.add(v);
		this.verticesNeedsUpdate = true;
	} 

	public Sphere getBoundingSphere() {
		return boundingSphere;
	}

	public void setBoundingSphere(Sphere boundingSphere) {
		this.boundingSphere = boundingSphere;
	}

	public void computeBoundingSphere(){
		Box3 box = new Box3();
		Vector3 vector = new Vector3();
		if(this.getBoundingSphere() ==null){
			this.setBoundingSphere(new Sphere());
		}

		if(this.vertices!=null && this.vertices.size()>0){
			Vector3 center = this.getBoundingSphere().getCenter();
			box.setFromArray(this.vertices);
			box.center(center);

			float maxRadiusSq = 0f;
			for ( int i = 0, il = this.vertices.size(); i < il; i ++) {

				vector.fromArray( this.vertices, i );
				maxRadiusSq = Math.max( maxRadiusSq, center.distanceToSquared( vector ) );

			}
			this.getBoundingSphere().setRadius((float) Math.sqrt(maxRadiusSq));
		}
	}
	public void buildAttributes(){
		this.makeBuffers();
		this.computeGroups();
	}
	public void computeGroups() {

		if(this.groupsNeedsUpdate){
			this.getGroups().clear();
			if(this.faces.size()>0){
				java.util.Iterator<Face3> iteratorFaces = this.faces.iterator();
				Face3 face;
				RenderGroup group = null;
				int materialIndex = -1;
				int counter = 0;//iterate each 3 vertices
				while (iteratorFaces.hasNext()){
					face = iteratorFaces.next();
					if(face.getMaterialIndex()!=materialIndex){
						materialIndex = face.getMaterialIndex();
						if(group!=null){
							group.setCount(counter*3 - group.getStart());
							this.getGroups().add(group);
						}
						group = new RenderGroup();
						group.setStart(counter*3);
						group.setMaterialIndex(materialIndex);
					}
					counter++;
				}
				if(group!=null){
					group.setCount(counter*3 - group.getStart());
					this.getGroups().add(group);
				}
			}
			this.groupsNeedsUpdate = false;
		}
	}

	public boolean isVerticesNeedsUpdate() {
		return verticesNeedsUpdate;
	}

	public void setVerticesNeedsUpdate(boolean verticesNeedsUpdate) {
		this.verticesNeedsUpdate = verticesNeedsUpdate;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public ObjectJSON getBuffers() {
		return buffers;
	}

	public void setBuffers(ObjectJSON buffers) {
		this.buffers = buffers;
	}

	public void makeBuffers() {
		if(this.indicesNeedsUpdate){
			GeometryUtils.makeBuffersFromFace3(this);
			if(this.uvsNeedsUpdate)		this.uvsNeedsUpdate 		= false;
			if(this.colorsNeedsUpdate)	this.colorsNeedsUpdate 		= false;
			if(this.normalsNeedsUpdate)	this.normalsNeedsUpdate 	= false;
			this.indicesNeedsUpdate = false;
			this.verticesNeedsUpdate = false;
		}
		if(this.verticesNeedsUpdate && this.faces.size()<=0){
			BufferAttribute bufferAttribute;
			bufferAttribute = new BufferAttribute();
			bufferAttribute.setV3(this.vertices);
			this.buffers.put("position",bufferAttribute);
			this.verticesNeedsUpdate = false;
		}
	}
	public void computeFaceNormals(){
		Vector3 cb = new Vector3(), ab = new Vector3();

		java.util.Iterator<Face3> facesIterator = this.faces.iterator();
		Face3 face;
		Vector3 vA,vB,vC;
		while(facesIterator.hasNext()){
			face = facesIterator.next();
			if(!face.hasNormals() || this.normalsNeedsUpdate){
				vA = this.vertices.get( face.getIndexA() );
				vB = this.vertices.get( face.getIndexB() );
				vC = this.vertices.get( face.getIndexC() );

				cb.subVectors( vC, vB );
				ab.subVectors( vA, vB );
				cb.cross( ab );

				cb.normalize();

				face.setNormal(cb);
			}
		}
		this.normalsNeedsUpdate = false;
	}

	public Geometry copy(Geometry geometry){
		this.boundingSphere = geometry.getBoundingSphere()!=null ? geometry.getBoundingSphere().clone() : null;
		java.util.Iterator<Face3> iteratorFaces = geometry.getFaces().iterator();
		Face3 face3;
		while(iteratorFaces.hasNext()){
			face3 = iteratorFaces.next().clone();
			this.faces.add(face3);
			this.indicesNeedsUpdate = true;
			this.groupsNeedsUpdate = true;
			if(face3.hasUvs()){
				this.uvsNeedsUpdate = true;
			}
			if(face3.hasColors()){
				this.colorsNeedsUpdate = true;
			}
			if(face3.hasNormals()){
				this.normalsNeedsUpdate = true;
			}
		}
		java.util.Iterator<Vector3> iteratorVertices = geometry.getVertices().iterator();
		while(iteratorVertices.hasNext()){
			this.vertices.add(iteratorVertices.next().clone());
			this.verticesNeedsUpdate = true;
		}
		return this;
	}
	@Override
	public Geometry clone(){
		Log.e(TAG,"Cloning Geometry");
		Geometry geo = new Geometry();
		return geo.copy(this);
	}

	public java.util.LinkedList<RenderGroup> getGroups() {
		return groups;
	}

	public void setGroups(java.util.LinkedList<RenderGroup> groups) {
		this.groups = groups;
	}
	public boolean isUvsNeedsUpdate(){
		return this.uvsNeedsUpdate;
	}
	public boolean isColorsNeedsUpdate(){
		return this.colorsNeedsUpdate;
	}
	public boolean isNormalsNeedsUpdate(){
		return this.normalsNeedsUpdate;
	}

	public void setNormalsNeedsUpdate(boolean normalsNeedsUpdate) {
		this.normalsNeedsUpdate = normalsNeedsUpdate;
	}

	public java.util.LinkedList<MorphTarget> getMorphTargets() {
		return morphTargets;
	}

	public void setMorphTargets(java.util.LinkedList<MorphTarget> morphTargets) {
		this.morphTargets = morphTargets;
	}

	public boolean isMorphTargetsNeedsUpdate() {
		return morphTargetsNeedsUpdate;
	}

	public void setMorphTargetsNeedsUpdate(boolean morphTargetsNeedsUpdate) {
		this.morphTargetsNeedsUpdate = morphTargetsNeedsUpdate;
	}

	public void dispose() {

		this.faces.clear();
		this.vertices.clear();
		this.groups.clear();
		this.morphTargets.clear();
		this.buffers.clear();

	}

	public java.util.LinkedList<BufferAttribute> getMorphAttributes() {
		return morphAttributes;
	}

	public void setMorphAttributes(java.util.LinkedList<BufferAttribute> morphAttributes) {
		this.morphAttributes = morphAttributes;
	}
}