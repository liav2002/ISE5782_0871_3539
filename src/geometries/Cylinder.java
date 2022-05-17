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

import static primitives.Util.alignZero;

public class Cylinder extends Tube {
    private double height;

    /**
     * Implement default constructor.
     *
     * @param height  - cylinder's height.
     * @param radius  - cylinder's base radius.
     * @param axisRay - cylinder's direction on the 3D space.
     */
    public Cylinder(double height, double radius, Ray axisRay) {
        super(radius, axisRay);
        this.height = height;
    }

    public Cylinder(double r, Point p1, Point p2) {
        super(r, p1, p2);
        this.height = p2.distance(p1);
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
     *
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
        List<GeoPoint> intersections = super.findGeoIntersectionsHelper(axisRay, distance);

        Point p0 = getAxisRay().getP0();
        Vector dir = getAxisRay().getDir();

        if (intersections != null) {
            LinkedList<GeoPoint> temp = new LinkedList<GeoPoint>();

            for (GeoPoint g : intersections) {
                double pointHeight = alignZero(g.point.subtract(p0).dotProduct(dir));
                if (pointHeight > 0 && pointHeight < height) {
                    temp.add(g);
                }
            }

            if (temp.isEmpty()) {
                intersections = null;
            } else {
                intersections = temp;
            }
        }

        List<GeoPoint> planeIntersection = new Plane(dir, p0).findGeoIntersections(axisRay);
        if (planeIntersection != null) {
            GeoPoint point = planeIntersection.get(0);
            if (point.point.equals(p0) || alignZero(point.point.subtract(p0).lengthSquared() - getRadius() * getRadius()) < 0) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                point.geometry = this;
                intersections.add(point);
            }
        }

        Point p1 = p0.add(dir.scale(height));

        planeIntersection = new Plane(dir, p1).findGeoIntersections(axisRay);
        if (planeIntersection != null) {
            GeoPoint point = planeIntersection.get(0);
            if (point.point.equals(p1) || alignZero(point.point.subtract(p1).lengthSquared() - getRadius() * getRadius()) < 0) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                point.geometry = this;
                intersections.add(point);
            }
        }

        if (intersections != null) {
            for (GeoPoint g : intersections) {
                g.geometry = this;
            }
        }

        return intersections;
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