package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * The Camera class represents a camera in 3D space with position, orientation,
 * and properties such as view plane width, height, and distance from the view plane.
 */
public class Camera implements Cloneable {

    private Point p0;           // The position of the camera
    private Vector vUp;         // The up direction vector of the camera
    private Vector vTo;         // The forward direction vector of the camera
    private Vector vRight;      // The right direction vector of the camera
    private double width = 0.0; // The width of the view plane
    private double height = 0.0; // The height of the view plane
    private double distance = 0.0; // The distance from the camera to the view plane
    private Point centerPoint;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;
    private Camera() {
    }

    /**
     * Constructor for Camera.
     *
     * @param p0  The position of the camera.
     * @param vTo The forward direction vector.
     * @param vUp The up direction vector.
     * @throws IllegalArgumentException If vTo and vUp are not orthogonal.
     */
    public Camera(Point p0, Vector vTo, Vector vUp) throws IllegalArgumentException {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("constructor threw - vUp and vTo are not orthogonal");
        }
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * Returns a builder instance for creating a Camera object.
     *
     * @return A new Builder instance.
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Gets the position of the camera.
     *
     * @return The position of the camera as a Point object.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Gets the up direction vector of the camera.
     *
     * @return The up direction vector of the camera.
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * Gets the forward direction vector of the camera.
     *
     * @return The forward direction vector of the camera.
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * Gets the right direction vector of the camera.
     *
     * @return The right direction vector of the camera.
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * Gets the width of the view plane.
     *
     * @return The width of the view plane.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of the view plane.
     *
     * @return The height of the view plane.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the distance from the camera to the view plane.
     *
     * @return The distance from the camera to the view plane.
     */
    public double getDistance() {
        return distance;
    }

    public void writeToImage() {
        imageWriter.writeToImage();
    }

    /**
     * Constructs a ray through a specific pixel on the view plane.
     *
     * @param nX The number of horizontal pixels.
     * @param nY The number of vertical pixels.
     * @param j  The pixel column index.
     * @param i  The pixel row index.
     * @return The constructed ray through the specified pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // Calculate the center point of the view plane
        Point pC = p0.add(vTo.scale(distance));

        // Calculate the ratio of the view plane
        double rY = height / nY;
        double rX = width / nX;

        // Calculate the pixel's center point on the view plane
        double yI = -(i - (nY - 1) / 2.0) * rY;
        double xJ = (j - (nX - 1) / 2.0) * rX;

        Point pIJ = pC;
        if (!isZero(xJ)) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pIJ = pIJ.add(vUp.scale(yI));
        }

        // Create the ray from the camera to the pixel
        Vector vIJ = pIJ.subtract(p0);

        return new Ray(p0, vIJ);
    }
    /**
     * Renders the image by tracing rays through each pixel and calculating their color.
     * Currently, throws an UnsupportedOperationException.
     * @throws UnsupportedOperationException if imageWriter or rayTracer are not set
     */
    public Camera renderImage() {
        if (this.imageWriter == null || this.rayTracerBase == null) {
            throw new UnsupportedOperationException("ImageWriter and RayTracer must be set");
        }

        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();

        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                castRay(nx, ny, i, j);
            }
        }

        imageWriter.writeToImage();
        return this;
    }

    /**
     * Casts a ray through a specific pixel and colors it.
     *
     * @param nx number of pixels in the x direction
     * @param ny number of pixels in the y direction
     * @param i x coordinate of the pixel
     * @param j y coordinate of the pixel
     */
    private void castRay(int nx, int ny, int i, int j) {
        Ray ray = constructRay(nx, ny, i, j);
        Color color = rayTracerBase.traceRay(ray);
        imageWriter.writePixel(i, j, color);
    }

    /**
     * Prints a grid on the image with the specified interval and color.
     *
     * @param interval the interval between the grid lines
     * @param color the color of the grid lines
     * @throws UnsupportedOperationException if imageWriter is not set
     */
    public Camera printGrid(int interval, Color color) {
        if (this.imageWriter == null) {
            throw new UnsupportedOperationException("ImageWriter must be set");
        }

        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();

        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }
        try{
            return (Camera) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Sets the size of the view plane.
     *
     * @param width  The width of the view plane.
     * @param height The height of the view plane.
     * @return The current Camera instance for method chaining.
     * @throws IllegalArgumentException If the provided width or height are non-positive.
     */
    public Camera setVpSize(double width, double height) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be positive.");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be positive.");
        }
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Sets the distance from the camera to the view plane.
     *
     * @param distance The distance from the camera to the view plane.
     * @return The current Camera instance for method chaining.
     * @throws IllegalArgumentException If the provided distance is non-positive.
     */
    public Camera setVPDistance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance must be positive.");
        }
        this.distance = distance;
        return this;
    }

    /**
     * Sets the image writer for the camera.
     * @param imageWriter the image writer
     * @return the camera instance
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Sets the ray tracer for the camera.
     * @param rayTracerBase the ray tracer
     * @return the camera instance
     */
    public Camera setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    /**
     * Builder class for creating Camera objects.
     */
    public static class Builder{

        private final Camera camera;
        /**
         * Default constructor that initializes the camera with a new Camera object.
         */
        public Builder() {
            this.camera = new Camera();
        }
        /**
         * Constructor that initializes the builder with a given Camera object.
         *
         * @param camera The Camera object to initialize the builder with.
         */
        public Builder(Camera camera) {
            this.camera = camera;
        }

        /**
         * Sets the position of the camera.
         *
         * @param p0 The position of the camera.
         * @return The current Builder instance for chaining.
         * @throws IllegalArgumentException If the provided point is null.
         */
        public Builder setLocation(Point p0) {
            if (p0 == null) {
                throw new IllegalArgumentException("Camera position (p0) cannot be null.");
            }
            this.camera.p0 = p0;
            return this;
        }

        /**
         * Sets the direction of the camera.
         *
         * @param vTo The forward direction vector.
         * @param vUp The up direction vector.
         * @return The current Builder instance for chaining.
         * @throws IllegalArgumentException If the provided vectors are null or not orthogonal.
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo == null || vUp == null) {
                throw new IllegalArgumentException("Direction vectors (vTo and vUp) cannot be null.");
            }
            // Check if the vectors are orthogonal
            double dotProduct = vTo.dotProduct(vUp);
            double epsilon = 1e-10;
            if (Math.abs(dotProduct) > epsilon) {
                throw new IllegalArgumentException("Direction vectors (vTo and vUp) must be orthogonal.");
            }
            this.camera.vTo = vTo.normalize();
            this.camera.vUp = vUp.normalize();
            this.camera.vRight = vTo.crossProduct(vUp).normalize();
            return this;
        }

        /**
         * Sets the size of the view plane.
         *
         * @param width  The width of the view plane.
         * @param height The height of the view plane.
         * @return The current Builder instance for chaining.
         * @throws IllegalArgumentException If the provided width or height are non-positive.
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0) {
                throw new IllegalArgumentException("Width must be positive.");
            }
            if (height <= 0) {
                throw new IllegalArgumentException("Height must be positive.");
            }
            this.camera.width = width;
            this.camera.height = height;
            return this;
        }

        /**
         * Sets the distance from the camera to the view plane.
         *
         * @param distance The distance from the camera to the view plane.
         * @return The current Builder instance for chaining.
         * @throws IllegalArgumentException If the provided distance is non-positive.
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be positive.");
            }
            this.camera.distance = distance;
            return this;
        }
        /**
         * Sets the image writer for the camera.
         * @param imageWriter the image writer
         * @return the builder instance
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            this.camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the ray tracer for the camera.
         * @param rayTracerBase the ray tracer
         * @return the builder instance
         */
        public Builder setRayTracer(RayTracerBase rayTracerBase) {
            this.camera.rayTracerBase = rayTracerBase;
            return this;
        }

        /**
         * Builds and returns the Camera object.
         *
         * @return The constructed Camera object.
         * @throws MissingResourceException If any required camera field is missing.
         */
        public Camera build() {
            // Constants for exception messages
            final String RENDERING_ERROR = "Missing rendering data";
            final String CAMERA_CLASS = "Camera";

            if (this.camera.p0 == null) {
                throw new MissingResourceException(RENDERING_ERROR, CAMERA_CLASS, "Camera position (p0)");
            }
            if (this.camera.vTo == null) {
                throw new MissingResourceException(RENDERING_ERROR, CAMERA_CLASS, "Camera direction (vTo)");
            }
            if (this.camera.vUp == null) {
                throw new MissingResourceException(RENDERING_ERROR, CAMERA_CLASS, "Camera direction (vUp)");
            }
            if (this.camera.vRight == null) {
                throw new MissingResourceException(RENDERING_ERROR, CAMERA_CLASS, "Camera direction (vRight)");
            }
            if (this.camera.width <= 0) {
                throw new MissingResourceException(RENDERING_ERROR, CAMERA_CLASS, "View plane width");
            }
            if (this.camera.height <= 0) {
                throw new MissingResourceException(RENDERING_ERROR, CAMERA_CLASS, "View plane height");
            }
            if (this.camera.distance <= 0) {
                throw new MissingResourceException(RENDERING_ERROR, CAMERA_CLASS, "View plane distance");
            }
            if (this.camera.imageWriter == null || this.camera.rayTracerBase == null) {
                throw new UnsupportedOperationException("ImageWriter and RayTracer must be set");            }

            // Ensure vRight is normalized (should already be normalized)
            this.camera.vRight = this.camera.vTo.crossProduct(this.camera.vUp).normalize();

            try {
                return (Camera) this.camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError("Clone not supported for Camera", e);
            }
        }
    }

}
