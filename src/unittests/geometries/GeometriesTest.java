

package unittests.geometries;

import geometries.*;
import org.junit.Test;
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
    /**
     * Test method for {@link geometries.Geometries#findGeoIntersections(Ray)}.
     */

    @Test
    public void findGeoIntersections() {
        Geometries g1 = new Geometries();
        Geometries g2 = new Geometries(new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(1, 1, 0)), new Sphere(new Point(3, 3, 4), 3), new Triangle(new Point(3.75, 2.75, 0), new Point(2.75, 3.75, 0), new Point(2.75, 2.75, 1)));

        assertNull("ERROR: problem with empty seen.", g1.findGeoIntersections(new Ray(new Point(1, 1, 1), new Vector(89, 7, 6))));
        assertNull("ERROR: problem with seen full but no intersection.", g2.findGeoIntersections(new Ray(new Point(7, 7, 30), new Vector(0, 1, 0))));
        assertEquals("ERROR: problem with seen with part of the geometries intersections.", 2, g2.findGeoIntersections(new Ray(new Point(3, 3, 1), new Vector(0, 0, -1))).size());
        assertEquals("ERROR: problem with seen with all of the geometries intersections.", 4, g2.findGeoIntersections(new Ray(new Point(3, 3, 9), new Vector(0, 0, -1))).size());
    }
}
