package Primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Point class
 */
class PointTest {

    Point p = new Point(1.0, 1.0, 1.0);

    /**
     * Test method for {@link Primitives.Point#subtract(Point)} .
     *      This method tests the subtract() method of the Point class.
     *      It checks if the method returns the correct Point after subtracting another Point from it.
     *      If the result is incorrect, the test fails.
     *      It also checks if an IllegalArgumentException is thrown when subtracting a Point from itself.
     */

    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(this.p, (new Point(2.0, 3.0, 4.0)).subtract(new Point(1.0, 2.0, 3.0)), "Wrong point subtract");

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> {
            this.p.subtract(this.p);
        }, "Subtract P from P must throw exception");
    }

    /**
     *  Test method for {@link Primitives.Point#add(Primitives.Vector)}.
     *      This method tests the add() method of the Point class.
     *      It checks if the method returns the correct Point after adding a Vector to it.
     *      If the result is incorrect, the test fails.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Point(2.0, 3.0, 4.0), this.p.add(new Vector(1.0, 2.0, 3.0)), "Wrong point add");
    }

    /**
     *      Test method for {@link Primitives.Point#distanceSquared(Point)} .
     *      This method tests the distanceSquared() method of the Point class.
     *      It checks if the method returns the correct squared distance between the Point and another Point.
     *      If the result is incorrect, the test fails.
     *      It also checks if the method returns 0.0 when finding the squared distance between a Point and itself.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(14.0, this.p.distanceSquared(new Point(2.0, 3.0, 4.0)), 1.0E-4, "Wrong squared distance between the point and itself");
        // =============== Boundary Values Tests ==================
        assertEquals(0.0, (new Point(1.0, 2.0, 3.0)).distanceSquared(new Point(1.0, 2.0, 3.0)), 1.0E-4, "Wrong squared distance between the point and itself");
    }

    /**
     * Test method for {@link Primitives.Point#distance(Point)}.
     * This method tests the distance() method of the Point class.
     * It checks if the method returns the correct distance between the Point and another Point.
     * If the result is incorrect, the test fails.
     * It also checks if the method returns 0.0 when finding the distance between a Point and itself.
     */

    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(Math.sqrt(14.0), this.p.distance(new Point(2.0, 3.0, 4.0)), 1.0E-4, "Wrong distance between the point and another point");

        // =============== Boundary Values Tests ==================
        assertEquals(0.0, (new Point(1.0, 2.0, 3.0)).distance(new Point(1.0, 2.0, 3.0)), 1.0E-4, "Wrong distance between the point and itself");
    }

}