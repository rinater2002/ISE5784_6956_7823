package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Plane class
 *
 * @author Yael Bouskila and Lital Amsalem
 */
class PlaneTest {
    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
     */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        // TC0:checking if all points are on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 1, 1), new Point(1, 1, 1), new Point(1, 1, 1)),
                "vector zero dude, next time do stuff properly");
        // TC1: checking if two points are equal
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0, 1, 0), new Point(0, 1, 0), new Point(0, 1, 2)),
                "vector zero dude, next time do stuff properly");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: test getting a normal vector from a point on plane
        Point[] points = {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)};
        Plane plane = new Plane(points[0], points[1], points[2]);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(new Point(0, 1, 0)),
                "getNormal() throws an unexpected exception");
        // generate the test result
        Vector result = plane.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to plane
        assertTrue(plane.getNormal().equals(result) || plane.getNormal().equals(result.scale(-1)),
                "getNormal() wrong result");
    }

    /**
     * The method for{@link  geometries.Plane#findIntsersections(Ray)}
     */
    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point(1, 0, 1), new Point(0, 1, 1), new Point(1, 1, 1));
        // ================ EP: The Ray must be neither orthogonal nor parallel to the plane ==================
        //TC01: Ray intersects the plane
        assertEquals(List.of(new Point(1, 0.5, 1)),
                plane.findIntsersections(new Ray(new Point(0, 0.5, 0), new Vector(1, 0, 1))),
                "Ray intersects the plane");

        //TC02: Ray does not intersect the plane
        assertNull(plane.findIntsersections(new Ray(new Point(1, 0.5, 2), new Vector(1, 2, 5))),
                "Ray does not intersect the plane");


        // ====================== Boundary Values Tests =======================//
        // **** Group: Ray is parallel to the plane
        //TC10: The ray included in the plane
        assertNull(plane.findIntsersections(new Ray(new Point(1,2,1), new Vector(1,0,0))), "Ray is parallel to the plane, the ray included in the plane");
        //TC11: The ray not included in the plane
        assertNull(plane.findIntsersections(new Ray(new Point(1,2,2), new Vector(1,0,0))), "Ray is parallel to the plane, the ray not included in the plane");


        // **** Group: Ray is orthogonal to the plane
        //TC12: according to 𝑃0, before the plane
        assertEquals(List.of(new Point(1,1,1)), plane.findIntsersections(new Ray(new Point(1,1,0), new Vector(0,0,1))), "Ray is orthogonal to the plane, according to p0, before the plane");
        //TC13: according to 𝑃0, in the plane
        assertNull(plane.findIntsersections(new Ray(new Point(1,2,1), new Vector(0,0,1))), "Ray is orthogonal to the plane, according to p0, in the plane");
        //TC14: according to 𝑃0, after the plane
        assertNull(plane.findIntsersections(new Ray(new Point(1,2,2), new Vector(0,0,1))), "Ray is orthogonal to the plane, according to p0, after the plane");


        // **** Group: Ray is neither orthogonal nor parallel to
        //TC15: Ray begins at the plane
        assertNull(plane.findIntsersections(new Ray(new Point(2,4,1), new Vector(2,3,5))), "Ray is neither orthogonal nor parallel to ray and begin at the plane");
        //TC16: Ray begins in the same point which appears as reference point in the plane
        assertNull(plane.findIntsersections(new Ray(new Point(1,0,1), new Vector(2,3,5))), "Ray is neither orthogonal nor parallel to ray and begins in the same point " + "which appears as reference point in the plane");
    }
}