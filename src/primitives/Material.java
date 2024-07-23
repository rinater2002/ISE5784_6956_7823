package primitives;

public class Material {
    // the Diffuse light factor of the object material type
    public Double3 kD = Double3.ZERO, /** diffusive attenuation factor */
    kS = Double3.ZERO, /** specular attenuation factor */
    kT = Double3.ZERO, /** transparency attenuation factor */
    kR = Double3.ZERO; /** reflective attenuation factor */

    // the specular light factor of the object material type
    // Glossiness factor
    public double kG = 0;
    // Blurriness factor
    public double kB = 0;

    // the shininess factor of the object material type//
    public int nShininess = 0;
    // Parameters for blur glass
    public int numOfRays = 1;
    public double blurGlassDistance = 1, blurGlassRadius = 1;

    /**
     * set kT function - the transparency factor (Double3)
     * @param kT
     * @return
     */
    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * set kT function - the transparency factor (double)
     * @param kT
     * @return
     */
    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * set kR function - the reflection factor (Double3)
     * @param kR
     * @return
     */
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * set kR function - the reflection factor (double)
     * @param kR
     * @return
     */
    public Material setkR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
    /**
     * set KD function - the diffuse light factor
     *
     * @param kD light factor (Double3)
     * @return
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * set KD function - the diffuse light factor
     *
     * @param kD light factor (double)
     * @return
     */
    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * set kS function - the specular light factor
     *
     * @param kS light factor (Double3)
     * @return
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }


    /**
     * set kS function the specular light factor
     *
     * @param kS light factor (double)
     * @return
     */
    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Set the shininess factor of the material
     *
     * @param nShininess shininess factor of the material (int)
     * @return this (Material)
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
    /**
     * Sets Glossy attenuation factor.
     *
     * @param kG the Glossy attenuation factor.
     * @return the material
     */
    public Material setKg(double kG) {
        this.kG = kG;
        return this;
    }

    /**
     * Sets Blur attenuation factor.
     *
     * @param kB the Blur attenuation factor.
     * @return the material
     */
    public Material setKb(double kB) {
        this.kB = kB;
        return this;
    }


    /**
     * Sets the number of rays for blur glass rendering.
     *
     * @param numOfRays The number of rays to set.
     * @return This Material object.
     * @throws IllegalArgumentException if numOfRays is less than 1.
     */
    public Material setNumOfRays(int numOfRays) {
        if (numOfRays < 1)
            throw new IllegalArgumentException("Illegal argument in setNumOfRays");
        this.numOfRays = numOfRays;
        return this;
    }

    /**
     * Sets the distance for blur glass rendering.
     *
     * @param blurGlassDistance The distance to set.
     * @return This Material object.
     * @throws IllegalArgumentException if blurGlassDistance is less than or equal
     *                                  to 0.
     */
    public Material setBlurGlassDistance(double blurGlassDistance) {
        if (blurGlassDistance <= 0)
            throw new IllegalArgumentException("Illegal argument in setBlurGlassDistance");
        this.blurGlassDistance = blurGlassDistance;
        return this;
    }

    /**
     * Sets the radius for blur glass rendering.
     *
     * @param blurGlassRadius The radius to set.
     * @return This Material object.
     * @throws IllegalArgumentException if blurGlassRadius is less than or equal to
     *                                  0.
     */
    public Material setBlurGlassRadius(double blurGlassRadius) {
        if (blurGlassRadius <= 0)
            throw new IllegalArgumentException("Illegal argument in setBlurGlassRadius");
        this.blurGlassRadius = blurGlassRadius;
        return this;
    }

    /**
     * Sets the parameters for blur glass rendering.
     *
     * @param numOfRays The number of rays to set.
     * @param distance  The distance to set.
     * @param radius    The radius to set.
     * @return This Material object.
     * @throws IllegalArgumentException if any of the parameters is invalid.
     */
    public Material setBlurGlass(int numOfRays, double distance, double radius) {
        if (numOfRays < 1 || distance <= 0 || radius <= 0)
            throw new IllegalArgumentException("Illegal argument in setBlurGlass");

        this.numOfRays = numOfRays;
        this.blurGlassDistance = distance;
        this.blurGlassRadius = radius;

        return this;
    }

}
