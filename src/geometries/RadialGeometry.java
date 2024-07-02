package geometries;

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

    /**
     * getting the radius of geometry
     *
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }


}
