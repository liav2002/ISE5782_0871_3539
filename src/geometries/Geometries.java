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

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
