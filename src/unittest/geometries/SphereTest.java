package unittest.geometries;

import geometries.Sphere;
import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Sphere class
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
     * Test method for {@link geometries.Sphere#findIntersections(Ray)}.
     */
    @Test
    public void testfindIntsersections() {
        Sphere sphere = new Sphere(new Point (1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull("Ray's line out of sphere", sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point(1.5, 0, 0),
                new Vector(1d, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray start's inside and crosses sphere",
                List.of(new Point(2d,0,0)), result);

        // TC04: Ray starts after the sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point(2.1d, 0, 0),
                new Vector(1d, 1d, 0)));
        assertNull("Ray start's after sphere", result);

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1d,-1d,0), new Vector(1d, 1d, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray start's at sphere and goes inside",
                List.of(new Point(2d, 0, 0)), result);

        // TC12: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point(2d, 0, 0),
                new Vector(1d, 1d, 0)));
        assertNull("Ray start's at sphere and goes outside", result);

        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point(1d, -2d, 0),
                new Vector(0, 1d, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere (through center)",
                List.of(new Point(1d,-1d,0), new Point(1d,1d,0)), result);

        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1d,-1d,0), new Vector(0, 1d, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray start's at sphere and goes inside (through center)",
                List.of(new Point(1d, 1d, 0)), result);

        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(0.5d,0,0), new Vector(1d, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray start's inside sphere (through center)",
                List.of(new Point(2d, 0, 0)), result);

        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point(1d,0,0), new Vector(1d, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray start's at sphere's center",
                List.of(new Point(2d, 0, 0)), result);

        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point(1d,-1d,0), new Vector(0, -1d, 0)));
        assertNull("Ray start's at sphere and goes outside (through center)", result);

        // TC18: Ray starts after sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point(3d,0,0), new Vector(1d, 0, 0)));
        assertNull("Ray start's after sphere (through center)", result);

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point
        result = sphere.findIntersections(new Ray(new Point(0,1d,0), new Vector(1d, 0, 0)));
        assertNull("Ray starts before the tangent point", result);

        // TC20: Ray starts at the tangent point
        result = sphere.findIntersections(new Ray(new Point(1d,1d,0), new Vector(1d, 0, 0)));
        assertNull("Ray starts at the tangent point", result);

        // TC21: Ray starts after the tangent point
        result = sphere.findIntersections(new Ray(new Point(3d,1d,0), new Vector(1d, 0, 0)));
        assertNull("Ray starts after the tangent point", result);

        // **** Group: Special cases

        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result = sphere.findIntersections(new Ray(new Point(3d,0,0), new Vector(0, 1d, 0)));
        assertNull("ray is orthogonal to ray start to sphere's center line", result);
    }

}