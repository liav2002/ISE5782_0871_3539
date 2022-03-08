package geometries;

import primitives.Point;
import primitives.Vector;


public class Plane implements Geometry {
    private Point q0;
    private Vector normal;

    public Point getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }

    public Plane(Point p1, Point p2, Point p3) {
        final Vector v1 = new Vector(p1.subtract(p2));
        final Vector v2 = new Vector(p2.subtract(p3));
        this.normal = v1.crossProduct(v2).normalize();
        this.q0 = p1;
    }

    public Plane(Vector normal, Point p) {
        this.normal = normal.normalize();
        this.q0 = p;
    }


    @Override
    public Vector getNormal(Point p) {
        // the normal not depends on the point in Plane
        return normal;
    }

    @Override
    public String toString() {
        return "Plane{ point = " + this.q0 + " normal = " + this.normal + "}";
    }
}
