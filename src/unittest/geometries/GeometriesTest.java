package unittest.geometries;

import geometries.Geometries;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Cylinder class
 *
 * @author1 Eyal
 * @authon2 Liav Ariel
 */

public class GeometriesTest {
    @Test
    public void findIntersections()
    {
        //check if list is null (BVA)
        try
        {
            Ray r = new Ray(new Point(1, 1, 0), new Vector(1, 1, 1));
            Geometries g = new Geometries();
            g.findIntersections(r);

            fail("list is null");
        } catch (IllegalArgumentException ex) {
            assertEquals("list is null", ex.getMessage());
        }
        assertTrue(true);

        //no shapes intersection (BVA)
        Ray r = new Ray(new Point(1, 1, 0), new Vector(1, 1, 1));
        Geometries g = new Geometries();
        List<Point> l = g.findIntersections(r);
        assertEquals(0,l.size());

        //one shapes intersection (BVA)
        Ray r1 = new Ray(new Point(1, 1, 0), new Vector(1, 1, 1));
        Geometries g1 = new Geometries();
        l=g.findIntersections(r);
        assertEquals(1,l.size());

        //not all shapes intersections (EP)

        //all shapes intersections (BVA)

    }
}
