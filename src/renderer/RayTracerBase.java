package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public  abstract class RayTracerBase {
    protected Scene scene;
    /**
     * Constructor that get a scene
     * @param scene
     */
    public RayTracerBase(Scene scene){
        this.scene = scene;
    }

    /**
     * trace the ray and calculate the rey's intersection point color
     * and any other object (or the background if the rey's intersection point
     * doesn't exist)
     *
     * @param ray
     * @return
     */
    abstract Color traceRay(Ray ray);

}
