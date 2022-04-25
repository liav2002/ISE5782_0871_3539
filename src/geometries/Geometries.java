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


public class Geometries extends Intersectable {
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
    protected List<GeoPoint> findGeoIntersectionsHelper (Ray ray) {
        LinkedList<GeoPoint> res = new LinkedList<GeoPoint>();
        List<GeoPoint> intersects;
        for (Intersectable G : list) {
            intersects = G.findGeoIntersectionsHelper(ray);
            if(intersects != null){
                res.addAll(intersects);
            }
        }
        if(res.isEmpty()) return null;
        return res;
    }
}
