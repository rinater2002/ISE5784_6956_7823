package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight extends Light{


    // Static final field NONE representing no ambient light
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0.0);

    /**
     * Constructor for AmbientLight that takes a Color and a 3Double attenuation factor.
     *
     * @param Ia the original intensity of the ambient light
     * @param Ka the attenuation factor as a 3Double
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }


    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }


}
