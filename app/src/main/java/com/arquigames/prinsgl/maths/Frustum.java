package com.arquigames.prinsgl.maths;


import com.arquigames.prinsgl.Object3D;
import com.arquigames.prinsgl.boxes.Box3;
import com.arquigames.prinsgl.geometries.Geometry;
import com.arquigames.prinsgl.maths.matrix.Matrix4;
import com.arquigames.prinsgl.meshes.Mesh;
import com.arquigames.prinsgl.planes.Plane;
import com.arquigames.prinsgl.spheres.Sphere;
import com.arquigames.prinsgl.maths.vectors.Vector3;

/**
 * Created by usuario on 27/06/2016.
 */
public class Frustum {
    private Plane[] planes;
    public Frustum(){
        this.planes = new Plane[]{
                new Plane(),
                new Plane(),
                new Plane(),
                new Plane(),
                new Plane(),
                new Plane()
        };
    }
    public Frustum(Plane[] planes){
        this.set(planes);
    }
    public Frustum(
            Plane p0,
            Plane p1,
            Plane p2,
            Plane p3,
            Plane p4,
            Plane p5
    ){
        this.getPlanes()[0].copy(p0);
        this.getPlanes()[1].copy(p1);
        this.getPlanes()[2].copy(p2);
        this.getPlanes()[3].copy(p3);
        this.getPlanes()[4].copy(p4);
        this.getPlanes()[5].copy(p5);
    }
    public Frustum set(
            Plane p0,
            Plane p1,
            Plane p2,
            Plane p3,
            Plane p4,
            Plane p5
    ){
        this.getPlanes()[0].copy(p0);
        this.getPlanes()[1].copy(p1);
        this.getPlanes()[2].copy(p2);
        this.getPlanes()[3].copy(p3);
        this.getPlanes()[4].copy(p4);
        this.getPlanes()[5].copy(p5);

        return this;
    }
    public Frustum set(Plane[] planes){
        for(int i=0; i<this.getPlanes().length;i++){
            this.getPlanes()[i].copy(planes[i]);
        }
        return this;
    }
    public Frustum clone(){
        return new Frustum(this.getPlanes());
    }
    public void setPlanes(Plane[] planes) {
        this.set(planes);
    }
    public Plane[] getPlanes() {
        return planes;
    }
    public Frustum copy(Frustum frustum){
        Plane[] planes = frustum.getPlanes();
        this.set(planes);
        return this;
    }
    public Frustum setFromMatrix(Matrix4 m){
        Plane[] planes = this.planes;
        float[] me = m.getElements();
        float me0 = me[ 0 ], me1 = me[ 1 ], me2 = me[ 2 ], me3 = me[ 3 ];
        float me4 = me[ 4 ], me5 = me[ 5 ], me6 = me[ 6 ], me7 = me[ 7 ];
        float me8 = me[ 8 ], me9 = me[ 9 ], me10 = me[ 10 ], me11 = me[ 11 ];
        float me12 = me[ 12 ], me13 = me[ 13 ], me14 = me[ 14 ], me15 = me[ 15 ];

        planes[ 0 ].setComponents( me3 - me0, me7 - me4, me11 - me8, me15 - me12 ).normalize();
        planes[ 1 ].setComponents( me3 + me0, me7 + me4, me11 + me8, me15 + me12 ).normalize();
        planes[ 2 ].setComponents( me3 + me1, me7 + me5, me11 + me9, me15 + me13 ).normalize();
        planes[ 3 ].setComponents( me3 - me1, me7 - me5, me11 - me9, me15 - me13 ).normalize();
        planes[ 4 ].setComponents( me3 - me2, me7 - me6, me11 - me10, me15 - me14 ).normalize();
        planes[ 5 ].setComponents( me3 + me2, me7 + me6, me11 + me10, me15 + me14 ).normalize();

        return this;
    }
    public boolean intersectsObject(Object3D object){
        Mesh mesh = null;
        if(object instanceof Mesh){
            mesh = (Mesh)object;
            Sphere sphere = new Sphere();
            Geometry geometry = mesh.getGeometry();

            if ( geometry.getBoundingSphere() == null ) geometry.computeBoundingSphere();

            sphere.copy( geometry.getBoundingSphere() );
            sphere.applyMatrix4( object.getMatrixWorld() );

            return this.intersectsSphere( sphere );
        }
        return false;

    }
    public boolean intersectsSphere(Sphere sphere){
        Plane[] planes = this.planes;
        Vector3 center = sphere.getCenter();
        float negRadius = - sphere.getRadius();

        for ( int i = 0; i < 6; i ++ ) {

            float distance = planes[ i ].distanceToPoint( center );

            if ( distance < negRadius ) {

                return false;

            }

        }

        return true;
    }
    public boolean intersectsBox(Box3 box){
        Vector3 p1 = new Vector3(),
                p2 = new Vector3();
        Plane[] planes = this.planes;

        Vector3 box_min = box.getMin();
        Vector3 box_max = box.getMax();

        for ( int i = 0; i < 6 ; i ++ ) {

            Plane plane = planes[ i ];
            Vector3 normal = plane.getNormal();


            p1.setX( normal.getX()>0 ? box_min.getX() : box_max.getX());
            p2.setX( normal.getX()>0 ? box_max.getX() : box_min.getX());

            p1.setY( normal.getY()>0 ? box_min.getY() : box_max.getY());
            p2.setY( normal.getY()>0 ? box_max.getY() : box_min.getY());

            p1.setZ( normal.getZ()>0 ? box_min.getZ() : box_max.getZ());
            p2.setZ( normal.getZ()>0 ? box_max.getZ() : box_min.getZ());

            float d1 = plane.distanceToPoint( p1 );
            float d2 = plane.distanceToPoint( p2 );

            // if both outside plane, no intersection

            if ( d1 < 0 && d2 < 0 ) {

                return false;

            }

        }

        return true;
    }
    public boolean constainsPoint(Vector3 point){
        Plane[] planes = this.planes;
        for ( int i = 0; i < 6; i ++ ) {
            if ( planes[ i ].distanceToPoint( point ) < 0 ) {
                return false;
            }
        }
        return true;
    }
}
