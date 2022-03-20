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


}