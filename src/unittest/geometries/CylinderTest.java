package unittest.geometries;

import geometries.Cylinder;
import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Cylinder class
 *
 * @author1 Eyal
 * @authon2 Liav Ariel
 */

public class CylinderTest {
    @Test
    public void testConstructor() {

    }

    /**
     * Test method for {@link Cylinder#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {

        Cylinder c = new Cylinder(1, 1, new Ray(Point.ZERO, new Vector(0, 1, 0)));
        //TC01: check the Tube getNormal for extreme cases there

        //TC02: the point on the side of the cylinder
        assertEquals("getNormal() bad normal on the side of the cylinder",
                c.getNormal(new Point(0, 0.5, 1)), new Vector(0, 0, 1));

        //TC03: the point on the top base of teh cylinder
        assertEquals("getNormal() bad normal on the top base of the cylinder",
                c.getNormal(new Point(0.1, 1, 0.1)), new Vector(0, 1, 0));

        //TC04: the point on the bottom base of the cylinder
        assertEquals("getNormal() bad normal on the bottom base of the cylinder",
                c.getNormal(new Point(0.1, 0, 0.1)), new Vector(0, -1, 0));
    }

    /**
     * Test method for {@link geometries.Cylinder#findIntersections(Ray)}.
     */
    @Test
    public void testfindIntsersections() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: basic intersection test
        Ray ray=new Ray(new Point(0,2,0),new Vector(1,0,0));
        Cylinder cylinder=new Cylinder(3, 1, new Ray(new Point(5,0,0),new Vector(0,1,0)));
        assertEquals("the ray intersecting the cylinder",ray.getPoint(4),cylinder.findIntersections(ray).get(0));
        assertEquals("the ray intersecting the cylinder",ray.getPoint(6),cylinder.findIntersections(ray).get(1));

        //TC02: the ray intersecting tha bases of the cylinder
        ray=new Ray(new Point(5,8,0) ,new Vector(0,-1,0));
        assertEquals("the ray intersecting with the planes of the cylinder",ray.getPoint(5),cylinder.findIntersections(ray).get(0));
        assertEquals("the ray intersecting with the planes of the cylinder",ray.getPoint(8),cylinder.findIntersections(ray).get(1));

        //TC03: the ray moving above the cylinder but not intersecting it
        ray=new Ray(new Point(0,5,0),new Vector(3,1,0));
        assertNull("the ray not intersecting with the cylinder but movingf above it",cylinder.findIntersections(ray));

        //TC04: the ray intersecting the cylinder in it base and at is side
        ray=new Ray(new Point(4,-1,0),new Vector(1,1,0));
        assertEquals("the ray intersecting the base and the side of the cylinder - plane intersection",new Point(5,0,0),cylinder.findIntersections(ray).get(1));
        assertEquals("the ray intersecting the base and the side of the cylinder - side intersection",new Point(6,1,0),cylinder.findIntersections(ray).get(0));
    }
}