package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
import java.util.List;

public class Plane extends Geometry{
    Point q;
    Vector normal;


    /**
     * parameters constructors
     * @param myq=q
     * @param myNormal=normal
     */
    public Plane(Point myq, Vector myNormal) {
        this.q = myq;
        this.normal = myNormal.normalize();
    }



    /**
     * parameters constructors
     * @param point1 point number 1
     * @param point2 point number 2
     * @param point3 point number 3
     */
    public Plane (Point point1,Point point2,Point point3){
        Vector v1=point2.subtract(point1);
        Vector v2=point3.subtract(point1);
        normal =v1.crossProduct(v2).normalize();
        q=point1;
    }

    /**
     * @return normal
     */
    Vector getNormal(){
        return normal;
    }

    public Vector getNormal(Point point) {return normal;}

    /**
     * @param ray=Ray object type
     * @return a list of intersection points between the ray and the geometry
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point P0 = ray.getHead(); // according to the illustration P0 is the same point of the ray's P0 (that's why the definition))
        Vector v = ray.getDirection(); // according to the illustration v is the same vector of the ray's vector (that's why the definition))

        if (this.q.equals(P0)) { // if the ray starting from the plane it doesn't cut the plane at all
            return null; // so return null
        }

        Vector n = this.normal; // the normal to the plane

        double nv = n.dotProduct(v); // the formula's denominator of "t" (t =(n*(Q-P0))/nv)

        // ray is lying on the plane axis
        if (isZero(nv)) { // can't divide by zero (nv is the denominator)
            return null;
        }

        Vector Q0_P0 = this.q.subtract(P0);
        double nP0Q0 = alignZero(n.dotProduct(Q0_P0));

        // t should be bigger than 0
        if (isZero(nP0Q0)) {
            return null;
        }

        double t = alignZero(nP0Q0 / nv);

        // t should be bigger than 0
        if (t <= 0) {
            return null;
        }

        // "this" - the specific geometry, "rey.getPoint(t)" - the point that the ray
        // cross the geometry
        return List.of(new GeoPoint(this, ray.getPoint(t)));
    }
}
