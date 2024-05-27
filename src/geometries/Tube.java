package geometries;
import Primitives.Point;
import Primitives.Ray;
import Primitives.Vector;

import static Primitives.Util.isZero;

public class Tube {
    public double radius;
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

}
