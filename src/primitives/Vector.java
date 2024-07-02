package primitives;

public class Vector extends Point {

    /**
     * Constructor for Vector with individual coordinates
     *
     * @param x coordinate value for x axis
     * @param y coordinate value for y axis
     * @param z coordinate value for z axis
     * @throws IllegalArgumentException if the vector is zero
     */
    public Vector(double x, double y, double z) {
        super(x, y, z); // Call to the superclass constructor (Point)
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Cannot create a zero vector");
        }
    }

    /**
     * Constructor for Vector with a Double3 object
     *
     * @param xyz a Double3 object representing the coordinates
     * @throws IllegalArgumentException if the vector is zero
     */
    public Vector(Double3 xyz) {
        super(xyz); // Call to the superclass constructor (Point)
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Cannot create a zero vector");
        }
    }

    /**
     * Adds another vector to this vector
     *
     * @param v the vector to be added
     * @return a new vector which is the sum of this vector and the given vector
     */
    public Vector add(Vector v) {
        return new Vector(this.xyz.add(v.xyz)); // Vector addition
    }

    /**
     * Scales this vector by a scalar value
     *
     * @param scalar the scalar value to scale the vector
     * @return a new vector which is this vector scaled by the given scalar
     */
    public Vector scale(double scalar) {
        return new Vector(this.xyz.scale(scalar)); // Scalar multiplication
    }

    /**
     * Computes the dot product of this vector and another vector
     *
     * @param v the other vector
     * @return the dot product of the two vectors
     */
    public double dotProduct(Vector v) {
        return this.xyz.d1 * v.xyz.d1 + this.xyz.d2 * v.xyz.d2 + this.xyz.d3 * v.xyz.d3;
    }

    /**
     * Computes the cross product of this vector and another vector
     *
     * @param v the other vector
     * @return a new vector which is the cross product of this vector and the given vector
     */
    public Vector crossProduct(Vector v) {
        double x = this.xyz.d2 * v.xyz.d3 - this.xyz.d3 * v.xyz.d2;
        double y = this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3;
        double z = this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1;
        return new Vector(x, y, z);
    }

    /**
     * Computes the squared length of this vector
     *
     * @return the squared length of this vector
     */
    public double lengthSquared() {
        return this.dotProduct(this); // Length squared
    }

    /**
     * Computes the length of this vector
     *
     * @return the length of this vector
     */
    public double length() {
        return Math.sqrt(lengthSquared()); // Length
    }

    /**
     * Normalizes this vector
     *
     * @return a new vector which is the normalized version of this vector
     * @throws IllegalArgumentException if the vector is zero
     */
    public Vector normalize() {
        double length = length();
        return new Vector(xyz.reduce(length)); // Normalize vector
    }
}
