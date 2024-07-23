package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight extends Light {
    public static final AmbientLight NONE=new AmbientLight(Color.BLACK,0);

    /**
     * constructor for knowing the intensity after the light factor
     *
     * @param Ia - Light illumination (RGB עצמת האור לפי קומפוננטות)
     * @param Ka - Light factor - מקדם הנחתה של האור
     */
    public AmbientLight(Color Ia, double Ka) {
        //calculation of the intensity after the light factor//
        super(Ia.scale(Ka));
    }

    public AmbientLight(Color Ia, Double3 Ka) {
        //calculation of the intensity after the light factor//
        super(Ia.scale(Ka));
    }

    /**
     * default constructor for initialize the background to black
     */
    public AmbientLight() {
        super(Color.BLACK);
    }


}
