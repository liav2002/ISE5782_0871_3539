package primitives;

import java.util.Objects;

public class Point {

    protected Double3 coordinate;

    public final static Point ZERO = new Point(0, 0, 0);

    public Point(Double3 c) {
        this.coordinate = c;
    }

    public Point(Point p) {
        this.coordinate = p.coordinate;
    }


    public Point(double x, double y, double z) {
        this.coordinate = new Double3(x, y, z);
    }

    /**
     * add vector to point
     *
     * @param vec to add
     * @return point
     */
    public Point add(Vector vec) {
        return new Point(this.coordinate.add(vec.coordinate));
    }

    /**
     * subtract point from vector
     *
     * @param pnt to subtract
     * @return vector
     */
    public Vector subtract(Point pnt) {
        return new Vector(new Point(this.coordinate.subtract(pnt.coordinate)));

    }

    public Double3 getDouble3() {
        return this.coordinate;
    }

    public double length(Point p) {
        return Math.sqrt(this.lengthSquared(p));
    }

    public double lengthSquared(Point p) {
        return (this.coordinate.d1 - p.coordinate.d1) * (this.coordinate.d1 - p.coordinate.d1) +
                (this.coordinate.d2 - p.coordinate.d2) * (this.coordinate.d2 - p.coordinate.d2) +
                (this.coordinate.d3 - p.coordinate.d3) * (this.coordinate.d3 - p.coordinate.d3);
    }


    Point mult(double multiplier) {
        return new Point(this.coordinate.d1 * multiplier,
                this.coordinate.d2 * multiplier,
                this.coordinate.d3 * multiplier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(coordinate, point.coordinate);
    }

    @Override
    public String toString() {
        return "Point{" + "coordinate=" + coordinate + '}';
    }
}
