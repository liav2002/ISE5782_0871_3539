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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Geometries implements Intersectable {
    private List<Intersectable> list;

    public Geometries() {
        this.list = new
                LinkedList<>();
    }

    public Geometries(Intersectable... Geometries) {
        this.list = new
                LinkedList<>();

        for (Intersectable intersectable : Geometries) {
            list.add(intersectable);
        }

    }

    public void add(Intersectable... Geometries) {
        for (Intersectable intersectable : Geometries) {
            list.add(intersectable);
        }
    }

    public List<Intersectable> getList() {
        return list;
    }

    /**
     * Implement interface function
     *
     * @param ray the interacting ray
     * @return all intersections points
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> ret = new LinkedList<>();
        for (Intersectable shape : list) {
            List intersection = shape.findIntersections(ray);
            if (intersection == null) {
                continue;
            } // if there is no intersections points - continue
            for (Point pnt : shape.findIntersections(ray)) {
                ret.add(pnt);
            }
        }
        return ret.isEmpty() ? null : ret;
    }
}
