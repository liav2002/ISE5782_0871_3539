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
import primitives.Vector;

/**
 * Cylinder class represents two-dimensional cylinder in 3D Cartesian coordinate
 * system, a type of tube but with height.
 *
 * @author1 Eyal Seckbach
 * @author2 Liav Ariel
 */

import java.util.LinkedList;
import java.util.List;

public class Cylinder extends Tube {
    private double height;

    /**
     * Implement default constructor.
     *
     * @param height - cylinder's height.
     * @param radius - cylinder's base radius.
     * @param axisRay - cylinder's direction on the 3D space.
     */
    public Cylinder(double height, double radius, Ray axisRay) {
        super(radius, axisRay);
        this.height = height;
    }

    @Override
    /**
     * Calculate the cylinder's normal vector to given point.
     *
     * @param pnt - point for calc the normal ratio to the point.
     * @retun normal vector.
     */
    public Vector getNormal(Point pnt) {
        Point topBaseCenter = this.axisRay.getPoint(this.height);//the center of the top base of the cylinder
        //if the point is on the bottom base make plane normal calculation:
        if (pnt.distance(this.axisRay.getP0()) <= this.radius) {//only on the base the distance between the center of the cylinder and the point is less then the radius, according to the Triangle inequality rule.
            return this.axisRay.getDir().normalize().scale(-1);//the normal is the ray direction*-1 because we want to ger outside of the cylinder
        } else if (pnt.distance(topBaseCenter) <= this.radius) {//the same calculation as before, just for the top base
            return this.axisRay.getDir();
        }
        return super.getNormal(pnt);
    }

    /**
     * getter for height.
     * @return Cylinder's height.
     */
    public double getHeight() {
        return this.height;
    }
    
    private Point planesIntersection(Plane cylinderPlane, Ray ray) {
        List<Point> planesIntersections = cylinderPlane.findIntersections(ray);
        if (planesIntersections != null && planesIntersections.get(0).distance(cylinderPlane.getQ0()) < this.radius) {
            return planesIntersections.get(0);
        }
        return null;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double distance) {
        List<GeoPoint> ret = super.findGeoIntersections(ray, distance);
        if (ret == null) {
            ret = new LinkedList<GeoPoint>();
        } else {
            System.out.print("");
        }
        Plane bottomPlane = new Plane(this.axisRay.getDir(), this.axisRay.getP0());
        Plane topPlane = new Plane(this.axisRay.getDir(), this.axisRay.getPoint(this.height));
        ret.removeIf(point -> !onCylinder(point.point));//remove all the points that out of the cylinder boundary
        Point intersectingPoint = planesIntersection(topPlane, ray);
        addIfNotNullOrFar(ret, intersectingPoint, ray.getP0(), distance);
        intersectingPoint = planesIntersection(bottomPlane, ray);
        addIfNotNullOrFar(ret, intersectingPoint, ray.getP0(), distance);
        if (ret.size() == 0) {
            return null;
        }
        return ret;
    }

    private boolean onCylinder(Point pt) {
        return false;
    }

    /**
     * add geoPoint to list only if it not null
     *
     * @param lst      the list
     * @param pt       the point to add (converted to geoPoint
     * @param dest     the destination to compare the distance from
     * @param distance teh max distance
     */
    private void addIfNotNullOrFar(List<GeoPoint> lst, Point pt, Point dest, double distance) {
        if (pt != null && pt.distance(dest) <= distance) {
            lst.add(new GeoPoint(this, pt));
        }
    }

    @Override
    public String toString() {
        return "Cylinder{" + " height=" + height + super.toString() + "}";
    }
}