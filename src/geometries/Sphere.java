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

/**
 * Cylinder class represents two-dimensional Sphere in 3D Cartesian coordinate
 * system.
 *
 * @author1 Eyal Seckbach
 * @author2 Liav Ariel
 */
public class Sphere extends Geometry {
    private Point center;
    private double radius;

    /**
     * getter for center
     * @return Sphere's center.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * getter for radius
     * @return Sphere's radius.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Implement default constructor
     * @param center - Sphere's center.
     * @param radius - Sphere's radius.
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }


    @Override
    public String toString() {
        return "Sphere{" + " center=" + center + " radius = " + this.radius + "}";
    }

    @Override
    public Vector getNormal(Point p) {
//        if (Util.isZero(p.distanceSquared(center) - radius * radius))
        return new Vector(this.center.subtract(p)).normalize();
//        throw new IllegalArgumentException("Point" + p + " not in Sphere " + this);
    }

    /**
<<<<<<< HEAD
     * calculating of intersections of a ray with our sphere.
     * @param ray - some ray in our scene.
     * @return a list of points of intersections between the ray to the sphere.
     */
=======
     * Calculate intersection points for a sphere and a ray, using equation:
     * u=O-P0
     * tm=v*u
     * d=sqrt(|u|^2-tn^2)
     * th=sqrt(r^2-d^2)
     * t1,2=tm+-th
     * Pi=P0+ti*v
     */

>>>>>>> 9c0f4daa4cb8ecacd7f7ea5f3054f857519afcd1
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
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
        if (tm - th1 > 0 && alignZero(tm - th1 - maxDistance) <= 0) {
            ret.add(new GeoPoint(this, ray.getPoint(tm - th1)));
        }

        if (tm - th2 > 0 && alignZero(tm - th1 - maxDistance) <= 0) {
            ret.add(new GeoPoint(this, ray.getPoint(tm - th2)));
        }

<<<<<<< HEAD
    /**
     * calculate and return normalized normal vector.
     * @param p - point on normal.
     * @return normalized normal vector.
     */
    @Override
    public Vector getNormal(Point p) {
//        if (Util.isZero(p.distanceSquared(center) - radius * radius))
            return new Vector(this.center.subtract(p)).normalize();
//        throw new IllegalArgumentException("Point" + p + " not in Sphere " + this);
=======
        return ret;
>>>>>>> 9c0f4daa4cb8ecacd7f7ea5f3054f857519afcd1
    }
}
