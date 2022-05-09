/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;

public class Ray {
    /**
     * beginning point of ray
     */
    private Point p0;

    /**
     * normalized direction vector of ray,
     */
    private Vector dir;

    private static final double DELTA = 0.1;
    public Ray(Point p, Vector Vec, Vector n){
        Vector Delta = n.scale(n.dotProduct(Vec) > 0 ? DELTA : - DELTA);
        Point point = p.add(Delta);
        p0 = point;
        dir = Vec.normalize();
    }
    /**
     * ray constructor
     *
     * @param p0  beginning point of ray
     * @param dir gets direction vector of ray and normalize it
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    // Getters
    public Point getP0() {
        return p0;
    }

    /**
     * Gets point on ray
     *
     * @param t scale
     * @return point
     */
    public Point getPoint(double t) {
        if (dir.mult(t).equals(Point.ZERO)) {
            return p0;
        }
        return p0.add(dir.scale(t));
    }


    public Vector getDir() {
        return dir;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        return (o instanceof Ray) &&
                this.p0.equals(((Ray) o).p0) &&
                this.dir.equals(((Ray) o).dir);
    }


    /**
     * It finds the closest intersection point to the camera
     *
     * @param intersections a list of all the intersections found by the ray.
     * @return The closest point to the ray's starting point.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        if (intersections == null || intersections.isEmpty()) {
            return null;
        }

        GeoPoint ClosestPoint = intersections.get(0);
        double shortestD = p0.distanceSquared(ClosestPoint.point);
        double currentD;

        for (GeoPoint point : intersections) {
            currentD = p0.distanceSquared(point.point);
            if (currentD < shortestD) {
                shortestD = currentD;
                ClosestPoint = point;
            }
        }
        return ClosestPoint;
    }

    @Override
    public String toString() {
        return "Ray{" + p0 + dir + '}';
    }

}