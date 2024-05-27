package geometries;
import Primitives.Point;
import Primitives.Vector;

/** sphere class is a polygon represented by a point and a radius*/
public class Sphere {

    /** A point that represents the center of the sphere*/
    final Point center ;
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

    public Vector getNormal(Point p0) {
        return p0.subtract(center).normalize();    }

    @Override
    public String toString() {
        return "Sphere{" + "\ncenter=" + center + "\nradius=" + radius + "\n}";
    }
}
