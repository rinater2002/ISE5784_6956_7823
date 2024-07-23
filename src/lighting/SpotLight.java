package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.alignZero;

public class SpotLight extends PointLight{
    private Vector dir;
    private double NarrowBeam=1;
    /**
     * constructor for the intensity
     *
     * @param color     of the intensity of the source of the light
     * @param direction
     */
    public SpotLight(Color color, Point position, Vector direction) {
        super(color, position);
        this.dir = direction.normalize();
    }
    /**
     * setkC function
     *
     * @param kC
     * @return
     */
    public SpotLight setkC(double kC) {
        super.setkC(kC);
        return this;
    }

    /**
     * setkL function
     * @param kL
     * @return
     */
    public SpotLight setkL(double kL) {
        super.setkL(kL);
        return this;
    }

    /**
     * setkQ function
     * @param kQ
     * @return
     */
    public SpotLight setkQ(double kQ) {
        super.setkQ(kQ);
        return this;
    }

    public SpotLight setNarrowBeam(double NarrowBeam) {
        this.NarrowBeam=NarrowBeam;
        return this;
    }
    @Override
    public Color getIntensity(Point p) {
        double cos = alignZero(dir.dotProduct(getL(p)));
        return NarrowBeam != 1
                ? super.getIntensity(p).scale(Math.pow(Math.max(0, dir.dotProduct(getL(p))), NarrowBeam))
                : super.getIntensity(p).scale(Math.max(0, dir.dotProduct(getL(p))));
    }
}
