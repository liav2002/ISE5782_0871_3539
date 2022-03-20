package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;


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
    public List<Point> findIntersections(Ray ray) {
        if (q0.equals(ray.getP0())) {
            return null;
        }
        if (Util.isZero(normal.dotProduct(ray.getDir()))) { //ray and normal are parallel
            return null;
        }
        double t = Util.alignZero(normal.dotProduct(q0.subtract(ray.getP0())) / normal.dotProduct(ray.getDir()));
        if (t <= 0) { //there is no intersection points
            return null;
        }
        List ret = new LinkedList<Point>(); //we dont using List.of so we could remove points while using polygon findIntersections
        ret.add(ray.getPoint(t));
        return ret;
        //return List.of(ray.getPoint(t));
    }

    @Override
    public String toString() {
        return "Plane{ point = " + this.q0 + " normal = " + this.normal + "}";
    }
}
