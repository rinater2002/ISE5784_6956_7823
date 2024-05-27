package geometries;
import Primitives.Point;
import Primitives.Vector;

/** sphere class is a polygon represented by a point and a radius*/
public class Sphere {

    /** A point that represents the center of the sphere*/
    final Point _center ;
    final double radius;
    /**
     * constructor
     * @param center type point
     * @param radius type double
     */
    public Sphere(Point center, double radius) {
        _center = center;
        this.radius = radius;
    }
    /**
     * Returns the center point of the sphere.
     *
     * @return The center point of the sphere.
     */
    public Point getCenter() {
        return _center;
    }

    public Vector getNormal(Point p0) {
        return p0.subtract(_center).normalize();    }

    @Override
    public String toString() {
        return "Sphere{" + "\ncenter=" + _center + "\nradius=" + radius + "\n}";
    }
}
