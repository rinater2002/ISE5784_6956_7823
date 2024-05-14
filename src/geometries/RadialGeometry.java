package geometries;

import Primitives.Point;
import Primitives.Vector;

public abstract class RadialGeometry implements Geometry {
    protected double radius;

    /**
     * Constructor to initialize the radius of the radial geometry.
     *
     * @param radius the radius of the radial geometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

}
