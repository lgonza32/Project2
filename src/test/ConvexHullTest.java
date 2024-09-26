package test;

import main.ConvexHull;
import main.Point;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * JUnit tests for the ConvexHull class.
 * Test determines if the methods within the ConvexHull class work as intended
 * through assertions.
 */
public class ConvexHullTest {

    /**
     * Test for findEastMost() method
     * Helps determine if method runs correctly by finding the largest x value in a
     * hull.
     */
    @Test
    public void testFindEastMost() {
        int eastMost;

        List<Point> hull = List.of(
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(-1, -1),
                new Point(-2, -2),
                new Point(-3, -3));

        eastMost = ConvexHull.findEastMost(hull);
        assertEquals(3, eastMost, "'East' most point should be at index 3: (3, 3).");
    }

    /**
     * Test for findWestMost() method.
     * Helps determine if method runs correctly by finding the smallest x value in a
     * hull.
     */
    @Test
    public void testFindWestMost() {
        int result;

        List<Point> hull = List.of(
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(-1, -1),
                new Point(-2, -2),
                new Point(-3, -3));

        result = ConvexHull.findWestMost(hull);
        assertEquals(6, result, "West most point should be at index 6: (-3, -3).");
    }

    /**
     * Test for checkCCW() method.
     * Helps determine if we are able to iterate through a set of hulls
     */
    @Test
    public void testCheckCCW() {
        int resultCCW, resultCW, resultCollinear;

        // Test CCW
        Point a = new Point(0, 0);
        Point b = new Point(1, 1);
        Point c = new Point(0, 2);
        resultCCW = ConvexHull.checkCCW(a, b, c);

        // Test CW
        Point d = new Point(0, 0);
        Point e = new Point(1, 1);
        Point f = new Point(2, 0);
        resultCW = ConvexHull.checkCCW(d, e, f);

        // Test Collinear
        Point g = new Point(0, 0);
        Point h = new Point(1, 1);
        Point i = new Point(2, 2);
        resultCollinear = ConvexHull.checkCCW(g, h, i);

        assertAll("Testing test cases for checkCCW()",
                () -> assertTrue(resultCCW > 0, "Expected counterclockwise orientation."),
                () -> assertTrue(resultCW < 0, "Expected clockwise orientation."),
                () -> assertEquals(0, resultCollinear, "Expected collinear points."));
    }
}
