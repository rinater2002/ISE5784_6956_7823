package geometries;

import Primitives.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(Primitives.Point)}.
     * This method tests the getNormal() method of the Sphere class.
     * It checks if the method returns the correct normal vector to the sphere at a given point on its surface.
     * The normal vector should point from the center of the sphere to the point on the surface.
     */
    @Test
    void testGetNormal() {
        Sphere s1 = new Sphere(new Point(1, 2, 3), 1);
        // ============ Equivalence Partitions Tests ==============

        // TC01: Test that the normal is the right one
        Vector e1 = s1.getNormal(new Point(2, 2, 3));
        assertEquals(new Vector(1, 0, 0), e1, "getNormal() wrong result");
    }
}