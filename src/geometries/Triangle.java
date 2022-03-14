package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public class Triangle extends Polygon {
    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);
    }

    @Override
    public String toString() {
        return "Triangle{" + super.toString() + "}";
    }

    @Override
    public List<Point> findIntersections(Ray ray)
    {
        return null;
    }

}
