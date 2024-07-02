package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The {@code Geometries} class represents a collection of geometric objects
 * that can be intersected by a ray. It implements the {@code Intersectable}
 * interface and aggregates multiple {@code Intersectable} objects.
 */
public class Geometries extends Intersectable {

    /** A list of geometries that can be intersected. */
    protected List<Intersectable> geometries = new LinkedList<Intersectable>();

    /**
     * Constructs a new {@code Geometries} object containing the specified
     * geometries.
     *
     * @param geometries the geometries to be added to this collection
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Adds the specified geometries to this collection.
     *
     * @param geometries the geometries to be added
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(this.geometries, geometries);
    }

    /**
     * Finds all intersection points between a given ray and all geometric
     * objects in this collection.
     *
     * @param ray the ray for which to find intersections
     * @return a list of intersection points, which may be empty if no
     * intersections are found; {@code null} if there are no geometries in
     * the collection
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (geometries.isEmpty()) {
            return null;                                         // if have no intersections
        }
        List<GeoPoint> result = null;
        for (var item : geometries) {                        // for all geometries in the list
            List<GeoPoint> itemList = item.findGeoIntersections(ray); // find intersections
            if (itemList != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(itemList);
            }
        }
        return result;

    }
}
