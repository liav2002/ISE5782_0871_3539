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

public class Sphere extends Geometry {
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
    protected LinkedList<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        LinkedList<GeoPoint> L = new LinkedList<GeoPoint>();
        Vector u = center.subtract(ray.getP0());
        double Tm = ray.getDir().dotProduct(u);
        double d = Math.sqrt(u.length() * u.length() - (Tm * Tm));
        if (d >= radius) {
            return null;
        }
        double Th = Math.sqrt(radius * radius - (d * d));
        double T1 = Util.alignZero(Tm - Th);
        double T2 = Util.alignZero(Tm + Th);
        if (T1 > 0) {
            L.add(new GeoPoint(this, ray.getPoint(T1)));
        }
        if (T2 > 0) {
            L.add(new GeoPoint(this, ray.getPoint(T2)));
        }
        if (L.isEmpty()) {
            return null;
        }
        return L;
    }

    @Override
    public Vector getNormal(Point p) {
//        if (Util.isZero(p.distanceSquared(center) - radius * radius))
            return new Vector(this.center.subtract(p)).normalize();
//        throw new IllegalArgumentException("Point" + p + " not in Sphere " + this);
    }
}
