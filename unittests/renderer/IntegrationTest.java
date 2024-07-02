package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CameraRayIntersectionsIntegrationTest {
    /**
     * Integration tests of Camera Ray construction with Ray-Sphere intersections
     */
    @Test
    public void cameraRaySphereIntegration() {
        Camera cam1 = new Camera(
                Point.ZERO,
                new Vector(0, 0, -1),
                new Vector(0, -1, 0));
        Camera cam2 = new Camera(
                new Point(0, 0, 0.5),
                new Vector(0, 0, -1),
                new Vector(0, -1, 0));

        // TC01: Small Sphere 2 points
        assertCountIntersections(2, cam1, new Sphere(new Point(0, 0, -3), 1));

        // TC02: Big Sphere 18 points
        assertCountIntersections(18, cam2, new Sphere(new Point(0, 0, -2.5), 2.5));

        // TC03: Medium Sphere 10 points
        assertCountIntersections(10, cam2, new Sphere(new Point(0, 0, -2), 2));

        // TC04: Inside Sphere 9 points
        assertCountIntersections(9, cam2, new Sphere(new Point(0, 0, -1), 4));

        // TC05: Beyond Sphere 0 points
        assertCountIntersections(0, cam1, new Sphere(new Point(0, 0, 1), 0.5));

    }

    /**
     * Integration tests of Camera Ray construction with Ray-Plane intersections
     */
    @Test
    public void cameraRayPlaneIntegration() {
        Camera cam = new Camera(Point.ZERO, new Vector(0, 0, -1),
                new Vector(0, -1, 0));

        // TC01: Plane against camera 9 points
        assertCountIntersections(9, cam, new Plane(new Point(0, 0, -5),
                        new Vector(0, 0, 1)));

        // TC02: Plane with small angle 9 points
        assertCountIntersections(9, cam, new Plane(new Point(0, 0, -5),
                        new Vector(0, 1, 2)));

        // TC03: Plane parallel to lower rays 6 points
        assertCountIntersections(6, cam, new Plane(new Point(0, 0, -5),
                        new Vector(0, 1, 1)));

        // TC04: Beyond Plane 0 points
        assertCountIntersections(6, cam, new Plane(new Point(0, 0, -5),
                        new Vector(0, 1, 1)));
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Triangle intersections
     */
    @Test
    public void cameraRayTriangleIntegration() {
        Camera cam = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0));

        // TC01: Small triangle 1 point
        assertCountIntersections(1, cam, new Triangle(new Point(1, 1, -2),
                        new Point(-1, 1, -2), new Point(0, -1, -2)));

        // TC02: Medium triangle 2 points
        assertCountIntersections(2, cam, new Triangle(new Point(1, 1, -2),
                        new Point(-1, 1, -2), new Point(0, -20, -2)));
    }

    /**
     * help function to test intersections with all kind of geometries
     *
     * @param cam      the camera we send rays from
     * @param geo      the geometry
     * @param expected tha expected number of intersections with the geometry
     */
    private void assertCountIntersections(int expected, Camera cam, Intersectable geo) {
        cam.setVpSize(3, 3).setVPDistance(1);

        int nX = 3, nY = 3, count = 0;

        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                var ray = cam.constructRay(nX, nY, j, i); // create ray in the view plane
                var intersections = geo.findIntersections(ray);

                if (intersections != null) {
                    count += intersections.size(); // count the total number of points
                }
            }
        }
        assertEquals(expected, count, "The number of intersections found was incorrect");
    }
}