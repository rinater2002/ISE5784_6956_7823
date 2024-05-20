package geometries;
import Primitives.Point;
import Primitives.Vector;

public class Sphere {
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

}
