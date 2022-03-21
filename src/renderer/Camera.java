package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Camera {
    private Point position;
    private Vector v1;
    private Vector v2;
    private Vector v3;

    private double height;
    private double width;
    private double distance;


    /**
     * getters
     */
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
        // the cross product between 2 normalized vector is also normalize
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
     * @param nX - number of pixels for width
     * @param nY - number of pixels for height
     * @param j  - number of column in row
     * @param i  - number of row in column
     * @return A ray from the camera to a given pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point imageCenter = position.add(v1.scale(distance));

        // calculate the pixel center
        double yI = -1 * (i - (nY - 1) / 2d) * height / nY;
        double xJ = (j - (nX - 1) / 2d) * width / nX;

        if (!Util.isZero(xJ)) imageCenter = imageCenter.add(v3.scale(xJ));
        if (!Util.isZero(yI)) imageCenter = imageCenter.add(v2.scale(yI));

        return new Ray(position, imageCenter.subtract(position));
    }
}
