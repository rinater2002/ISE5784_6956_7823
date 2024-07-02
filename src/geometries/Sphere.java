package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The {@code Sphere} class represents a sphere in 3D space, defined by a center
 * point and a radius. This class implements the {@code Geometry} interface.
 */
public class Sphere implements Geometry {

    /**
     * The center point of the sphere.
     */
    final Point center;

    /**
     * The radius of the sphere.
     */
    final double radius;

    /**
     * Constructs a sphere with the specified center point and radius.
     *
     * @param center the center point of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return the center point of the sphere
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Calculates the normal vector to the sphere at a given point.
     *
     * @param p0 the point on the surface of the sphere
     * @return the normal vector to the sphere at the given point
     */
    public Vector getNormal(Point p0) {
        return p0.subtract(center).normalize();
    }

    /**
     * Returns a string representation of the sphere.
     *
     * @return a string representation of the sphere
     */
    @Override
    public String toString() {
        return "Sphere{" + "\ncenter=" + center + "\nradius=" + radius + "\n}";
    }

    /**
     * Finds all intersection points between a given ray and the sphere.
     *
     * @param ray the ray for which to find intersections
     * @return a list of intersection points, which may be empty if no intersections
     * are found; {@code null} if there are no intersections
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point P0 = ray.getHead();        // Get the start point of the ray
        Vector v = ray.getDirection();   // Get the direction of the ray

        if (P0.equals(center)) { // If the start of the ray is the sphere's center
            return List.of(center.add(v.scale(radius)));
        }

        Vector U = center.subtract(P0);
        double tm = alignZero(v.dotProduct(U));
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm));

        // No intersections: the ray direction is above the sphere
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
