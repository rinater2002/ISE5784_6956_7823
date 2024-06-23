package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * Interface for geometric objects in a 3D space.
 * Provides a method to get the normal vector at a given point on the geometry.
 */
public interface Geometry extends Intersectable {

    /**
     * Calculates the normal vector to the geometry at the given point.
     *
     * @param point the point on the geometry where the normal is to be calculated
     * @return the normal vector to the geometry at the given point
     */
    Vector getNormal(Point point);
}
