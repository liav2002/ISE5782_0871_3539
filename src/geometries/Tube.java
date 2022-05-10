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

<<<<<<< HEAD
/**
 * Tube class represents two-dimensional tube in 3D Cartesian coordinate
 * system.
 *
 * @author1 Eyal Seckbach
 * @author2 Liav Ariel
 */
=======
import static primitives.Util.alignZero;

>>>>>>> 9c0f4daa4cb8ecacd7f7ea5f3054f857519afcd1
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

    @Override
    /**
     * Calculate the tube's normal vector to given point.
     *
     * @param p - point for calc the normal ratio to the point.
     * @retun normal vector.
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

<<<<<<< HEAD
    /**
     * Interface function, we don't use it on Tube, therefore we return null.
     * @return null.
     */
=======
>>>>>>> 9c0f4daa4cb8ecacd7f7ea5f3054f857519afcd1
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
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
        double discriminant = ((2 * rayOriginY * rayDirectionY * tubeDirectionX * tubeDirectionX + 2 * rayOriginZ * rayDirectionZ * tubeDirectionX * tubeDirectionX - 2 * rayDirectionY * tubeOriginY * tubeDirectionX * tubeDirectionX - 2 * rayDirectionZ * tubeOriginZ * tubeDirectionX * tubeDirectionX - 2 * rayOriginY * rayDirectionX * tubeDirectionX * tubeDirectionY - 2 * rayOriginX * rayDirectionY * tubeDirectionX * tubeDirectionY + 2 * rayDirectionY * tubeOriginX * tubeDirectionX * tubeDirectionY + 2 * rayDirectionX * tubeOriginY * tubeDirectionX * tubeDirectionY + 2 * rayOriginX * rayDirectionX * tubeDirectionY * tubeDirectionY + 2 * rayOriginZ * rayDirectionZ * tubeDirectionY * tubeDirectionY - 2 * rayDirectionX * tubeOriginX * tubeDirectionY * tubeDirectionY - 2 * rayDirectionZ * tubeOriginZ * tubeDirectionY * tubeDirectionY - 2 * rayOriginZ * rayDirectionX * tubeDirectionX * tubeDirectionZ - 2 * rayOriginX * rayDirectionZ * tubeDirectionX * tubeDirectionZ + 2 * rayDirectionZ * tubeOriginX * tubeDirectionX * tubeDirectionZ + 2 * rayDirectionX * tubeOriginZ * tubeDirectionX * tubeDirectionZ - 2 * rayOriginZ * rayDirectionY * tubeDirectionY * tubeDirectionZ - 2 * rayOriginY * rayDirectionZ * tubeDirectionY * tubeDirectionZ + 2 * rayDirectionZ * tubeOriginY * tubeDirectionY * tubeDirectionZ + 2 * rayDirectionY * tubeOriginZ * tubeDirectionY * tubeDirectionZ + 2 * rayOriginX * rayDirectionX * tubeDirectionZ * tubeDirectionZ + 2 * rayOriginY * rayDirectionY * tubeDirectionZ * tubeDirectionZ - 2 * rayDirectionX * tubeOriginX * tubeDirectionZ * tubeDirectionZ - 2 * rayDirectionY * tubeOriginY * tubeDirectionZ * tubeDirectionZ) * (2 * rayOriginY * rayDirectionY * tubeDirectionX * tubeDirectionX + 2 * rayOriginZ * rayDirectionZ * tubeDirectionX * tubeDirectionX - 2 * rayDirectionY * tubeOriginY * tubeDirectionX * tubeDirectionX - 2 * rayDirectionZ * tubeOriginZ * tubeDirectionX * tubeDirectionX - 2 * rayOriginY * rayDirectionX * tubeDirectionX * tubeDirectionY - 2 * rayOriginX * rayDirectionY * tubeDirectionX * tubeDirectionY + 2 * rayDirectionY * tubeOriginX * tubeDirectionX * tubeDirectionY + 2 * rayDirectionX * tubeOriginY * tubeDirectionX * tubeDirectionY + 2 * rayOriginX * rayDirectionX * tubeDirectionY * tubeDirectionY + 2 * rayOriginZ * rayDirectionZ * tubeDirectionY * tubeDirectionY - 2 * rayDirectionX * tubeOriginX * tubeDirectionY * tubeDirectionY - 2 * rayDirectionZ * tubeOriginZ * tubeDirectionY * tubeDirectionY - 2 * rayOriginZ * rayDirectionX * tubeDirectionX * tubeDirectionZ - 2 * rayOriginX * rayDirectionZ * tubeDirectionX * tubeDirectionZ + 2 * rayDirectionZ * tubeOriginX * tubeDirectionX * tubeDirectionZ + 2 * rayDirectionX * tubeOriginZ * tubeDirectionX * tubeDirectionZ - 2 * rayOriginZ * rayDirectionY * tubeDirectionY * tubeDirectionZ - 2 * rayOriginY * rayDirectionZ * tubeDirectionY * tubeDirectionZ + 2 * rayDirectionZ * tubeOriginY * tubeDirectionY * tubeDirectionZ + 2 * rayDirectionY * tubeOriginZ * tubeDirectionY * tubeDirectionZ + 2 * rayOriginX * rayDirectionX * tubeDirectionZ * tubeDirectionZ + 2 * rayOriginY * rayDirectionY * tubeDirectionZ * tubeDirectionZ - 2 * rayDirectionX * tubeOriginX * tubeDirectionZ * tubeDirectionZ - 2 * rayDirectionY * tubeOriginY * tubeDirectionZ * tubeDirectionZ)) - 4 * (rayDirectionY * rayDirectionY * tubeDirectionX * tubeDirectionX + rayDirectionZ * rayDirectionZ * tubeDirectionX * tubeDirectionX - 2 * rayDirectionX * rayDirectionY * tubeDirectionX * tubeDirectionY + rayDirectionX * rayDirectionX * tubeDirectionY * tubeDirectionY + rayDirectionZ * rayDirectionZ * tubeDirectionY * tubeDirectionY - 2 * rayDirectionX * rayDirectionZ * tubeDirectionX * tubeDirectionZ - 2 * rayDirectionY * rayDirectionZ * tubeDirectionY * tubeDirectionZ + rayDirectionX * rayDirectionX * tubeDirectionZ * tubeDirectionZ + rayDirectionY * rayDirectionY * tubeDirectionZ * tubeDirectionZ) * (rayOriginY * rayOriginY * tubeDirectionX * tubeDirectionX + rayOriginZ * rayOriginZ * tubeDirectionX * tubeDirectionX - 2 * rayOriginY * tubeOriginY * tubeDirectionX * tubeDirectionX + tubeOriginY * tubeOriginY * tubeDirectionX * tubeDirectionX - 2 * rayOriginZ * tubeOriginZ * tubeDirectionX * tubeDirectionX + tubeOriginZ * tubeOriginZ * tubeDirectionX * tubeDirectionX - 2 * rayOriginX * rayOriginY * tubeDirectionX * tubeDirectionY + 2 * rayOriginY * tubeOriginX * tubeDirectionX * tubeDirectionY + 2 * rayOriginX * tubeOriginY * tubeDirectionX * tubeDirectionY - 2 * tubeOriginX * tubeOriginY * tubeDirectionX * tubeDirectionY + rayOriginX * rayOriginX * tubeDirectionY * tubeDirectionY + rayOriginZ * rayOriginZ * tubeDirectionY * tubeDirectionY - 2 * rayOriginX * tubeOriginX * tubeDirectionY * tubeDirectionY + tubeOriginX * tubeOriginX * tubeDirectionY * tubeDirectionY - 2 * rayOriginZ * tubeOriginZ * tubeDirectionY * tubeDirectionY + tubeOriginZ * tubeOriginZ * tubeDirectionY * tubeDirectionY - 2 * rayOriginX * rayOriginZ * tubeDirectionX * tubeDirectionZ + 2 * rayOriginZ * tubeOriginX * tubeDirectionX * tubeDirectionZ + 2 * rayOriginX * tubeOriginZ * tubeDirectionX * tubeDirectionZ - 2 * tubeOriginX * tubeOriginZ * tubeDirectionX * tubeDirectionZ - 2 * rayOriginY * rayOriginZ * tubeDirectionY * tubeDirectionZ + 2 * rayOriginZ * tubeOriginY * tubeDirectionY * tubeDirectionZ + 2 * rayOriginY * tubeOriginZ * tubeDirectionY * tubeDirectionZ - 2 * tubeOriginY * tubeOriginZ * tubeDirectionY * tubeDirectionZ + rayOriginX * rayOriginX * tubeDirectionZ * tubeDirectionZ + rayOriginY * rayOriginY * tubeDirectionZ * tubeDirectionZ - 2 * rayOriginX * tubeOriginX * tubeDirectionZ * tubeDirectionZ + tubeOriginX * tubeOriginX * tubeDirectionZ * tubeDirectionZ - 2 * rayOriginY * tubeOriginY * tubeDirectionZ * tubeDirectionZ + tubeOriginY * tubeOriginY * tubeDirectionZ * tubeDirectionZ - tubeDirectionX * tubeDirectionX * this.radius * this.radius - tubeDirectionY * tubeDirectionY * this.radius * this.radius - tubeDirectionZ * tubeDirectionZ * this.radius * this.radius);
        //-b for the quadratic equation
        final double Bminus = -2 * rayOriginY * rayDirectionY * tubeDirectionX * tubeDirectionX - 2 * rayOriginZ * rayDirectionZ * tubeDirectionX * tubeDirectionX + 2 * rayDirectionY * tubeOriginY * tubeDirectionX * tubeDirectionX + 2 * rayDirectionZ * tubeOriginZ * tubeDirectionX * tubeDirectionX + 2 * rayOriginY * rayDirectionX * tubeDirectionX * tubeDirectionY + 2 * rayOriginX * rayDirectionY * tubeDirectionX * tubeDirectionY - 2 * rayDirectionY * tubeOriginX * tubeDirectionX * tubeDirectionY - 2 * rayDirectionX * tubeOriginY * tubeDirectionX * tubeDirectionY - 2 * rayOriginX * rayDirectionX * tubeDirectionY * tubeDirectionY - 2 * rayOriginZ * rayDirectionZ * tubeDirectionY * tubeDirectionY + 2 * rayDirectionX * tubeOriginX * tubeDirectionY * tubeDirectionY + 2 * rayDirectionZ * tubeOriginZ * tubeDirectionY * tubeDirectionY + 2 * rayOriginZ * rayDirectionX * tubeDirectionX * tubeDirectionZ + 2 * rayOriginX * rayDirectionZ * tubeDirectionX * tubeDirectionZ - 2 * rayDirectionZ * tubeOriginX * tubeDirectionX * tubeDirectionZ - 2 * rayDirectionX * tubeOriginZ * tubeDirectionX * tubeDirectionZ + 2 * rayOriginZ * rayDirectionY * tubeDirectionY * tubeDirectionZ + 2 * rayOriginY * rayDirectionZ * tubeDirectionY * tubeDirectionZ - 2 * rayDirectionZ * tubeOriginY * tubeDirectionY * tubeDirectionZ - 2 * rayDirectionY * tubeOriginZ * tubeDirectionY * tubeDirectionZ - 2 * rayOriginX * rayDirectionX * tubeDirectionZ * tubeDirectionZ - 2 * rayOriginY * rayDirectionY * tubeDirectionZ * tubeDirectionZ + 2 * rayDirectionX * tubeOriginX * tubeDirectionZ * tubeDirectionZ + 2 * rayDirectionY * tubeOriginY * tubeDirectionZ * tubeDirectionZ;
        //the denominator for the quadratic equation
        final double aTwo = 2 * (rayDirectionY * rayDirectionY * tubeDirectionX * tubeDirectionX + rayDirectionZ * rayDirectionZ * tubeDirectionX * tubeDirectionX - 2 * rayDirectionX * rayDirectionY * tubeDirectionX * tubeDirectionY + rayDirectionX * rayDirectionX * tubeDirectionY * tubeDirectionY + rayDirectionZ * rayDirectionZ * tubeDirectionY * tubeDirectionY - 2 * rayDirectionX * rayDirectionZ * tubeDirectionX * tubeDirectionZ - 2 * rayDirectionY * rayDirectionZ * tubeDirectionY * tubeDirectionZ + rayDirectionX * rayDirectionX * tubeDirectionZ * tubeDirectionZ + rayDirectionY * rayDirectionY * tubeDirectionZ * tubeDirectionZ);
        //no intersection or tangent
        if (discriminant <= 0) {
            return null;
        }

        //there must be 2 intersection points
        List<GeoPoint> ret = new LinkedList<GeoPoint>();//we using linked list so we could remove points if using cylinder
        //add only the positive results to the list
        boolean listEmpty = true;
        double t = (Bminus - Math.sqrt(discriminant)) / aTwo;
        if (t > 0 && alignZero(t - maxDistance) <= 0) {
            listEmpty = false;
            ret.add(new GeoPoint(this, ray.getPoint(t)));
        }
        t = (Bminus + Math.sqrt(discriminant)) / aTwo;
        //(-b - Math.sqrt(discriminant)) / (2 * a);
        if (t > 0 && alignZero(t - maxDistance) <= 0) {
            listEmpty = false;
            ret.add(new GeoPoint(this, ray.getPoint(t)));
        }
        return listEmpty ? null : ret;
    }
    @Override
    public String toString() {
        return "Tube{" + " radius = " + this.radius + " axisRay = " + this.axisRay + "}";
    }
}
