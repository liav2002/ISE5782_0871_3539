/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

package unittest.geometries;

import geometries.Triangle;
import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
     * Test method for {@link geometries.Triangle#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        Triangle tl = new Triangle(
                new Point(1d, 0, 0),
                new Point(2d, 2d, 0),
                new Point(3d, 0, 0)
        );
        Point pnt = new Point(0, 0, 3d);
        // TC01: Inside triangle
        List<Point> result = tl.findIntersections(new Ray(pnt, new Vector(2d, 1d, -3d)));
        assertEquals("Inside triangle", List.of(new Point(2, 1, 0)), result);
        // TC02: Outside against edge

        result = tl.findIntersections(new Ray(pnt, new Vector(1d, 1d, -3d)));
        assertNull("Outside against edge", result);

        // TC03: Outside against vertex
        result = tl.findIntersections(new Ray(pnt, new Vector(2d, 3d, -3d)));
        assertNull("Outside against vertex", result);

        // =============== Boundary Values Tests ==================

        // TC04: On edge
        result = tl.findIntersections(new Ray(pnt, new Vector(2d, 0, -3d)));
        assertNull("On edge", result);

        // TC05: In vertex
        result = tl.findIntersections(new Ray(pnt, new Vector(1d, 0, -3d)));
        assertNull("In vertex", result);

        // TC06: On edge's continuation
        result = tl.findIntersections(new Ray(pnt, new Vector(3d, 4d, -3d)));
        assertNull("On edge's continuation", result);
    }

}