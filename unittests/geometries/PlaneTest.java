package geometries;

import org.junit.jupiter.api.Test;
import Primitives.Point;
import Primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    /**
     * Test method for {@link Plane#Plane(Point, Point, Point)}.
     * This method tests the constructor of the Plane class.
     * It checks if the constructor creates a plane correctly with three non-collinear points.
     * If the points are collinear or any two points are the same, the constructor should throw an IllegalArgumentException.
     */
    void testConstructor() {

        // ============ Equivalence Partitions Tests ==============

        try {
            Plane plane = new Plane(new Point(0, 1, 1),
                    new Point(-2, 1, 0),
                    new Point(5, 0, 2));
        } catch (IllegalArgumentException e) {
            fail("failed constructing plane");
        }
        // TC01: first point and second point are same
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 1, 1), new Point(1, 1, 1), new Point(1, 2, 1)),
                "Constructed a plane with two equal points");
        // TC02: three point are on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(2, 1, 0), new Point(5, 0, 3),
                        new Point(3.5, 0.5, 1.5)),
                "Constructed a plane with two equal points");
    }

    /**
     * Test method for {@link Plane#getNormal(Point)}.
     * This method tests the getNormal() method of the Plane class.
     * It checks if the method returns the correct normal vector to the plane at a given point.
     * The length of the normal vector should be 1.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: check if getNormal calculated correct
        Plane plane = new Plane(new Point(0, 0, 1),
                new Point(1, 0, 0),
                new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), plane.getNormal(new Point(0, 0, 1)),
                "Error: Plane getNormal not returning correct value");
        // TC02: the test check if the length of normal different from 1
        assertEquals(1, plane.getNormal().length(), "ERROR: the length different from 1");
    }
}