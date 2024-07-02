package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene class represents a scene with a name, background color, ambient light, and geometries.
 */
public class Scene {
    public List<LightSource> lights = new LinkedList<>();
    public String name;
    public Color background = Color.BLACK; //default color
    public AmbientLight ambientLight;
    public Geometries geometries = new Geometries();

    /**
     * Constructor for Scene.
     *
     * @param sceneName The name of the scene.
     */
    public Scene(String sceneName) {
        name = sceneName;
    }
    // ***************** Setters ********************** //
    // ** all setters implements the Builder Design Pattern **//
    /**
     * Sets the background color of the scene.
     *
     * @param background The background color.
     * @return The scene itself.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight The ambient light.
     * @return The scene itself.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries of the scene.
     *
     * @param geometries The geometries.
     * @return The scene itself.
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
    public Scene setLight(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
