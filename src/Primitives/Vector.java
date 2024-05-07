package Primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector(primitives.Double3 double3) {
        super(double3);
    }
}
