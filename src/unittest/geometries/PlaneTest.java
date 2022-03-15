package unittest.geometries;

import geometries.Plane;
import org.junit.Test;
import primitives.Point;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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

    @Test
    public void testfindIntsersections() {
        Plane plane = new Plane(new Vector(0, 1, 0), new Point(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // **** Group: Ray neither orthogonal nor parallel
        // ****
        // ****

        // TC01: Ray intersects the plane
        List<Point> result = plane.findIntersections(new Ray(new Point(0, 1d, 0),
                new Vector(2d, -1d, 0)));

        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses plane", List.of(new Point(2d, 0, 0)), result);

        // TC02: Ray does not intersect the plane
        result = plane.findIntersections(new Ray(new Point(0, 1d, 0), new Vector(2d, 1d, 0)));
        assertNull("Ray does not crosses plane", result);

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // ****
        // ****

        // TC03: ray is included in the plane
        result = plane.findIntersections(new Ray(new Point(3d, 0, 0), new Vector(2d, 0, 0)));
        assertNull("Ray is included in the plane", result);

        //TC04: ray is not included in the plane
        result = plane.findIntersections(new Ray(new Point(0, 1d, 0), new Vector(2d, 0, 0)));
        assertNull("Ray is not included in the plane", result);

        // **** Group: Ray is orthogonal to the plane
        // ****
        // ****

        // TC05: ray is before the plane
        result = plane.findIntersections(new Ray(new Point(2d, -1d, 0), new Vector(0, 2d, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray is orthogonal before plane", List.of(new Point(2d, 0, 0)), result);

        // TC06: ray is in plane
        result = plane.findIntersections(new Ray(new Point(2d, 0, 0), new Vector(0, 2d, 0)));
        assertNull("Ray is orthogonal in plane", result);

        // TC07: ray is after the plane
        result = plane.findIntersections(new Ray(new Point(2d, 1d, 0), new Vector(0, 2d, 0)));
        assertNull("Ray is orthogonal after plane", result);

        // TC08: Ray is neither orthogonal nor parallel to plane and begins at the plane
        result = plane.findIntersections(new Ray(new Point(2d, 0, 0), new Vector(2d, -1d, 0)));
        assertNull("Ray is orthogonal neither orthogonal nor parallel in plane", result);

        // TC09: Ray is neither orthogonal nor parallel and begins in Q point
        result = plane.findIntersections(new Ray(new Point(1d, 0, 0), new Vector(1d, 1d, 0)));
        assertNull("Ray begins at Q point", result);
    }

}