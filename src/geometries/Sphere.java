package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Sphere extends RadialGeometry {
    private final Point center;

    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }


    @Override
    public Vector getNormal(Point MyPoint) {
        return MyPoint.subtract(center).normalize();
    }

    /**
     * @param ray=Ray object type
     * @return a list of intersection points between the ray and the geometry
     */




    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getHead(); // ray's starting point
        Point O = this.center; //the sphere's center point
        Vector V = ray.getDirection(); // "the v vector" from the presentation

        // if p0 on center, calculate with line parametric representation
        // the direction vector normalized.
        if (O.equals(p0)) {
            Point newPoint = p0.add(ray.getDirection().scale(this.radius));
            return List.of(new GeoPoint(this,newPoint));
        }

        Vector U = O.subtract(p0);
        double tm = V.dotProduct(U);
        double d = Math.sqrt(U.lengthSquared() - tm * tm);
        if (d >= this.radius) {
            return null;
        }

        double th = Math.sqrt(this.radius * this.radius - d * d);
        double t1 = tm - th;
        double t2 = tm + th;

        if (t1 > 0 && t2 > 0) {
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this,p1),new GeoPoint(this,p2));
        }

        if (t1 > 0) {
            Point p1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this,p1));
        }

        if (t2 > 0) {
            Point p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this,p2));
        }
        return null;
    }
}



