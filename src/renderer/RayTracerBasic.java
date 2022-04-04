package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene s) {
        super(s);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections != null) {
            Point closestPoint = ray.findClosestPoint((LinkedList<Point>) intersections);
            return calcColor(closestPoint);
        }
        //ray did not intersect any geometrical object
        return scene.background;
    }

    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
