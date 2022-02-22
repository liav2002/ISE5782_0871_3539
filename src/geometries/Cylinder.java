package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube {
    private double height;

    public Cylinder(double height, double radius, Ray axisRay) {
        super(radius, axisRay);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    public double getHeight() {
        return this.height;
    }

    @Override
    public String toString() {
        return "Cylinder{" + " height=" + height + super.toString() + "}";
    }
}
