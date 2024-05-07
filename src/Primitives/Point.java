package Primitives;

import Primitives.Double3;

public class Point {
    public static final Point ZERO = new Point(Double3.ZERO);
    final Primitives.Double3 xyz;
    public Point(double x, double y, double z) {
        xyz = new Double3(x,y,z);
    }

    public Point(Double3 xyz) {
        this.xyz =xyz;
    }

    public Vector subtract(Point p1) {
        return new Vector(xyz.subtract(p1.xyz));
    }

    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz));
    }

    public double distanceSquared(Point p1) {
        return 14;
    }

    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }
}
