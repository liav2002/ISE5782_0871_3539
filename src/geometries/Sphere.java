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

public class Sphere implements Geometry {
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
    public List<Point> findIntersections(Ray ray) {

        if(center.equals(ray.getP0())){ // Ray head is the center of the sphere
            return List.of(center.add(ray.getDir().scale(radius)));
        }

        Vector u = center.subtract(ray.getP0());
        double tm = Util.alignZero(ray.getDir().dotProduct(u));
        double d=Util.alignZero(Math.sqrt(u.lengthSquared()-tm*tm));

        if (d>=radius) { // no points to return
            return null;
        }

        double th1 = Util.alignZero(Math.sqrt(radius*radius-d*d));
        double th2 = Util.alignZero(-1*Math.sqrt(radius*radius-d*d));

        if(tm-th1<=0&&tm-th2<=0) // no points to return
        {
            return null;
        }

        List<Point> ret = new LinkedList<>();
        if (tm-th1>0){
            Point pnt1 = ray.getPoint(tm-th1);
            ret.add(pnt1);
        }
        if (tm-th2>0){
            Point pnt2 = ray.getPoint(tm-th2);
            ret.add(pnt2);
        }
        return ret;
    }

    @Override
    public Vector getNormal(Point p) {
        if (Util.isZero(p.lengthSquared(center) - radius * radius))
            return new Vector(this.center.subtract(p)).normalize();
        throw new IllegalArgumentException("Point" + p + " not in the Sphere");
    }
}
