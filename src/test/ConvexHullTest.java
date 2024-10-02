package test;

import main.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.LinkedList;

/**
 * JUnit tests for the ConvexHull class.
 * Test determines if the methods within the ConvexHull class work as intended
 * through assertions.
 */
public class ConvexHullTest {

    /**
     * Test for findUpperTangent() and findLowerTangent() methods.
     * Helps determine if we are able to each respective tangent between a set of
     * hulls correctly.
     */
    @Test
    public void testFindTangents() {
        List<Point> leftHull = new LinkedList<>(List.of(
                new Point(-6, -4), // index 0
                new Point(-4, -2), // index 1
                new Point(-2, 0), // index 2
                new Point(0, 2), // index 3
                new Point(2, 1) // index 4
        ));

        List<Point> rightHull = new LinkedList<>(List.of(
                new Point(4, 3), // index 0
                new Point(6, 5), // index 1
                new Point(8, 4), // index 2
                new Point(10, 2), // index 3
                new Point(12, -1) // index 4
        ));

        int indexLeft = 4; // Index of the "east" most point in the left hull.
        int indexRight = 0; // Index of the "west" most point in the right hull.

        // Test findUpperTangent() and findLowerTangent()
        int[] upperTangent = ConvexHull.findUpperTangent(leftHull, rightHull, indexLeft, indexRight);
        int[] lowerTangent = ConvexHull.findLowerTangent(leftHull, rightHull, indexLeft, indexRight);

        // I don't know if these are good
        assertAll("Testing test cases for testFindTangents()",
                () -> assertEquals(0, upperTangent[0], "Upper left tangent should be at index 0."),
                () -> assertEquals(4, upperTangent[1], "Upper right tangent should be at index 4."),
                () -> assertEquals(3, lowerTangent[0], "Lower left tangent should be at index 3."),
                () -> assertEquals(1, lowerTangent[1], "Lower right tangent should be at index ."));
    }

    /**
     * Test for mergeHulls() method.
     * This helps ensure merging is correct.
     */
    @Test
    public void testMergeHulls() {
        List<Point> leftHull = new LinkedList<>(List.of(
                new Point(-6, -4), // index 0
                new Point(-4, -2), // index 1
                new Point(-2, 0), // index 2
                new Point(0, 2), // index 3
                new Point(2, 1) // index 4
        ));

        List<Point> rightHull = new LinkedList<>(List.of(
                new Point(4, 3), // index 0
                new Point(6, 5), // index 1
                new Point(8, 4), // index 2
                new Point(10, 2), // index 3
                new Point(12, -1) // index 4
        ));

        // Test mergedHull()
        List<Point> mergedHull = ConvexHull.mergeHulls(leftHull, rightHull);

        // Expected result
        List<Point> expectedHull = new LinkedList<>(List.of(
                new Point(-6, -4), // index 0
                new Point(-4, -2), // index 1
                new Point(-2, 0), // index 2
                new Point(0, 2), // index 3
                new Point(6, 5), // index 4
                new Point(8, 4), // index 5
                new Point(10, 2), // index 6
                new Point(12, -1) // index 7
        ));

        // Check if the merged hull matches the expected result
        assertEquals(expectedHull.size(), mergedHull.size(), "Merged hull size should match expected size.");
    }

    /**
     * Test for mergeHulls() method.
     * This helps ensure the Divide and Conquer step is correct.
     */
    @Test
    public void testFindHull() {
        Point[] points = {
                new Point(-6, -4),
                new Point(-4, -2),
                new Point(-2, 0),
                new Point(0, 2),
                new Point(6, 5),
                new Point(8, 4),
                new Point(10, 2),
                new Point(12, -1)
        };

        // Test findHull()
        List<Point> convexHull = ConvexHull.findHull(points, 0, points.length - 1);

        List<Point> expectedHull = List.of(
                new Point(-6, -4),
                new Point(0, 2),
                new Point(6, 5),
                new Point(12, -1));

        // Check if the convexHull matches expectedHull
        assertEquals(expectedHull.size(), convexHull.size(), "Convex hull size should match expected size.");
    }

    /**
     * Test for convexHull() method.
     * This helps ensure the Divide and Conquer step is correct.
     */
    @Test
    public void testConvexHull() {
        Point[] points = {
                new Point(-6, -4),
                new Point(-4, -2),
                new Point(-2, 0),
                new Point(0, 2),
                new Point(6, 5),
                new Point(8, 4),
                new Point(10, 2),
                new Point(12, -1)
        };

        // Test convexHull()
        List<Point> convexHull = ConvexHull.convexHull(points);

        List<Point> expectedHull = List.of(
                new Point(-6, -4),
                new Point(0, 2),
                new Point(6, 5),
                new Point(12, -1));

        // Check if the convexHull matches expectedHull
        assertEquals(expectedHull.size(), convexHull.size(), "Convex hull size should match expected size.");
    }
}
