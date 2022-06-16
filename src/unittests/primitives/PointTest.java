

package unittests.primitives;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;


import static org.junit.Assert.*;

/**
 * Unit tests for primitives.Point class
 * @author1 Eyal
 * @authon2 Liav Ariel
 */
class PointTest extends Object {

    @Test
    void add() {
        Point p = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: test point addition
        assertEquals("add() Point + Vector does not work correctly",
                Point.ZERO, p.add(new Vector(-1, -2, -3)));
    }

    @Test
    void subtract() {
        Point p = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: test point submission
        assertEquals("subtract() Point - Vector does not work correctly",
                new Vector(1, 1, 1), new Point(2, 3, 4).subtract(p));
    }

    @Test
    void length() {
        Point p = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: test point submission
        assertEquals("subtract() Point - Vector does not work correctly", 0, p.distance(p), 0.0001d);

    }

    @Test
    void lengthSquared() {
        Point p = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: test point submission
        assertEquals("subtract() Point - Vector does not work correctly", 0, p.distanceSquared(p), 0.0001d);
        Assertions.assertEquals(14, p.distanceSquared(Point.ZERO), "");

    }
}