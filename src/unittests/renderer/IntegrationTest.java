

package unittests.renderer;


import static org.junit.Assert.*;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;

import primitives.*;
import renderer.Camera;

import java.util.List;


public class IntegrationTest {
    /**
     * Test method for
     * {@link renderer.Camera#constructRay(int, int, int, int)}
     * {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */

    @Test
    public void testSphereIntegration() {

        // TC01: Only middle pixel intersect sphere
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3, 3);
        Sphere sp = new Sphere(new Point(0, 0, -3d), 1d);
        assertEquals("TC01: Only middle pixel intersect sphere", 2, intersectionsCalculate(camera, sp, 3, 3));

        // TC02: All pixels intersect sphere
        camera = new Camera(new Point(0, 0, 0.5d), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3, 3);
        sp = new Sphere(new Point(0, 0, -2.5d), 2.5d);
        assertEquals("TC02: All pixels intersect sphere", 18, intersectionsCalculate(camera, sp, 3, 3));

        // TC03: Corners are not intersect sphere
        camera = new Camera(new Point(0, 0, 0.5d), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3, 3);
        sp = new Sphere(new Point(0, 0, -2d), 2d);
        assertEquals("TC03: Corners are not intersect sphere", 10, intersectionsCalculate(camera, sp, 3, 3));

        // TC04: Camera is in sphere
        camera = new Camera(new Point(0, 0, 0.5d), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3, 3);
        sp = new Sphere(new Point(0, 0, -2d), 4d);
        assertEquals("TC04: Camera is in sphere", 9, intersectionsCalculate(camera, sp, 3, 3));

        // TC05: Camera is before sphere
        camera = new Camera(new Point(0, 0, 0.5d), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3, 3);
        sp = new Sphere(new Point(0, 0, 1d), 0.5d);
        assertEquals("TC05: Camera is before sphere", 0, intersectionsCalculate(camera, sp, 3, 3));
    }

    @Test
    public void testPlaneIntegration() {
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3, 3);

        // TC01: Plane is vertical to camera
        Plane pl = new Plane(new Vector(0, 0, 1d), new Point(0, 0, -1d));
        assertEquals("TC01: Plane is vertical to camera", 9, intersectionsCalculate(camera, pl, 3, 3));

        // TC02: Plane is tilt but dont cross viewPlane
        pl = new Plane(new Vector(0, -0.5d, 1d), new Point(0, 0, -1d));
        assertEquals("TC02: Plane is tilt but dont cross viewPlane", 9, intersectionsCalculate(camera, pl, 3, 3));

        // TC03: Plane is tilt and cross viewPlane
        pl = new Plane(new Vector(0, -1d, 1d), new Point(0, 0, -1d));
        assertEquals("TC03: Plane is tilt and cross viewPlane", 6, intersectionsCalculate(camera, pl, 3, 3));
    }

    @Test
    public void testTriangleIntegration() {
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1).setVPSize(3, 3);

        // TC01: Small triangle
        Triangle tl = new Triangle(new Point(0, 1d, -2d), new Point(1d, -1d, -2d), new Point(-1d, -1d, -2d));
        assertEquals("TC01: Small triangle", 1, intersectionsCalculate(camera, tl, 3, 3));

        // TC02: Long and tight triangle
        tl = new Triangle(new Point(0, 20d, -2d), new Point(1d, -1d, -2d), new Point(-1d, -1d, -2d));
        assertEquals("TC02: Long and tight triangle", 2, intersectionsCalculate(camera, tl, 3, 3));
    }

    private int intersectionsCalculate(Camera camera, Intersectable shape, int nX, int nY) {
        int ret = 0;
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                Ray ray = camera.constructRay(nX, nY, j, i);
                List<Point> inters = shape.findIntersections(ray);
                if (inters != null) {
                    ret += inters.size();
                }
            }
        }
        return ret;
    }
}