package unittest.geometries;

import geometries.Geometries;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
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
    @Test
    public void findIntersections() {
        Geometries geos = new Geometries(
                new Sphere(new Point(2d, 2d, 0), 1d),
                new Triangle(new Point(-3d, 1d, 0), new Point(-3d, 4d, 1d), new Point(-3d, 4d, -1d)),
                new Polygon(new Point(5d, 4d, -1d), new Point(5d, 4d, 1d), new Point(5d, 1d, 1d),new Point(5d, 1d, 0)));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Not all the shapes are intersects
        List<Point> result = geos.findIntersections(new Ray(new Point(0, 2d, 0),
                new Vector(1d, 0, 0)));

        assertEquals("Some of the shapes are intersects", 3, result.size());

        // =============== Boundary Values Tests ==================
        // TC02: All shapes are intersects
        result = geos.findIntersections(new Ray(new Point(-4d, 2d, 0),
                new Vector(1d, 0, 0)));
        assertEquals("All shapes are intersects", 4, result.size());

        // TC03: One shape only intersects
        result = geos.findIntersections(new Ray(new Point(-1d, 2d, 0),
                new Vector(-1d, 0, 0)));
        assertEquals("One shape only intersects", 1, result.size());

        // TC04: No shape is intersects
        result = geos.findIntersections(new Ray(new Point(8d, 2d, 0),
                new Vector(1d, 0, 0)));
        assertNull("No shape is intersects", result);

        // TC05: List is empty
        geos = new Geometries();
        result = geos.findIntersections(new Ray(new Point(-1d, 2d, 0),
                new Vector(-1d, 0, 0)));
        assertNull("List is empty", result);
    }
}
