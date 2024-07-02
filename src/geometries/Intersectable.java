package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The {@code Intersectable} interface defines a contract for geometric objects
 * that can be intersected by a ray. Implementing classes should provide
 * the method to find intersections between a given ray and the geometric object.
 */
public abstract class Intersectable {
    public List<Point> findIntersections(Ray ray)
    {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .toList();
    }
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);

    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    public static class GeoPoint {
        public Geometry geometry;
        public Point point;
        /**
     *constructor
     */
    public GeoPoint(Geometry geometry, Point point) {
        this.geometry = geometry;
        this.point = point;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GeoPoint other = (GeoPoint)obj;
        boolean sameGeometryType = geometry.getClass().equals(other.geometry.getClass());
        return sameGeometryType && point.equals(other.point);
    }
    @Override
    public String toString() {
        return "GeoPoint{" +
                "geometry=" + geometry +
                ", point=" + point +
                '}';
    }

    };
}
