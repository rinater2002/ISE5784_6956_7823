package geometries;
import Primitives.Point;
import Primitives.Vector;

/** sphere class is a polygon represented by a point and a radius*/
public class Sphere {

    /** A point that represents the center of the sphere*/
    final Point center;

    /** The radius of the sphere */
    final double radius;
    /**
     * constructor
     * @param center1 type point
     * @param radius type double
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

    /**
     * Returns the normal vector to the sphere at a given point.
     * The normal is calculated as the normalized vector from the sphere's center to the given point.
     *
     * @param p0 The point on the sphere where the normal is to be calculated.
     * @return The normal vector to the sphere at the given point.
     */
    public Vector getNormal(Point p0) {
        return p0.subtract(center).normalize();    }

}
