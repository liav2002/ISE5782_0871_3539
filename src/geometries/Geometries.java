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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class Geometries extends Intersectable {
    private List<Intersectable> list;

    public Geometries() {
        this.list = new
                LinkedList<>();
    }

    public Geometries(Intersectable... Geometries) {
        this.list = new
                LinkedList<>();

        for (Intersectable intersectable : Geometries) {
            list.add(intersectable);
        }

    }

    public void add(Intersectable... Geometries) {
        for (Intersectable intersectable : Geometries) {
            list.add(intersectable);
        }
    }

    public List<Intersectable> getList() {
        return list;
    }


    /**
     * get the closest intersection of the given ray with the Intersectable
     *
     * @param ray the ray to trace
     * @return the closest intersection point
     */
    public GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = this.findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }
        return intersections.stream().min(Comparator.comparingDouble(pt1 -> ray.getP0().distanceSquared(pt1.point))).orElse(null);
    }


    /**
     * Implement interface function
     *
     * @param ray the interacting ray
     * @return all intersections points
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> ret = new LinkedList<GeoPoint>();
        for (Intersectable shape : list) {
            var geoIntersection = shape.findGeoIntersections(ray, maxDistance);
            if (geoIntersection == null) {
                continue;
            } // if there is no intersections points - continue

            for (var gPnt : geoIntersection) {
                ret.add(gPnt);
            }
        }
        return ret.isEmpty() ? null : ret;
    }
}
