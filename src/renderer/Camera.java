package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;


import java.util.*;
import java.util.MissingResourceException;

import static primitives.Util.*;


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

    final int STARTING_DEPTH = 1;
    final int STARTING_SIGN = 1;

    private int MAX_DEPTH_OF_ADAPTIVE = 5;

    private boolean superSampling = false;
    private final int NUM_OF_RAYS = 50;
    private boolean adaptiveGrid = false;

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

        double x = random(-Rx / 2,Rx / 2) ;
        double y = random(-Ry / 2,Ry / 2) ;

        Point PIJ = pc;
        if (!isZero(xJ)) PIJ = PIJ.add(v_right.scale(xJ + x));
        if (!isZero(yJ)) PIJ = PIJ.add(v_up.scale(yJ + y));

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


        public Builder setAdaptiveGrid(boolean adaptiveGrid) {
            camera.adaptiveGrid = adaptiveGrid;
            return this;
        }

        public Builder setSuperSampling(boolean superSampling) {
            camera.superSampling = superSampling;
            return this;
        }

        public Builder setMAX_DEPTH_OF_ADAPTIVE(int MAX_DEPTH_OF_ADAPTIVE) {
            camera.MAX_DEPTH_OF_ADAPTIVE = MAX_DEPTH_OF_ADAPTIVE;
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
            for (int j = 0; j < this.imageWriter.getNy(); j++) { //TODO: CHANGE TO GETNX(?)
                Color color = castRay(j,i);
                this.imageWriter.writePixel(j, i, color);
            }
        }
        return this;
    }
    private Color castRay(int j,int i){
        if (adaptiveGrid) return adaptiveGrid(this.imageWriter.getNx(), this.imageWriter.getNy(), j, i);

        Color color = new Color(java.awt.Color.BLACK);
        Ray ray;
        for (int index = 0; index < NUM_OF_RAYS; index++){
            ray = constructRay(this.imageWriter.getNx(), this.imageWriter.getNy(), j, i);
            color = color.add(this.rayTracer.traceRay(ray));
        }
        return color.reduce(NUM_OF_RAYS);
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

    /**
     * Cast rays adaptively to the object and calculate the final color of the pixel
     *
     * @param nX  number of columns (int)
     * @param nY  number of rows (int)
     * @param col column index of the point in the view plane (int)
     * @param row row index of the point in the view plane (int)
     * @return The final color of the pixel (Color)
     */
    private Color adaptiveGrid(int nX, int nY, int col, int row) {

        Color color = Color.BLACK;
        List<Color> colors = new ArrayList<>();

        //-----------------------
        // calculate the center of the main pixel
        Point center = centerOfMainPixel(nX, nY, col, row);

        // calculate the sub-pixels centers
        List<Point> centers = centerOfPixels(nX, nY, center , STARTING_DEPTH);
        //-----------------------


        // Collect all the four colors of the four pixel's corners
        // and add it to 'color' for calculating the average color of the pixel (the final color of the pixel)
        // ---
        // The order of the ray list is:
        // up-right corner   -  1  -- index 0
        // up-left corner    -  2  -- index 1
        // down-left corner  -  3  -- index 2
        // down-right corner -  4  -- index 3
        // --
        // The order of the colors of the corners are in the same order of the ray list
        List<Ray> ray = constructRayAdaptive(nX, nY, STARTING_DEPTH, STARTING_SIGN, STARTING_SIGN, center);
        for (int i = 0; i < ray.size(); i++) {
            // We save the colors for comparing them for knowing if we need to make deeper grid
            colors.add(this.rayTracer.traceRay(ray.get(i)));
            color = color.add(colors.get(i));
        }

        // If not all four corners are with the same variety, make deeper grid
        if (!colors.get(0).isEqual(colors.get(1)) ||
                !colors.get(1).isEqual(colors.get(2)) ||
                !colors.get(2).isEqual(colors.get(3))) {

            color = Color.BLACK;
            List<Color> colRay1 = adaptiveGrid2(nX, nY, STARTING_DEPTH + 1, 1, 1, List.of(colors.get(0))
                    ,centers.get(0)); // Right up
            color = color.add(colRay1.get(4));

            List<Color> colRay2 = adaptiveGrid2(nX, nY, STARTING_DEPTH + 1, -1, 1
                    , List.of(colRay1.get(1), colors.get(1), colRay1.get(2)),centers.get(1)); // Left up
            color = color.add(colRay2.get(4));


            List<Color> colRay3 = adaptiveGrid2(nX, nY, STARTING_DEPTH + 1, -1, -1,
                    List.of(colRay1.get(2), colRay2.get(2), colors.get(2)),centers.get(2)); // left down
            color = color.add(colRay3.get(4));


            List<Color> colRay4 = adaptiveGrid2(nX, nY, STARTING_DEPTH + 1, 1, -1,
                    List.of(colRay1.get(3), colRay1.get(2), colRay3.get(3), colors.get(3)),centers.get(3)); // right down
            color = color.add(colRay4.get(4));

        }
        return color.reduce(4);
    }



    /**
     * @param nX        number of columns (int)
     * @param nY        number of rows (int)
     * @param depth     The depth of the recursion
     * @param signX     The X sign of the current quarter
     * @param signY     The Y sign of the current quarter
     * @param oldColors List of colors we already calculate
     * @return List of the corners colors and the average of them
     */
    private List<Color> adaptiveGrid2(int nX, int nY, int depth, int signX, int signY, List<Color> oldColors, Point centerOfPixel) {

        // To calculate the final average color
        Color color = Color.BLACK;

        // List to save the final colors
        List<Color> colRayList = new ArrayList<>();

        // REORGANIZED
        // We organize the list of the rays and they colors (colRayList) refer to:
        // up-right corner   -  1  -- index 0
        // up-left corner    -  2  -- index 1
        // down-left corner  -  3  -- index 2
        // down-right corner -  4  -- index 3
        // the average color -     -- index 4
        List<Ray> rays = constructRayAdaptive(nX, nY, depth, signX, signY, centerOfPixel); // The new corners

        // First quarter
        if (signX > 0 && signY > 0) {
            colRayList.add(oldColors.get(0)); // up-right corner
            color = color.add(oldColors.get(0));

            for (int i = 0; i < 3; i++) {
                colRayList.add(rayTracer.traceRay(rays.get(i)));
                color = color.add(colRayList.get(i + 1));
            }
        }

        // Second quarter
        if (signX < 0 && signY > 0) {
            colRayList.add(oldColors.get(0)); // up-right corner
            color = color.add(oldColors.get(0));

            colRayList.add(oldColors.get(1)); // up-left corner
            color = color.add(oldColors.get(1));

            colRayList.add(rayTracer.traceRay(rays.get(0))); // down-left corner
            color = color.add(colRayList.get(2));

            colRayList.add(oldColors.get(2)); // down-right corner
            color = color.add(oldColors.get(2));
        }

        // Third quarter
        if (signX < 0 && signY < 0) {

            colRayList.add(oldColors.get(0)); // up-right corner
            color = color.add(oldColors.get(0));

            colRayList.add(oldColors.get(1)); // up-left corner
            color = color.add(oldColors.get(1));

            colRayList.add(oldColors.get(2)); // down-left corner
            color = color.add(oldColors.get(2));

            colRayList.add(rayTracer.traceRay(rays.get(0))); // down-right corner
            color = color.add(colRayList.get(3));
        }

        // Fourth quarter
        if (signX > 0 && signY < 0) {
            //We receive all the corners colors as parameters (in 'oldRays')
            // so we just add them to 'colRayList'.
            for (int i = 0; i < 4; i++) {
                colRayList.add(oldColors.get(i)); // We receive them sorted so we just save them
                color = color.add(oldColors.get(i));
            }
        }

        // Stop recursive condition
        if (depth >= MAX_DEPTH_OF_ADAPTIVE) {
            colRayList.add(color.reduce(4));
            return colRayList;
        }

        // Check if all corners in the same variety
        if (!colRayList.get(0).isEqual(colRayList.get(1)) ||
                !colRayList.get(1).isEqual(colRayList.get(2)) ||
                !colRayList.get(2).isEqual(colRayList.get(3))) {

            List<Point> centers = centerOfPixels(nX, nY, centerOfPixel , depth);

            color = Color.BLACK;
            List<Color> colRay1 = adaptiveGrid2(nX, nY, depth + 1, 1, 1, List.of(colRayList.get(0))
                    ,centers.get(0)); // Right up
            color = color.add(colRay1.get(4));

            List<Color> colRay2 = adaptiveGrid2(nX, nY, depth + 1, -1, 1
                    , List.of(colRay1.get(1), colRayList.get(1), colRay1.get(2)),centers.get(1)); // Left up
            color = color.add(colRay2.get(4));

            List<Color> colRay3 = adaptiveGrid2(nX, nY, depth + 1, -1, -1,
                    List.of(colRay1.get(2), colRay2.get(2), colRayList.get(2)),centers.get(2)); // Left down
            color = color.add(colRay3.get(4));


            List<Color> colRay4 = adaptiveGrid2(nX, nY, depth + 1, 1, -1,
                    List.of(colRay1.get(3), colRay1.get(2), colRay3.get(3), colRayList.get(3)),centers.get(3)); // Right down
            color = color.add(colRay4.get(4));
        }

        // Returns a list with four current corners
        // colors, and the average of the colors
        colRayList.add(color.reduce(4));
        return colRayList;
    }

    public Point centerOfMainPixel(int nX, int nY, int j, int i) {
        //Image center
        Point pC = p0.add(v_t0.scale(distance));

        // Ratio (pixel width & height)
        double rX = width / nX;
        double rY = height / nY;

        //Pixel [i,j] center
        double yI = -1 * (i - (nY - 1) / 2d) * rY;
        double xJ = (j - (nX - 1) / 2d) * rX;

        // in the beginning pIJ is the center pixel, and if we need to move up and down or right and left
        Point pIJ = pC;
        if (xJ != 0) pIJ = pIJ.add(v_right.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(v_up.scale(yI));

        return pIJ;
    }

    /**
     * Calculate the four centers of the sub-pixels
     *
     * @param nX    number of columns (int)
     * @param nY    number of rows (int)
     * @param pIJ   Center of a pixel
     * @param depth The depth of the adaptive recursion
     * @return A list with four sub-pixels centers
     */
    public List<Point> centerOfPixels(int nX, int nY, Point pIJ, int depth) {

        List<Point> centerOfPixels = new LinkedList<>();

        // Ratio (pixel width & height)
        double rX = width / nX;
        double rY = height / nY;

        double axisXLength = rX / Math.pow(2, depth);
        double axisYLength = rY / Math.pow(2, depth);

        Point center1 = pIJ.add(v_up.scale(axisYLength)).add(v_right.scale(axisXLength));
        Point center2 = pIJ.add(v_up.scale(axisYLength)).add(v_right.scale(axisXLength * -1));
        Point center3 = pIJ.add(v_up.scale(axisYLength * -1)).add(v_right.scale(axisXLength * -1));
        Point center4 = pIJ.add(v_up.scale(axisYLength * -1)).add(v_right.scale(axisXLength));

        centerOfPixels.add(center1);
        centerOfPixels.add(center2);
        centerOfPixels.add(center3);
        centerOfPixels.add(center4);

        return centerOfPixels;
    }

    /**
     * Generate the rays from the camera to the object
     * and go through the view plane and the focal plane.
     * <p>
     * The return rays is refer to wich quarter it is.
     *
     * @param nX number of columns (int)
     * @param nY number of rows (int)
     * @param depth The depth of the recursion
     * @param signX The X sign of the quarter
     * @param signY The Y sign of the quarter
     * @param pIJ The center of the quarter
     * @return List of rays from the lens of the camera to the p(i,j) in the view plane (Ray)
     */
    public List<Ray> constructRayAdaptive(int nX, int nY, int depth, int signX, int signY, Point pIJ) {

        List<Ray> rays = new LinkedList<>();

        // Ratio (pixel width & height)
        double rX = width / nX;
        double rY = height / nY;

        double axisXLength = rX / Math.pow(2, depth);
        double axisYLength = rY / Math.pow(2, depth);


        // If the first time, we need the right-up corner
        if (depth == STARTING_DEPTH) {
            // Right up
            Point pIJ1 = pIJ.add(v_right.scale(axisXLength * signX)).add(v_up.scale(axisYLength * signY));
            Vector vIJ1 = pIJ1.subtract(p0);
            rays.add(new Ray(p0, vIJ1));
        }

        // If we are in the 2nd quarter, we need the left-down corner
        if (signX < 0 && signY > 0) {
            Point pIJ3 = pIJ.add(v_right.scale(axisXLength * -signX)).add(v_up.scale(axisYLength * -signY)); // Left down
            Vector vIJ3 = pIJ3.subtract(p0);
            rays.add(new Ray(p0, vIJ3));
        }

        // If we are in the first quarter (or it's the first time) we need the left-down, left-up and right-down corners too
        if (signX > 0 && signY > 0) {

            Point pIJ2 = pIJ.add(v_right.scale(axisXLength * -signX)).add(v_up.scale(axisYLength * signY)); // Left up
            Vector vIJ2 = pIJ2.subtract(p0);
            rays.add(new Ray(p0, vIJ2));

            Point pIJ3 = pIJ.add(v_right.scale(axisXLength * -signX)).add(v_up.scale(axisYLength * -signY)); // Left down
            Vector vIJ3 = pIJ3.subtract(p0);
            rays.add(new Ray(p0, vIJ3));

            Point pIJ4 = pIJ.add(v_right.scale(axisXLength * signX)).add(v_up.scale(axisYLength * -signY)); // Right down
            Vector vIJ4 = pIJ4.subtract(p0);
            rays.add(new Ray(p0, vIJ4));
        }

        // If we are in the 3rd quarter we need the right-down corner
        if (signX < 0 && signY < 0) {
            Point pIJ4 = pIJ.add(v_right.scale(axisXLength * -signX)).add(v_up.scale(axisYLength * signY)); // Right down
            Vector vIJ4 = pIJ4.subtract(p0);
            rays.add(new Ray(p0, vIJ4));
        }

        // return the ray go through the pixel
        return rays;
    }
}
