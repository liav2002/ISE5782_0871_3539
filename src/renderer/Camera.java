/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

package renderer;

import geometries.Plane;
import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.isZero;
import static primitives.Util.random;

public class Camera {
    private Point position;

    private Vector v1; //Inside
    private Vector v2; // Up
    private Vector v3; // Right

    private double printInterval;
    private int threadsCount;
    private int numOfRays = 1;

    // the view plane
    private double height;  // the height of the camera from the screen
    private double width;  // the width of the camera from the screen
    private double distance; // the distance of the camera from the screen

    //Aperture properties
    private final int APERTURE_NUMBER_OF_POINTS = 100;

    //number with integer square for the matrix of points.
    private double apertureSize;
    private Point[] aperturePoints;

    //the focal plane parameters
    private double FP_distance;
    private Plane FOCAL_PLANE;

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
        // Setting the position of the object to the position of the mouse.
        this.position = position;

        // Normalizing the vector v1.
        this.v1 = v1.normalize();

        // Normalizing the vector.
        this.v2 = v2.normalize();

        // Setting the apertureSize to 0.
        this.apertureSize = 0;

        // Checking if the dot product of the two vectors is not zero.
        if (!isZero(v1.dotProduct(v2)))
            // if they not valid
            throw new IllegalArgumentException("Non vertical");

        // the cross product between 2 normalized vector is also normalize!
        v3 = this.v1.crossProduct(this.v2);
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

    public Camera setFPDistance(double distance) {
        this.FP_distance = distance;
        this.FOCAL_PLANE = new Plane(this.v1, this.position.add(this.v1.scale(FP_distance)));
        return this;
    }

    /**
     * setting the aperture size as the given parameter, and initialize the points array.
     *
     * @param size the given parameter.
     * @return the camera itself for farther initialization.
     */
    public Camera setApertureSize(double size) {
        this.apertureSize = size;

        /////initializing the points of the aperture.
        if (size != 0) initializeAperturePoint();

        return this;
    }

    /**
     * This function sets the number of rays to be casted by the camera and returns the camera object.
     *
     * @param numOfRays The number of rays that will be casted from the camera to the scene.
     * @return The camera object itself.
     */
    public Camera setNumOfRays(int numOfRays) {
        this.numOfRays = numOfRays;
        return this;
    }

    /**
     * the function that initialize the aperture size and the points that it represents.
     */
    private void initializeAperturePoint() {
        //the number of points in a row
        int pointsInRow = (int) Math.sqrt(this.APERTURE_NUMBER_OF_POINTS);

        //the array of point saved as an array
        this.aperturePoints = new Point[pointsInRow * pointsInRow];

        //calculating the initial values.
        double pointsDistance = (this.apertureSize * 2) / pointsInRow;

        //calculate the initial point to be the point with coordinates outside the aperture in the down left point, so we won`t have to deal with illegal vectors.
        Point initialPoint = this.position.add(this.v2.scale(-this.apertureSize - pointsDistance / 2).add(this.v3.scale(-this.apertureSize - pointsDistance / 2)));

        //initializing the points array
        for (int i = 1; i <= pointsInRow; i++) {
            for (int j = 1; j <= pointsInRow; j++) {
                this.aperturePoints[(i - 1) + (j - 1) * pointsInRow] = initialPoint.add(this.v2.scale(i * pointsDistance).add(this.v3.scale(j * pointsDistance)));
            }
        }
    }

    /**
     * The function constructs a list of rays through the center of the pixel and through a random point in the pixel
     *
     * @param nX number of pixels in the x axis
     * @param nY number of pixels in the y axis
     * @param j  the index of the pixel in the x axis
     * @param i  the row number of the pixel
     * @return A list of rays that are being sent from the camera to the scene.
     */
    public List<Ray> constructRayAnalising(int nX, int nY, int j, int i) {
        // Checking if the distance is zero. If it is, it throws an exception.
        if (isZero(distance)) throw new IllegalArgumentException("distance can't be 0");

        // Creating a new linked list of type Ray.
        LinkedList<Ray> rays = new LinkedList<>();

        // Calculating the width and height of each cell in the grid
        double rX = width / nX;
        double rY = height / nY;

        // Declaring a variable of type double called randX and randY.
        double randX, randY;


        // Calculating the center point of the pixel in the image.
        Point pCenterPixel = CalculatCenterPointInPixel(nX, nY, j, i);

        // Checking if the position is the same as the center pixel. If it is, it returns the rays.
        if (position.equals(pCenterPixel)) return rays;

        // Creating a ray from the camera to the center pixel of the image.
        rays.add(new Ray(position, pCenterPixel.subtract(position)));

        Point pInPixel;

        // Creating a for loop that will run for the number of rays that are being used.
        for (int k = 0; k < numOfRays; k++) {
            // Generating a random number between -rX/2 and rX/2.
            randX = random(-rX / 2, rX / 2);

            // Generating a random number between -rY/2 and rY/2.
            randY = random(-rY / 2, rY / 2);

            // Creating a new point that is a random distance from the center point.
            pInPixel = new Point(pCenterPixel.getX() + randX, pCenterPixel.getY() + randY, pCenterPixel.getZ());

            // Creating a ray from the camera to the pixel.
            if (!position.equals(pInPixel)) rays.add(new Ray(position, pInPixel.subtract(position)));
        }

        // Creating a new array of rays, and then it is looping through the array and setting each ray to a new ray.
        return rays;
    }

    /**
     * > The function calculates the center point of the pixel in the 3D space
     *
     * @param nX number of pixels in the width of the image
     * @param nY number of pixels in the vertical direction
     * @param j  the column number of the pixel in the image
     * @param i  the row number of the pixel in the image
     * @return the point in the middle of the screen.
     */
    private Point CalculatCenterPointInPixel(int nX, int nY, int j, int i) {
        // Finding the point that is distance units away from the position in the direction of the vector v1.
        Point pC = position.add(v1.scale(distance));

        // Creating a new Point object and assigning it to pIJ.
        Point pIJ = pC;

        // Calculating the width and height of each cell in the grid.
        double rY = height / nY;
        double rX = width / nX;

        // Calculating the y and x coordinate of the pixel in the image.
        double yI = -(i - (nY - 1) / 2d) * rY;
        double xJ = (j - (nX - 1) / 2d) * rX;

        // Checking if the value of xJ is not equal to zero.
        if (!isZero(xJ)) {
            // Adding the vector v3 to the vector pIJ.
            pIJ = pIJ.add(v3.scale(xJ));
        }

        // Checking if the value of yI is not equal to zero.
        if (!isZero(yI)) {
            // Adding the vector v2 to the vector pIJ.
            pIJ = pIJ.add(v2.scale(yI));
        }

        // Creating a new array of size n and filling it with the values of the array pIJ.
        return pIJ;
    }

    /**
     * This function takes a list of rays and returns the average color of the rays
     *
     * @param rays A list of rays to be traced.
     * @return The average color of the rays.
     */
    public Color AverageColor(List<Ray> rays) {
        // Creating a new Color object with the color black.
        Color color = Color.BLACK;

        // Iterating through the rays array
        for (Ray ray : rays) {

            // Tracing the ray and adding the color to the color variable.
            color = color.add(rayTracer.traceRay(ray));
        }

        // Reducing the number of rays to the number of colors.
        return color.reduce(rays.size());
    }

    /**
     * It returing the color for some pixel in our scene, using antialasing / depth of fields / none of them.
     *
     * @param c the column of the pixel
     * @param r the row of the pixel
     * @return The color of the pixel.
     */
    private Color castRay(int c, int r) {
        // Checking if the number of rays is not equal to 1.
        if (this.numOfRays != 1) {
            List<Ray> rays;
            // The above code is constructing the rays that will be used to analise the scene.
            rays = this.constructRayAnalising(imageWriter.getNx(), imageWriter.getNy(), c, r);

            // Checking if the aperture size is zero, if so,
            // it returns the average color of the rays, otherwise it returns
            // the averaged beam color of the rays.
            return isZero(this.apertureSize) ? AverageColor(rays) :
                    averagedBeamColor(constructRay(imageWriter.getNx(), imageWriter.getNy(), c, r));
        } else {
            // Constructing a ray from the camera through the pixel (c,r)
            Ray ray = constructRay(imageWriter.getNx(), imageWriter.getNy(), c, r);

            // It checks if the aperture size is zero, if it is,
            // it will return the color of the ray, if not, it will return
            // the average color of the beam.
            return isZero(this.apertureSize) ? rayTracer.traceRay(ray) : averagedBeamColor(ray);
        }
    }

    private Color castRayAntialiasing(int c, int r) {
        // The above code is constructing the rays that will be used to analise the scene.
        List<Ray> rays;
        rays = this.constructRayAnalising(imageWriter.getNx(), imageWriter.getNy(), c, r);

        // Checking if the aperture size is zero, if so, it returns the average color of the rays, otherwise it returns
        // the averaged beam color of the rays.
        return isZero(this.apertureSize) ? AverageColor(rays) :
                averagedBeamColor(constructRay(imageWriter.getNx(), imageWriter.getNy(), c, r));
    }

    /**
     * the function that goes through every point in the array and calculate the average color.
     *
     * @param ray the original ray to construct the surrounding beam.
     * @return the average color of the beam.
     */
    private Color averagedBeamColor(Ray ray) {
        // Initializing the averageColor to black and the apertureColor to null.
        Color averageColor = Color.BLACK, apertureColor;
        // The number of points in the aperture.
        int numOfPoints = this.aperturePoints.length;
        // A ray that is used to trace the ray from the aperture point to the focal point.
        Ray apertureRay;
        // Finding the intersection point of the ray and the focal plane.
        Point focalPoint = this.FOCAL_PLANE.findGeoIntersections(ray).get(0).point;

        // A for each loop that goes through every point in the array and calculate the average color.
        for (Point aperturePoint : this.aperturePoints) {
            apertureRay = new Ray(aperturePoint, focalPoint.subtract(aperturePoint));
            apertureColor = rayTracer.traceRay(apertureRay);
            // Adding the color of the ray to the average color.
            averageColor = averageColor.add(apertureColor);
        }

        return averageColor.reduce(numOfPoints);
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
        if (!isZero(xJ)) imageCenter = imageCenter.add(v3.scale(xJ));
        if (!isZero(yI)) imageCenter = imageCenter.add(v2.scale(yI));

        return new Ray(position, imageCenter.subtract(position));
    }

    /**
     * Prints a grid of the specified color on the image
     *
     * @param interval The interval between grid lines.
     * @param color    The color of the grid lines.
     */
    public Camera printGrid(int interval, Color color) {
        // Printing the grid of the image.
        imageWriter.printGrid(interval, color);
        return this;
    }

    /**
     * Given the imageWriter and rayTracer, render the image
     */
    public Camera renderImage() {
        try {
            // Checking if the imageWriter is null. If it is null, it will throw exception
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            // Checking if the rayTracer is null. If it is null, it will throw exception
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBasic.class.getName(), "");
            }

            //rendering the image
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();

            // Checking if we try to use threads.
            if (threadsCount == 1) {
                //rendering image without using of threads (by-default)
                for (int i = 0; i < nX; i++) {
                    for (int j = 0; j < nY; j++) {
                        Color pixelColor = castRay(j, i);
                        imageWriter.writePixel(j, i, pixelColor);
                    }
                }
            } else {
                //rendering image with using of threads
                Pixel.initialize(nY, nX, printInterval);
                while (threadsCount-- > 0) {
                    new Thread(() -> {
                        for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()) {
                            Color pixelColor = castRay(pixel.col, pixel.row);
                            imageWriter.writePixel(pixel.col, pixel.row, pixelColor);
                        }

                    }).start();
                }
                Pixel.waitToFinish();
            }

        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
        return this;
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
     * Rotates the camera around the axes with the given angles
     *
     * @param amount vector of angles
     * @return the current camera
     */
    public Camera rotate(Vector amount) {
        return rotate(amount.getX(), amount.getY(), amount.getZ());
    }

    /**
     * Rotates the camera around the axes with the given angles
     *
     * @param x angles to rotate around the x axis
     * @param y angles to rotate around the y axis
     * @param z angles to rotate around the z axis
     * @return the current camera
     */
    public Camera rotate(double x, double y, double z) {
        v1.rotateX(x).rotateY(y).rotateZ(z);
        v2.rotateX(x).rotateY(y).rotateZ(z);
        v3 = v1.crossProduct(v2);

        return this;
    }

    /**
     * Write the image to the image file
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

    public Camera setDebugPrint(double k) {
        this.printInterval = k;
        return this;
    }

    public Camera setMultithreading(int n) {
        this.threadsCount = n;
        return this;
    }


}
