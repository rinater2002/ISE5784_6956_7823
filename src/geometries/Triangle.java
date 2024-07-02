package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Triangle extends Polygon
 */

public class Triangle extends Polygon {
    /**
     * Constructor to initialize Triangle based object with the values of three different points
     *
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }

    /**
     * Get Normal
     *
     * @return normal vector from the point to the Triangle
     */
    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }

    /**
     * findIntersections find intersections between the triangle to ray
     *
     * @param ray The Ray to intersect
     * @return list of point that intersections between the triangle to ray
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> result = plane.findGeoIntersections(ray);
        if (result == null) {
            return null;
        }
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);

        Vector v1 = p1.subtract(p0);// p0->p1
        Vector v2 = p2.subtract(p0);// p0->p2
        Vector v3 = p3.subtract(p0);//p0->p3

        double n1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(n1)) {
            return null;
        }

        double n2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(n2)) {
            return null;
        }

        double n3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(n3)) {
            return null;
        }


        if (!((n1 < 0 && n2 < 0 && n3 < 0) || (n1 > 0 && n2 > 0 && n3 > 0))) {
            return null;
        }
        return List.of(new GeoPoint(this, result.get(0).point));
    }
}









