package geometries;

import primitives.Point;
import primitives.*;

import java.util.List;

/**
 * The {@code Intersectable} interface defines a contract for geometric objects
 * that can be intersected by a ray. Implementing classes should provide
 * the method to find intersections between a given ray and the geometric object.
 */
public interface Intersectable {

    /**
     * Finds all intersection points between a given ray and the geometric object
     * that implements this interface.
     *
     * @param ray the ray for which to find intersections
     * @return a list of intersection points, which may be empty if no intersections are found
     */
    List<Point> findIntersections(Ray ray);
}
