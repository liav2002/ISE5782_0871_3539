/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.*;

import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */

public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane plane;
    private int size;

    public List<Point> getVertices() {
        return vertices;
    }

    public Plane getPlane() {
        return plane;
    }

    public int getSize() {
        return size;
    }

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3)
            return; // no need for more tests for a Triangle

        Vector n = plane.getNormal(null);

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
        size = vertices.length;
    }

    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal(null);
    }


    @Override
    public String toString() {
        return "Polygon{ size = " + this.size + "}";
    }

    /**
     * check if a point is on the polygon
     *
     * @param pt the point to check
     * @return is the point on the polygon
     */
    private boolean isOn(GeoPoint pt, Ray ray) {
        Vector[] normals = new Vector[this.vertices.size()];//all the normals for the formula
        Vector[] polygonVectors = new Vector[this.vertices.size()];//all the vectors that we can build from the first vertex
        Point headPoint = ray.getP0();
        for (int i = 0; i < polygonVectors.length; i++) {//calculate the vectors
            polygonVectors[i] = this.vertices.get(i).subtract(headPoint);
        }
        for (int i = 0; i < normals.length - 1; i++) {//calculate the normals (we not normalizing because it not necessary)
            normals[i] = polygonVectors[i].crossProduct(polygonVectors[i + 1]);
        }
        //calculate the last normal
        normals[normals.length - 1] = polygonVectors[polygonVectors.length - 1].crossProduct(polygonVectors[0]);
        Vector pointVector = pt.point.subtract(headPoint);//calculating the vector from the first vertex to the given point
        boolean isPositive = pointVector.dotProduct(normals[0]) > 0;//checking the saign of the first product
        for (Vector normal : normals) {//check the product of all the normals with the point vector (calculating also the first one so we can check if it 0)
            if ((normal.dotProduct(pointVector) > 0 ^ isPositive) || alignZero(normal.dotProduct(pointVector)) == 0) {//to check if the products have different sign we can use XOR
                return false;
            }
        }
        return true;
    }

    /**
     * search for all intersection points within maxDistance from ray's head
     * @param ray
     * @param maxDistance
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        //there is two option, 1: the ray have intersection point with the plane, 2 the ray is on the plane
        //check option 1
        List<GeoPoint> ret = plane.findGeoIntersections(ray, maxDistance);
        if (ret == null) {//we must check if ret is null before we running on the list
            return null;
        }
        ret.removeIf(gPt -> !this.isOn(gPt, ray));//remove all points outside of the polygon
        return ret.size() == 0 ? null : List.of(new GeoPoint(this, ret.get(0).point));
    }
}
