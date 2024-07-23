package primitives;

public class Vector extends Point{
    /**
     * A parameter constructor that accepts three numbers and creates a dot
     * @param x=xyx.d1
     * @param y=xyz.d2
     * @param z=xyz.d3
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (x == Double3.ZERO.d1 && y == Double3.ZERO.d2 && z == Double3.ZERO.d3) {
            throw new IllegalArgumentException("This Vector is ZERO");
        }
    }

    /**
     * parameters constructor
     * @param vec=xyz
     */
    public Vector(Double3 vec) {
        super(vec);
        if(vec.d1==0&&vec.d2==0&&vec.d3==0) {
            throw new IllegalArgumentException("This Vector is ZERO");
        }
    }
    /**
     * @param obj=point
     * @return If two points are equal
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * @return A string representing the vector class
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * @param v1=the point that we want to add
     * @return new vector after the adding
     */
    public Vector add(Vector v1) {
        if(v1.xyz==Double3.ZERO) {
            throw new IllegalArgumentException("vector is zero");
        }
        return new Vector(super.add(v1).xyz);

    }

    /**
     * @param myScale=the number
     * @return new vector after scale
     */
    public Vector scale(double myScale) {
        return new Vector(super.xyz.scale(myScale));
    }

    /**
     * @param v1=the vector
     * @return Scalar product between a vector and a number
     */
    public double dotProduct(Vector v1) {
        return (this.xyz.d1*v1.xyz.d1) + (this.xyz.d2*v1.xyz.d2)+(this.xyz.d3*v1.xyz.d3);
    }

    /**
     * @param v1=the vector
     * @return A vector product between two vectors
     */
    public Vector crossProduct(Vector v1) {
        return new Vector((this.xyz.d2*v1.xyz.d3-this.xyz.d3*v1.xyz.d2),(this.xyz.d3*v1.xyz.d1-this.xyz.d1*v1.xyz.d3),(this.xyz.d1*v1.xyz.d2-this.xyz.d2*v1.xyz.d1));
    }

    /**
     * @return Vector length squared
     */
    public double lengthSquared(){
        return this.xyz.d1*this.xyz.d1+this.xyz.d2*this.xyz.d2+this.xyz.d3*this.xyz.d3;
    }

    /**
     * @return Vector length
     */
    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * @return Normalized vector
     */
    public Vector normalize() {
        return new Vector(this.xyz.d1/length(),this.xyz.d2/length(),this.xyz.d3/length());
    }
    /**
     * create vector normal to this vector
     *
     * @return
     */
    public Vector createNormal() {
        if (Util.isZero(this.xyz.d1))
            return new Vector(1, 0, 0);

        return new Vector(this.xyz.d2, -this.xyz.d1, 0).normalize();
    }
}
