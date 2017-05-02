package com.arquigames.prinsgl.maths;

import com.arquigames.prinsgl.maths.vectors.Vector2;
import com.arquigames.prinsgl.maths.vectors.Vector3;

public class Face3{
	private short indexA=0;
	private short indexB=0;
	private short indexC=0;

	private int materialIndex = 0;

	private Vector2 uvA = null;
	private Vector2 uvB = null;
	private Vector2 uvC = null;

	private Color colorA = null;
	private Color colorB = null;
	private Color colorC = null;
	
	private Vector3 normalA = null;
	private Vector3 normalB = null;
	private Vector3 normalC = null;


	public Face3(){
		this((short)0,(short)0,(short)0);

	}
	public Face3(short _a,short _b,short _c){
		this.indexA = _a;
		this.indexB = _b;
		this.indexC = _c;

	}
	public Face3(short _a,short _b,short _c,int _matIndex){
		this(_a,_b,_c);
		this.materialIndex = _matIndex;
	} 
	public Face3(short _a,short _b,short _c,int _matIndex,Vector2 uvA,Vector2 uvB,Vector2 uvC){
		this(_a,_b,_c,_matIndex);
		this.uvA = uvA;
		this.uvB = uvB;
		this.uvC = uvC;  
	} 
	public Face3(short _a,short _b,short _c,int _matIndex,Color cA,Color cB,Color cC){
		this(_a,_b,_c,_matIndex); 
		
		this.colorA = cA;
		this.colorB = cB;
		this.colorC = cC;
	} 
	public Face3(short _a, short _b, short _c, int _matIndex, Vector3 nA, Vector3 nB, Vector3 nC){
		this(_a,_b,_c,_matIndex); 
		
		this.normalA = nA;
		this.normalB = nB;
		this.normalC = nC;
	} 
	public Face3(short _a,short _b,short _c,int _matIndex,Vector3 n){
		this(_a,_b,_c,_matIndex);  
		this.normalA = n.clone();
		this.normalB = n.clone();
		this.normalC = n.clone();
	}
	public Face3(
			short a,
			short b,
			short c,
			int materialIndex,
			Vector3 nA,
			Vector3 nB,
			Vector3 nC,
			Vector2 uvA,
			Vector2 uvB,
			Vector2 uvC

	){
		this(a,b,c,materialIndex);

		this.uvA = uvA;
		this.uvB = uvB;
		this.uvC = uvC;

		this.normalA = nA;
		this.normalB = nB;
		this.normalC = nC;
	}
	public Face3(
			short a,
			short b,
			short c,
			int materialIndex,
			Vector3 nA,
			Vector3 nB,
			Vector3 nC,
			Color cA,
			Color cB,
			Color cC
	){
		this(a,b,c,materialIndex);

		this.colorA = cA;
		this.colorB = cB;
		this.colorC = cC;

		this.normalA = nA;
		this.normalB = nB;
		this.normalC = nC;
	}
	public Face3(
			short a,
			short b,
			short c,
			int materialIndex,
			Vector3 nA,
			Vector3 nB,
			Vector3 nC,
			Vector2 uvA,
			Vector2 uvB,
			Vector2 uvC,
			Color cA,
			Color cB,
			Color cC
	){
		this(a,b,c,materialIndex);

		this.colorA = cA;
		this.colorB = cB;
		this.colorC = cC;

		this.normalA = nA;
		this.normalB = nB;
		this.normalC = nC;

		this.uvA = uvA;
		this.uvB = uvB;
		this.uvC = uvC;
	}
	public Face3(
		short _a,
		short _b,
		short _c,
		int _matIndex,
		Vector3 n,
		Vector2 uvA,
		Vector2 uvB,
		Vector2 uvC
	){
		this(_a,_b,_c,_matIndex);  
		this.normalA = n.clone();
		this.normalB = n.clone();
		this.normalC = n.clone();
		this.uvA = uvA;
		this.uvB = uvB;
		this.uvC = uvC;  
	}
	public void setNormal(Vector3 normal){
		this.normalA = normal.clone();
		this.normalB = normal.clone();
		this.normalC = normal.clone();
	}
	public Vector3 getNormal(){
		if(this.normalA!=null){
			return this.normalA.clone();
		}
		return null;
	}
	public void setIndexA(short val){
		this.indexA = val;
	}
	public void setIndexB(short val){
		this.indexB = val;
	}
	public void setIndexC(short val){
		this.indexC = val;
	}
	public void setNormalA(Vector3 val){
		this.normalA = val;
	}
	public void setNormalB(Vector3 val){
		this.normalB = val;
	}
	public void setNormalC(Vector3 val){
		this.normalC = val;
	}
	public void setMaterialIndex(int val){
		this.materialIndex = val;
	}
	public void setUv(String index,Vector2 uv){
		switch(index){
			case "a":
				this.setUvAtIndexA(uv);
			break;
			case "b":
				this.setUvAtIndexB(uv);
			break;
			case "c":
				this.setUvAtIndexC(uv);
			break;
		}
	}
	public void setColor(Color c){
		this.colorA = c.clone();
		this.colorB = c.clone();
		this.colorC = c.clone();
	}
	public void setColor(String index,Color c){
		switch(index){
			case "a":
				this.setColorAtIndexA(c);
			break;
			case "b":
				this.setColorAtIndexB(c);
			break;
			case "c":
				this.setColorAtIndexC(c);
			break;
		}
	}
	public void setUvAtIndexA(Vector2 uv){
		this.uvA = uv;
	}
	public void setUvAtIndexB(Vector2 uv){
		this.uvB = uv;
	}
	public void setUvAtIndexC(Vector2 uv){
		this.uvC = uv;
	}
	public void setColorAtIndexA(Color c){
		this.colorA = c;
	}
	public void setColorAtIndexB(Color c){
		this.colorB = c;
	}
	public void setColorAtIndexC(Color c){
		this.colorC = c;
	}
	public boolean hasUvs(){
		return this.uvA!=null && this.uvB!=null && this.uvC!=null ;
	}
	public boolean hasColors(){
		return this.colorA!=null && this.colorB!=null && this.colorC!=null ;
	}
	public boolean hasNormals(){
		return this.normalA!=null && this.normalB!=null && this.normalC!=null;
	}
	public void removeColor(){
		this.colorA = null;
		this.colorB = null;
		this.colorC = null;
	}
	public void removeUvs(){
		this.uvA = null;
		this.uvB = null;
		this.uvC = null;
	}


	public Face3 copy(Face3 face3){
		this.indexA = face3.getIndexA();
		this.indexB = face3.getIndexB();
		this.indexC = face3.getIndexC();
		this.materialIndex = face3.getMaterialIndex();
		this.uvA = face3.getUvA()!=null ? face3.getUvA().clone(): null;
		this.uvB = face3.getUvB()!=null ? face3.getUvB().clone(): null;
		this.uvC = face3.getUvC()!=null ? face3.getUvC().clone(): null;

		this.colorA = face3.getColorA()!=null ? face3.getColorA().clone(): null;
		this.colorB = face3.getColorB()!=null ? face3.getColorB().clone(): null;
		this.colorC = face3.getColorC()!=null ? face3.getColorC().clone(): null;

		this.normalA = face3.getNormalA()!=null ? face3.getNormalA().clone(): null;
		this.normalB = face3.getNormalB()!=null ? face3.getNormalB().clone(): null;
		this.normalC = face3.getNormalC()!=null ? face3.getNormalC().clone(): null;
		return this;
	}
	public Face3 clone(){
		Face3 face3 = new Face3();
		face3.copy(this);
		return face3;
	}

	public Color getColorA(){
		return this.colorA;
	}
	public Color getColorB(){
		return this.colorB;
	}
	public Color getColorC(){
		return this.colorC;
	}
	public Vector2 getUvA(){
		return this.uvA;
	}
	public Vector2 getUvB(){
		return this.uvB;
	}
	public Vector2 getUvC(){
		return this.uvC;
	}
	public short getIndexA(){
		return this.indexA;
	}
	public short getIndexB(){
		return this.indexB;
	}
	public short getIndexC(){
		return this.indexC;
	}
	public int getMaterialIndex(){
		return this.materialIndex;
	}

	public Vector3 getNormalA(){
		return this.normalA;
	}
	public Vector3 getNormalB(){
		return this.normalB;
	}
	public Vector3 getNormalC(){
		return this.normalC;
	}

	public String toString(){
		return "Face3("+
				"a="+this.indexA+
				",b="+this.indexB+
				",c="+this.indexC+
				",materialIndex="+this.materialIndex+
				",nA="+(this.normalA!=null ? this.normalA.toString():"NULL")+
				",nB="+(this.normalB!=null ? this.normalB.toString():"NULL")+
				",nC="+(this.normalC!=null ? this.normalC.toString():"NULL")+
				",uvA="+(this.uvA!=null ? this.uvA.toString():"NULL")+
				",uvB="+(this.uvB!=null ? this.uvB.toString():"NULL")+
				",uvC="+(this.uvC!=null ? this.uvC.toString():"NULL")+
				",cA="+(this.colorA!=null ? this.colorA.toString():"NULL")+
				",cB="+(this.colorB!=null ? this.colorB.toString():"NULL")+
				",cC="+(this.colorC!=null ? this.colorC.toString():"NULL")+")";
	}
}