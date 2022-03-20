package unittest.geometries;

import geometries.Tube;
import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

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
     * Test method for {@link geometries.Tube#findIntersections(Ray)}.
     */
    @Test
    public void testfindIntsersections() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: basic intersection test
        Tube tb = new Tube(1, new Ray(new Point(5d, 0, 0), new Vector(0, 1d, 0)));
        Ray ray = new Ray(new Point(0, 2d, 0), new Vector(1d, 0, 0));

        assertEquals("basic intersection test", ray.getPoint(4), tb.findIntersections(ray).get(0));
        assertEquals("basic intersection test", ray.getPoint(6), tb.findIntersections(ray).get(1));

        //TC02: The ray parallel to the tube and outside from him
        tb = new Tube(20, new Ray(new Point(3, 0, 0), new Vector(0, 1, 0)));
        ray = new Ray(new Point(0, 5, -25), new Vector(0, 1, 0));
        assertNull("the ray is parallel to the tube", tb.findIntersections(ray));

        //TC03: The ray parallel to the tube and in  inside him
        tb = new Tube(20, new Ray(new Point(3, 0, 0), new Vector(0, 1, 0)));
        ray = new Ray(new Point(0, 5, 0), new Vector(0, 1, 0));
        assertNull("the ray is parallel to the tube", tb.findIntersections(ray));
    }

}