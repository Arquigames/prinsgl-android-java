package com.arquigames.prinsgl.textures;


import android.graphics.Bitmap;

import com.arquigames.prinsgl.Constants;
import com.arquigames.prinsgl.materials.Material;
import com.arquigames.prinsgl.maths.MathUtils;
import com.arquigames.prinsgl.maths.vectors.Vector2;

import java.nio.Buffer;

/**
 * Created by usuario on 12/07/2016.
 */
public class Texture implements Cloneable{
    protected String uuid = "";
    protected String name="";
    protected String sourceFile = "";
    protected int resourceID = -1;

    protected android.graphics.Bitmap image = null;//Bitmap(Android),com.jogamp.opengl.util.texture.Texture(JOGL)

    protected int mapping;

    protected java.util.LinkedList<Integer> mipmaps = new java.util.LinkedList<Integer>();

    protected int wrapS;
    protected int wrapT;
    protected int magFilter;
    protected int minFilter;
    protected float anisotropy;
    protected int format;
    protected int type;
    protected Vector2 offset = new Vector2( 0, 0 );
    protected Vector2 repeat = new Vector2( 1, 1 );

    protected boolean generateMipmaps;
    protected boolean premultiplyAlpha;
    protected boolean flipY;
    protected int unpackAlignment; // valid values: 1, 2, 4, 8 (see http://www.khronos.org/opengles/sdk/docs/man/xhtml/glPixelStorei.xml)

    protected int version = 0;
    protected int lastVersion = -1;

    protected int bufferLocation = -1;

    protected Buffer buffer = null;
    protected java.util.TreeSet<String> materialsUUID;
    public Texture(Bitmap image){
        this();
        this.image = image;
    }
    public Texture(int resourceID){
        this();
        this.resourceID = resourceID;
    }
    public Texture(String sourceFile){
        this();
        this.sourceFile = sourceFile;
    }
    public Texture(){
        this.uuid = MathUtils.generateUUID();
        this.setMapping(Constants.DEFAULT_MAPPING);

        this.setWrapS(Constants.CLAMP_TO_EDGE_WRAPPING);
        this.setWrapT(Constants.CLAMP_TO_EDGE_WRAPPING);

        this.setMagFilter(Constants.LINEAR_FILTER);
        this.setMinFilter(Constants.LINEAR_FILTER);

        this.setAnisotropy(1);

        this.setFormat(Constants.RGBA_FORMAT);
        this.setType(Constants.UNSIGNED_BYTE_TYPE);

        this.setOffset(new Vector2( 0, 0 ));
        this.setRepeat(new Vector2( 1, 1 ));

        this.setGenerateMipmaps(true);
        this.setPremultiplyAlpha(false);
        this.setFlipY(true);
        this.setUnpackAlignment(4); // valid values: 1, 2, 4, 8 (see http://www.khronos.org/opengles/sdk/docs/man/xhtml/glPixelStorei.xml)

        this.setVersion(0);
        this.materialsUUID = new java.util.TreeSet<String>();
    }

    public Texture(

            android.graphics.Bitmap image,
            Integer mapping,
            Integer wrapS,
            Integer wrapT,
            Integer magFilter,
            Integer minFilter,
            Integer format,
            Integer type,
            Integer anisotropy
    ) {
        this.uuid = MathUtils.generateUUID();
        this.setImage(image);
        this.setMapping(mapping!=null ? mapping : Constants.DEFAULT_MAPPING);

        this.setWrapS(wrapS !=null ? wrapS : Constants.CLAMP_TO_EDGE_WRAPPING);
        this.setWrapT(wrapT !=null ? wrapT : Constants.CLAMP_TO_EDGE_WRAPPING);

        this.setMagFilter(magFilter !=null ? magFilter : Constants.LINEAR_FILTER);
        this.setMinFilter(minFilter !=null ? minFilter : Constants.LINEAR_MIPMAP_LINEAR_FILTER);

        this.setAnisotropy(anisotropy !=null ? anisotropy : 1);

        this.setFormat(format !=null ? format : Constants.RGBA_FORMAT);
        this.setType(type !=null ? type : Constants.UNSIGNED_BYTE_TYPE);

        this.setOffset(new Vector2( 0, 0 ));
        this.setRepeat(new Vector2( 1, 1 ));

        this.setGenerateMipmaps(true);
        this.setPremultiplyAlpha(false);
        this.setFlipY(true);
        this.setUnpackAlignment(4); // valid values: 1, 2, 4, 8 (see http://www.khronos.org/opengles/sdk/docs/man/xhtml/glPixelStorei.xml)

        this.setVersion(0);
    }
    public void needsUpdate(){
        this.setVersion(this.getVersion() + 1);
        if(this.version==100){
            this.version = 0;
        }
    }
    @Override
    public Texture clone(){
        Texture t = new Texture();
        t.copy(this);
        return t;
    }
    public Texture copy(Texture source){
        this.setSourceFile(source.getSourceFile());
        this.setResourceID(source.getResourceID());
        this.setImage(source.getImage());
        java.util.Iterator<Integer> iterator = source.getMipmaps().iterator();
        while(iterator.hasNext()){
            this.getMipmaps().add( iterator.next() );
        }
        this.setMapping(source.getMapping());

        this.setWrapS(source.getWrapS());
        this.setWrapT(source.getWrapT());

        this.setMagFilter(source.getMagFilter());
        this.setMinFilter(source.getMinFilter());

        this.setAnisotropy(source.getAnisotropy());

        this.setFormat(source.getFormat());
        this.setType(source.getType());

        this.getOffset().copy(source.getOffset());
        this.getRepeat().copy(source.getRepeat());

        this.setGenerateMipmaps(source.isGenerateMipmaps());
        this.setPremultiplyAlpha(source.isPremultiplyAlpha());
        this.setFlipY(source.isFlipY());
        this.setUnpackAlignment(source.getUnpackAlignment());

        return this;
    }
    public void transformUv(Vector2 uv){
        if ( this.getMapping() != Constants.UV_MAPPING)  return;

        uv.multiply(this.getRepeat() );
        uv.add(this.getOffset() );

        if ( uv.getX() < 0 || uv.getX() > 1 ) {

            switch (this.getWrapS()) {

                case Constants.REPEAT_WRAPPING:

                    uv.setX(uv.getX() - (float) Math.floor( uv.getX() ));
                    break;

                case Constants.CLAMP_TO_EDGE_WRAPPING:

                    uv.setX(uv.getX() < 0 ? 0 : 1);
                    break;

                case Constants.MIRRORED_REPEAT_WRAPPING:

                    if ( (float) Math.abs( (float) Math.floor( uv.getX() ) % 2 ) == 1 ) {

                        uv.setX((float) Math.ceil( uv.getX() ) - uv.getX());

                    } else {

                        uv.setX(uv.getX() - (float) Math.floor( uv.getX() ));

                    }
                    break;

            }

        }

        if ( uv.getY() < 0 || uv.getY() > 1 ) {

            switch (this.getWrapT()) {

                case Constants.REPEAT_WRAPPING:
                    uv.setY(uv.getY() - (float) Math.floor( uv.getY() ));
                    break;

                case Constants.CLAMP_TO_EDGE_WRAPPING:
                    uv.setY(uv.getY() < 0 ? 0 : 1);
                    break;

                case Constants.MIRRORED_REPEAT_WRAPPING:

                    if ( (float) Math.abs( (float) Math.floor( uv.getY() ) % 2 ) == 1 ) {
                        uv.setY((float) Math.ceil( uv.getY() ) - uv.getY());
                    } else {
                        uv.setY( uv.getY() - (float) Math.floor( uv.getY()) );
                    }
                    break;

            }

        }

        if (this.isFlipY()) {

            uv.setY(1 - uv.getY());

        }
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setImage(android.graphics.Bitmap image) {
        this.image = image;
    }

    public android.graphics.Bitmap getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public int getMapping() {
        return mapping;
    }

    public void setMapping(int mapping) {
        this.mapping = mapping;
    }

    public java.util.LinkedList<Integer> getMipmaps() {
        return mipmaps;
    }

    public void setMipmaps(java.util.LinkedList<Integer> mipmaps) {
        this.mipmaps = mipmaps;
    }

    public int getWrapS() {
        return wrapS;
    }

    public void setWrapS(int wrapS) {
        this.wrapS = wrapS;
    }

    public int getWrapT() {
        return wrapT;
    }

    public void setWrapT(int wrapT) {
        this.wrapT = wrapT;
    }

    public int getMagFilter() {
        return magFilter;
    }

    public void setMagFilter(int magFilter) {
        this.magFilter = magFilter;
    }

    public int getMinFilter() {
        return minFilter;
    }

    public void setMinFilter(int minFilter) {
        this.minFilter = minFilter;
    }

    public float getAnisotropy() {
        return anisotropy;
    }

    public void setAnisotropy(float anisotropy) {
        this.anisotropy = anisotropy;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Vector2 getOffset() {
        return offset;
    }

    public void setOffset(Vector2 offset) {
        this.offset = offset;
    }

    public Vector2 getRepeat() {
        return repeat;
    }

    public void setRepeat(Vector2 repeat) {
        this.repeat = repeat;
    }

    public boolean isGenerateMipmaps() {
        return generateMipmaps;
    }

    public void setGenerateMipmaps(boolean generateMipmaps) {
        this.generateMipmaps = generateMipmaps;
    }

    public boolean isPremultiplyAlpha() {
        return premultiplyAlpha;
    }

    public void setPremultiplyAlpha(boolean premultiplyAlpha) {
        this.premultiplyAlpha = premultiplyAlpha;
    }

    public boolean isFlipY() {
        return flipY;
    }

    public void setFlipY(boolean flipY) {
        this.flipY = flipY;
    }

    public int getUnpackAlignment() {
        return unpackAlignment;
    }

    public void setUnpackAlignment(int unpackAlignment) {
        this.unpackAlignment = unpackAlignment;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(int lastVersion) {
        this.lastVersion = lastVersion;
    }

    public int getBufferLocation() {
        return bufferLocation;
    }

    public void setBufferLocation(int bufferLocation) {
        this.bufferLocation = bufferLocation;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void attachMaterialUUID(Material material) {
        this.materialsUUID.add(material.getUuid());
    }
    public void removeMaterialUUID(Material material) {
        this.materialsUUID.remove(material.getUuid());
    }
    public int totalMaterialsUUIDAttached(){
        return this.materialsUUID.size();
    }
    public void dispose(){
        if(this.totalMaterialsUUIDAttached()==0){
            if(!this.image.isRecycled()){
                this.image.recycle();
            }
            this.mipmaps.clear();
            if(this.buffer!=null){
                this.buffer.clear();
            }
        }
    }
}

