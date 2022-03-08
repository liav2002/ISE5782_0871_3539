package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Tube implements Geometry {
    protected double radius;
    protected Ray axisRay;

    public Tube(double radius, Ray axisRay) {
        this.radius = radius;
        this.axisRay = axisRay;
    }

    @Override
    public Vector getNormal(Point p) {
        double t = axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
        if (t == 0) { /* point is facing the head of the tube's ray */
            return p.subtract(axisRay.getP0()).normalize();
        }
        return p.subtract(axisRay.getPoint(t)).normalize();
    }

    public double getRadius() {
        return radius;
    }

    public Ray getAxisRay() {
        return this.axisRay;
    }

    @Override
    public String toString() {
        return "Tube{" + " radius = " + this.radius + " axisRay = " + this.axisRay + "}";
    }
}
