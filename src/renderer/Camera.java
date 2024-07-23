package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;


import java.util.MissingResourceException;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


public class Camera implements Cloneable {

    /**
     * starting position
     */
    private Point p0;

    /**
     * Vector to the right of the Camera, up, and where it was pointing.
     */
    private Vector v_t0;
    private Vector v_up;
    private Vector v_right;

    private double width = 0.0;
    private double height = 0.0;
    private double distance = 0.0;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    /**
     * constractor of camera
     */
    private Camera() {
    }

    /**
     * getter for Camera starting position.
     *
     * @return Camera starting position.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * getter for Camera direction.
     *
     * @return Camera direction.
     */
    public Vector getV_t0() {
        return v_t0;
    }

    /**
     * getter for above the Camera.
     *
     * @return above the Camera.
     */
    public Vector getV_up() {
        return v_up;
    }

    /**
     * getter for view plane height.
     *
     * @return view plane height.
     */
    public double getHeight() {
        return height;
    }

    /**
     * getter for view plane width.
     *
     * @return view plane width.
     */
    public double getWidth() {
        return width;
    }

    /**
     * getter for view plane distance.
     *
     * @return view plane distance.
     */
    public double getDistance() {
        return distance;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pc = p0.add(v_t0.scale(distance));     // center of the view plane
        double Ry = height / (double) nY;                      // Ratio - pixel height
        double Rx = width / (double) nX;                       // Ratio - pixel width

        double yJ = alignZero(-(i - ((double) nY - 1) / 2) * Ry);       // move pc Yi pixels
        double xJ = alignZero((j - ((double) nX - 1) / 2) * Rx);        // move pc Xj pixels

        Point PIJ = pc;
        if (!isZero(xJ)) PIJ = PIJ.add(v_right.scale(xJ));
        if (!isZero(yJ)) PIJ = PIJ.add(v_up.scale(yJ));

        return new Ray(p0, PIJ.subtract(p0));
    }

    @Override
    public Camera clone() {
        try {
            Camera clone = (Camera) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * nested inner class
     */
    public static class Builder {
        private final Camera camera = new Camera();

        /**
         * Set the location of the camera.
         *
         * @param My_p0 the location point of the camera.
         * @return the Builder object itself for method chaining.
         * @throws IllegalArgumentException if the location is null
         */
        public Builder setLocation(Point My_p0) {
            if (My_p0 == null) {
                throw new IllegalArgumentException("p0 cannot be null");
            }
            camera.p0 = My_p0;
            return this;
        }

        /**
         * Set the direction vectors of the camera.
         *
         * @param my_v_t0 the forward direction vector.
         * @param my_v_up the up direction vector.
         * @return the Builder object itself for method chaining.
         * @throws IllegalArgumentException if the vectors are null or not orthogonal
         */
        public Builder setDirection(Vector my_v_t0, Vector my_v_up) throws IllegalArgumentException {
            if (my_v_t0 == null || my_v_up == null) {
                throw new IllegalArgumentException("Direction vectors cannot be null");
            }
            if (!isZero(my_v_t0.dotProduct(my_v_up))) {
                throw new IllegalArgumentException("vTo and vUp must be orthogonal");
            }
            camera.v_t0 = my_v_t0.normalize();
            camera.v_up = my_v_up.normalize();
            return this;
        }

        /**
         * Set the size of the view plane.
         *
         * @param my_width  the width of the view plane.
         * @param my_height the height of the view plane.
         * @return the Builder object itself for method chaining.
         * @throws IllegalArgumentException if the dimensions aren't positive
         */
        public Builder setVpSize(double my_width, double my_height) throws IllegalArgumentException {
            if (my_width <= 0 || my_height <= 0) {
                throw new IllegalArgumentException("View plane dimensions must be positive");
            }
            camera.width = my_width;
            camera.height = my_height;
            return this;
        }

        /**
         * Set the distance between the camera and the view plane.
         *
         * @param my_distance the distance between the camera and the view plane.
         * @return the Builder object itself for method chaining.
         * @throws IllegalArgumentException if the distance is not positive
         */
        public Builder setVpDistance(double my_distance) throws IllegalArgumentException {
            if (my_distance <= 0) {
                throw new IllegalArgumentException("View plane distance must be positive");
            }
            camera.distance = my_distance;
            return this;
        }

        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Builds the Camera object.
         *
         * @return the constructed Camera object.
         * @throws MissingResourceException if any required field is missing.
         */
        public Camera build() throws MissingResourceException {

            String missingData = "Missing rendering data";

            camera.v_right = camera.v_t0.crossProduct(camera.v_up).normalize();

            if (camera.p0 == null) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "location(p0)");
            }
            if (camera.v_t0 == null || camera.v_up == null || camera.v_right == null) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "direction vectors");
            }
            if (camera.width == 0.0 || camera.height == 0.0) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "view plane size");
            }
            if (camera.distance == 0.0) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "view plane distance");
            }
            if (camera.imageWriter == null) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "imageWriter");
            }
            if (camera.rayTracer == null) {
                throw new MissingResourceException(missingData, Camera.class.getName(), "ray Tracer");
            }


            return (Camera) camera.clone();
        }
    }

    public Camera renderImage() {
        if (this.imageWriter == null)
            throw new UnsupportedOperationException("Missing imageWriter");
        if (this.rayTracer == null)
            throw new UnsupportedOperationException("Missing rayTracerBase");

        for (int i = 0; i < this.imageWriter.getNy(); i++) {
            for (int j = 0; j < this.imageWriter.getNy(); j++) {
                Color color = castRay(j,i);
                this.imageWriter.writePixel(j, i, color);
            }
        }
        return this;
    }
    private Color castRay(int j,int i){
        Ray ray = constructRay(
                this.imageWriter.getNx(),
                this.imageWriter.getNy(),
                j,
                i);
        return this.rayTracer.traceRay(ray);
    }

    public Camera printGrid(int interval, Color color) {
        //=== running on the view plane===//
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                //=== create the net ===//
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }
        imageWriter.writeToImage();
        return this;
    }
    public void writeToImage() {
        this.imageWriter.writeToImage();
    }



}
