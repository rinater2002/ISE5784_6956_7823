package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class TriangleTest {
    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: test getting a normal vector from a point on triangle
        Point[] points = {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)};
        Triangle newTriangle = new Triangle(points[0], points[1], points[2]);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> newTriangle.getNormal(new Point(0, 1, 0)), "getNormal() throws an unexpected exception");
        // generate the test result
        Vector result = newTriangle.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertTrue(isZero(result.dotProduct(points[i].subtract(points[i == 0 ? 2 : i - 1]))),
                    "Triangle's normal is not orthogonal to one of the vectors of the plane");
    }

    /**
     * Test method for {@link geometries.Triangle#findIntsersections(Ray)}.
     */
    @Test
    void findIntersections() {
        Triangle triangle = new Triangle(new Point(0, 1, 0), new Point(0, 5, 0), new Point(0, 3, 5));

        // ============ Equivalence Partitions Tests ==============
        // TC01: The intersection point is in the triangle
        assertEquals(List.of(new Point(0, 3, 1)), triangle.findIntsersections(new Ray(new Point(1, 3, 0), new Vector(-1, 0, 1))), "The point supposed to be in the triangle");

        // TC02: The intersection point is outside the triangle, against edge
        assertNull(triangle.findIntsersections(new Ray(new Point(1, 0, 0), new Vector(-1, 0, 1))), "The point supposed to be outside the triangle, against edge");

        // TC03: The intersection point is outside the triangle, against vertex
        assertNull(triangle.findIntsersections(new Ray(new Point(1, 0, 0), new Vector(-1, 0.1, -0.1))), "The point supposed to be outside the triangle, against vertex");

        // =============== Boundary Values Tests ==================
        // TC10: The point is on edge
        assertNull(triangle.findIntsersections(new Ray(new Point(1, 3, 0), new Vector(-1, 0, 0))), "The point supposed to be on edge");

        // TC11: The point is in vertex
        assertNull(triangle.findIntsersections(new Ray(new Point(1, 1, 0), new Vector(-1, 0, 0))), "The point supposed to be in vertex");

        // TC12: The point is on edge's continuation
        assertNull(triangle.findIntsersections(new Ray(new Point(1, 0, 0), new Vector(-1, 0.1, 0))), "The point supposed to be on edge's continuation");
    }
}