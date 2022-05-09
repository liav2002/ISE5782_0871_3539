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

import static primitives.Util.alignZero;

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
        double T1 = alignZero(Tm - Th);
        double T2 = alignZero(Tm + Th);
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

    /**
     * Calculate intersection points for a sphere and a ray, using equation:
     * u=O-P0
     * tm=v*u
     * d=sqrt(|u|^2-tn^2)
     * th=sqrt(r^2-d^2)
     * t1,2=tm+-th
     * Pi=P0+ti*v
     */

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        if (center.equals(ray.getP0())) { // Ray head is the center of the sphere
            return List.of(new GeoPoint(this, center.add(ray.getDir().scale(radius))));
        }

        Vector u = center.subtract(ray.getP0());
        double tm = alignZero(ray.getDir().dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));

        if (d >= radius) { // no points to return
            return null;
        }

        double th1 = alignZero(Math.sqrt(radius * radius - d * d));
        double th2 = alignZero(-1 * Math.sqrt(radius * radius - d * d));

        if (tm - th1 <= 0 && tm - th2 <= 0) // no points to return
        {
            return null;
        }

        List<GeoPoint> ret = new LinkedList<>();
        if (tm - th1 > 0 && alignZero(tm - th1 - maxDistance) <=0 ) {
            ret.add(new GeoPoint(this, ray.getPoint(tm - th1)));
        }

        if (tm - th2 > 0 && alignZero(tm - th1 - maxDistance) <=0) {
            ret.add(new GeoPoint(this, ray.getPoint(tm - th2)));
        }

        return ret;
    }
}
