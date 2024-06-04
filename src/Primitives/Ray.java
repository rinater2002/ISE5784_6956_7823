package Primitives;

import static Primitives.Util.isZero;

public class Ray {
    private final Point head; // Starting point of the ray
    private final Vector direction; // Direction vector of the ray

    /**
     * Constructor for Ray
     * @param head The starting point of the ray
     * @param direction The direction vector of the ray
     * @throws IllegalArgumentException if the direction vector has zero length
     */
    public Ray(Point head, Vector direction) {
        this.head = head; // Initialize the head
        this.direction = direction.normalize(); // Normalize and initialize the direction
    }

    /**
     * Override equals method to compare two Ray objects
     * @param obj The object to compare with
     * @return true if the given object is equal to this Ray, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        // Check if the objects are the same
        if (this == obj) return true;
        // Check if the object is an instance of Ray
        if (!(obj instanceof Ray other)) return false;
        // Compare head and direction for equality
        return this.head.equals(other.head) && this.direction.equals(other.direction);
    }

    /**
     * Override toString method to provide a string representation of the Ray
     * @return String representation of the Ray
     */
    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }

    public Point getHead() {
        return head;
    }

    public Vector getDirection() {
        return direction;
    }
    public Point getPoint(double delta ) {
        if (isZero(delta)) {
            return head;
        }
        return head.add(direction.normalize().scale(delta));
    }
}
