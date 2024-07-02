package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}.
     * Tests the getNormal method of the Tube class with a normal case.
     */
    @Test
    void testGetNormal() {
// ============ Equivalence Partitions Tests ==============
// Create a ray and a tube
        Ray ray = new Ray(new Point(0, 1, 0), new Vector(0, 1, 0));
        Tube tb = new Tube(3, ray);

// Test the getNormal method with a specific point
        //TC01: simple test
        assertEquals(tb.getNormal(new Point(3, 0, 0)), new Vector(1, 0, 0),
                "Normal abnormality");

// =============== Boundary Values Tests ==================
// Test when connecting the point to the horn head of the cylinder axis produces a right angle with the axis
        Tube tube = new Tube(2, new Ray(new Point(0, 1, 0), new Vector(0, 1, 0)));
        //TC10: The point in front of the head of the foundation
        assertEquals(tube.getNormal(new Point(2, 1, 0)), new Vector(1, 0, 0),
                "The point in front of the head of the foundation");
    }
}