package geometries;

import Primitives.Point;
import Primitives.Vector;

public class Plane implements Geometry {

    protected Point q;
    protected Vector normal;

    public Plane(Point v0, Point v1, Point v2) {
        this.normal= null;
    }
    public Plane(Point point, Vector normal) {
        this.q = point;
        this.normal = normal.normalize();
    }
    public Vector getNormal() {
        return null;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
