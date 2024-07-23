package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
public class Geometries extends Intersectable{
    List<Intersectable> MyIntersectables= new LinkedList<Intersectable>();


    public Geometries() {

    }

    public Geometries(Intersectable...intersectables) {
        MyIntersectables = new LinkedList<Intersectable>();
        Collections.addAll(MyIntersectables,intersectables);
    }

    public void add(Intersectable... intersectables){
        Collections.addAll(MyIntersectables,intersectables);
    }

    @Override

    //=== find intersection point between a geometry (we know now) and the ray ===//
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        List<GeoPoint> intersection = null;

        for (Intersectable geometry : this.MyIntersectables) { // loop on all the geometry that implement "the Intersectables"
            // list of crossing point between the ray ana the geometry//
            var geoIntersections = geometry.findGeoIntersections(ray);
            if (geoIntersections != null) { // if there is a crossing
                if (intersection == null) {
                    intersection = new LinkedList<>();
                }
                intersection.addAll(geoIntersections);
            }
        }

        return intersection;
    }
}
