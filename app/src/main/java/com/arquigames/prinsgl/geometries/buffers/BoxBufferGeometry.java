package com.arquigames.prinsgl.geometries.buffers;

import com.arquigames.prinsgl.gl.attributes.BufferAttribute;
import com.arquigames.prinsgl.gl.renderer.RenderGroup;
import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 12/08/2016.
 */
public class BoxBufferGeometry extends BufferGeometry {

    protected float width;
    protected float height;
    protected float depth;
    protected int widthSegments;
    protected int heightSegments;
    protected int depthSegments;

    protected int vertexBufferOffset;
    protected int uvBufferOffset;
    protected int indexBufferOffset;
    protected int numberOfVertices;
    protected int groupStart;

    public BoxBufferGeometry(
            float width,
            float height,
            float depth,
            int widthSegments,
            int heightSegments,
            int depthSegments){
        super();
        this.width  = width<1   ? 1: width;
        this.height = height<1  ? 1: height;
        this.depth  = depth<1   ? 1: depth;
        this.widthSegments  = widthSegments<1   ? 1: widthSegments;
        this.heightSegments = heightSegments<1  ? 1: heightSegments;
        this.depthSegments  = depthSegments<1   ? 1: depthSegments;

        // these are used to calculate buffer length
        int vertexCount = this.calculateVertexCount( this.widthSegments, this.heightSegments, this.depthSegments );
        int indexCount = this.calculateIndexCount( this.widthSegments, this.heightSegments, this.depthSegments );

        // buffers
        this.indices = new short[indexCount];
        this.vertices = new float[vertexCount * 3] ;
        this.normals = new float[vertexCount * 3];
        this.uvs = new float[vertexCount * 2] ;

        // offset variables
        this.vertexBufferOffset = 0;
        this.uvBufferOffset = 0;
        this.indexBufferOffset = 0;
        this.numberOfVertices = 0;

        // group variables
        this.groupStart = 0;

        // build each side of the box geometry
        this.buildPlane( "z", "y", "x", - 1, - 1, this.depth, this.height,   this.width,  this.depthSegments, this.heightSegments, 0 ); // px
        this.buildPlane( "z", "y", "x",   1, - 1, this.depth, this.height, - this.width,  this.depthSegments, this.heightSegments, 1 ); // nx
        this.buildPlane( "x", "z", "y",   1,   1, this.width, this.depth,    this.height, this.widthSegments, this.depthSegments,  2 ); // py
        this.buildPlane( "x", "z", "y",   1, - 1, this.width, this.depth,  - this.height, this.widthSegments, this.depthSegments,  3 ); // ny
        this.buildPlane( "x", "y", "z",   1, - 1, this.width, this.height,   this.depth,  this.widthSegments, this.heightSegments, 4 ); // pz
        this.buildPlane( "x", "y", "z", - 1, - 1, this.width, this.height, - this.depth,  this.widthSegments, this.heightSegments, 5 ); // nz

        // build geometry
        this.setIndex( new BufferAttribute( this.indices, 1 ) );
        this.addAttribute( "position", new BufferAttribute( this.vertices, 3 ) );
        this.addAttribute( "normal", new BufferAttribute( this.normals, 3 ) );
        this.addAttribute( "uv", new BufferAttribute( this.uvs, 2 ) );

    }
    public BoxBufferGeometry(){
        this(0,0,0,0,0,0);
    }
    private int calculateVertexCount ( int w,int h,int d ) {

        int vertices = 0;

        // calculate the amount of vertices for each side (plane)
        vertices += (w + 1) * (h + 1) * 2; // xy
        vertices += (w + 1) * (d + 1) * 2; // xz
        vertices += (d + 1) * (h + 1) * 2; // zy

        return vertices;

    }

    private int calculateIndexCount (int w,int h,int d ) {

        int index = 0;

        // calculate the amount of squares for each side
        index += w * h * 2; // xy
        index += w * d * 2; // xz
        index += d * h * 2; // zy

        return index * 6; // two triangles per square => six vertices per square

    }
    private void buildPlane (String u,String v,String w,float udir,float vdir,float width,float height,float depth,int gridX,int gridY,int materialIndex ) {

        float segmentWidth	= width / gridX;
        float segmentHeight = height / gridY;

        float widthHalf = width / 2;
        float heightHalf = height / 2;
        float depthHalf = depth / 2;

        int gridX1 = gridX + 1;
        int gridY1 = gridY + 1;

        int vertexCounter = 0;
        int groupCount = 0;

        Vector3 vector = new Vector3();

        int iy,ix;

        // generate vertices, normals and uvs

        for ( iy = 0; iy < gridY1; iy ++ ) {

            float y = iy * segmentHeight - heightHalf;

            for ( ix = 0; ix < gridX1; ix ++ ) {

                float x = ix * segmentWidth - widthHalf;

                // set values to correct vector component
                vector.set(u,x * udir);
                vector.set(v,y * vdir);
                vector.set(w,depthHalf);

                // now apply vector to vertex buffer
                this.vertices[ this.vertexBufferOffset ] = vector.getX();
                this.vertices[ this.vertexBufferOffset + 1 ] = vector.getY();
                this.vertices[ this.vertexBufferOffset + 2 ] = vector.getZ();

                // set values to correct vector component
                vector.set(u,0);
                vector.set(v,0);
                vector.set(w,depth > 0 ? 1 : - 1);

                // now apply vector to normal buffer
                this.normals[ vertexBufferOffset ] = vector.getX();
                this.normals[ vertexBufferOffset + 1 ] = vector.getY();
                this.normals[ vertexBufferOffset + 2 ] = vector.getZ();

                // uvs
                this.uvs[ this.uvBufferOffset ] = ix / gridX;
                this.uvs[ this.uvBufferOffset + 1 ] = 1 - ( iy / gridY );

                // update offsets and counters
                this.vertexBufferOffset += 3;
                this.uvBufferOffset += 2;
                vertexCounter += 1;

            }

        }

        // 1. you need three indices to draw a single face
        // 2. a single segment consists of two faces
        // 3. so we need to generate six (2*3) indices per segment

        for ( iy = 0; iy < gridY; iy ++ ) {

            for ( ix = 0; ix < gridX; ix ++ ) {

                // indices
                short a = (short) (this.numberOfVertices + ix + gridX1 * iy);
                short b = (short) (this.numberOfVertices + ix + gridX1 * ( iy + 1 ));
                short c = (short) (this.numberOfVertices + ( ix + 1 ) + gridX1 * ( iy + 1 ));
                short d = (short) (this.numberOfVertices + ( ix + 1 ) + gridX1 * iy);

                // face one
                this.indices[ this.indexBufferOffset ] = a;
                this.indices[ this.indexBufferOffset + 1 ] = b;
                this.indices[ this.indexBufferOffset + 2 ] = d;

                // face two
                this.indices[ this.indexBufferOffset + 3 ] = b;
                this.indices[ this.indexBufferOffset + 4 ] = c;
                this.indices[ this.indexBufferOffset + 5 ] = d;

                // update offsets and counters
                this.indexBufferOffset += 6;
                groupCount += 6;

            }

        }

        // add a group to the geometry. this will ensure multi material support
        this.groups.add(new RenderGroup(this.groupStart, groupCount, materialIndex));

        // calculate new start value for groups
        this.groupStart += groupCount;

        // update total number of vertices
        this.numberOfVertices += vertexCounter;

    }
}
