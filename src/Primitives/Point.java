package Primitives;

import Primitives.Double3;

import static Primitives.Util.isZero;

public class Point {
    public static final Point ZERO = new Point(Double3.ZERO);
    final protected Primitives.Double3 xyz;
    public Point(double x, double y, double z) {
        xyz = new Double3(x,y,z);
    }

    public Point(Double3 xyz) {
        this.xyz =xyz;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }
    @Override
    public String toString() {
        return "Point{" +
                "x=" + xyz.d1 +
                ", y=" + xyz.d2 +
                ", z=" + xyz.d3 +
                '}';
    }
    public Vector subtract(Point p1) {
        return new Vector(xyz.subtract(p1.xyz));
    }

    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz));
    }

    public double distanceSquared(Point p1) {
        double dx = xyz.d1 - p1.xyz.d1;
        double dy = xyz.d2 - p1.xyz.d2;
        double dz = xyz.d3 - p1.xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }
}
