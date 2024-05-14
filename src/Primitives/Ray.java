package Primitives;

public class Ray {
    private final Point head;
    private final Vector direction;

    // Constructor
    public Ray(Point head, Vector direction) {
        if (direction.length() == 0) {
            throw new IllegalArgumentException("Direction vector cannot be zero");
        }
        this.head = head;
        this.direction = direction.normalize();
    }
    // Override equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Ray other)) return false;
        return this.head.equals(other.head) && this.direction.equals(other.direction);
    }

    // Override toString method
    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }

}
