package unittest.geometries;

import geometries.Sphere;
import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.awt.*;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Sphere class
 * @author Eyal
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

}