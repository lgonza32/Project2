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

    /**
     * Test for findUpperTangent() and findLowerTangent() methods.
     */
    @Test
    public void testFindTangents() {
        List<Point> leftHull = List.of(
                new Point(-6, -4), // index 0
                new Point(-4, -2), // index 1
                new Point(-2, 0), // index 2
                new Point(0, 2), // index 3
                new Point(2, 1) // index 4
        );

        List<Point> rightHull = List.of(
                new Point(4, 3), // index 0
                new Point(6, 5), // index 1
                new Point(8, 4), // index 2
                new Point(10, 2), // index 3
                new Point(12, -1) // index 4
        );

        int indexLeft = 4; // Index of the "east" most point in the left hull.
        int indexRight = 0; // Index of the "west" most point in the right hull.

        // Test findUpperTangent() and findLowerTangent()
        int[] upperTangent = ConvexHull.findUpperTangent(leftHull, rightHull, indexLeft, indexRight);
        int[] lowerTangent = ConvexHull.findLowerTangent(leftHull, rightHull, indexLeft, indexRight);

        // I don't know if these are good enough
        assertAll("Testing test cases for checkCCW()",
                () -> assertEquals(0, upperTangent[0], "Upper left tangent should be at index 0."),
                () -> assertEquals(4, upperTangent[1], "Upper right tangent should be at index 4."),
                () -> assertEquals(3, lowerTangent[0], "Lower left tangent should be at index 3."),
                () -> assertEquals(1, lowerTangent[1], "Lower right tangent should be at index 1."));
    }

    /**
     * Test for mergeHulls() method.
     * This helps ensure merging is correct
     */
    @Test
    public void testMergeHulls() {
        List<Point> leftHull = List.of(
                new Point(-6, -4), // index 0
                new Point(-4, -2), // index 1
                new Point(-2, 0), // index 2
                new Point(0, 2), // index 3
                new Point(2, 1) // index 4
        );

        List<Point> rightHull = List.of(
                new Point(4, 3), // index 0
                new Point(6, 5), // index 1
                new Point(8, 4), // index 2
                new Point(10, 2), // index 3
                new Point(12, -1) // index 4
        );

        // Test mergedHull()
        List<Point> mergedHull = ConvexHull.mergeHulls(leftHull, rightHull);

        // Expected result
        List<Point> expectedHull = List.of(
                new Point(-6, -4), // index 0
                new Point(-4, -2), // index 1
                new Point(-2, 0), // index 2
                new Point(0, 2), // index 3
                new Point(6, 5), // index 4
                new Point(8, 4), // index 5
                new Point(10, 2), // index 6
                new Point(12, -1) // index 7
        );

        // Check if the merged hull matches the expected result
        assertEquals(expectedHull.size(), mergedHull.size(), "Merged hull size should match expected size.");

        for (int i = 0; i < expectedHull.size(); i++) {
            Point expectedPoint = expectedHull.get(i);
            Point actualPoint = mergedHull.get(i);
            assertEquals(expectedPoint.x, actualPoint.x, "x coords should match for point " + i);
            assertEquals(expectedPoint.y, actualPoint.y, "y coords should match for point " + i);
        }
    }
}
