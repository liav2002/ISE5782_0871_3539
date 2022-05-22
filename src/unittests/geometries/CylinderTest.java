package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTests {

    /**
     * Test method for {@link Vector getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {
        Cylinder cylinder = new Cylinder(new Ray(Point.ZERO, new Vector(0, 0, 1)), 1, 1);
        String error = "ERROR: geometries.Cylinder.getNormal() -- bad result";


        // ============ Equivalence Partitions Tests ==============
        // TC01: Point is on surface
        assertEquals(new Vector(0, 1, 0), cylinder.getNormal(new Point(0, 1, 0.5)), error);


        // =============== Boundary Values Tests ==================
        // TC11: Point is on top base
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(0.5, 0.5, 1)), error);

        // TC12: Point is on bottom base
        assertEquals(new Vector(0, 0, -1), cylinder.getNormal(new Point(0.5, 0.5, 0)), error);

        // TC13: Point is on top base and on surface
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(0, 1, 1)), error);

        // TC14: Point is on bottom base and on surface
        assertEquals(new Vector(0, 0, -1), cylinder.getNormal(new Point(0, 1, 0)), error);

        // TC15: Point is on top base and on axisRay
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(0, 0, 1)), error);

        // TC16: Point is on bottom base and on axisRay
        assertEquals(new Vector(0, 0, -1), cylinder.getNormal(Point.ZERO), error);
    }

    /**
     * Test method for {@link List<Point> findIntsersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {

        Cylinder cylinder = new Cylinder(new Ray(new Point(2, 0, 0), new Vector(0, 0, 1)), 1d, 2d);
        List<Point> lst;
        String error = "ERROR: geometries.Cylinder.findIntersections() -- bad result";


        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's outside, and parallel to Cylinder's Ray
        lst = cylinder.findIntersections(new Ray(new Point(5, 0, 0), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC02: Ray's inside, and parallel to Cylinder's Ray
        lst = cylinder.findIntersections(new Ray(new Point(2.5, 0, 1), new Vector(0, 0, 1)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(2.5, 0, 2)), lst, error);

        // TC03: Ray starts outside, and parallel to Cylinder's Ray and crosses Cylinder
        lst = cylinder.findIntersections(new Ray(new Point(2.5, 0, -1), new Vector(0, 0, 1)));
        assertEquals(2, lst.size(), error);
        assertEquals(List.of(new Point(2.5, 0, 0), new Point(2.5, 0, 2)), lst, error);

        // TC04: Ray starts outside, and crosses Cylinder
        lst = cylinder.findIntersections(new Ray(new Point(-2, 0, 0), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC05: ray starts inside and crosses the cylinder
        lst = cylinder.findIntersections(new Ray(new Point(1.5, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(3, 0, 0.5)), lst, error);

        // TC06: Ray starts outside the Cylinder, and doesn't cross Cylinder
        lst = cylinder.findIntersections(new Ray(new Point(5, 0, 0), new Vector(1, 0, 0)));
        assertNull(lst, error);


        // =============== Boundary Values Tests ==================
        // TC11: Ray's on the surface of Cylinder
        lst = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC12: Ray's on the base of Cylinder, and crosses 2 times
        lst = cylinder.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC13: Ray's in center of Cylinder
        lst = cylinder.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0, 0, 1)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(2, 0, 2)), lst, error);

        // TC14: Ray's perpendicular to Cylinder's Ray, and starts outside Cylinder
        lst = cylinder.findIntersections(new Ray(new Point(-2, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(2, lst.size(), error);
        assertEquals(List.of(new Point(1, 0, 0.5), new Point(3, 0, 0.5)), lst, error);

        // TC15: Ray's perpendicular to Cylinder's Ray, and starts inside Cylinder (not center)
        lst = cylinder.findIntersections(new Ray(new Point(1.5, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(3, 0, 0.5)), lst, error);

        // TC16: Ray's perpendicular to Cylinder's Ray, and starts the center of Cylinder
        lst = cylinder.findIntersections(new Ray(new Point(2, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(3, 0, 0.5)), lst, error);

        // TC17: Ray's perpendicular to Cylinder's Ray, and starts the surface of Cylinder to inside
        lst = cylinder.findIntersections(new Ray(new Point(1, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(3, 0, 0.5)), lst, error);

        // TC18: Ray's perpendicular to Cylinder's Ray, and starts from the surface of Cylinder to outside
        lst = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC19: Ray starts on the surface to outside
        lst = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 1, 1)));
        assertNull(lst, error);

        // TC20: Ray starts on the surface to inside
        lst = cylinder.findIntersections(new Ray(new Point(3, 0, -0.5), new Vector(-1, 0, 1)));
        assertEquals(2, lst.size(), error);
        assertEquals(List.of(new Point(2.5, 0, 0), new Point(1, 0, 1.5)), lst, error);

        // TC21: Ray starts from the center
        lst = cylinder.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 1)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(3, 0, 1)), lst, error);

        // TC22: Prolongation of Ray crosses Cylinder
        lst = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC23: Ray starts from the surface to inside
        lst = cylinder.findIntersections(new Ray(new Point(3, 0, 0.5), new Vector(-1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1, 0, 0.5)), lst, error);

        // TC24: Ray starts from the center
        lst = cylinder.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 1)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(3, 0, 1)), lst, error);

        // TC25: Prolongation of Ray crosses Cylinder
        lst = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC26: Ray's on the surface, and starts before Cylinder
        lst = cylinder.findIntersections(new Ray(new Point(3, 0, -1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC27: Ray's on the surface, and starts on bottom's base
        lst = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC28: Ray's on the surface, and starts on the surface
        lst = cylinder.findIntersections(new Ray(new Point(3, 0, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC29: Ray's on the surface, and starts on top's base
        lst = cylinder.findIntersections(new Ray(new Point(3, 0, 2), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is orthogonal to cylinder axis no intersect the tube (0 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC30:  ray head is before and above cylinder axis head (0 point)
        cylinder = new Cylinder(new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)), 1d, 3d);
        lst = cylinder.findIntersections(new Ray(new Point(-1, 1, 2), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC31:  ray head is after and above cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(3, 1, 2), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC32:  ray head is in same x coordinates and above cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 3, 2), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC33:  ray head is before and under cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(-1, 1, -1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC34:  ray head is after and under cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(3, 1, -1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC35:  ray head is in same x coordinates and under cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 3, -1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC36:  ray head is before and same height as cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(-1, 1, 1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC37:  ray head is after and same height as cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(3, 1, 1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC38:  ray head is in same x coordinates and same height as cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 3, 1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is orthogonal to cylinder axis no intersect the tube because the ray head is on tube surface (0 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC39:  ray head is before and above cylinder axis head and tangent to the tube surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 2), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC40:  ray head is after and above cylinder axis head and tangent to the tube surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 2), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC41:  ray head is in same x coordinates and above cylinder axis head and tangent to the tube surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 2, 2), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC42:  ray head is before and under cylinder axis head and tangent to the tube surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, -1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC43:  ray head is after and under cylinder axis head and tangent to the tube surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, -1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC44:  ray head is in same x coordinates and under cylinder axis head and tangent to the tube surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 2, -1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC45:  ray head is before and same height as cylinder axis head and tangent to the tube surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC46:  ray head is after and same height as cylinder axis head tangent to the tube surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC47:  ray head is in same x coordinates and same height as cylinder axis head tangent to the tube surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 2, 1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is orthogonal to cylinder axis and intersect the tube (1 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC48:  ray head is before and above cylinder axis head (1 point)
        lst = cylinder.findIntersections(new Ray(new Point(0.5, 1, 2), new Vector(0, 1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(0.5, 1.866025403784, 2.0)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.

        // TC49:  ray head is after and above cylinder axis head (1 point)
        lst = cylinder.findIntersections(new Ray(new Point(1.5, 1, 2), new Vector(0, 1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1.5, 1.866025403784, 2.0)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.

        // TC50:  ray head is in same x coordinates and above cylinder axis head (1 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 1.5, 2), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1.866025403784, 1.5, 2.0)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.

        // TC51:  ray head is before and under cylinder axis head (1 point)
        cylinder = new Cylinder(new Ray(new Point(1, 1, 1), new Vector(0, 0, -1)), 1d, 3d);
        lst = cylinder.findIntersections(new Ray(new Point(0.5, 1, -1), new Vector(0, 1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(0.5, 1.866025403784, -1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.

        // TC52:  ray head is after and under cylinder axis head (1 point)
        lst = cylinder.findIntersections(new Ray(new Point(1.5, 1, -1), new Vector(0, 1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1.5, 1.866025403784, -1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC53:  ray head is in same x coordinates and under cylinder axis head (1 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 1.5, -1), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1.866025403784, 1.5, -1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is orthogonal to cylinder axis and intersect the tube (2 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC54:  ray head is before and above cylinder axis head (2 point)
        cylinder = new Cylinder(new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)), 1d, 3d);
        lst = cylinder.findIntersections(new Ray(new Point(0.5, -1, 2), new Vector(0, 1, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(0.5, 0.133974596215, 2)), lst.get(0), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(0.5, 1.866025403784, 2)), lst.get(1), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC55:  ray head is after and above cylinder axis head (2 point)
        lst = cylinder.findIntersections(new Ray(new Point(1.5, -1, 2), new Vector(0, 1, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(1.5, 0.133974596215, 2)), lst.get(0), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(1.5, 1.866025403784, 2)), lst.get(1), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC56:  ray head is in same x coordinates and above cylinder axis head (2 point)
        lst = cylinder.findIntersections(new Ray(new Point(-1, 1.5, 2), new Vector(1, 0, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(0.133974596215, 1.5, 2)), lst.get(0), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(1.866025403784, 1.5, 2)), lst.get(1), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC57:  ray head is before and under cylinder axis head (2 point)
        cylinder = new Cylinder(new Ray(new Point(1, 1, 1), new Vector(0, 0, -1)), 1d, 3d);
        lst = cylinder.findIntersections(new Ray(new Point(0.5, -1, -1), new Vector(0, 1, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(0.5, 0.133974596215, -1)), lst.get(0), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(0.5, 1.866025403784, -1)), lst.get(1), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC58:  ray head is after and under cylinder axis head (2 point)
        lst = cylinder.findIntersections(new Ray(new Point(1.5, -1, -1), new Vector(0, 1, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(1.5, 0.133974596215, -1)), lst.get(0), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(1.5, 1.866025403784, -1)), lst.get(1), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC59:  ray head is in same x coordinates and under cylinder axis head (2 point)
        lst = cylinder.findIntersections(new Ray(new Point(-1, 1.5, -1), new Vector(1, 0, 0)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(0.133974596215, 1.5, -1)), lst.get(0), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(1.866025403784, 1.5, -1)), lst.get(1), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is orthogonal to cylinder axis no intersect the tube because the ray is tangent to tube surface (0 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC60:  ray head is before and above cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, -1, 2), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC61:  ray head is after and above cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, -1, 2), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC62:  ray head is in same x coordinates and above cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(-1, 2, 2), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC63:  ray head is before and under cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, -1, -1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC64:  ray head is after and under cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, -1, -1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC65:  ray head is in same x coordinates and under cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(-1, 2, -1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC66:  ray head is before and same height as cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, -1, 1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC67:  ray head is after and same height as cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, -1, 1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC68:  ray head is in same x coordinates and same height as cylinder axis head (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(-1, 2, 1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is orthogonal to cylinder axis and the ray head is on the surface, ray go into the cylinder (1 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC69:  ray head is before and above cylinder axis head ray head is on the surface (1 point)
        cylinder = new Cylinder(new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)), 1d, 3d);
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 2), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(2, 1, 2)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC70:  ray head is after and above cylinder axis head ray head is on the surface (1 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 2), new Vector(-1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(0, 1, 2)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC71:  ray head is in same x coordinates and above cylinder axis head ray head is on the surface (1 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 2, 2), new Vector(0, -1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1, 0, 2)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC72:  ray head is before and under cylinder axis head ray head is on the surface (1 point)
        cylinder = new Cylinder(new Ray(new Point(1, 1, 1), new Vector(0, 0, -1)), 1d, 3d);
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, -1), new Vector(1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(2, 1, -1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC73:  ray head is after and under cylinder axis head ray head is on the surface (1 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, -1), new Vector(-1, 0, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(0, 1, -1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC74:  ray head is in same x coordinates and under cylinder axis head ray head is on the surface (1 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 2, -1), new Vector(0, -1, 0)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(1, 0, -1)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is orthogonal to cylinder axis and the ray head is on the surface, ray go far from the cylinder (0 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC76:  ray head is before and above cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 2), new Vector(-1, 0, 0)));
        assertNull(lst, error);

        // TC77:  ray head is after and above cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 2), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC78:  ray head is in same x coordinates and above cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 2, 2), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC79:  ray head is before and under cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, -1), new Vector(-1, 0, 0)));
        assertNull(lst, error);

        // TC80:  ray head is after and under cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, -1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC81:  ray head is in same x coordinates and under cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 2, -1), new Vector(0, 1, 0)));
        assertNull(lst, error);

        // TC82:  ray head is before and same height as cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 1), new Vector(-1, 0, 0)));
        assertNull(lst, error);

        // TC83:  ray head is after and same height as cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 1), new Vector(1, 0, 0)));
        assertNull(lst, error);

        // TC84:  ray head is in same x coordinates and same height as cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, -1, 0)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is parallel to cylinder axis and the ray head is on the surface, ray go far from the cylinder (0 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC85:  ray head is before and above cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 2), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC86:  ray head is after and above cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 2), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC87:  ray head is in same x coordinates and above cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 2, 2), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC88:  ray head is before and under cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, -1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC89:  ray head is after and under cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, -1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC90:  ray head is in same x coordinates and under cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 2, -1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC91:  ray head is before and same height as cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC92:  ray head is after and same height as cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC93:  ray head is in same x coordinates and same height as cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is no orthogonal and no parallel to cylinder axis and the ray head is on the surface, ray go far from the cylinder (0 point) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC94:  ray head is before and above cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 2), new Vector(-1, 1, 0)));
        assertNull(lst, error);

        // TC95:  ray head is after and above cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 2), new Vector(1, 1, 0)));
        assertNull(lst, error);

        // TC96:  ray head is in same x coordinates and above cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 2, 2), new Vector(1, 1, 0)));
        assertNull(lst, error);

        // TC97:  ray head is before and under cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, -1), new Vector(-1, 1, 0)));
        assertNull(lst, error);

        // TC98:  ray head is after and under cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, -1), new Vector(1, 1, 0)));
        assertNull(lst, error);

        // TC99:  ray head is in same x coordinates and under cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 2, -1), new Vector(1, 1, 0)));
        assertNull(lst, error);

        // TC100:  ray head is before and same height as cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 1), new Vector(-1, 1, 0)));
        assertNull(lst, error);

        // TC101:  ray head is after and same height as cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 1), new Vector(1, 1, 0)));
        assertNull(lst, error);

        // TC102:  ray head is in same x coordinates and same height as cylinder axis head ray head is on the surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0, 1), new Vector(1, -1, 0)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray is parallel to cylinder axis and the ray head
        // TC103:  ray head is before and under cylinder axis head ray is in same direction as cylinder axis, outside the cylinder (0 point)
        cylinder = new Cylinder(new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)), 1d, 3d);
        lst = cylinder.findIntersections(new Ray(new Point(-1, 1, -1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC104:  ray head is before and under cylinder axis head ray is in opposite direction from the cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(-1, 1, -1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC105:  ray head is in same x coordinates and under cylinder axis head ray is in same direction as cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, -1, -1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC106:  ray head is in same x coordinates and under cylinder axis head ray is in opposite direction from the cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, -1, -1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC107:  ray head is after and under cylinder axis head ray is in same direction as cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(3, 1, -1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC108:  ray head is after and under cylinder axis head ray is in opposite direction from the cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(3, 1, -1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC109:  ray head is before and under cylinder axis head ray is in same direction as cylinder axis, inside the cylinder (2 point)
        lst = cylinder.findIntersections(new Ray(new Point(0.5, 1, -1), new Vector(0, 0, 1)));
        assertEquals(2, lst.size(), error);

        // TC110:  ray head is before and under cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0.5, 1, -1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC111:  ray head is in same x coordinates and under cylinder axis head ray is in same direction as cylinder axis, inside the cylinder (2 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0.5, -1), new Vector(0, 0, 1)));
        assertEquals(2, lst.size(), error);

        // TC112:  ray head is in same x coordinates and under cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0.5, -1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC113:  ray head is after and under cylinder axis head ray is in same direction as cylinder axis, inside the cylinder (2 point)
        lst = cylinder.findIntersections(new Ray(new Point(1.5, 1, -1), new Vector(0, 0, 1)));
        assertEquals(2, lst.size(), error);

        // TC114:  ray head is after and under cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1.5, 1, -1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC115:  ray head is before and under cylinder axis head ray is in same direction as cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, -1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC116:  ray head is before and under cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, -1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC117:  ray head is in same x coordinates and under cylinder axis head ray is in same direction as cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0, -1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC118:  ray head is in same x coordinates and under cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0, -1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC119:  ray head is after and under cylinder axis head ray is in same direction as cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, -1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC120:  ray head is after and under cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, -1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC121:  ray head is before and in same height cylinder axis head ray is in same direction as cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(-1, 1, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC122:  ray head is before and in same height cylinder axis head ray is in opposite direction from the cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(-1, 1, 1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC123:  ray head is in same x coordinates and in same height cylinder axis head ray is in same direction as cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, -1, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC124:  ray head is in same x coordinates and in same height cylinder axis head ray is in opposite direction from the cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, -1, 1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC125:  ray head is after and in same height cylinder axis head ray is in same direction as cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(3, 1, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC126:  ray head is after and in same height cylinder axis head ray is in opposite direction from the cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(3, 1, 1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC127:  ray head is before and in same height cylinder axis head ray is in same direction as cylinder axis, inside the cylinder (1 point)
        lst = cylinder.findIntersections(new Ray(new Point(0.5, 1, 1), new Vector(0, 0, 1)));
        assertEquals(1, lst.size(), error);

        // TC128:  ray head is before and in same height cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0.5, 1, 1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC129:  ray head is in same x coordinates and in same height cylinder axis head ray is in same direction as cylinder axis, inside the cylinder (1 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0.5, 1), new Vector(0, 0, 1)));
        assertEquals(1, lst.size(), error);

        // TC130:  ray head is in same x coordinates and in same height cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0.5, 1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC131:  ray head is after and in same height cylinder axis head ray is in same direction as cylinder axis, inside the cylinder (1 point)
        lst = cylinder.findIntersections(new Ray(new Point(1.5, 1, 1), new Vector(0, 0, 1)));
        assertEquals(1, lst.size(), error);

        // TC132:  ray head is after and in same height cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1.5, 1, 1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC133:  ray head is before and in same height cylinder axis head ray is in same direction as cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC134:  ray head is before and in same height cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC135:  ray head is in same x coordinates and in same height cylinder axis head ray is in same direction as cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC136:  ray head is in same x coordinates and in same height cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC137:  ray head is after and in same height cylinder axis head ray is in same direction as cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 1), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC138:  ray head is after and in same height cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC139:  ray head is before and above cylinder axis head ray is in same direction as cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(-1, 1, 5), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC140:  ray head is before and above cylinder axis head ray is in opposite direction from the cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(-1, 1, 5), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC141:  ray head is in same x coordinates and above cylinder axis head ray is in same direction as cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, -1, 5), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC142:  ray head is in same x coordinates and above cylinder axis head ray is in opposite direction from the cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, -1, 5), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC143:  ray head is after and above cylinder axis head ray is in same direction as cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(3, 1, 5), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC144:  ray head is after and above cylinder axis head ray is in opposite direction from the cylinder axis, outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(3, 1, 5), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC145:  ray head is before and above cylinder axis head ray is in same direction as cylinder axis, inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0.5, 1, 5), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC146:  ray head is before and above cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder (2 point)
        lst = cylinder.findIntersections(new Ray(new Point(0.5, 1, 5), new Vector(0, 0, -1)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(0.5, 1, 4)), lst.get(0), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(0.5, 1, 1)), lst.get(1), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC147:  ray head is in same x coordinates and above cylinder axis head ray is in same direction as cylinder axis, inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0.5, 5), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC148:  ray head is in same x coordinates and above cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder (2 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0.5, 5), new Vector(0, 0, -1)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(1, 0.5, 4)), lst.get(0), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(1, 0.5, 1)), lst.get(1), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC149:  ray head is after and above cylinder axis head ray is in same direction as cylinder axis, inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1.5, 1, 5), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC150:  ray head is after and above cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder (2 point)
        lst = cylinder.findIntersections(new Ray(new Point(1.5, 1, 5), new Vector(0, 0, -1)));
        assertEquals(2, lst.size(), error);
        // First intersection point is the closest to ray head
        assertEquals((new Point(1.5, 1, 4)), lst.get(0), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.
        assertEquals((new Point(1.5, 1, 1)), lst.get(1), error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC151:  ray head is before and above cylinder axis head ray is in same direction as cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 5), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC152:  ray head is before and above cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 5), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC153:  ray head is in same x coordinates and above cylinder axis head ray is in same direction as cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0, 5), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC154:  ray head is in same x coordinates and above cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 0, -1), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // TC155:  ray head is after and above cylinder axis head ray is in same direction as cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 5), new Vector(0, 0, 1)));
        assertNull(lst, error);

        // TC156:  ray head is after and above cylinder axis head ray is in opposite direction from the cylinder axis, inside the cylinder, ray coalesce with cylinder surface (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 5), new Vector(0, 0, -1)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Group: Ray Passes through cylinder axis and The two intersection points between the surface and the bases @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC157: ray head is before and above cylinder axis head, ray go inside the cylinder (0 point)
        cylinder = new Cylinder(new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)), 1d, 3d);
        lst = cylinder.findIntersections(new Ray(new Point(-2, 1, 7), new Vector(2, 0, -3)));
        assertNull(lst, error);

        // TC158: ray head is before and above cylinder axis head, ray go outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(-2, 1, 7), new Vector(-2, 0, 3)));
        assertNull(lst, error);

        // TC159: ray head is before and above cylinder axis head on the intersection point between the surface and the base, ray go inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 4), new Vector(2, 0, -3)));
        assertNull(lst, error);

        // TC160: ray head is before and above cylinder axis head on the intersection point between the surface and the base, ray go outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 4), new Vector(-2, 0, 3)));
        assertNull(lst, error);

        // TC161: ray head is before and above cylinder axis head inside the cylinder, ray go inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 1, 2.5), new Vector(2, 0, -3)));
        assertNull(lst, error);

        // TC162: ray head is before and above cylinder axis head inside the cylinder, ray go outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 1, 2.5), new Vector(-2, 0, 3)));
        assertNull(lst, error);

        // TC163: ray head is in same x coordinates and above cylinder axis head, ray go inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 4, 7), new Vector(0, -2, -3)));
        assertNull(lst, error);

        // TC164: ray head is in same x coordinates and above cylinder axis head, ray go outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 4, 7), new Vector(0, 2, 3)));
        assertNull(lst, error);

        // TC165: ray head is in same x coordinates and above cylinder axis head on the intersection point between the surface and the base, ray go inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 2, 4), new Vector(0, -2, -3)));
        assertNull(lst, error);

        // TC166: ray head is in same x coordinates and above cylinder axis head on the intersection point between the surface and the base, ray go outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 2, 4), new Vector(0, 2, 3)));
        assertNull(lst, error);

        // TC167: ray head is in same x coordinates and above cylinder axis head inside the cylinder, ray go inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 1, 2.5), new Vector(0, -2, -3)));
        assertNull(lst, error);

        // TC168: ray head is in same x coordinates and above cylinder axis head inside the cylinder, ray go outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 1, 2.5), new Vector(0, 2, 3)));
        assertNull(lst, error);

        // TC169: ray head is after and above cylinder axis head, ray go inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(4, 1, 7), new Vector(-2, 0, -3)));
        assertNull(lst, error);

        // TC170: ray head is after and above cylinder axis head, ray go outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(4, 1, 7), new Vector(2, 0, 3)));
        assertNull(lst, error);

        // TC171: ray head is after and above cylinder axis head on the intersection point between the surface and the base, ray go inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 4), new Vector(-2, 0, -3)));
        assertNull(lst, error);

        // TC172: ray head is after and above cylinder axis head on the intersection point between the surface and the base, ray go outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(2, 1, 4), new Vector(2, 0, 3)));
        assertNull(lst, error);

        // TC173: ray head is after and above cylinder axis head inside the cylinder, ray go inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 1, 2.5), new Vector(-2, 0, -3)));
        assertNull(lst, error);

        // TC174: ray head is after and above cylinder axis head inside the cylinder, ray go outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 1, 2.5), new Vector(2, 0, 3)));
        assertNull(lst, error);

        // @@@@@@@@@@@@@@****@@@@@@@@@@@@@@@@@@@@ Group: Ray Passes through cylinder axis and The intersection one point between the surface and the base  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // TC175: ray head is before and above cylinder axis head, ray go inside the cylinder (0 point)
        cylinder = new Cylinder(new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)), 1d, 3d);
        lst = cylinder.findIntersections(new Ray(new Point(-2, 1, 5), new Vector(2, 0, -2)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(0, 1, 3)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.


        // TC176: ray head is before and above cylinder axis head, ray go outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(-2, 1, 5), new Vector(-2, 0, 2)));
        assertNull(lst, error);

        // TC177: ray head is before and above cylinder axis head on the intersection point between the surface and the base, ray go inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 3), new Vector(2, 0, -2)));
        assertNull(lst, error);

        // TC178: ray head is before and above cylinder axis head on the intersection point between the surface and the base, ray go outside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(0, 1, 3), new Vector(-2, 0, 2)));
        assertNull(lst, error);

        // TC179: ray head is before and above cylinder axis head inside the cylinder, ray go inside the cylinder (0 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 1, 2), new Vector(2, 0, -2)));
        assertNull(lst, error);

        // TC180: ray head is before and above cylinder axis head inside the cylinder, ray go outside the cylinder (1 point)
        lst = cylinder.findIntersections(new Ray(new Point(1, 1, 2), new Vector(-2, 0, 2)));
        assertEquals(1, lst.size(), error);
        assertEquals(List.of(new Point(0, 1, 3)), lst, error); // The points are compared by alignZero, which means that it around 12 digits after the decimal point.

    }
}