/**
 * Unit tests for the Ray class.
 * These tests verify the behavior of finding the closest point to a ray among a list of points.
 */
package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTests {

    /**
     * Test case for finding the closest point to a ray among a list of points.
     * <p>
     * Equivalence Partitions:
     * - A point in the middle of the list is closest to the ray.
     * <p>
     * Boundary Value Tests:
     * - Empty list: Should return null.
     * - The closest point is the first in the list.
     * - The closest point is the last in the list.
     */
    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));

        // Equivalence Partitions Test: A point in the middle of the list is closest to the ray
        List<Point> points = List.of(
                new Point(1, 2, 3),
                new Point(4, 5, 6),
                new Point(0.5, 0.5, 0.5), // This is the closest point
                new Point(7, 8, 9)
        );
        assertEquals(new Point(0.5, 0.5, 0.5), ray.findClosestPoint(points), "ERROR: wrong closest point");

        // Boundary Value Tests

        // Case 1: Empty list
        points = List.of();
        assertNull(ray.findClosestPoint(points), "ERROR: empty list should return null");

        // Case 2: The closest point is the first in the list
        points = List.of(
                new Point(0.1, 0, 0), // This is the closest point
                new Point(1, 2, 3),
                new Point(4, 5, 6),
                new Point(7, 8, 9)
        );
        assertEquals(new Point(0.1, 0, 0), ray.findClosestPoint(points), "ERROR: wrong closest point");

        // Case 3: The closest point is the last in the list
        points = List.of(
                new Point(1, 2, 3),
                new Point(4, 5, 6),
                new Point(7, 8, 9),
                new Point(0.1, 0, 0) // This is the closest point
        );
        assertEquals(new Point(0.1, 0, 0), ray.findClosestPoint(points), "ERROR: wrong closest point");
    }
}
