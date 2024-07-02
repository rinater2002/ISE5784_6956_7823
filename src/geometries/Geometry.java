package geometries;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Interface for geometric objects in a 3D space.
 * Provides a method to get the normal vector at a given point on the geometry.
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;
    private Material material = new Material();


    /**
     *get Material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     *set Material
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * get Emission
     * @return light emission of the geometry
     */
    public Color getEmission() {
        return emission;
    }
    /**
     *set Emission
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }


    /**
     * @param point
     * @return normal from the point to geometrries
     * @return normal from the point to geometries
     */
    public abstract Vector getNormal(Point point);

}