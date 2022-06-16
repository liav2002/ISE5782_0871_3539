

package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * Cylinder class represents two-dimensional Triangle in 3D Cartesian coordinate
 * system.
 *
 * @author1 Eyal Seckbach
 * @author2 Liav Ariel
 */
public class Triangle extends Polygon {

    /**
     * Implement default constructor.
     *
     * @param a - point 1.
     * @param b - point 2.
     * @param c - point 3.
     */
    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);
    }

    @Override
    public String toString() {
        return "Triangle{" + super.toString() + "}";
    }

    /**
     * calculating of intersections of a ray with our triangle.
     * @param ray - some ray in our scene.
     * @return a list of points of intersections between the ray to the triangle.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> L = super.findGeoIntersectionsHelper(ray, maxDistance);
        if (L == null) {
            return null;
        }
        for (GeoPoint P :
                L) {
            P.geometry = this;
        }
        return L;
    }
}
