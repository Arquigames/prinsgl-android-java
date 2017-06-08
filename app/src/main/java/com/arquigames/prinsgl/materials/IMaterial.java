package com.arquigames.prinsgl.materials;

public interface IMaterial extends Cloneable{


	int getId();
	boolean isVisible();
	void setVisible(boolean visible);
	IMaterial clone() throws CloneNotSupportedException;
}