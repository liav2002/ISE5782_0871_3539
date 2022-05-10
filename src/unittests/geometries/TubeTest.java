/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

package unittests.geometries;

import geometries.Intersectable.GeoPoint;
import geometries.Triangle;
import geometries.Tube;
import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Tube class
 *
 * @author1 Eyal
 * @authon2 Liav Ariel
 */
public class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Tube tb = new Tube(1d,
                new Ray(
                        new Point(1, 0, 0),
                        new Vector(0, 0, 1d)));

        assertEquals("Bad normal to plane",
                new Vector(0, 1d, 0),
                tb.getNormal(new Point(1d, 1d, 3d)));

        // =============== Boundary Values Tests ==================
        // TC10: Point is facing the head of the tube's ray
        Point facingPnt = new Point(1d, 1d, 0);
        assertEquals("Bad normal to plane", new Vector(0, 1d, 0), tb.getNormal(new Point(1d, 1d, 0)));

    }

    /**
     * Test method for {@link geometries.Tube#findGeoIntersections(Ray)}.
     */
    @Test
    public void findGeoIntersections() {
        Triangle T1 = new Triangle(new Point(5, 5, 5), new Point(0, 5, 5), new Point(5, 0, 5));
        LinkedList<GeoPoint> L = new LinkedList<GeoPoint>();
        L.clear();
        L.add(new GeoPoint(T1, new Point(4, 4, 5)));
        assertEquals("ERROR: findGeoIntersections() Does not work well.",
                L, T1.findGeoIntersections(new Ray(new Point(1, 1, 1), new Vector(3, 3, 4))));

        //Ray is orthogonal to The Triangle
        L.clear();
        L.add(new GeoPoint(T1, new Point(3, 3, 5)));
        assertEquals("ERROR: findGeoIntersections() Does not work well.",
                L, T1.findGeoIntersections(new Ray(new Point(3, 3, 0), new Vector(0, 0, 1))));

        //Ray parallels to Triangle
        L.clear();
        assertEquals("ERROR: findGeoIntersections() Does not work well.",
                null, T1.findGeoIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))));

        //Ray does not intersects with Triangle and is not parallel
        L.clear();
        assertEquals("ERROR: findGeoIntersections() Does not work well.",
                null, T1.findGeoIntersections(new Ray(new Point(3, 3, 0), new Vector(-1, 0, -1))));


        //Ray starts from triangle and goes inside (Parallel)
        L.clear();
        assertEquals("ERROR: findGeoIntersections() Does not work well.",
                null, T1.findGeoIntersections(new Ray(new Point(4, 4, 5), new Vector(1, 1, 0))));
        //Ray starts from triangle and goes outside
        L.clear();
        assertEquals("ERROR: findGeoIntersections() Does not work well.",
                null, T1.findGeoIntersections(new Ray(new Point(4, 4, 5), new Vector(1, 0, 1))));

        //Ray starts from triangle and is orthogonal
        L.clear();
        assertEquals("ERROR: findGeoIntersections() Does not work well.",
                null, T1.findGeoIntersections(new Ray(new Point(4, 4, 5), new Vector(0, 0, 1))));

    }

}