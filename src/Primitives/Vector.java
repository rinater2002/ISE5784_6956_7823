package Primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Cannot create a zero vector");
        }
    }

    public Vector(Double3 double3) {
        super(double3);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Cannot create a zero vector");
        }
    }

    // Vector addition
    public Vector add(Vector v) {
        return new Vector(this.xyz.add(v.xyz));
    }

    // Scalar multiplication
    public Vector scale(double scalar) {
        return new Vector(this.xyz.scale(scalar));
    }

    // Dot product
    public double dotProduct(Vector v) {
        return this.xyz.d1 * v.xyz.d1 + this.xyz.d2 * v.xyz.d2 + this.xyz.d3 * v.xyz.d3;
    }

    // Cross product
    public Vector crossProduct(Vector v) {
        double x = this.xyz.d2 * v.xyz.d3 - this.xyz.d3 * v.xyz.d2;
        double y = this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3;
        double z = this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1;
        return new Vector(x, y, z);
    }

    // Length squared
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    // Length
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    // Normalize vector
    public Vector normalize() {
        double length = length();
        if (length == 0) {
            throw new IllegalArgumentException("Cannot normalize a zero vector");
        }
        return this.scale(1 / length);
    }
}


