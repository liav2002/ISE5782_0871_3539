
package primitives;


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

    @Override
    public String toString() {
        return "Ray{" + p0 + dir + '}';
    }

}