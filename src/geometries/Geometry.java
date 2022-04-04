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
import primitives.Vector;

public interface Geometry extends Intersectable {

    /**
     * gets geometries normal
     * @param p
     * @return geometries normal
     */
    Vector getNormal(Point p);
}
