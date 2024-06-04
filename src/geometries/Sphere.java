package geometries;
import Primitives.Point;
import Primitives.Vector;
import Primitives.Ray;

import java.util.List;

import static Primitives.Util.alignZero;

/** sphere class is a polygon represented by a point and a radius*/
public class Sphere implements Geometry {

    /**
     * A point that represents the center of the sphere
     */
    final Point center;
    final double radius;

    /**
     * constructor
     *
     * @param center1 type point
     * @param radius  type double
     */
    public Sphere(Point center1, double radius) {
        center = center1;
        this.radius = radius;
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return The center point of the sphere.
     */
    public Point getCenter() {
        return center;
    }

    public Vector getNormal(Point p0) {
        return p0.subtract(center).normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" + "\ncenter=" + center + "\nradius=" + radius + "\n}";
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Point P0 = ray.getHead();        //get point of start ray
        Vector v = ray.getDirection();      //get dir of ray

        if (P0.equals(center)) {    //if start of ray equal to the sphere's center
            return List.of(center.add(v.scale(radius)));
        }

        Vector U = center.subtract(P0);

        double tm = alignZero(v.dotProduct(U));
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm));
        // no intersections : the ray direction is above the sphere
        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 > 0 && t2 > 0) {
            Point P1 = ray.getPoint(t1);
            Point P2 = ray.getPoint(t2);
            return List.of(P1, P2);
        }
        if (t1 > 0) {
            Point P1 = ray.getPoint(t1);
            return List.of(P1);
        }
        if (t2 > 0) {
            Point P2 = ray.getPoint(t2);
            return List.of(P2);
        }
        return null;

    }
}
