

package unittests.primitives;

import geometries.Intersectable.GeoPoint;
import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Unit tests for primitives.Ray class
 *
 * @author1 Eyal
 * @authon2 Liav Ariel
 */
public class RayTest {
    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    public void testGetPoint() {
        try {
            Ray ray = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
            assertEquals("The function getPoint dont work correct", new Point(2, 0, 1), ray.getPoint(2));

            assertEquals("The function getPoint dont work correct", new Point(0, 0, 1), ray.getPoint(0));

        } catch (Exception e) {
        }
    }

    @Test
    public void testGetClosestGeoPoint() {
        try {
            // =============== Boundary Values Tests ==================
            Ray ray = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
            GeoPoint p1 = new GeoPoint(null, new Point(1, 0, 0));
            GeoPoint p2 = new GeoPoint(null, new Point(2, 0, 0));
            GeoPoint p3 = new GeoPoint(null, new Point(3, 0, 0));

            //The first point is closest to the beginning of the foundation
            List<GeoPoint> points = List.of(p1, p2, p3);
            assertEquals("", p1, ray.findClosestGeoPoint(points));

            //The last point is closest to the beginning of the foundation
            points = List.of(p2, p3, p1);
            assertEquals("", p1, ray.findClosestGeoPoint(points));

            //An empty list
            points = List.of(null);
            assertEquals("", null, ray.findClosestGeoPoint(points));

            // ============ Equivalence Partitions Tests ==============
            //A point in the middle of the list is closest to the beginning of the fund
            points = List.of(p2, p1, p3);
            assertEquals("", p1, ray.findClosestGeoPoint(points));

        } catch (Exception ex) {
        }
    }
}