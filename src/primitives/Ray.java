
package primitives;

import java.util.LinkedList;

public class Ray {
    /**
     * beginning point of ray
     */
    private Point p0;

    /**
     * normalized direction vector of ray,
     */
    private Vector dir;


    /**
     * ray constructor
     *
     * @param p0  beginning point of ray
     * @param dir gets direction vector of ray and normalize it
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    // Getters
    public Point getP0() {
        return p0;
    }

    /**
     * Gets point on ray
     *
     * @param t scale
     * @return point
     */
    public Point getPoint(double t) {
        if (dir.mult(t).equals(Point.ZERO)) {
            return p0;
        }
        return p0.add(dir.scale(t));
    }


    public Vector getDir() {
        return dir;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        return (o instanceof Ray) &&
                this.p0.equals(((Ray) o).p0) &&
                this.dir.equals(((Ray) o).dir);
    }

    public Point findClosestPoint(LinkedList<Point> intersections) {
        if (intersections == null) {
            return null;
        }

        Point ClosestPoint = intersections.getFirst();
        double shortestD = p0.lengthSquared(ClosestPoint);
        double currentD;

        for (Point point : intersections) {
            currentD = p0.lengthSquared(point);
            if (currentD < shortestD) {
                shortestD = currentD;
                ClosestPoint = point;
            }
        }
        return ClosestPoint;
    }

    @Override
    public String toString() {
        return "Ray{" + p0 + dir + '}';
    }

}