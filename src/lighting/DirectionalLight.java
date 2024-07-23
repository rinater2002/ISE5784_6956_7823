package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
    private Vector direction;

    /**
     * constructor for the intensity
     *
     * @param intensity of the intensity of the source of the light
     */
    public DirectionalLight(Color intensity, Vector dir) {
        super(intensity);
        this.direction = dir.normalize();
    }

    /**
     * Return the intensity light on specific point222222222
     *
     * @param p the point on the object (Point3D)
     * @re
     *
     *
     *
     *
     *
     *
     *
     *
    בבבבבבבבturn the intensity (Color)
     */
    @Override
    public Color getIntensity(Point p) {
        return this.intensity;
    }

    /**
     * Return normalize direction vector from the light source to the object
     *
     * @param p the point on the object (Point)
     * @return normalize direction vector from the light source to the object (Vector)
     */
    @Override
    public Vector getL(Point p) {
        return this.direction.normalize();
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}