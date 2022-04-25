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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Defining an interface. for calculation of insersections with ray.
 *
 * @author1 Eyal
 * @authon2 Liav Ariel
 */
public abstract class Intersectable {
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    //Finds intersections between a ray and a body
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     * The class `GeoPoint` has two fields, `geometry` and `point`, and a `toString` function
     */
    public static class GeoPoint {

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        // A field of the class.
        public Geometry geometry;

        // A point in the geometry.
        public Point point;

        /**
         * equals function
         *
         * @param o
         * @return boolean true or false if equality or not
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(this.geometry, geoPoint.geometry) &&
                    Objects.equals(this.point, geoPoint.point);
        }

        @Override
        // A function that returns a string.
        public String toString() {
            return "point: " + point + ", geometry: " + geometry;
        }


    }


}
