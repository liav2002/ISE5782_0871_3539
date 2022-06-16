

package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * Cylinder class represents two-dimensional plane in 3D Cartesian coordinate
 * system
 *
 * @author1 Eyal Seckbach
 * @author2 Liav Ariel
 */

import static primitives.Util.alignZero;

public class Plane extends Geometry {
    private Point q0;
    private Vector normal;

    /**
     * getter to q0
     * @return point q0.
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * getter to normal
     * @return plane's normal.
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * Implement default constructor.
     *
     * @param p1 - point 1.
     * @param p2 - point 2.
     * @param p3 - point 3.
     *
     * with 3 points we can calculate normal, and choose p1 to be q0,
     * and we have everything we need for represent a plane in 3D model.
     */
    public Plane(Point p1, Point p2, Point p3) {
        final Vector v1 = new Vector(p1.subtract(p2));
        final Vector v2 = new Vector(p2.subtract(p3));
        this.normal = v1.crossProduct(v2).normalize();
        this.q0 = p1;
    }

    /**
     * Implement another basic constructor.
     *
     * @param normal - plane's normal.
     * @param p - point on normal.
     *
     * This constructor is more simple, and no calculates are need.
     */
    public Plane(Vector normal, Point p) {
        this.normal = normal.normalize();
        this.q0 = p;
    }

    /**
     * getter for normal.
     *
     * @param p - point, not necessary on this geometry, but it's an interface function. and we must override it with the same parameters.
     * @return plane's normal.
     */
    @Override
    public Vector getNormal(Point p) {
        // the normal not depends on the point in Plane
        return normal;
    }

    /**
     * calculating of intersections of a ray with our plane.
     * @param ray - some ray in our scene.
     * @return a list of points of intersections between the ray to the plane.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (q0.equals(ray.getP0())) {
            return null;
        }
        if (Util.isZero(normal.dotProduct(ray.getDir()))) { //ray and normal are parallel
            return null;
        }
        double t = alignZero(normal.dotProduct(q0.subtract(ray.getP0())) / normal.dotProduct(ray.getDir()));
        if (t <= 0) { //there is no intersection points
            return null;
        }

        Point intersection = ray.getPoint(t);
        //if(!ray.isPointInRange(intersection,maxDistance)){
        if(t>maxDistance){
            return null;
        }

        List ret = new LinkedList<GeoPoint>(); //we dont using List.of so we could remove points while using polygon findIntersections
        ret.add(new GeoPoint(this, intersection));
        return ret;
    }

    @Override
    public String toString() {
        return "Plane{ point = " + this.q0 + " normal = " + this.normal + "}";
    }
}
