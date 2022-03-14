package geometries;

import primitives.Point;
import primitives.Vector;

public interface Geometry extends Intersectable {
    Vector getNormal(Point p);
}
