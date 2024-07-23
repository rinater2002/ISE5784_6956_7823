package geometries;

import primitives.Point;
import primitives.Vector;

public abstract class RadialGeometry extends Geometry{

    final protected double radius;

    /**
     * parameters construcrtor
     * @param myRadius=radius
     */
    public RadialGeometry(double myRadius) {
        this.radius = myRadius;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
