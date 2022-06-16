

package unittests.geometries;

import geometries.Intersectable;
import geometries.Plane;
import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Plane class
 *
 * @author1 Eyal
 * @authon2 Liav Ariel
 */
public class PlaneTest {

    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================

        // TC01: Vertices on a same line
        try {
            new Plane(new Point(0, 0, 1),
                    new Point(0, 1, 0), new Point(0, 0.5, 0.5));
            fail("Constructed a plane with vertices on a same line");
        } catch (IllegalArgumentException e) {
            //pass
        }

        // TC02: Converging points
        try {
            new Plane(new Point(1, 0, 0),
                    new Point(0, 1, 0), new Point(0, 1, 0));
            fail("Constructed a plane with Converging points");
        } catch (IllegalArgumentException e) {
            //pass
        }

    }

    /**
     * Test method for {@link geometries.Plane#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to plane",
                new Vector(sqrt3, sqrt3, sqrt3),
                pl.getNormal(new Point(0, 0, 1)));
    }

    /**
     * Test method for {@link geometries.Plane#findGeoIntersections(Ray)}.
     */
    @Test
    public void findGeoIntersections() {
        Plane P1 = new Plane(
                new Point(0, 0, 0),
                new Point(0, 5, 0),
                new Point(5, 0, 0)
        );
        Plane P2 = new Plane(
                new Point(5, 5, 5),
                new Point(0, 5, 5),
                new Point(5, 0, 5)
        );

        LinkedList<Intersectable.GeoPoint> L = new LinkedList<Intersectable.GeoPoint>();
        //ray intersects plane
        L.clear();
        L.add(new Intersectable.GeoPoint(P2, new Point(0, 5, 5)));
        assertEquals("ERROR: findIntersections() Does not work well.", L, P2.findGeoIntersections(new Ray(new Point(0, -1, -1), new Vector(0, 1, 1))));

        //ray parallel to plane in plane
        L.clear();
        assertEquals("ERROR: findIntersections() Does not work well.", null, P2.findGeoIntersections(new Ray(new Point(5, 5, 5), new Vector(0, 1, 0))));

        //ray parallel to plane in outside
        L.clear();
        assertEquals("ERROR: findIntersections() Does not work well.", null, P2.findGeoIntersections(new Ray(new Point(5, 5, 4), new Vector(0, 1, 0))));

        //ray is orthogonal to the plane
        L.clear();
        L.add(new Intersectable.GeoPoint(P2, new Point(1, 1, 5)));
        assertEquals("ERROR: findIntersections() Does not work well.", L, P2.findGeoIntersections(new Ray(new Point(1, 1, 0), new Vector(0, 0, 1))));

        //ray starts on plane to out
        L.clear();
        assertEquals("ERROR: findIntersections() Does not work well.", null, P2.findGeoIntersections(new Ray(new Point(5, 4, 5), new Vector(0, 0, 1))));

        //ray q0 = plane q0
        L.clear();
        assertEquals("ERROR: findIntersections() Does not work well.", null, P2.findGeoIntersections(new Ray(new Point(5, 5, 5), new Vector(0, 1, 1))));
    }

}