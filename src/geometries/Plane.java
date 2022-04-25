/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;


public class Plane extends Geometry {
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
    protected LinkedList<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        LinkedList<GeoPoint> L = new LinkedList<GeoPoint>();
        L.clear();

        if (Util.isZero(normal.dotProduct(ray.getDir()))) {
            return null;
        }
        try {
            double t = q0.subtract(ray.getP0()).dotProduct(normal);
            t = t / normal.dotProduct(ray.getDir());
            if (Util.isZero(t)) {
                return null;
            }
            L.add(new GeoPoint(this, ray.getPoint(t)));
        } catch (Exception e) {
            return null;
        }
        return L;
    }

    @Override
    public String toString() {
        return "Plane{ point = " + this.q0 + " normal = " + this.normal + "}";
    }
}
