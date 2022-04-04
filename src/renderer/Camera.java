/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

package renderer;

import primitives.*;

import java.util.MissingResourceException;

public class Camera {
    private Point position;

    private Vector v1; //Inside
    private Vector v2; // Up
    private Vector v3; // Right


    // the view plane
    private double height;  // the height of the camera from the screen
    private double width;  // the width of the camera from the screen
    private double distance; // the distance of the camera from the screen

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;


    /**
     * constructor
     *
     * @param position camera's location
     * @param v1       the forward direction vector
     * @param v2       the up direction vector
     */
    public Camera(Point position, Vector v1, Vector v2) {
        this.position = position;
        this.v1 = v1.normalize();
        this.v2 = v2.normalize();

        if (v1.dotProduct(v2) != 0)
            // if they not valid
            throw new IllegalArgumentException("Non vertical");

        v3 = this.v1.crossProduct(this.v2);
        // the cross product between 2 normalized vector is also normalize!
    }


    // getters
    public Point getPosition() {
        return position;
    }

    public Vector getV1() {
        return v1;
    }

    public Vector getV2() {
        return v2;
    }

    public Vector getV3() {
        return v3;
    }

    public Camera setVPSize(double w, double h) {
        this.width = w;
        this.height = h;

        return this; // the Builder design pattern
    }

    public Camera setVPDistance(double d) {
        this.distance = d;

        return this; // the Builder design pattern
    }

    /**
     * Generate a ray from camera to a middle of a given pixel
     *
     * @param nX - number of columns
     * @param nY - number of rows
     * @param j  - the pixel index: number of column in row
     * @param i  - the pixel index: number of row in column
     * @return A ray from the camera to a given pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // the point that is in the image center = position + (distance * v1)
        Point imageCenter = position.add(v1.scale(distance));


        // Calculating the pixel center:
        // (height / nY): is the height ratio
        // (width / nY): is the width ratio
        double yI = -1 * (i - (nY - 1) / 2d) * height / nY;
        double xJ = (j - (nX - 1) / 2d) * width / nX;

        // Checking if the xJ and yI are zero, if not, it will add the xJ and yI to the imageCenter.
        if (!Util.isZero(xJ)) imageCenter = imageCenter.add(v3.scale(xJ));
        if (!Util.isZero(yI)) imageCenter = imageCenter.add(v2.scale(yI));

        return new Ray(position, imageCenter.subtract(position));
    }

    /**
     * Prints a grid of the specified color on the image
     *
     * @param interval The interval between grid lines.
     * @param color The color of the grid lines.
     */
    public void printGrid(int interval, Color color) {
        imageWriter.printGrid(interval, color);
    }

    /**
     * Given the imageWriter and rayTracer, render the image
     */
    public void renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBasic.class.getName(), "");
            }

            //rendering the image
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            for (int i = 0; i < nX; i++) {
                for (int j = 0; j < nY; j++) {
                    Ray ray = constructRay(nX, nY, j, i);
                    Color pixelColor = rayTracer.traceRay(ray);
                    imageWriter.writePixel(j, i, pixelColor);
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
    }


    /**
     * This function sets the image writer for the camera
     *
     * @param imageWriter The ImageWriter to use for writing images.
     * @return This.
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }


    /**
     * It sets the ray tracer to be used by the camera.
     *
     * @param rayTracer The ray tracer to use.
     * @return This.
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }


    /**
     * Write the image to the image file
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }
}
