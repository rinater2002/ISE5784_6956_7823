package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * plane class is a polygon represented by a point and a vector
 */
public class Plane extends Geometry {

    /**
     * A point that is on the plane
     */
    protected Point q;

    /**
     * A vector that is vertical to the plane
     */
    protected Vector normal;

    /**
     * Constructor for Plane using three points.
     * @param v0 first point on the plane
     * @param v1 second point on the plane
     * @param v2 third point on the plane
     */
    public Plane(Point v0, Point v1, Point v2) {
        // Compute two vectors from the three points
        Vector v0v1 = v1.subtract(v0);
        Vector v0v2 = v2.subtract(v0);

        // Compute the cross product of these vectors to get the normal vector
        this.normal = v0v1.crossProduct(v0v2).normalize();

        // Set one of the points as the reference point on the plane
        this.q = v0;
    }

    /**
     * Constructor for Plane using a point and a normal vector.
     * @param point a point on the plane
     * @param normal the normal vector to the plane
     */
    public Plane(Point point, Vector normal) {
        this.q = point; // Set the reference point
        this.normal = normal.normalize(); // Normalize and set the normal vector
    }

    /**
     * getting the point that is on the plane
     *
     * @return the point
     */
    public Point getQ() {
        return q;
    }

    /**
     * Getter for the normal vector.
     * @return the normal vector of the plane
     */
    public Vector getNormal() {
        return this.normal; // Return the normal vector
    }

    /**
     * Getter for the normal vector at a specific point on the plane.
     * @param point a point on the plane
     * @return the normal vector of the plane
     */
    @Override
    public Vector getNormal(Point point) {
        return this.normal; // Return the normal vector (same for all points on the plane)
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        Point P0 = ray.getHead();
        Vector v = ray.getDirection();
        Vector n = normal;

        if (q.equals(P0)) {//if start of ray equal to q0
            return null;
        }
        Vector P0_Q0 = q.subtract(P0);
        //numerator
        double nP0Q0 = alignZero(n.dotProduct(P0_Q0));
        if (isZero(nP0Q0)) {
            return null;
        }
        //denominator
        double nv = alignZero(n.dotProduct(v));
        // ray is lying in the plane axis
        if (isZero(nv)) {
            return null;
        }
        double t = alignZero(nP0Q0 / nv);
        if (t <= 0) {
            return null;
        }
        Point point = P0.add(v.scale(t));
        return List.of(new GeoPoint(this, ray.getPoint(t)));

    }

    /**
     * Interface Geometry requires implementation of getNormal method.
     * This can be implemented later based on the requirements of the Geometry interface.
     */
}
