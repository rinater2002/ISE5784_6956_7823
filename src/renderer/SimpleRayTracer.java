package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * SimpleRayTracer is a simple implementation of RayTracerBase.
 */
public class SimpleRayTracer extends RayTracerBase {
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Abstract method to trace a ray and get the color at the point of intersection.
     *
     * @param ray the ray to be traced
     * @return the color at the intersection point
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * Calculates the color at the given intersection point.
     *
     * @param point the intersection point
     * @return the color at the intersection point
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
