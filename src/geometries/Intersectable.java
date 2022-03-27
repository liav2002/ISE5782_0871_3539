/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

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
