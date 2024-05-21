package Primitives;

import Primitives.Double3;
import static Primitives.Util.isZero;

public class Point {
    public static final Point ZERO = new Point(Double3.ZERO); // A constant representing the origin point (0, 0, 0)
    final protected Primitives.Double3 xyz; // Coordinates of the point

    /**
     * Constructor for Point with individual coordinates
     * @param x coordinate value for x axis
     * @param y coordinate value for y axis
     * @param z coordinate value for z axis
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z); // Initialize xyz with given coordinates
    }

    /**
     * Constructor for Point with a Double3 object
     * @param xyz a Double3 object representing the coordinates
     */
    public Point(Double3 xyz) {
        this.xyz = xyz; // Initialize xyz with the given Double3 object
    }

    /**
     * Override equals method to compare two Point objects
     * @param obj The object to compare with
     * @return true if the given object is equal to this Point, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check if the objects are the same
        return (obj instanceof Point other) // Check if the object is an instance of Point
                && this.xyz.equals(other.xyz); // Compare xyz for equality
    }

    /**
     * Override toString method to provide a string representation of the Point
     * @return String representation of the Point
     */
    @Override
    public String toString() {
        return "Point{" +
                "x=" + xyz.d1 +
                ", y=" + xyz.d2 +
                ", z=" + xyz.d3 +
                '}'; // Return the string representation of the point
    }

    /**
     * Subtracts another point from this point to create a vector
     * @param p1 the point to subtract
     * @return a new vector which is the result of the subtraction
     */
    public Vector subtract(Point p1) {
        return new Vector(xyz.subtract(p1.xyz)); // Vector subtraction
    }

    /**
     * Adds a vector to this point to get a new point
     * @param v1 the vector to add
     * @return a new point which is the result of the addition
     */
    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz)); // Add a vector to a point
    }

    /**
     * Calculates the squared distance between this point and another point
     * @param p1 the other point
     * @return the squared distance between the two points
     */
    public double distanceSquared(Point p1) {
        double dx = xyz.d1 - p1.xyz.d1;
        double dy = xyz.d2 - p1.xyz.d2;
        double dz = xyz.d3 - p1.xyz.d3;
        return dx * dx + dy * dy + dz * dz; // Distance squared calculation
    }

    /**
     * Calculates the distance between this point and another point
     * @param p1 the other point
     * @return the distance between the two points
     */
    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1)); // Distance calculation
    }
}
