

package unittests.geometries;

import geometries.Intersectable;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;


public class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(Point)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tri = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to plane", new Vector(sqrt3, sqrt3, sqrt3), tri.getNormal(new Point(0, 0, 1)));
    }

    /**
     * Test method for {@link geometries.Triangle#findGeoIntersections(Ray)}.
     */
    @Test
    public void testFindGeoIntersections() {

        Triangle T1 = new Triangle(new Point(5, 5, 5), new Point(0, 5, 5), new Point(5, 0, 5));
        LinkedList<Intersectable.GeoPoint> L = new LinkedList<Intersectable.GeoPoint>();

        //Ray intersects with Triangle
        L.clear();
        L.add(new Intersectable.GeoPoint(T1, new Point(4, 4, 5)));
        assertEquals("ERROR: findGeoIntersections() Does not work well.", L, T1.findGeoIntersections(new Ray(new Point(1, 1, 1), new Vector(3, 3, 4))));

        //Ray is orthogonal to The Triangle
        L.clear();
        L.add(new Intersectable.GeoPoint(T1, new Point(3, 3, 5)));
        assertEquals("ERROR: findGeoIntersections() Does not work well.", L, T1.findGeoIntersections(new Ray(new Point(3, 3, 0), new Vector(0, 0, 1))));

        //Ray parallels to Triangle
        L.clear();
        assertEquals("ERROR: findGeoIntersections() Does not work well.", null, T1.findGeoIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))));

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
        assertEquals("ERROR: findGeoIntersections() Does not work well.", null, T1.findGeoIntersections(new Ray(new Point(4, 4, 5), new Vector(0, 0, 1))));

    }

}