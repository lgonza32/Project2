package test;

import main.ConvexHull;
import main.Point;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * JUnit tests for the Convex Hull class.
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
     * Test for findWestMost() method
     * Helps determine if method runs correctly by finding the smallest x value in a
     * hull.
     */
    @Test
    public void testFindWestMost() {
        int westMost;

        List<Point> hull = List.of(
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(-1, -1),
                new Point(-2, -2),
                new Point(-3, -3));

        westMost = ConvexHull.findWestMost(hull);
        assertEquals(6, westMost, "West most point should be at index 6: (-3, -3).");
    }

}
