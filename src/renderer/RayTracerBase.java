package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * RayTracerBase is an abstract class that represents a ray tracer.
 * It contains a scene and provides a method to trace a ray and get the color of the intersection.
 */
public abstract class RayTracerBase {

    protected Scene scene;

    /**
     * Constructor for RayTracerBase.
     *
     * @param scene the scene to be traced
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }
    /**
     * Abstract method to trace a ray and get the color at the point of intersection.
     *
     * @param ray the ray to be traced
     * @return the color at the intersection point
     */
    public abstract Color traceRay(Ray ray);
}
