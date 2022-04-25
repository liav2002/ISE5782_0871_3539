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

import java.util.LinkedList;
import java.util.List;

public class Triangle extends Polygon {
    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);
    }

    @Override
    public String toString() {
        return "Triangle{" + super.toString() + "}";
    }

    @Override
    protected LinkedList<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        LinkedList<GeoPoint> L = plane.findGeoIntersectionsHelper(ray);
        if(L == null){
            return null;
        }
        Vector v0 = vertices.get(1).subtract(vertices.get(0));
        Vector v1 = vertices.get(2).subtract(vertices.get(0));
        Vector v2 = L.get(0).point.subtract(vertices.get(0));
        double dot00 = v0.dotProduct(v0);
        double dot01 = v0.dotProduct(v1);
        double dot02 = v0.dotProduct(v2);
        double dot11 = v1.dotProduct(v1);
        double dot12 = v1.dotProduct(v2);

        double invDenom = 1 / (dot00 * dot11 - dot01 * dot01);
        double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
        double v = (dot00 * dot12 - dot01 * dot02) * invDenom;
// Check if point is in triangle
        if((u >= 0) && (v >= 0) && (u + v < 1))
        {
            L.getFirst().geometry = this;
            return L;
        }
        return null;
    }
}
