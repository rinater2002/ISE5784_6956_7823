package geometries;

import Primitives.Point;
import Primitives.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(Point)}.
     * Tests the getNormal method of the Triangle class.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // Create a vector with equal components
        double sqrt3 = Math.sqrt(1d/3);
        Vector n = new Vector(sqrt3, sqrt3, sqrt3);

        // Create a new triangle and check its normal
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        Point point = new Point(1, 0, 0);
        //TC01: simple test
        assertEquals(n, triangle.getNormal(point), "Triangle's normal is not correct");
    }
}