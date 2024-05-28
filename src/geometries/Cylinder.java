package geometries;
import Primitives.*;

public class Cylinder extends Tube{
    public double height;

    /**
     * Constructor for Cylinder
     *
     * @param radius the radius of the cylinder
     * @param ray the central axis of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(double radius,Ray ray, double height) {
        super(radius, ray);
        if (Util.alignZero(height) <= 0) {
            throw new IllegalArgumentException("Hight must be greater than zero");
        }
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p0) {
        // Check that surface point is different from head of axisRay to avoid creating
        // a zero vector
        if (p0.equals(ray.getHead()))
            return ray.getDirection().normalize().scale(-1);
        // Finding the nearest point to the given point that is on the axis ray
        double t = ray.getDirection().dotProduct(p0.subtract(ray.getHead()));
        // Finds out if surface point is on a base and returns a normal appropriately
        if (t == 0)
            return ray.getDirection().normalize().scale(-1);
        if (t == height)
            return ray.getDirection().normalize();
        // If surface point is on the side of the cylinder, the superclass (Tube) is
        // used to find the normal
        return super.getNormal(p0);
    }
}
