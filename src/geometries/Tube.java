package geometries;
import Primitives.Point;
import Primitives.Ray;
import Primitives.Vector;

import static Primitives.Util.isZero;

/**
 * Tube class represents a tube in 3D space defined by a radius and a central axis ray.
 */
public class Tube {

    /** The radius of the tube */
    public double radius;

    /** The central axis ray of the tube */
    final Ray _ray;

    /**
     * constructor
     * @param radius type double
     * @param ray type ray
     */
    public Tube(double radius, Ray ray) {

        this.radius = radius;
        _ray = ray;
    }

    /**
     * Returns the normal vector to the tube at a given point.
     * The normal is calculated as the vector perpendicular to the tube's surface at the given point.
     *
     * @param point The point on the tube where the normal is to be calculated.
     * @return The normal vector to the tube at the given point.
     */
    public Vector getNormal(Point point) {
        Point head = _ray.getHead();
        Vector v = _ray.getDirection();
        Vector head_p = point.subtract(head);

        double w = v.dotProduct(head_p);

        if (isZero(w)) {
            return head_p.normalize();
        }

        Point startP = head.add(v.scale(w));
        Vector n = point.subtract(startP);
        return n.normalize();
    }

}
