package geometries;

import primitives.Point;
import primitives.Vector;

public interface Geometry extends Intersectable {

    /**
     * gets geometries normal
     * @param p
     * @return geometries normal
     */
    Vector getNormal(Point p);
}
