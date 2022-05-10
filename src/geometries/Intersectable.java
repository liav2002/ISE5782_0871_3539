package geometries;

import primitives.*;
import java.util.List;

<<<<<<< HEAD
/**
 * Defining an interface. for calculation of intersections with ray.
 *
 * @author1 Eyal Seckbach
 * @authon2 Liav Ariel
 */
=======
>>>>>>> 9c0f4daa4cb8ecacd7f7ea5f3054f857519afcd1
public abstract class Intersectable {

    /**
     * Given a ray, find all the points where the ray intersects the sphere
     *
     * @param ray The ray to check for intersections with.
     * @return A list of points.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }


    /**
     * This function returns a list of all the intersections of the ray with the geometry of the scene
     *
     * @param ray The ray to find intersections with.
     * @return A list of GeoPoints.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersections(ray,Double.POSITIVE_INFINITY);
    }

    /**
     * "Finds the intersections of a ray with the Earth's surface, up to a maximum distance."
     *
     * The function is declared as `public final` and `static`. The `final` keyword means that the function cannot be
     * overridden by a subclass. The `static` keyword means that the function is a class method, not an instance method
     *
     * @param ray The ray to intersect with the GeoJsonFeature.
     * @param maxDistance The maximum distance to search for intersections.
     * @return A list of GeoPoints.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * > Finds the intersection points of the ray with the surface of the object
     *
     * @param ray The ray to intersect with the GeoPoint.
     * @return A list of GeoPoints that are the intersections of the ray with the object.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    /**
     * > A GeoPoint is a Geometry that is a Point
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * A constructor for the GeoPoint class.
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * This function returns the geometry of the feature
         *
         * @return The geometry of the object.
         */
        public Geometry getGeometry() {
            return geometry;
        }

        /**
         * Returns the point.
         *
         * @return The point object.
         */
        public Point getPoint() {
            return point;
        }

        /**
         * If the object is not null, and is an instance of GeoPoint, and the geometry and point are equal, then return
         * true
         *
<<<<<<< HEAD
         * @param o - some object from geometries or primitives
         * @return boolean true or false if equality or not
=======
         * @param obj The object to compare to.
         * @return The hashcode of the object.
>>>>>>> 9c0f4daa4cb8ecacd7f7ea5f3054f857519afcd1
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof GeoPoint)) return false;
            GeoPoint other = (GeoPoint)obj;
            return this.geometry.equals(other.geometry) && this.point.equals(other.point);
        }

        /**
         * The toString() method returns a string representation of the object
         *
         * @return The toString() method is being overridden to return the values of the geometry and point variables.
         */
        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
