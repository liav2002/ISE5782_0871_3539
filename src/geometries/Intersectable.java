package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Defining an interface. for calculation of insersections with ray.
 *
 * @author1 Eyal
 * @authon2 Liav Ariel
 */
public interface Intersectable {
    List<Point> findIntersections(Ray ray);
}
