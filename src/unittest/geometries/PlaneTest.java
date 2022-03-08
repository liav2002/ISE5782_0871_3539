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
 * @author Eyal
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

}