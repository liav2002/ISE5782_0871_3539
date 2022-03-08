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

}