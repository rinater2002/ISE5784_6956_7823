package geometries;
import Primitives.Point;
import Primitives.Ray;
import Primitives.Vector;

import static Primitives.Util.isZero;

public class Tube {
    public double radius;
    final Ray ray;

    /**
     * constructor
     * @param radius type double
     * @param ray1 type ray
     */

    public Tube(double radius, Ray ray1) {

        this.radius = radius;
        ray = ray1;
    }

    public Vector getNormal(Point point) {
        Point head = ray.getHead();
        Vector v = ray.getDirection();
        Vector head_p = point.subtract(head);

        double w = v.dotProduct(head_p);

        if (isZero(w)) {
            return head_p;
        }

        Point startP = head.add(v.scale(w));
        Vector n = point.subtract(startP);
        return n.normalize();
    }

}
