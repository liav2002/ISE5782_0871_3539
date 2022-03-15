package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Cylinder extends Tube {
    private double height;

    public Cylinder(double height, double radius, Ray axisRay) {
        super(radius, axisRay);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point pnt) {
        Point topBaseCenter = this.axisRay.getPoint(this.height);//the center of the top base of the cylinder
        //if the point is on the bottom base make plane normal calculation:
        if (pnt.length(this.axisRay.getP0()) <= this.radius) {//only on the base the distance between the center of the cylinder and the point is less then the radius, according to the Triangle inequality rule.
            return this.axisRay.getDir().normalize().scale(-1);//the normal is the ray direction*-1 because we want to ger outside of the cylinder
        } else if (pnt.length(topBaseCenter) <= this.radius) {//the same calculation as before, just for the top base
            return this.axisRay.getDir();
        }
        return super.getNormal(pnt);
    }

    public double getHeight() {
        return this.height;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
    
    @Override
    public String toString() {
        return "Cylinder{" + " height=" + height + super.toString() + "}";
    }
}
