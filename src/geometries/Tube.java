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

public class Tube extends Geometry {
    protected double radius;
    protected Ray axisRay;

    public Tube(double radius, Ray axisRay) {
        this.radius = radius;
        this.axisRay = axisRay;
    }

    @Override
    public Vector getNormal(Point p) {
        double t = axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
        if (t == 0) { /* point is facing the head of the tube's ray */
            return p.subtract(axisRay.getP0()).normalize();
        }
        return p.subtract(axisRay.getPoint(t)).normalize();
    }

    public double getRadius() {
        return radius;
    }

    public Ray getAxisRay() {
        return this.axisRay;
    }

//    @Override
//    public List<Point> findIntersections(Ray ray) {
//        //for better performances -> when need to do power we saving to temp instead of calling function twice
//        double t1, t2;
//
//        //the vectors and points that we need (insted of calling many functions many times we will call them once)
//        Point rayOrigin = ray.getP0();
//        Point tubeOrigin = this.axisRay.getP0();
//        Vector rayDirection = ray.getDir();
//        Vector tubeDirection = this.axisRay.getDir();
//
//        //some equation variables
//        double m = tubeDirection.dotProduct(tubeOrigin.subtract(tubeOrigin)) / tubeDirection.lengthSquared();
//        double n = rayDirection.lengthSquared() / tubeDirection.lengthSquared();
//
//        //discriminant variables
//        double a = rayDirection.lengthSquared()
//        + 2 * n * rayDirection.dotProduct(tubeDirection) + n * n * tubeDirection.lengthSquared();
//        double b = (-2) * m * tubeDirection.dotProduct(rayDirection)
//        - 2 * m * n * tubeDirection.lengthSquared();
//        double c = m * m * tubeDirection.lengthSquared() - this.radius * this.radius;
//
//        if (!rayOrigin.equals(Point.ZERO)) {
//            Vector rayOriginVec = new Vector(rayOrigin);
//            b += 2 * rayDirection.dotProduct(rayOriginVec)
//            + 2 * n * tubeDirection.dotProduct(rayOriginVec);
//            c += (-2) * m * tubeDirection.dotProduct(rayOriginVec);
//            c += rayOriginVec.lengthSquared();
//        }
//        if (!tubeOrigin.equals(Point.ZERO)) {
//            Vector tubeOriginVec = new Vector(tubeOrigin);
//            b += (-2) * tubeOriginVec.dotProduct(rayDirection)
//            - 2 * n * tubeDirection.dotProduct(tubeOriginVec);
//            c += 2 * m * tubeOriginVec.dotProduct(tubeDirection);
//            c += tubeOrigin.lengthSquared(Point.ZERO);
//
//        }
//        if (!tubeOrigin.equals(Point.ZERO) && !rayOrigin.equals(Point.ZERO)) {
//            Vector tubeOriginVec = new Vector(tubeOrigin);
//            Vector rayOriginVec = new Vector(rayOrigin);
//
//            c += (-2) * rayOriginVec.dotProduct(tubeOriginVec);
//        }
//
//        //calculate the discriminant
//        double discriminant = b * b - 4 * a * c;
//        if (discriminant < 0) {
//            return null;
//        }
//
//        LinkedList<Point> ret = new LinkedList<>();
//        double result1 = ((-b + Math.sqrt(discriminant)) / (2 * a));
//        double result2 = ((-b - Math.sqrt(discriminant)) / (2 * a));
//
//        ret.add(ray.getPoint(result1));
//        ret.add(ray.getPoint(result2));
//        return ret;
//    }

    @Override
    protected LinkedList<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }


    @Override
    public String toString() {
        return "Tube{" + " radius = " + this.radius + " axisRay = " + this.axisRay + "}";
    }
}
