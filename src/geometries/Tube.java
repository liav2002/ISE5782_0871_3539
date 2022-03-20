package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

public class Tube implements Geometry {
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
    public List<Point> findIntersections(Ray ray) {
        //for using less functions we storing all teh parameters in other variables
        double rayOriginX = ray.getP0().getX();
        double rayOriginY = ray.getP0().getY();
        double rayOriginZ = ray.getP0().getZ();
        double tubeOriginX = this.axisRay.getP0().getX();
        double tubeOriginY = this.axisRay.getP0().getY();
        double tubeOriginZ = this.axisRay.getP0().getZ();
        double rayDirectionX = ray.getDir().getX();
        double rayDirectionY = ray.getDir().getY();
        double rayDirectionZ = ray.getDir().getZ();
        double tubeDirectionX = this.axisRay.getDir().getX();
        double tubeDirectionY = this.axisRay.getDir().getY();
        double tubeDirectionZ = this.axisRay.getDir().getZ();

        //the discriminate for the quadratic equation
        double v2 = 2 * rayOriginY * rayDirectionY * tubeDirectionX * tubeDirectionX
                + 2 * rayOriginZ * rayDirectionZ * tubeDirectionX * tubeDirectionX
                - 2 * rayDirectionY * tubeOriginY * tubeDirectionX * tubeDirectionX
                - 2 * rayDirectionZ * tubeOriginZ * tubeDirectionX * tubeDirectionX
                - 2 * rayOriginY * rayDirectionX * tubeDirectionX * tubeDirectionY
                - 2 * rayOriginX * rayDirectionY * tubeDirectionX * tubeDirectionY
                + 2 * rayDirectionY * tubeOriginX * tubeDirectionX * tubeDirectionY
                + 2 * rayDirectionX * tubeOriginY * tubeDirectionX * tubeDirectionY
                + 2 * rayOriginX * rayDirectionX * tubeDirectionY * tubeDirectionY
                + 2 * rayOriginZ * rayDirectionZ * tubeDirectionY * tubeDirectionY
                - 2 * rayDirectionX * tubeOriginX * tubeDirectionY * tubeDirectionY
                - 2 * rayDirectionZ * tubeOriginZ * tubeDirectionY * tubeDirectionY
                - 2 * rayOriginZ * rayDirectionX * tubeDirectionX * tubeDirectionZ
                - 2 * rayOriginX * rayDirectionZ * tubeDirectionX * tubeDirectionZ
                + 2 * rayDirectionZ * tubeOriginX * tubeDirectionX * tubeDirectionZ
                + 2 * rayDirectionX * tubeOriginZ * tubeDirectionX * tubeDirectionZ
                - 2 * rayOriginZ * rayDirectionY * tubeDirectionY * tubeDirectionZ
                - 2 * rayOriginY * rayDirectionZ * tubeDirectionY * tubeDirectionZ
                + 2 * rayDirectionZ * tubeOriginY * tubeDirectionY * tubeDirectionZ
                + 2 * rayDirectionY * tubeOriginZ * tubeDirectionY * tubeDirectionZ
                + 2 * rayOriginX * rayDirectionX * tubeDirectionZ * tubeDirectionZ
                + 2 * rayOriginY * rayDirectionY * tubeDirectionZ * tubeDirectionZ
                - 2 * rayDirectionX * tubeOriginX * tubeDirectionZ * tubeDirectionZ
                - 2 * rayDirectionY * tubeOriginY * tubeDirectionZ * tubeDirectionZ;
        double v1 = v2;
        double v = v2;
        double v3 = rayDirectionY * rayDirectionY * tubeDirectionX * tubeDirectionX + rayDirectionZ * rayDirectionZ * tubeDirectionX * tubeDirectionX
                - 2 * rayDirectionX * rayDirectionY * tubeDirectionX * tubeDirectionY + rayDirectionX * rayDirectionX * tubeDirectionY * tubeDirectionY + rayDirectionZ * rayDirectionZ * tubeDirectionY * tubeDirectionY
                - 2 * rayDirectionX * rayDirectionZ * tubeDirectionX * tubeDirectionZ
                - 2 * rayDirectionY * rayDirectionZ * tubeDirectionY * tubeDirectionZ + rayDirectionX * rayDirectionX * tubeDirectionZ * tubeDirectionZ + rayDirectionY * rayDirectionY * tubeDirectionZ * tubeDirectionZ;
        double discriminant = (v * v2) - 4 * v3 * (rayOriginY * rayOriginY * tubeDirectionX * tubeDirectionX + rayOriginZ * rayOriginZ * tubeDirectionX * tubeDirectionX
                - 2 * rayOriginY * tubeOriginY * tubeDirectionX * tubeDirectionX + tubeOriginY * tubeOriginY * tubeDirectionX * tubeDirectionX
                - 2 * rayOriginZ * tubeOriginZ * tubeDirectionX * tubeDirectionX + tubeOriginZ * tubeOriginZ * tubeDirectionX * tubeDirectionX
                - 2 * rayOriginX * rayOriginY * tubeDirectionX * tubeDirectionY
                + 2 * rayOriginY * tubeOriginX * tubeDirectionX * tubeDirectionY
                + 2 * rayOriginX * tubeOriginY * tubeDirectionX * tubeDirectionY
                - 2 * tubeOriginX * tubeOriginY * tubeDirectionX * tubeDirectionY +
                rayOriginX * rayOriginX * tubeDirectionY * tubeDirectionY + rayOriginZ * rayOriginZ * tubeDirectionY * tubeDirectionY
                - 2 * rayOriginX * tubeOriginX * tubeDirectionY * tubeDirectionY +
                tubeOriginX * tubeOriginX * tubeDirectionY * tubeDirectionY
                - 2 * rayOriginZ * tubeOriginZ * tubeDirectionY * tubeDirectionY +
                tubeOriginZ * tubeOriginZ * tubeDirectionY * tubeDirectionY
                - 2 * rayOriginX * rayOriginZ * tubeDirectionX * tubeDirectionZ
                + 2 * rayOriginZ * tubeOriginX * tubeDirectionX * tubeDirectionZ
                + 2 * rayOriginX * tubeOriginZ * tubeDirectionX * tubeDirectionZ
                - 2 * tubeOriginX * tubeOriginZ * tubeDirectionX * tubeDirectionZ
                - 2 * rayOriginY * rayOriginZ * tubeDirectionY * tubeDirectionZ
                + 2 * rayOriginZ * tubeOriginY * tubeDirectionY * tubeDirectionZ
                + 2 * rayOriginY * tubeOriginZ * tubeDirectionY * tubeDirectionZ
                - 2 * tubeOriginY * tubeOriginZ * tubeDirectionY * tubeDirectionZ +
                rayOriginX * rayOriginX * tubeDirectionZ * tubeDirectionZ + rayOriginY * rayOriginY * tubeDirectionZ * tubeDirectionZ
                - 2 * rayOriginX * tubeOriginX * tubeDirectionZ * tubeDirectionZ +
                tubeOriginX * tubeOriginX * tubeDirectionZ * tubeDirectionZ
                - 2 * rayOriginY * tubeOriginY * tubeDirectionZ * tubeDirectionZ +
                tubeOriginY * tubeOriginY * tubeDirectionZ * tubeDirectionZ -
                tubeDirectionX * tubeDirectionX * this.radius * this.radius -
                tubeDirectionY * tubeDirectionY * this.radius * this.radius -
                tubeDirectionZ * tubeDirectionZ * this.radius * this.radius);
        //-b for the quadratic equation
        final double Bminus = -2 * rayOriginY * rayDirectionY * tubeDirectionX * tubeDirectionX
                - 2 * rayOriginZ * rayDirectionZ * tubeDirectionX * tubeDirectionX
                + 2 * rayDirectionY * tubeOriginY * tubeDirectionX * tubeDirectionX
                + 2 * rayDirectionZ * tubeOriginZ * tubeDirectionX * tubeDirectionX
                + 2 * rayOriginY * rayDirectionX * tubeDirectionX * tubeDirectionY
                + 2 * rayOriginX * rayDirectionY * tubeDirectionX * tubeDirectionY
                - 2 * rayDirectionY * tubeOriginX * tubeDirectionX * tubeDirectionY
                - 2 * rayDirectionX * tubeOriginY * tubeDirectionX * tubeDirectionY
                - 2 * rayOriginX * rayDirectionX * tubeDirectionY * tubeDirectionY
                - 2 * rayOriginZ * rayDirectionZ * tubeDirectionY * tubeDirectionY
                + 2 * rayDirectionX * tubeOriginX * tubeDirectionY * tubeDirectionY
                + 2 * rayDirectionZ * tubeOriginZ * tubeDirectionY * tubeDirectionY
                + 2 * rayOriginZ * rayDirectionX * tubeDirectionX * tubeDirectionZ
                + 2 * rayOriginX * rayDirectionZ * tubeDirectionX * tubeDirectionZ
                - 2 * rayDirectionZ * tubeOriginX * tubeDirectionX * tubeDirectionZ
                - 2 * rayDirectionX * tubeOriginZ * tubeDirectionX * tubeDirectionZ
                + 2 * rayOriginZ * rayDirectionY * tubeDirectionY * tubeDirectionZ
                + 2 * rayOriginY * rayDirectionZ * tubeDirectionY * tubeDirectionZ
                - 2 * rayDirectionZ * tubeOriginY * tubeDirectionY * tubeDirectionZ
                - 2 * rayDirectionY * tubeOriginZ * tubeDirectionY * tubeDirectionZ
                - 2 * rayOriginX * rayDirectionX * tubeDirectionZ * tubeDirectionZ
                - 2 * rayOriginY * rayDirectionY * tubeDirectionZ * tubeDirectionZ
                + 2 * rayDirectionX * tubeOriginX * tubeDirectionZ * tubeDirectionZ
                + 2 * rayDirectionY * tubeOriginY * tubeDirectionZ * tubeDirectionZ;
        //the denominator for the quadratic equation
        final double aTwo = 2 * v3;
        //no intersection or tangent
        if (discriminant <= 0) {
            return null;
        }

        //there must be 2 intersection points
        List<Point> ret = new LinkedList<Point>();//we using linked list so we could remove points if using cylinder
        //add only the positive results to the list
        boolean listEmpty = true;
        double t = (Bminus - Math.sqrt(discriminant)) / aTwo;
        if (t > 0) {
            listEmpty = false;
            ret.add(ray.getPoint(t));
        }
        t = (Bminus + Math.sqrt(discriminant)) / aTwo;
        //(-b - Math.sqrt(discriminant)) / (2 * a);
        if (t > 0) {
            listEmpty = false;
            ret.add(ray.getPoint(t));
        }
        return listEmpty ? null : ret;
    }


    @Override
    public String toString() {
        return "Tube{" + " radius = " + this.radius + " axisRay = " + this.axisRay + "}";
    }
}
