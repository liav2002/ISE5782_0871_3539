/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

package unittests.primitives;

import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Unit tests for primitives.Ray class
 *
 * @author1 Eyal
 * @authon2 Liav Ariel
 */
public class RayTest {
    @Test
    public void findClosestPoint() {

        //Test 1
        //Closest Point in middle of list

        LinkedList<Point> L = new LinkedList<Point>();
        Ray R1;

        R1 = new Ray(new Point(1, 0, 1), new Vector(1, 0, 0));
        L.clear();
        L.add(new Point(5, 0, 1));
        L.add(new Point(2, 0, 1));
        L.add(new Point(7, 0, 1));
        assertEquals("ERROR: findClosestPoint() does not work well.", new Point(2, 0, 1), R1.findClosestPoint(L));

        //Test 2
        //Empty List
        R1 = new Ray(new Point(1, 0, 1), new Vector(1, 0, 0));
        L.clear();
        Ray finalR = R1;
        assertEquals("ERROR: findClosestPoint() does not work well.", null, finalR.findClosestPoint(L));

        //Test 3
        //Closest Point is 1st in list
        R1 = new Ray(new Point(1, 0, 1), new Vector(1, 0, 0));
        L.clear();
        L.add(new Point(2, 0, 1));
        L.add(new Point(5, 0, 1));
        L.add(new Point(7, 0, 1));
        assertEquals("ERROR: findClosestPoint() does not work well.", new Point(2, 0, 1), R1.findClosestPoint(L));


        //Test 4
        //Closest Point is last in list
        R1 = new Ray(new Point(1, 0, 1), new Vector(1, 0, 0));
        L.clear();
        L.add(new Point(5, 0, 1));
        L.add(new Point(7, 0, 1));
        L.add(new Point(2, 0, 1));
        assertEquals("ERROR: findClosestPoint() does not work well.", new Point(2, 0, 1), R1.findClosestPoint(L));

    }
}