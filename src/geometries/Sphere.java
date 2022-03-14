package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

public class Sphere implements Geometry {
    private Point center;
    private double radius;

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }


    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }


    @Override
    public String toString() {
        return "Sphere{" + " center=" + center + " radius = " + this.radius + "}";
    }

    @Override
    public List<Point> findIntersections(Ray ray)
    {
        return null;
    }

    @Override
    public Vector getNormal(Point p) {
        if (Util.isZero(p.lengthSquared(center) - radius * radius))
            return new Vector(this.center.subtract(p)).normalize();
        throw new IllegalArgumentException("Point" + p + " not in the Sphere");
    }
}
