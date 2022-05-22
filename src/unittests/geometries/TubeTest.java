package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TubeTests {

    /**
     * Test method for {@link Vector getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {
        Tube t = new Tube(new Ray(new Point(3, 5, 8), new Vector(4, 7, 6)), 4);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test if the normal of a tube is correct
        Vector normal = t.getNormal(new Point(4, 2, 1));

        assertTrue(normal.equals(new Vector(0.6736255682471604, 0.21987778192043828, -0.7056077910719512))
                        || normal.equals(new Vector(0.6736255682471604, 0.21987778192043828, -0.7056077910719512)),
                "Normal of the tube is wrong");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from add
        Tube t1 = new Tube(new Ray(new Point(3, 4, 0), new Vector(2, -1, 0)), 4);

        assertThrows(IllegalArgumentException.class, () -> t1.getNormal(new Point(2, 2, -3)),
                "Vector 0 does not throw an exception");
    }

    /**
     * Test method for {@link List<Point> findIntsersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Tube tube = new Tube(new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)), 1);
        List<Point> lst;
        String error = "ERROR: geometries.Tube.findIntersections() -- bad result";


        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's axis is outside Tube (0 points)
        lst = tube.findIntersections(new Ray(new Point(-2, 0, 0), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC02: Ray starts before Tube and crosses it (2 points)
        lst = tube.findIntersections(new Ray(new Point(-1, 2, 3), new Vector(1, -1, 0)));
        assertEquals(2, lst.size(), error);
        assertEquals(new Point(0, 1, 3), lst.get(0), error); // closest Point first
        assertEquals(new Point(1, 0, 3), lst.get(1), error); // farthest Point second

        // TC03: Ray starts inside Tube (1 point)
        lst = tube.findIntersections(new Ray(new Point(0.5, 0.5, 3), new Vector(1, -1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(new Point(1, 0, 3), lst.get(0), error);

        // TC04: Ray starts after Tube (0 points)
        lst = tube.findIntersections(new Ray(new Point(-1, 2, 3), new Vector(-1, 1, 0)));
        assertNull(lst, error);


        // =============== Boundary Values Tests ==================
        // *********** Group: Ray's axis crosses Tube (but not at center) ***********
        // TC11: Ray starts at Tube and goes inside (1 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, 3), new Vector(1, -1, 0)));
        assertEquals(1, lst.size(), error); //list's size is 1 because it does not consider p0 as intersection point
        assertEquals(new Point(1, 0, 3), lst.get(0), error);

        // TC12: Ray starts at Tube and goes outside (0 points)
        lst = tube.findIntersections(new Ray(new Point(0, 1, 3), new Vector(-1, 1, 0)));
        assertNull(lst, error);

        // *********** Group: Ray's axis goes through center ***********
        // TC13: Ray starts before Tube (2 points)
        lst = tube.findIntersections(new Ray(new Point(-2, 0, 1), new Vector(1, 0, 0)));
        assertEquals(2, lst.size(), error);
        assertEquals(new Point(-1, 0, 1), lst.get(0), error); // closest Point first
        assertEquals(new Point(1, 0, 1), lst.get(1), error); // farthest Point second

        // TC14: Ray starts on Tube and goes inside (1 point)
        lst = tube.findIntersections(new Ray(new Point(-1, 0, 2), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(new Point(1, 0, 2), lst.get(0), error);

        // TC15: Ray starts inside Tube (1 point)
        lst = tube.findIntersections(new Ray(new Point(-0.5, 0, 2.5), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(new Point(1, 0, 2.5), lst.get(0), error);

        // TC16: Ray starts at center of Tube (1 point)
        lst = tube.findIntersections(new Ray(new Point(0, 0, 3), new Vector(3, 0, 3)));
        assertEquals(1, lst.size(), error);
        assertEquals(new Point(1, 0, 4), lst.get(0), error);

        // TC17: Ray starts after Tube (0 points)
        lst = tube.findIntersections(new Ray(new Point(-2, 2, 0), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // *********** Group: Ray's axis is tangent to Tube (all tests 0 points) ***********
        // TC18: Ray starts before tangent point
        lst = tube.findIntersections(new Ray(new Point(-2, 1, 3), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC19: Ray starts at tangent point
        lst = tube.findIntersections(new Ray(new Point(0, 1, 3), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC20: Ray starts after tangent point
        lst = tube.findIntersections(new Ray(new Point(2, 1, 3), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // *********** Group: Special cases ***********
        // TC21: Ray's axis is outside, Ray is orthogonal to line starts at Tube's center
        lst = tube.findIntersections(new Ray(new Point(-2, 0, 1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC22: Ray's axis is Tube's axis
        lst = tube.findIntersections(new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC23: Ray's direction is Tube's axis direction
        lst = tube.findIntersections(new Ray(new Point(0, 0, -9), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // *********** Group: Ray's axis is parallel to Tube's axis (all tests 0 points) ***********
        // TC24: Ray starts inside Tube, but no coalesces with the cylinder axis
        tube = new Tube(new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)), 1d);
        lst = tube.findIntersections(new Ray(new Point(0.5, 0.5, 0.5), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC25: Ray is outside Tube (0 points)
        lst = tube.findIntersections(new Ray(new Point(0.5, -0.5, 0.5), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC26: Ray is at Tube surface (0 points)
        lst = tube.findIntersections(new Ray(new Point(2, 1, 0.5), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC27: Ray is inside Tube, and starts against axis head. but no coalesces with the cylinder axis (0 points)
        lst = tube.findIntersections(new Ray(new Point(0.5, 0.5, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC28: Ray is outside Tube, and starts against axis head (0 points)
        lst = tube.findIntersections(new Ray(new Point(0.5, -0.5, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC29: Ray is at Tube surface, and starts against axis head (0 points)
        lst = tube.findIntersections(new Ray(new Point(2, 1, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC30: Ray is inside Tube and starts at axis head and coalesces with the cylinder axis (0 points)
        lst = tube.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is orthogonal to cylinder axis no intersect the tube (0 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC31:  ray head is before and above cylinder axis head (0 point)
        tube = new Tube(new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)), 1d);
        lst = tube.findIntersections(new Ray(new Point(-1, 1, 2), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC32:  ray head is after and above cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(3, 1, 2), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC33:  ray head is in same x coordinates and above cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 3, 2), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC34:  ray head is before and under cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(-1, 1, -1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC35:  ray head is after and under cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(3, 1, -1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC36:  ray head is in same x coordinates and under cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 3, -1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC37:  ray head is before and same height as cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(-1, 1, 1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC38:  ray head is after and same height as cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(3, 1, 1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC39:  ray head is in same x coordinates and same height as cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 3, 1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is orthogonal to cylinder axis no intersect the tube because the ray head is on tube surface (0 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC40:  ray head is before and above cylinder axis head and tangent to the tube surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, 2), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC41:  ray head is after and above cylinder axis head and tangent to the tube surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, 2), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC42:  ray head is in same x coordinates and above cylinder axis head and tangent to the tube surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 2, 2), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC43:  ray head is before and under cylinder axis head and tangent to the tube surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, -1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC44:  ray head is after and under cylinder axis head and tangent to the tube surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, -1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC45:  ray head is in same x coordinates and under cylinder axis head and tangent to the tube surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 2, -1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC46:  ray head is before and same height as cylinder axis head and tangent to the tube surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, 1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC47:  ray head is after and same height as cylinder axis head tangent to the tube surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, 1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC48:  ray head is in same x coordinates and same height as cylinder axis head tangent to the tube surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 2, 1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is orthogonal to cylinder axis and intersect the tube (1 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC49:  ray head is before and above cylinder axis head (1 point)
        lst = tube.findIntersections(new Ray(new Point(0.5, 1, 2), new Vector(0, 1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(0.5, 1.866025403784, 2)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC50:  ray head is after and above cylinder axis head (1 point)
        lst = tube.findIntersections(new Ray(new Point(1.5, 1, 2), new Vector(0, 1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1.5, 1.866025403784, 2)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC51:  ray head is in same x coordinates and above cylinder axis head (1 point)
        lst = tube.findIntersections(new Ray(new Point(1, 1.5, 2), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1.866025403784, 1.5, 2)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC52:  ray head is before and under cylinder axis head (1 point)
        lst = tube.findIntersections(new Ray(new Point(0.5, 1, -1), new Vector(0, 1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(0.5, 1.866025403784, -1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC53:  ray head is after and under cylinder axis head (1 point)
        lst = tube.findIntersections(new Ray(new Point(1.5, 1, -1), new Vector(0, 1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1.5, 1.866025403784, -1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC54:  ray head is in same x coordinates and under cylinder axis head (1 point)
        lst = tube.findIntersections(new Ray(new Point(1, 1.5, -1), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1.866025403784, 1.5, -1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.

        // TC55:  ray head is before and same height as cylinder axis head (1 point)
        lst = tube.findIntersections(new Ray(new Point(0.5, 1, 1), new Vector(0, 1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(0.5, 1.866025403784, 1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC56:  ray head is after and same height as cylinder axis head (1 point)
        lst = tube.findIntersections(new Ray(new Point(1.5, 1, 1), new Vector(0, 1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1.5, 1.866025403784, 1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC57:  ray head is in same x coordinates and same height as cylinder axis head (1 point)
        lst = tube.findIntersections(new Ray(new Point(1, 0.5, 1), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1.866025403784, 0.5, 1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is orthogonal to cylinder axis and intersect the tube (2 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC58:  ray head is before and above cylinder axis head (2 point)
        lst = tube.findIntersections(new Ray(new Point(0.5, -1, 2), new Vector(0, 1, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(0.5, 0.133974596215, 2)), lst.get(0), error); // closest Point first The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(0.5, 1.866025403784, 2)), lst.get(1), error); // farthest Point second The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC59:  ray head is after and above cylinder axis head (2 point)
        lst = tube.findIntersections(new Ray(new Point(1.5, -1, 2), new Vector(0, 1, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(1.5, 0.133974596215, 2)), lst.get(0), error); // closest Point first The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(1.5, 1.866025403784, 2)), lst.get(1), error); // farthest Point second The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC60:  ray head is in same x coordinates and above cylinder axis head (2 point)
        lst = tube.findIntersections(new Ray(new Point(-1, 1.5, 2), new Vector(1, 0, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(0.133974596215, 1.5, 2)), lst.get(0), error); // closest Point first The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(1.866025403784, 1.5, 2)), lst.get(1), error); // farthest Point second The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC61:  ray head is before and under cylinder axis head (2 point)
        lst = tube.findIntersections(new Ray(new Point(0.5, -1, -1), new Vector(0, 1, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(0.5, 0.133974596215, -1)), lst.get(0), error); // closest Point first The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(0.5, 1.866025403784, -1)), lst.get(1), error); // farthest Point second The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC62:  ray head is after and under cylinder axis head (2 point)
        lst = tube.findIntersections(new Ray(new Point(1.5, -1, -1), new Vector(0, 1, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(1.5, 0.133974596215, -1)), lst.get(0), error); // closest Point first The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(1.5, 1.866025403784, -1)), lst.get(1), error); // farthest Point second The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC63:  ray head is in same x coordinates and under cylinder axis head (2 point)
        lst = tube.findIntersections(new Ray(new Point(-1, 1.5, -1), new Vector(1, 0, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(0.133974596215, 1.5, -1)), lst.get(0), error); // closest Point first The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(1.866025403784, 1.5, -1)), lst.get(1), error); // farthest Point second The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC64:  ray head is before and same height as cylinder axis head (2 point)
        lst = tube.findIntersections(new Ray(new Point(0.5, -1, 1), new Vector(0, 1, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(0.5, 0.133974596215, 1)), lst.get(0), error); // closest Point first The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(0.5, 1.866025403784, 1)), lst.get(1), error); // farthest Point second The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC65:  ray head is after and same height as cylinder axis head (2 point)
        lst = tube.findIntersections(new Ray(new Point(1.5, -1, 1), new Vector(0, 1, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(1.5, 0.133974596215, 1)), lst.get(0), error); // closest Point first The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(1.5, 1.866025403784, 1)), lst.get(1), error); // farthest Point second The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC66:  ray head is in same x coordinates and same height as cylinder axis head (2 point)
        lst = tube.findIntersections(new Ray(new Point(-1, 0.5, 1), new Vector(1, 0, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(0.133974596215, 0.5, 1)), lst.get(0), error); // closest Point first The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(1.866025403784, 0.5, 1)), lst.get(1), error); // farthest Point second The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is orthogonal to cylinder axis no intersect the tube because the ray is tangent to tube surface (0 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC67:  ray head is before and above cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, -1, 2), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC68:  ray head is after and above cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, -1, 2), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC69:  ray head is in same x coordinates and above cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(-1, 2, 2), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC70:  ray head is before and under cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, -1, -1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC71:  ray head is after and under cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, -1, -1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC72:  ray head is in same x coordinates and under cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(-1, 2, -1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC73:  ray head is before and same height as cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, -1, 1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC74:  ray head is after and same height as cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, -1, 1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC75:  ray head is in same x coordinates and same height as cylinder axis head (0 point)
        lst = tube.findIntersections(new Ray(new Point(-1, 2, 1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is orthogonal to cylinder axis and the ray head is on the surface, ray go into the cylinder (1 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC76:  ray head is before and above cylinder axis head ray head is on the surface (1 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, 2), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(2, 1, 2)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC77:  ray head is after and above cylinder axis head ray head is on the surface (1 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, 2), new Vector(-1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(0, 1, 2)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC78:  ray head is in same x coordinates and above cylinder axis head ray head is on the surface (1 point)
        lst = tube.findIntersections(new Ray(new Point(1, 2, 2), new Vector(0, -1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1, 0, 2)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC79:  ray head is before and under cylinder axis head ray head is on the surface (1 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, -1), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(2, 1, -1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC80:  ray head is after and under cylinder axis head ray head is on the surface (1 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, -1), new Vector(-1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(0, 1, -1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC81:  ray head is in same x coordinates and under cylinder axis head ray head is on the surface (1 point)
        lst = tube.findIntersections(new Ray(new Point(1, 2, -1), new Vector(0, -1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1, 0, -1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC82:  ray head is before and same height as cylinder axis head ray head is on the surface (1 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, 1), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(2, 1, 1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC83:  ray head is after and same height as cylinder axis head ray head is on the surface (1 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, 1), new Vector(-1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(0, 1, 1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC84:  ray head is in same x coordinates and same height as cylinder axis head ray head is on the surface (1 point)
        lst = tube.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, 1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1, 2, 1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is orthogonal to cylinder axis and the ray head is on the surface, ray go far from the cylinder (0 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC85:  ray head is before and above cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, 2), new Vector(-1, 0, 0)));
        assertNull(lst, error);

        // TC86:  ray head is after and above cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, 2), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC87:  ray head is in same x coordinates and above cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 2, 2), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC88:  ray head is before and under cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, -1), new Vector(-1, 0, 0)));
        assertNull(lst, error);

        // TC89:  ray head is after and under cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, -1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC90:  ray head is in same x coordinates and under cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 2, -1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC91:  ray head is before and same height as cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, 1), new Vector(-1, 0, 0)));
        assertNull(lst, error);

        // TC92:  ray head is after and same height as cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, 1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC93:  ray head is in same x coordinates and same height as cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, -1, 0)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is parallel to cylinder axis and the ray head is on the surface, ray go far from the cylinder (0 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC94:  ray head is before and above cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, 2), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC95:  ray head is after and above cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, 2), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC96:  ray head is in same x coordinates and above cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 2, 2), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC97:  ray head is before and under cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, -1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC98:  ray head is after and under cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, -1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC99:  ray head is in same x coordinates and under cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 2, -1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC100:  ray head is before and same height as cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC101:  ray head is after and same height as cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC102:  ray head is in same x coordinates and same height as cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is no orthogonal and no parallel to cylinder axis and the ray head is on the surface, ray go far from the cylinder (0 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC103:  ray head is before and above cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, 2), new Vector(-1, 1, 0)));
        assertNull(lst, error);

        // TC104:  ray head is after and above cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, 2), new Vector(1, 1, 0)));
        assertNull(lst, error);

        // TC105:  ray head is in same x coordinates and above cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 2, 2), new Vector(1, 1, 0)));
        assertNull(lst, error);

        // TC106:  ray head is before and under cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, -1), new Vector(-1, 1, 0)));
        assertNull(lst, error);

        // TC107:  ray head is after and under cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, -1), new Vector(1, 1, 0)));
        assertNull(lst, error);

        // TC108:  ray head is in same x coordinates and under cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 2, -1), new Vector(1, 1, 0)));
        assertNull(lst, error);

        // TC109:  ray head is before and same height as cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(0, 1, 1), new Vector(-1, 1, 0)));
        assertNull(lst, error);

        // TC110:  ray head is after and same height as cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(2, 1, 1), new Vector(1, 1, 0)));
        assertNull(lst, error);

        // TC111:  ray head is in same x coordinates and same height as cylinder axis head ray head is on the surface (0 point)
        lst = tube.findIntersections(new Ray(new Point(1, 0, 1), new Vector(1, -1, 0)));
        assertNull(lst, error);
    }
}