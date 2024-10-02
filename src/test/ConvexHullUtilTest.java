package test;

import main.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.LinkedList;

/**
 * JUnit tests for the ConvexHull class.
 * Test determines if the methods within the ConvexHullUtil class work as
 * intended through assertions.
 */
public class ConvexHullUtilTest {

    /**
     * Test for findEastMost() and findWestMost() methods
     * Helps determine if method runs correctly by finding the largest ("east" most)
     * and smallest ("west" most) x values in a
     * hull.
     */
    @Test
    public void testHullXValues() {
        int eastMost, westMost;

        List<Point> hull = new LinkedList<>(List.of(
                new Point(0, 0),
                new Point(1, 1),
                new Point(3, 2),
                new Point(3, 3),
                new Point(-1, -1),
                new Point(-3, -2),
                new Point(-3, -3)));

        eastMost = ConvexHullUtil.findEastMost(hull);
        westMost = ConvexHullUtil.findWestMost(hull);

        // Test to find appropriate largest and smallest points
        assertEquals(3, eastMost, "'East' most point should be at index 3: (3, 3).");
        assertEquals(6, westMost, "'West' most point should be at index 6: (-3, -3).");

        hull.clear();

        // Test to see if IllegalArgumentException is thrown when the list is empty list
        assertThrows(IllegalArgumentException.class, () -> {
            ConvexHullUtil.findEastMost(hull);
        }, "Expected IllegalArgumentException for null list");

        // Test to see if IllegalArgumentException is thrown when there is no list
        // (null)
        assertThrows(IllegalArgumentException.class, () -> {
            ConvexHullUtil.findEastMost(null);
        }, "Expected IllegalArgumentException for null list");

    }

    /**
     * Test for checkCCW() method.
     * Helps determine if we are able to iterate through a set of hulls correctly.
     */
    @Test
    public void testCheckCCW() {
        int resultCCW, resultCW, resultCollinear;

        // Test CCW
        Point a = new Point(0, 0);
        Point b = new Point(1, 1);
        Point c = new Point(0, 2);
        resultCCW = ConvexHullUtil.checkCCW(a, b, c);

        // Test CW
        Point d = new Point(0, 0);
        Point e = new Point(1, 1);
        Point f = new Point(2, 0);
        resultCW = ConvexHullUtil.checkCCW(d, e, f);

        // Test Collinear
        Point g = new Point(0, 0);
        Point h = new Point(1, 1);
        Point i = new Point(2, 2);
        resultCollinear = ConvexHullUtil.checkCCW(g, h, i);

        assertAll("Testing test cases for checkCCW()",
                () -> assertTrue(resultCCW > 0, "Expected counterclockwise turn."),
                () -> assertTrue(resultCW < 0, "Expected clockwise turn."),
                () -> assertEquals(0, resultCollinear, "Expected collinear."));
    }
}
