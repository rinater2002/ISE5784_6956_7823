package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    private final Color intensity;

    // Static final field NONE representing no ambient light
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0.0);

    /**
     * Constructor for AmbientLight that takes a Color and a 3Double attenuation factor.
     *
     * @param Ia the original intensity of the ambient light
     * @param Ka the attenuation factor as a 3Double
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity = Ia.scale(Ka);
    }

    /**
     * Constructor for AmbientLight that takes a Color and a double attenuation factor.
     *
     * @param Ia the original intensity of the ambient light
     * @param Ka the attenuation factor as a double
     */
    public AmbientLight(Color Ia, double Ka) {
        this.intensity = Ia.scale(Ka);
    }

    /**
     * Method to get the intensity of the ambient light.
     *
     * @return the intensity of the ambient light
     */
    public Color getIntensity() {
        return intensity;
    }
}
