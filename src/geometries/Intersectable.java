package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.List;
import java.util.Objects;

public abstract class Intersectable {
    /**
     * @param ray=Ray object type
     * @return a list of intersection points between the ray and the geometry
     */
    public List<Point> findIntsersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * this class has been written because we want to know the specific geometry the ray cross it over
     * because we added the emission light for each geometry and if we want to calculate the color at the point
     * we have to mind the geometry's color (this class is PDS)
     */
    public static class GeoPoint {
        public final Geometry geometry;
        public final Point point;

        /**
         * constractor
         * @param geometry=geometry
         * @param point=point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && point.equals(geoPoint.point);
        }


        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }

    }
}