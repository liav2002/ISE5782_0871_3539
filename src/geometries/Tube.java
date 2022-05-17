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

/**
 * Tube class represents two-dimensional tube in 3D Cartesian coordinate
 * system.
 *
 * @author1 Eyal Seckbach
 * @author2 Liav Ariel
 */
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Tube extends Geometry {
    protected double radius;
    protected Ray axisRay;

    /**
     * Implement default constructor.
     *
     * @param radius - Tube's base radius.
     * @param axisRay - Tube's direction on 3D space.
     */
    public Tube(double radius, Ray axisRay) {
        this.radius = radius;
        this.axisRay = axisRay;
    }
    public Tube(double radius, Point p1, Point p2) {
        this.radius = radius;
        this.axisRay = new Ray(p1, p1.subtract(p2));
    }

    @Override
    /**
     * Calculate the tube's normal vector to given point.
     *
     * @param p - point for calc the normal ratio to the point.
     * @return normal vector.
     */
    public Vector getNormal(Point p) {
        double t = axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
        if (t == 0) { /* point is facing the head of the tube's ray */
            return p.subtract(axisRay.getP0()).normalize();
        }
        return p.subtract(axisRay.getPoint(t)).normalize();
    }

    /**
     * getter for radius
     * @return Tube's radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * getter for axisRay
     * @return Tube's axisRay
     */
    public Ray getAxisRay() {
        return this.axisRay;
    }

    /**
     * Interface function, we don't use it on Tube, therefore we return null.
     * @return null.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
    {
        Vector d = axisRay.getDir();
        Vector v = axisRay.getDir();
        if(d.equals(v) || d.equals(v.scale(-1)))
            return null;

        LinkedList<GeoPoint> res = new LinkedList<GeoPoint>();
        Vector x = axisRay.getP0().subtract(axisRay.getP0());
        Vector nx = x.normalize();
        double dv = d.dotProduct(v);
        if(axisRay.getP0().equals(axisRay.getP0()) || nx.equals(v) || nx.equals(v.scale(-1)))
        {
            if(isZero(dv))
            {
                res.add(new GeoPoint(this, axisRay.getPoint(radius)));
                return res;
            }
            res.add(new GeoPoint(this, axisRay.getPoint(Math.sqrt(radius * radius / d.subtract(v.scale(dv)).lengthSquared()))));
            return res;
        }

        double xv = x.dotProduct(v);

        double a = 1 - dv * dv;
        double b = 2 * x.dotProduct(d) - 2 * dv * xv;
        double c = x.lengthSquared() - xv * xv - radius * radius;

        double disc = alignZero(b * b - 4 * a * c);

        if(disc < 0)
            return null;

        double t;

        if(disc == 0)
        {
            if(isZero(dv))
                return null;

            t = alignZero(-b / 2 * a);

            if(t <= 0)
                return null;

            res.add(new GeoPoint(this, axisRay.getPoint(t)));
            return res;
        }

        double sqrtDisc = Math.sqrt(disc);
        t = alignZero((-b + sqrtDisc) / 2 * a);

        if(t > 0)
            res.add(new GeoPoint(this, axisRay.getPoint(t)));

        t = alignZero((-b - sqrtDisc) / 2 * a);

        if(t > 0)
            res.add(new GeoPoint(this, axisRay.getPoint(t)));

        if(res.isEmpty())
            return null;
        return res;
    }

    @Override
    public String toString() {
        return "Tube{" + " radius = " + this.radius + " axisRay = " + this.axisRay + "}";
    }
}
