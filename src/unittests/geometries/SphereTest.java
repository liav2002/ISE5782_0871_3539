

package unittests.geometries;

import geometries.Intersectable;
import geometries.Sphere;
import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Sphere class
 *
 * @author1 Eyal
 * @authon2 Liav Ariel
 */
public class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sp = new Sphere(Point.ZERO, 1d);

        assertEquals("Bad normal to plane",
                new Vector(-1, 0, 0),
                sp.getNormal(new Point(1, 0, 0)));

    }

    /**
     * Test method for {@link geometries.Sphere#findGeoIntersections(Ray)}.
     */
    @Test
    public void testfindIntsersections() {

        Sphere s0 = new Sphere(new Point(0, 0, 0), 3);
        Sphere s1 = new Sphere(new Point(5, 5, 5), 5);
        LinkedList<Intersectable.GeoPoint> L = new LinkedList<Intersectable.GeoPoint>();

        // ray in circle
        L.clear();
        L.add(new Intersectable.GeoPoint(s1, new Point(5, 5, 0)));
        assertEquals("ERROR: FindIntersections() does not work well.", L, s1.findGeoIntersections(new Ray(new Point(5, 5, 4), new Vector(0, 0, -1))));

        // ray on circle and goes in
        L.clear();
        L.add(new Intersectable.GeoPoint(s1, new Point(5, 5, 0)));
        assertEquals("ERROR: FindIntersections() does not work well.", L, s1.findGeoIntersections(new Ray(new Point(5, 5, 10), new Vector(0, 0, -1))));

        // ray on circle and goes out
        L.clear();
        assertEquals("ERROR: FindIntersections() does not work well.", null, s1.findGeoIntersections(new Ray(new Point(5, 5, 0), new Vector(0, 0, -1))));

        // ray out from circle and touches it
        L.clear();
        assertEquals("ERROR: FindIntersections() does not work well.", null, s1.findGeoIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 1, 0))));

        // ray out from circle and gets inside it
        L.clear();
        L.add(new Intersectable.GeoPoint(s1, new Point(5, 5, 10)));
        L.add(new Intersectable.GeoPoint(s1, new Point(5, 5, 0)));
        assertEquals("ERROR: FindIntersections() does not work well.", L, s1.findGeoIntersections(new Ray(new Point(5, 5, 11), new Vector(0, 0, -1))));

        // ray out from circle and does not touch it
        L.clear();
        assertEquals("ERROR: FindIntersections() does not work well.", null, s1.findGeoIntersections(new Ray(new Point(5, 5, -1), new Vector(0, 0, -1))));

    }

}