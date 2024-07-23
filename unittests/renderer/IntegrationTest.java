package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CameraRayIntersectionsIntegrationTest {
    /**
     * Integration tests of Camera Ray construction with Ray-Sphere intersections
     */
    private final Scene          scene  = new Scene("Test scene");

    @Test
    public void cameraRaySphereIntegration() {
        Camera cam1 = Camera.getBuilder()
                .setLocation(new Point(0, 0, 0))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .setImageWriter(new ImageWriter("test", 3, 3))
                .setRayTracer((new SimpleRayTracer(scene)))
                .build();

        Camera cam2 = Camera.getBuilder()
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .setImageWriter(new ImageWriter("test", 3, 3))
                .setRayTracer(new SimpleRayTracer(scene))
                .build();

        // TC01: Small Sphere 2 points
        assertCountIntersections(2, cam1, new Sphere(new Point(0, 0, -3), 1), 3, 3);

        // TC02: Big Sphere 18 points
        assertCountIntersections(18, cam2, new Sphere(new Point(0, 0, -2.5), 2.5), 3, 3);

        // TC03: Medium Sphere 10 points
        assertCountIntersections(10, cam2, new Sphere(new Point(0, 0, -2), 2), 3, 3);

        // TC04: Inside Sphere 9 points
        assertCountIntersections(9, cam2, new Sphere(new Point(0, 0, -1), 4), 3, 3);

        // TC05: Beyond Sphere 0 points
        assertCountIntersections(0, cam1, new Sphere(new Point(0, 0, 1), 0.5), 3, 3);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Plane intersections
     */
    @Test
    public void cameraRayPlaneIntegration() {
        Camera cam = Camera.getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .setImageWriter(new ImageWriter("test", 3, 3))
                .setRayTracer(new SimpleRayTracer(scene))
                .build();


        // TC01: Plane against camera 9 points
        assertCountIntersections(9, cam, new Plane(new Point(0, 0, -5),
                new Vector(0, 0, 1)), 3, 3);

        // TC02: Plane with small angle 9 points
        assertCountIntersections(9, cam, new Plane(new Point(0, 0, -5),
                new Vector(0, 1, 2)), 3, 3);

        // TC03: Plane parallel to lower rays 6 points
        assertCountIntersections(6, cam, new Plane(new Point(0, 0, -5),
                new Vector(0, 1, 1)), 3, 3);

        // TC04: Beyond Plane 0 points
        assertCountIntersections(0, cam, new Plane(new Point(0, 0, 5),
                new Vector(0, 1, 1)), 3, 3);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Triangle intersections
     */
    @Test
    public void cameraRayTriangleIntegration() {


        Camera cam = Camera.getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .setImageWriter(new ImageWriter("test", 3, 3))
                .setRayTracer( new SimpleRayTracer(scene))
                .build();

        // TC01: Small triangle 1 point
        assertCountIntersections(1, cam, new Triangle(new Point(1, 1, -2),
                new Point(-1, 1, -2), new Point(0, -1, -2)), 3, 3);

        // TC02: Medium triangle 2 points
        assertCountIntersections(2, cam, new Triangle(new Point(1, 1, -2),
                new Point(-1, 1, -2), new Point(0, -20, -2)), 3, 3);
    }

    /**
     * Helper function to test intersections with all kinds of geometries
     *
     * @param expected the expected number of intersections
     * @param cam      the camera we send rays from
     * @param geo      the geometry
     * @param nX       number of pixels in the x direction
     * @param nY       number of pixels in the y direction
     */
    private void assertCountIntersections(int expected, Camera cam, Intersectable geo, int nX, int nY) {
        int count = 0;

        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                var ray = cam.constructRay(nX, nY, j, i); // create ray in the view plane
                var intersections = geo.findIntsersections(ray);

                if (intersections != null) {
                    count += intersections.size(); // count the total number of points
                }
            }
        }
        assertEquals(expected, count, "The number of intersections found was incorrect");
    }
}
